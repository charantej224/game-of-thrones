package com.thrones.of.game.utils;

import com.thrones.of.game.config.ApplicationConfiguration;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : MessagePrinter
 * this class helps to print the startup message and the command level messages.
 */
public class MessagePrinter {

    private Properties helpProperties = ApplicationConfiguration.getInstance().getHelptextProperties();

    /**
     * Method : printStartupMessages
     * this helps to print the startup messages to help player with the information required to understand the game.
     */
    public void printStartupMessages() {

        printIntroductoryMessages();
        printCommandMessages();
    }

    /**
     * Method : printIntroductoryMessages
     * this helps to print introductory message for the user to understand the context of the game.
     */
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

    /**
     * Method : printCommandMessages.
     * This helps to print command level message at the startup for the user to understand what commands are possible
     * to play the game.
     */
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
