package com.thrones.of.game.utils;

import com.thrones.of.game.config.ApplicationConfiguration;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.INTRO;
import static com.thrones.of.game.config.Constants.PURPLE_BOLD;

public class MessagePrinter {

    public void printStartupMessages() {
        Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
        int i = 1;
        while (null != helpProperties.getProperty(INTRO + i)) {
            System.out.println(PURPLE_BOLD + helpProperties.getProperty(INTRO + i) + "\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception.getMessage());
            }
            i++;
        }
    }
}
