package com.theMerchantsGuild.GameService.repository;

import com.theMerchantsGuild.GameService.entity.ScreenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameExternalRepository extends JpaRepository<ScreenData, Long> {
}
