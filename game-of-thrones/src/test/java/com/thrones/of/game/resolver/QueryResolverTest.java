package com.thrones.of.game.resolver;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryResolverTest {

    private QueryResolver queryResolverUnderTest;

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
        queryResolverUnderTest = new QueryResolver();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Session.getInstance().clearSession();
    }
    @Test
    public void testResolveQuery_validInputs() {
        final String inputQuery = "register testuser";
        queryResolverUnderTest.resolveQuery(inputQuery);
        assertTrue(outContent.toString().contains("Lord testuser, you are now registered"));
        assertEquals(20,Session.getInstance().getCurrentStage().intValue());
    }

    @Test
    public void testResolveQuery_invalidInputs() {
        final String inputQuery = "register ";
        queryResolverUnderTest.resolveQuery(inputQuery);
        assertTrue(outContent.toString().contains("your profile name doesn't meet the requirements"));
        assertEquals(10,Session.getInstance().getCurrentStage().intValue());
    }

    @Test
    public void testResolveQuery_invalidCommand() {
        final String inputQuery = "not valid";
        queryResolverUnderTest.resolveQuery(inputQuery);
        assertTrue(outContent.toString().contains("I'm still learning, cannot comprehend your command"));
        assertEquals(10,Session.getInstance().getCurrentStage().intValue());
    }
}
