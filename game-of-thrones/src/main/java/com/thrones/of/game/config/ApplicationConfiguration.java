package com.thrones.of.game.config;

import com.thrones.of.game.domain.HousesModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Name : ApplicationConfiguration
 * this is a singleton instance of the application level configuration.
 * This copy is same across the application.
 * Members:
 *  - gameProperties
 *  - patternProperties
 *  - helptextProperties
 *  - houseMap
 */
public class ApplicationConfiguration {

    private static ApplicationConfiguration applicationConfiguration;

    private Properties gameProperties;
    private Properties patternProperties;
    private Properties helptextProperties;
    private Map<String, HousesModel> houseMap = new HashMap<>();

    private ApplicationConfiguration() {

    }

    public static ApplicationConfiguration getInstance() {
        return applicationConfiguration = (null != applicationConfiguration) ? applicationConfiguration : new ApplicationConfiguration();
    }

    public Properties getPatternProperties() {
        return patternProperties;
    }

    public void setPatternProperties(Properties patternProperties) {
        this.patternProperties = patternProperties;
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

    public Properties getHelptextProperties() {
        return helptextProperties;
    }

    public void setHelptextProperties(Properties helptextProperties) {
        this.helptextProperties = helptextProperties;
    }

}
