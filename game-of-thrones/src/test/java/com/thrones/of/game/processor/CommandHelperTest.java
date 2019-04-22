package com.thrones.of.game.processor;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandHelperTest {

    private CommandHelper commandHelperUnderTest;
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
        commandHelperUnderTest = new CommandHelper();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1_testListCommands_allCommands() {
        final String commandList = "commands-all";
        commandHelperUnderTest.listCommands(commandList);
        assertTrue(outContent.toString().contains("REGISTER"));
        assertTrue(outContent.toString().contains("PLAYHOUSE"));
        commandHelperUnderTest.listCommands(commandList);
    }

    @Test
    public void test2_testListCommands_specificCommand(){
        final String commandList = "register";
        commandHelperUnderTest.listCommands(commandList);
        assertTrue(outContent.toString().contains("REGISTER <NAME>"));
    }

    @Test
    public void test3_testListCommands_helpmeCommand(){
        EntryProcessor entryProcessor = new EntryProcessor();
        entryProcessor.registerPlayer("test");
        commandHelperUnderTest.listCommands("helpme");
        assertTrue(outContent.toString().contains("REGISTER"));
    }

    @Test
    public void test4_testCanUseCommands_forstartup() {
        final boolean isStartups = true;
        commandHelperUnderTest.canUseCommands(isStartups);
        assertTrue(outContent.toString().contains("commands you can start with"));
    }

    @Test
    public void test5_testCanUseCommands_duringGameRun() {
        final boolean isStartups = false;
        commandHelperUnderTest.canUseCommands(isStartups);
        assertTrue(outContent.toString().contains("Hint: As you commander, I recommend to use commands to proceed further"));
    }
}
