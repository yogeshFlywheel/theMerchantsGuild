package com.theMerchantsGuild.GameService.external;

import com.theMerchantsGuild.GameService.model.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PLAYER-SERVICE")
@Service
public interface PlayerService {

    @GetMapping("/player/{id}")
    Player getPlayer(@PathVariable("id") long playerId);

    @PutMapping("/player/{id}")
    Player updatePlayer(@PathVariable("id") long playerId, @RequestBody Player player);

}
