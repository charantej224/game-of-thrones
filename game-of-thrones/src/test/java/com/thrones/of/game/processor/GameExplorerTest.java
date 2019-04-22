package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.PlayerProfile;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class GameExplorerTest {

    private GameExplorer gameExplorerUnderTest;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ApplicationConfiguration applicationConfiguration;

    @BeforeClass
    public static void initLoader() throws Exception{
        FileLoader.loadFiles();
        Session.getInstance().clearSession();
        Session.getInstance();
        Files.deleteIfExists(Paths.get("session.ser"));
    }

    @Before
    public void setUp() {
        gameExplorerUnderTest = new GameExplorer();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        applicationConfiguration = ApplicationConfiguration.getInstance();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1_testExploreHouse() {
        final String houseName = "all";
        gameExplorerUnderTest.exploreHouse(houseName);
        assertTrue(outContent.toString().contains("Ours is the Fury"));
    }

    @Test
    public void test2_testExploreHouse() {
        final String houseName = "stark";
        gameExplorerUnderTest.exploreHouse(houseName);
        assertTrue(outContent.toString().contains("Arya"));
    }

    @Test
    public void test3_testExploreHouse() {
        final String houseName = "ababbcabca";
        gameExplorerUnderTest.exploreHouse(houseName);
        assertTrue(outContent.toString().contains("No such house present, my load"));
    }

    @Test
    public void test4_testPlayerProfile() {
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName("profileTest");
        Session.getInstance().setPlayerProfile(playerProfile);
        gameExplorerUnderTest.playerProfile("test");
        assertTrue(outContent.toString().contains("profileTest"));
    }

    @Test
    public void test5_testGetInfo() {
        Session session = Session.getInstance();
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName("profileTest");
        Session.getInstance().setPlayerProfile(playerProfile);
        session.setSelectedHouse(applicationConfiguration.getHouseMap().get("stark"));
        session.setEnemyHouse(applicationConfiguration.getHouseMap().get("targaryen"));
        session.setSelectedWeapons(applicationConfiguration.getHouseMap().get("stark").getWeapons());
        session.setEnemyWeapons(applicationConfiguration.getHouseMap().get("targaryen").getWeapons());
        session.setSelected(applicationConfiguration.getHouseMap().get("stark").getMembers().get(0));
        session.setEnemy(applicationConfiguration.getHouseMap().get("targaryen").getMembers().get(0));
        gameExplorerUnderTest.getInfo("input");
        assertTrue(outContent.toString().contains("Winter is coming, North Remembers"));
        gameExplorerUnderTest.getmyWeapons("profileTest");
        assertTrue(outContent.toString().contains("wildlings"));
    }

    @Test
    public void test6_testGetInfo_noSession(){
        Session.getInstance().setPlayerProfile(null);
        gameExplorerUnderTest.getInfo("input");
        assertTrue(outContent.toString().contains("Your session is not yet ready, please make required selections"));
    }


}
