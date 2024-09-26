package com.theMerchantsGuild.GameService.repository;

import com.theMerchantsGuild.GameService.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByStageAndLevel(String stage, Long level);
}

