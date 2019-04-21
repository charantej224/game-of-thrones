package com.thrones.of.game.domain;

import com.thrones.of.game.utils.InputOutputHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {

    private PlayerProfile playerProfile;
    private Member selected;
    private Member enemy;
    private List<Weapon> selectedWeapons;
    private List<Weapon> enemyWeapons;
    private HousesModel selectedHouse;
    private HousesModel enemyHouse;
    private Boolean isCurrentGameOver;
    private Integer currentStage = 10;
    private Boolean updateStagePostCommand = Boolean.TRUE;

    private static Session session;

    private Session() {
    }

    public static Session getInstance() {
        if(session != null)
            return session;
        try {
            InputOutputHelper<Session> inputOutputHelper = new InputOutputHelper<>();
            session = inputOutputHelper.readFile("session.ser");
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
        if(session == null)
            return session = new Session();
        else
            return session;
    }

    public Boolean getUpdateStagePostCommand() {
        return updateStagePostCommand;
    }

    public void setUpdateStagePostCommand(Boolean updateStagePostCommand) {
        this.updateStagePostCommand = updateStagePostCommand;
    }

    public void clearSession() {
        session = new Session();
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public void setPlayerProfile(PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
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

    public Boolean getCurrentGameOver() {
        return isCurrentGameOver;
    }

    public void setCurrentGameOver(Boolean currentGameOver) {
        isCurrentGameOver = currentGameOver;
    }

    public Integer getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        this.currentStage = currentStage;
    }
}
