package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.config.CONSTANTS;
import com.thrones.of.game.domain.HousesModel;

import java.util.Map;
import java.util.Optional;

import static com.thrones.of.game.config.CONSTANTS.BLUE;
import static com.thrones.of.game.config.CONSTANTS.BLUE_BACKGROUND;

public class HelpProcessor {

    ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();

    public void exploreHouse(String houseName) {
        Map<String, HousesModel> houseMap = applicationConfiguration.getHouseMap();

        if("all".equalsIgnoreCase(houseName)){
            houseMap.entrySet().forEach(entry -> {
                System.out.println(BLUE + entry.getValue().getHouseName() + " - " + entry.getValue().getTagLines());
            });
        } else {
            Optional<Map.Entry<String,HousesModel>> optional = houseMap.entrySet().stream()
                    .filter(entry ->
                            entry.getValue().getHouseName().equalsIgnoreCase(houseName) ||
                                    entry.getValue().getHouseName().toLowerCase().contains(houseName.toLowerCase())
                    ).findFirst();
            if(optional.isPresent()){
                System.out.println(BLUE + optional.get().getValue().toString());
            } else{
                System.out.println(BLUE + "No such house present, my load. Please try again");
            }
        }


    }
}
