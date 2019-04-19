package com.thrones.of.game.resolver;

import com.thrones.of.game.config.ApplicationConfiguration;

import java.lang.reflect.Method;
import java.util.Properties;

public class QueryResolver {

    public void resolveQuery(String inputQuery) throws Exception {
        inputQuery = inputQuery.toLowerCase();
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
        Properties patternProperties = applicationConfiguration.getPatternProperties();
        int i = 1;
        while (null != patternProperties.getProperty("pattern" + i)) {
            String className = inputQuery.contains(patternProperties.getProperty("pattern" + i).toLowerCase())
                    || patternProperties.getProperty("pattern" + i).toLowerCase().contains(inputQuery) ?
                    patternProperties.getProperty("pattern" + i + "_class") : null;
            if (null != className) {
                Object object = Class.forName(className).newInstance();
                Method method = Class.forName(className).getMethod(patternProperties.getProperty("pattern" + i+"_method"),String.class);
                method.invoke(object,inputQuery.replace(patternProperties.getProperty("pattern" + i).toLowerCase(), "").trim());
                break;
            }
            i++;
        }
    }
}
