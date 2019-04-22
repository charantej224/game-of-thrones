package com.thrones.of.game.domain;

import java.io.Serializable;

import static com.thrones.of.game.config.Constants.MAX_POINTS;
import static com.thrones.of.game.config.Constants.MIN_POINTS;
/**
 * Name : PlayerProfile
 * implements: Cloneable
 * Model required to deep clone to create an instance in the running session.
 * implements: Serializable
 * Model required to be written into file, hence, serialization is required.
 * Holds the player profile throughout.
 */
public class PlayerProfile implements Serializable, Cloneable {

    private String playerName;
    private Integer wins = 0;
    private Integer lost = 0;
    private Integer ties = 0;
    private String playerLevel = "BEGINNER";
    private Integer playerPoints = 0;

    public void updateWins() {
        ++wins;
        if (playerPoints < MAX_POINTS)
            ++playerPoints;
        this.playerLevel = PlayerLevel.getPlayerLevel(playerPoints);
    }

    public void updateLostGames() {
        ++lost;
        if (playerPoints > MIN_POINTS)
            --playerPoints;
        this.playerLevel = PlayerLevel.getPlayerLevel(playerPoints);
    }

    public void updateTies() {
        ++ties;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public String getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(String playerLevel) {
        this.playerLevel = playerLevel;
    }

    public Integer getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(Integer playerPoints) {
        this.playerPoints = playerPoints;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    @Override
    public PlayerProfile clone() {
        try {
            return (PlayerProfile) super.clone();
        } catch (CloneNotSupportedException cloneException) {
            throw new RuntimeException(cloneException.getMessage());
        }
    }
}
