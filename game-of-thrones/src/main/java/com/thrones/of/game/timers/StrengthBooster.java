package com.thrones.of.game.timers;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.util.Properties;
import java.util.TimerTask;

import static com.thrones.of.game.config.Constants.STENGTH_BOOST;

public class StrengthBooster extends TimerTask {

    @Override
    public void run() {
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
        Properties helpText = applicationConfiguration.getHelptextProperties();
        Session session = Session.getInstance();
        if(session.getCurrentGameOver() != null && !session.getCurrentGameOver()){
            synchronized (session){
                Integer strength = session.getEnemy().getStrength();
                session.getEnemy().setStrength(strength + 10);
                strength = session.getSelected().getStrength();
                session.getSelected().setStrength(strength + 10);
            }
            System.out.println(helpText.getProperty(STENGTH_BOOST));
        }
    }
}
