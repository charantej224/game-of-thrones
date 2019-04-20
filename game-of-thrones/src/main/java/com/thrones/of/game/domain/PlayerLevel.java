package com.thrones.of.game.domain;

public enum PlayerLevel {
    BEGINNER(10), INTERMEDIATE(20), ADVANCED(50), PROFESSIONAL(80);

    private int points;

    PlayerLevel(int points) {
        this.points = points;
    }

    public static String getPlayerLevel(int points) {
        if (points < BEGINNER.points)
            return BEGINNER.name();
        if (points < INTERMEDIATE.points)
            return INTERMEDIATE.name();
        if (points < ADVANCED.points)
            return ADVANCED.name();
        else
            return PROFESSIONAL.name();
    }
}
