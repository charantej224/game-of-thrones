package com.thrones.of.game.processor;

public class ExitProcessor implements Processor {

    @Override
    public void performAction(String action) {
        System.exit(0);
    }
}
