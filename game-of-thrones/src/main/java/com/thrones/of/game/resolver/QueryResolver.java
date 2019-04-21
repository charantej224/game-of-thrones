package com.thrones.of.game.resolver;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.lang.reflect.Method;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class QueryResolver {

    public void resolveQuery(String inputQuery) throws Exception {
        String className = null;
        inputQuery = inputQuery.toLowerCase();
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
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
    }

    private void setSessionStage(String exitStage) {
        if ("0".equalsIgnoreCase(exitStage))
            return;
        if (Session.getInstance() != null) {
            Session.getInstance().setCurrentStage(Integer.parseInt(exitStage));
        }
    }
}
