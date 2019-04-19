package com.thrones.of.game.domain;

import java.io.Serializable;

public class Session implements Serializable {

    private String playerName;
    private Member selectedCharacter;
    private Member selectedEnemy;
    private Weapon selectedCharacterWeapons;
    private Weapon selectedEnemyWeapons;

    private static Session session;

    private Session() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Session getInstance() {
        return session = (null != session) ? session : new Session();
    }

    public Member getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Member selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public Member getSelectedEnemy() {
        return selectedEnemy;
    }

    public void setSelectedEnemy(Member selectedEnemy) {
        this.selectedEnemy = selectedEnemy;
    }

    public Weapon getSelectedCharacterWeapons() {
        return selectedCharacterWeapons;
    }

    public void setSelectedCharacterWeapons(Weapon selectedCharacterWeapons) {
        this.selectedCharacterWeapons = selectedCharacterWeapons;
    }

    public Weapon getSelectedEnemyWeapons() {
        return selectedEnemyWeapons;
    }

    public void setSelectedEnemyWeapons(Weapon selectedEnemyWeapons) {
        this.selectedEnemyWeapons = selectedEnemyWeapons;
    }
}
