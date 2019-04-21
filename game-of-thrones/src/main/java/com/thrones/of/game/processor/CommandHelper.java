package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class CommandHelper {

    Properties helptext = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
    Properties patternProperties = ApplicationConfiguration.getApplicationConfiguration().getPatternProperties();

    public void listCommands(String commandList){
        if("commands-all".equalsIgnoreCase(commandList)){
            int i = 0;
            while(null != helptext.getProperty(COMMAND+i)){
                System.out.println(YELLOW+helptext.getProperty(COMMAND+i));
                i++;
            }
        } else {
            int i = 0;
            while (null != helptext.getProperty(COMMAND+i)){
                if(helptext.getProperty(COMMAND+i).toLowerCase().contains(commandList)){
                    System.out.println(YELLOW+helptext.getProperty(COMMAND+i));
                }
                i++;
            }
        }
    }

    public void canUseCommands(){
        Integer playerLevel = Session.getInstance().getCurrentStage();
        int i =1;
        String commands = "";
        while (null != patternProperties.getProperty(PATTERN+i)){
            if(playerLevel == Integer.parseInt(patternProperties.getProperty(PATTERN+i+"_entry_level"))){
                commands = commands + " -  " + patternProperties.getProperty(PATTERN+i);
            }
            i++;
        }
        System.out.println(CYAN + helptext.getProperty(USE_COMMANDS).replace("$$",commands));
    }
}
