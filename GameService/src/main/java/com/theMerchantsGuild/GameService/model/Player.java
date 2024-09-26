package com.theMerchantsGuild.GameService.model;

import lombok.Data;

@Data
public class Player {
    private Long id;
    private String username;
    private String password;
    private int exp;
    private int levelCleared;
    private String avatarImage;
}
