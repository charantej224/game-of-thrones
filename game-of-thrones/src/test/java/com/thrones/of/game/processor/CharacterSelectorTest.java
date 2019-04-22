package com.thrones.of.game.processor;

import com.thrones.of.game.domain.HousesModel;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CharacterSelectorTest {

    private CharacterSelector characterSelectorUnderTest;
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
        characterSelectorUnderTest = new CharacterSelector();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1_testSelectPlayerCharacterHouse() {
        final String name = "stark";
        characterSelectorUnderTest.selectPlayerCharacterHouse(name);
        assertTrue(outContent.toString().contains("Arya Stark"));
        assertTrue(outContent.toString().contains("John Snow"));
        assertTrue(outContent.toString().contains("Sansa Stark"));
    }

    @Test
    public void test2_testSelectEnemyCharacterHouse() {
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectEnemyCharacterHouse("targar");
        assertTrue(outContent.toString().contains("Viserys Targaryen"));
        assertTrue(outContent.toString().contains("Daenerys Targaryen"));
    }

    @Test
    public void test3_testSelectEnemyCharacter() {
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectEnemyCharacterHouse("targar");
        characterSelectorUnderTest.selectEnemyCharacter("daenerys");
        assertTrue(outContent.toString().contains("Get ready with your gear to bring them to your feet Lord"));
    }

    @Test
    public void test4_testSelectCharacter() {
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectCharacter("arya");
        assertTrue(outContent.toString().contains("My Lord, your role is selected"));
    }

    @Test
    public void test5_testSelectHouse() {
        final Optional<HousesModel> result = characterSelectorUnderTest.selectHouse("stark");
        assertTrue(result.isPresent());

        final Optional<HousesModel> result1 = characterSelectorUnderTest.selectHouse("abcdac");
        assertFalse(result1.isPresent());
    }

    @Test
    public void test6_testSelectPlayerCharacterHouse_notfound() {
        final String name = "abcabdabc";
        characterSelectorUnderTest.selectPlayerCharacterHouse(name);
        assertTrue(outContent.toString().contains("No such house found"));
    }

    @Test
    public void test7_testSelectEnemyHouse_notfound() {
        final String name = "abcabdabc";
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectEnemyCharacterHouse(name);
        assertTrue(outContent.toString().contains("No such house found"));
    }

    @Test
    public void test8_testSelectCharacter_notfound() {
        final String name = "abcabdabc";
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectCharacter(name);
        assertTrue(outContent.toString().contains("There seems to a mistake with selection"));
    }

    @Test
    public void test9_testEnemyCharacter_notfound() {
        final String name = "abcabdabc";
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectEnemyCharacterHouse("tyrell");
        characterSelectorUnderTest.selectEnemyCharacter(name);
        assertTrue(outContent.toString().contains("There seems to a mistake with selection"));
    }

    @Test
    public void test10_testEnemyHouse_samehouseAsPlayer() {
        final String name = "abcabdabc";
        characterSelectorUnderTest.selectPlayerCharacterHouse("stark");
        characterSelectorUnderTest.selectEnemyCharacterHouse("stark");
        assertTrue(outContent.toString().contains("As a Royal Lord, you are not supposed to back stab your own house"));
    }
}
