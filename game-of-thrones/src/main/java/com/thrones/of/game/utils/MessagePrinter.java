package com.thrones.of.game.utils;

import com.thrones.of.game.config.ApplicationConfiguration;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;


public class MessagePrinter {

    private Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();

    public void printStartupMessages() {

        printIntroductoryMessages();
        printCommandMessages();
    }

    private void printIntroductoryMessages() {
        int i = 1;
        while (null != helpProperties.getProperty(INTRO + i)) {
            System.out.println(PURPLE_BOLD + helpProperties.getProperty(INTRO + i) + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception.getMessage());
            }
            i++;
        }
    }

    private void printCommandMessages() {
        int i = 0;
        while (null != helpProperties.getProperty(COMMAND + i)) {
            System.out.println(YELLOW_BOLD + helpProperties.getProperty(COMMAND + i) + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception.getMessage());
            }
            i++;
        }
    }
}
