package com.thrones.of.game.processor;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntryProcessorTest {

    private EntryProcessor entryProcessorUnderTest;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeClass
    public static void initLoader() throws Exception{
        FileLoader.loadFiles();
        Session.getInstance().clearSession();
        Session.getInstance();
        Files.deleteIfExists(Paths.get("session.ser"));
    }

    @Before
    public void setUp() {
        entryProcessorUnderTest = new EntryProcessor();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1_testRegisterPlayer() {
        final String name = "test";
        entryProcessorUnderTest.registerPlayer(name);
        assertEquals(name,Session.getInstance().getPlayerProfile().getPlayerName());
        assertTrue(outContent.toString().contains("you are now registered"));
    }

    @Test
    public void test2_testDeregisterPlayer() {
        final String name = "test";
        entryProcessorUnderTest.deregisterPlayer(name);
        assertEquals(null,Session.getInstance().getPlayerProfile());
        assertTrue(outContent.toString().contains("test"));
    }

    @Test
    public void test3_testRegisterPlayer_badName() {
        final String name = "";
        entryProcessorUnderTest.registerPlayer(name);
        assertEquals(null,Session.getInstance().getPlayerProfile());
        assertTrue(outContent.toString().contains("your profile name doesn't meet the requirements"));
    }

    @Test
    public void test4_testStartNewGame() {
        final String name = "test";
        entryProcessorUnderTest.registerPlayer(name);
        entryProcessorUnderTest.startNewGame(name);
        assertTrue(outContent.toString().contains("starting new game..."));
    }

    @Test
    public void test5_testStartNewGame_noplayer() {
        final String name = "test";
        Session.getInstance().setPlayerProfile(null);
        entryProcessorUnderTest.startNewGame(name);
        assertTrue(outContent.toString().contains("you cannot start a new game without registering, Please register using"));
    }
}
