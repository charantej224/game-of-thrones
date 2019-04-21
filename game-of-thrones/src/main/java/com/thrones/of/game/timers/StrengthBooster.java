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
        if (session.getCurrentGameOver() != null && !session.getCurrentGameOver()) {
            synchronized (session) {
                Integer enemyStrength = session.getEnemy().getStrength();
                if (enemyStrength < 100)
                    session.getEnemy().setStrength(enemyStrength + 10);

                Integer playerStrength = session.getSelected().getStrength();
                if (playerStrength < 100)
                    session.getSelected().setStrength(playerStrength + 10);
            }
            System.out.println(helpText.getProperty(STENGTH_BOOST));
        }
    }
}
