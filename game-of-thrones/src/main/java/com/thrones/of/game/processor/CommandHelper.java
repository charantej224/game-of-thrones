package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : CommandHelper
 * helps players to understand commands and recommend commands based on his current level.
 */
public class CommandHelper {

    private Properties helptext = ApplicationConfiguration.getInstance().getHelptextProperties();
    private Properties patternProperties = ApplicationConfiguration.getInstance().getPatternProperties();

    /**
     * Method listCommands
     * @param commandList
     * commander helps to identify commands and recommend the commands he can use.
     */
    public void listCommands(String commandList) {
        if ("commands-all".equalsIgnoreCase(commandList)) {
            int i = 0;
            while (null != helptext.getProperty(COMMAND + i)) {
                System.out.println(YELLOW + helptext.getProperty(COMMAND + i));
                i++;
            }
        } else if ("helpme".equalsIgnoreCase(commandList)) {
            canUseCommands(false);
        } else {
            int i = 0;
            while (null != helptext.getProperty(COMMAND + i)) {
                if (helptext.getProperty(COMMAND + i).toLowerCase().contains(commandList)) {
                    System.out.println(YELLOW + helptext.getProperty(COMMAND + i));
                }
                i++;
            }
        }
    }

    /**
     * Method : canUseCommands
     * @param isStartups
     * based on the stage of the game, this method recommends that user what commands he could use.
     */
    public void canUseCommands(boolean isStartups) {
        Integer playerLevel = Session.getInstance().getCurrentStage();
        int i = 1;
        String commands = "";
        String nonGameCommands = "";
        while (null != patternProperties.getProperty(PATTERN + i)) {
            if (playerLevel == Integer.parseInt(patternProperties.getProperty(PATTERN + i + "_entry_level"))) {
                commands = commands + " -  " + patternProperties.getProperty(PATTERN + i);
            } else if (0 == Integer.parseInt(patternProperties.getProperty(PATTERN + i + "_entry_level"))) {
                nonGameCommands = nonGameCommands + " - " + patternProperties.getProperty(PATTERN + i);
            }
            i++;
        }
        if (isStartups) {
            System.out.println(CYAN + helptext.getProperty(NON_GAME_COMMANDS).replace("$$", nonGameCommands));
            System.out.println(CYAN + helptext.getProperty(BLANK_LINE));
            System.out.println(CYAN + helptext.getProperty(USE_COMMANDS).replace("$$", commands));
        } else {
            System.out.println(CYAN + helptext.getProperty(BLANK_LINE));
            System.out.println(CYAN + helptext.getProperty(NON_GAME_COMMANDS).replace("$$", nonGameCommands));
            System.out.println(CYAN + helptext.getProperty(BLANK_LINE));
            System.out.println(CYAN + helptext.getProperty(RECOMMEND_COMMANDS).replace("$$", commands));

        }

    }
}
