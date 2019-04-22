package com.thrones.of.game.Validator;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.PlayerProfile;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.processor.CharacterSelector;
import com.thrones.of.game.processor.EntryProcessor;
import com.thrones.of.game.processor.FightModuleProcessor;
import com.thrones.of.game.utils.FileLoader;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameValidatorTest {

    private GameValidator gameValidatorUnderTest;
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
        gameValidatorUnderTest = new GameValidator();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Session.getInstance().setCurrentGameOver(Boolean.FALSE);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1_testCheckIfRegisteredUser_notRegistered() {
        gameValidatorUnderTest.checkIfRegisteredUser();
        assertTrue(outContent.toString().contains("Seems you are new to game, mind registering yourself"));
    }

    @Test
    public void test2_testCheckIfRegisteredUser_notRegistered() {
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName("test");
        Session.getInstance().setPlayerProfile(playerProfile);
        gameValidatorUnderTest.checkIfRegisteredUser();
        assertTrue(outContent.toString().contains("Welcome back Lord test, great to have you back."));
    }

    @Test
    public void test3_testValidatePlayerLevel_Success(){
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName("test");
        Session.getInstance().setPlayerProfile(playerProfile);
        boolean result = gameValidatorUnderTest.validatePlayerLevel("pattern5");
        assertTrue(result);
    }

    @Test
    public void test4_testValidatePlayerLevel_NotEnough(){
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName("test");
        Session.getInstance().setPlayerProfile(playerProfile);
        boolean result = gameValidatorUnderTest.validatePlayerLevel("pattern8");
        assertFalse(result);
        assertTrue(outContent.toString().contains("This command is not allowed at this stage"));
    }
}
