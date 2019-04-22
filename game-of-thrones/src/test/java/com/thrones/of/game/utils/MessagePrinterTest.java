package com.thrones.of.game.utils;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.processor.CharacterSelector;
import com.thrones.of.game.processor.EntryProcessor;
import com.thrones.of.game.processor.FightModuleProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class MessagePrinterTest {

    private MessagePrinter messagePrinterUnderTest;
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
        messagePrinterUnderTest = new MessagePrinter();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void testPrintStartupMessages() {
        messagePrinterUnderTest.printStartupMessages();
        assertTrue(outContent.toString().contains("Welcome to Game of Thrones - A role playing command line program"));
        assertTrue(outContent.toString().contains("I'm your commander towards the game, you can ask me commands with"));
        assertTrue(outContent.toString().contains("Below are the commands you could use to play game"));
        assertTrue(outContent.toString().contains("REGISTER <NAME> - registers user and creates a profile"));
    }
}
