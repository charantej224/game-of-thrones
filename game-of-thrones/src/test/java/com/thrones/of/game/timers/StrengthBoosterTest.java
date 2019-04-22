package com.thrones.of.game.timers;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.processor.CharacterSelector;
import com.thrones.of.game.processor.EntryProcessor;
import com.thrones.of.game.resolver.QueryResolver;
import com.thrones.of.game.utils.FileLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class StrengthBoosterTest {

    private StrengthBooster strengthBoosterUnderTest;
    private CharacterSelector characterSelector;
    private EntryProcessor entryProcessor;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeClass
    public static void initLoader() throws Exception {
        FileLoader.loadFiles();
        Session.getInstance().clearSession();
        Session.getInstance();
        Files.deleteIfExists(Paths.get("session.ser"));
    }

    @Before
    public void setUp() {
        characterSelector = new CharacterSelector();
        strengthBoosterUnderTest = new StrengthBooster();
        entryProcessor = new EntryProcessor();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Session.getInstance().setCurrentGameOver(Boolean.FALSE);
        entryProcessor.registerPlayer("test");
        characterSelector.selectPlayerCharacterHouse("stark");
        characterSelector.selectCharacter("arya");
        characterSelector.selectEnemyCharacterHouse("targaryen");
        characterSelector.selectEnemyCharacter("daenerys");
    }
    @Test
    public void testRun() {
        int selectStrength = Session.getInstance().getSelected().getStrength();
        int enemyStrength = Session.getInstance().getEnemy().getStrength();
        strengthBoosterUnderTest.run();
        assertEquals(selectStrength+10,Session.getInstance().getSelected().getStrength().intValue());
        assertEquals(enemyStrength+10,Session.getInstance().getEnemy().getStrength().intValue());

    }
}
