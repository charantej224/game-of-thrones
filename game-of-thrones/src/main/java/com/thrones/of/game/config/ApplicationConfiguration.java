package com.thrones.of.game.config;

import com.thrones.of.game.domain.HousesModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApplicationConfiguration {

    private static ApplicationConfiguration applicationConfiguration;

    private Properties gameProperties;

    private Map<String, HousesModel> houseMap = new HashMap<>();

    private ApplicationConfiguration() {

    }

    public static ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration = (null != applicationConfiguration) ? applicationConfiguration : new ApplicationConfiguration();
    }

    public Properties getGameProperties() {
        return gameProperties;
    }

    public void setGameProperties(Properties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public Map<String, HousesModel> getHouseMap() {
        return houseMap;
    }

    public void setHouseMap(Map<String, HousesModel> houseMap) {
        this.houseMap = houseMap;
    }
}
