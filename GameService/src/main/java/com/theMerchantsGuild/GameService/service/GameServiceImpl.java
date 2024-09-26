package com.theMerchantsGuild.GameService.service;

import com.theMerchantsGuild.GameService.entity.Game;
import com.theMerchantsGuild.GameService.entity.ScreenData;
import com.theMerchantsGuild.GameService.external.PlayerService;
import com.theMerchantsGuild.GameService.model.Player;
import com.theMerchantsGuild.GameService.repository.GameRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameServiceImpl implements GameService {

    GameRepository gameRepository;
    PlayerService playerService;

    @PersistenceContext(unitName = "primaryEntityManagerFactory")
    private EntityManager gameEntityManager;
    @PersistenceContext(unitName = "secondaryEntityManagerFactory")
    private EntityManager screenDataEntityManager;

    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @Override
    public String getQuestions(String stage, Long level) {
        Game game = gameRepository.findByStageAndLevel(stage, level);
        return game.getQuestion();
    }

    @Override
    public Player submitAnswer(String stage, Long level, Long playerId, String playerCode) {
        return null;
    }

    @Override
    public Player submitQuery(String stage, Long level, Long playerId, String playerQuery) {
        Query query = screenDataEntityManager.createQuery(playerQuery);
        ScreenData screenData = (ScreenData) query.getResultList().getFirst();


    }

    @Override
    @Transactional(transactionManager = "primaryTransactionManager")
    public Player submitMcq(String stage, Long level, Long playerId, String playerMcq) {
        Game game = gameRepository.findByStageAndLevel(stage, level);
        Player player = playerService.getPlayer(playerId);
        boolean bool = playerMcq.equals(game.getAnswer());
        if(bool) {
            player.setExp(player.getExp() + 100);
            player.setLevelCleared(player.getLevelCleared() + 1);
        }
        else{
            int exp = player.getExp();
            exp -= 100;
            player.setExp(Math.max(exp, 0));
        }
        playerService.updatePlayer(playerId, player);
        return player;
    }
}
