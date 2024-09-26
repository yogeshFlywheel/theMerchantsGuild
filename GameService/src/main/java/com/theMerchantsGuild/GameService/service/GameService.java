package com.theMerchantsGuild.GameService.service;

import com.theMerchantsGuild.GameService.model.Player;

public interface GameService {

    String getQuestions(String stage, Long level);

    Player submitAnswer(String stage, Long level, Long playerId, String playerCode);

    Player submitQuery(String stage, Long level, Long playerId, String playerQuery);

    Player submitMcq(String stage, Long level, Long playerId, String playerMcq);

}
