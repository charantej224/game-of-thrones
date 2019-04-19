package com.thrones.of.game.resolver;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.processor.Processor;

import java.util.Properties;

public class QueryResolver {

    Processor processor;

    public void resolveQuery(String inputQuery) throws Exception{
        inputQuery = inputQuery.toLowerCase();
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
        Properties patternProperties = applicationConfiguration.getPatternProperties();
        int i = 1;
        while(null != patternProperties.getProperty("pattern"+i)){
            String className = inputQuery.contains(patternProperties.getProperty("pattern"+i).toLowerCase())?
                    patternProperties.getProperty("pattern"+i+"_class") : null;
            if(null != className){
                processor = (Processor) Class.forName(className).newInstance();
                processor.performAction(inputQuery.replace(patternProperties.getProperty("pattern"+i).toLowerCase(),""));
                break;
            }
            i++;
        }
    }
}
