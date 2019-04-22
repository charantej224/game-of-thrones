package com.thrones.of.game.timers;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.util.Properties;
import java.util.TimerTask;

import static com.thrones.of.game.config.Constants.STENGTH_BOOST;
import static com.thrones.of.game.config.Constants.YELLOW;

/**
 * Class : StrengthBooster
 * this an implementation of timertask which runs as a scheduled task
 * to boost the strength of the player and enemy as well. after getting exhausted post fight.
 */
public class StrengthBooster extends TimerTask {

    /**
     * Method : run
     * This is an implementation to boost strength of player and opponents.
     * it increases by 10 points.
     * However, it starts boosting only when fight has actually started. without fight starting
     * it cannot boost the strength as designed.
     */
    @Override
    public void run() {
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();
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
            System.out.println(YELLOW + helpText.getProperty(STENGTH_BOOST));
        }
    }
}
