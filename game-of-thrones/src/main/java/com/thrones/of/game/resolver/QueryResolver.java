package com.thrones.of.game.resolver;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.processor.CommandHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : QueryResolver
 * this class is the one which helps with pattern matches and then picks the right type of processor based on the
 * commands executed in the console. it also validate if the user is at the right state in the context of game to execute
 * a particular command.
 */
public class QueryResolver {

    /**
     * Method : resolveQuery
     * @param inputQuery
     * it loops around the pattern properties to identify the pattern of the command and then execute
     * right processor methods by using reflection patterns.
     * it also validate if user can execute command in the context of game.
     * if the command is not understood then it will request you to train the game.
     */
    public void resolveQuery(String inputQuery) {
        try {
            String className = null;
            inputQuery = inputQuery.toLowerCase();
            ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();
            Properties patternProperties = applicationConfiguration.getPatternProperties();
            GameValidator gameValidator = new GameValidator();
            int i = 1;
            while (null != patternProperties.getProperty("pattern" + i)) {
                className = inputQuery.contains(patternProperties.getProperty("pattern" + i).toLowerCase())
                        || patternProperties.getProperty("pattern" + i).toLowerCase().contains(inputQuery) ?
                        patternProperties.getProperty("pattern" + i + "_class") : null;
                if (null != className) {
                    if (gameValidator.validatePlayerLevel("pattern" + i)) {
                        Object object = Class.forName(className).newInstance();
                        Method method = Class.forName(className).getMethod(patternProperties.getProperty("pattern" + i + "_method"), String.class);
                        method.invoke(object, inputQuery.replace(patternProperties.getProperty("pattern" + i).toLowerCase(), "").trim());
                        setSessionStage(patternProperties.getProperty("pattern" + i + "_exit_level"));
                    }
                    break;
                }
                i++;
            }
            if (null == className) {
                Properties helptext = applicationConfiguration.getHelptextProperties();
                System.out.println(BLUE + helptext.getProperty(TRAIN_ME));
                System.out.println(BLUE + helptext.getProperty(TRAIN_ME1));
            }
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException exception){
            throw new RuntimeException(exception.getMessage());
        }

    }

    /**
     * Method : setSessionStage
     * @param exitStage
     * This method is executed by the resolveQuery method to help to set the exit level when a particular command
     * is executed.
     * 
     */
    private void setSessionStage(String exitStage) {
        CommandHelper commandHelper = new CommandHelper();
        if ("0".equalsIgnoreCase(exitStage))
            return;
        if (Session.getInstance() != null && Session.getInstance().getUpdateStagePostCommand()) {
            Session.getInstance().setCurrentStage(Integer.parseInt(exitStage));
            commandHelper.canUseCommands(false);
        }
        if(!Session.getInstance().getUpdateStagePostCommand())
            Session.getInstance().setUpdateStagePostCommand(Boolean.TRUE);
    }
}
