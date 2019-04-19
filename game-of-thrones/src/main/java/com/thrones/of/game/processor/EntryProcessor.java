package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;

import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.ENTRY_WELCOME;
import static com.thrones.of.game.config.CONSTANTS.GREEN;

public class EntryProcessor implements Processor {

    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
    @Override
    public void performAction(String action) {
        System.out.println(GREEN+helpProperties.getProperty(ENTRY_WELCOME));
    }
}
