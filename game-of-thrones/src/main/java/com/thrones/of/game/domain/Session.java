package com.thrones.of.game.domain;

import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {

    private String playerName;
    private Member selected;
    private Member enemy;
    private List<Weapon> selectedWeapons;
    private List<Weapon> enemyWeapons;
    private HousesModel selectedHouse;
    private HousesModel enemyHouse;


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

    public void clearSession() {
        session = new Session();
    }

    public Member getSelected() {
        return selected;
    }

    public void setSelected(Member selected) {
        this.selected = selected;
    }

    public Member getEnemy() {
        return enemy;
    }

    public void setEnemy(Member enemy) {
        this.enemy = enemy;
    }

    public List<Weapon> getSelectedWeapons() {
        return selectedWeapons;
    }

    public void setSelectedWeapons(List<Weapon> selectedWeapons) {
        this.selectedWeapons = selectedWeapons;
    }

    public List<Weapon> getEnemyWeapons() {
        return enemyWeapons;
    }

    public void setEnemyWeapons(List<Weapon> enemyWeapons) {
        this.enemyWeapons = enemyWeapons;
    }

    public HousesModel getSelectedHouse() {
        return selectedHouse;
    }

    public void setSelectedHouse(HousesModel selectedHouse) {
        this.selectedHouse = selectedHouse;
    }

    public HousesModel getEnemyHouse() {
        return enemyHouse;
    }

    public void setEnemyHouse(HousesModel enemyHouse) {
        this.enemyHouse = enemyHouse;
    }
}
