package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;
import com.thrones.of.game.utils.FileLoader;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FightModuleProcessorTest {

    private FightModuleProcessor fightModuleProcessorUnderTest;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ApplicationConfiguration applicationConfiguration;
    private CharacterSelector characterSelector;
    private EntryProcessor entryProcessor;

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
        entryProcessor = new EntryProcessor();
        fightModuleProcessorUnderTest = new FightModuleProcessor();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        applicationConfiguration = ApplicationConfiguration.getInstance();
        entryProcessor.registerPlayer("test");
        characterSelector.selectPlayerCharacterHouse("stark");
        characterSelector.selectEnemyCharacterHouse("targar");
        characterSelector.selectEnemyCharacter("daenerys");
        characterSelector.selectCharacter("arya");
        Session.getInstance().setCurrentGameOver(Boolean.FALSE);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test01_testFightEnemy_success() {
        fightModuleProcessorUnderTest.fightEnemy("wildlings");
        assertTrue(outContent.toString().contains("well done My Lord, your weapon"));
    }

    @Test
    public void test02_testFightEnemy1_success() {
        fightModuleProcessorUnderTest.fightEnemy("freefolks");
        assertTrue(outContent.toString().contains("well done My Lord, your weapon"));

    }

    @Test
    public void test03_testHandleComms_success() {
        String status = "status";
        final Weapon playerWeapon = applicationConfiguration.getHouseMap().get("stark").getWeapons().get(0);
        final Weapon enemyWeapon = applicationConfiguration.getHouseMap().get("targaryen").getWeapons().get(0);
        if(playerWeapon.getStrength().intValue() == enemyWeapon.getStrength().intValue())
            status = "EQUAL";
        else if(playerWeapon.getStrength().intValue() > enemyWeapon.getStrength().intValue())
            status = "MORE";
        else status = "LESS";
        fightModuleProcessorUnderTest.handleComms(playerWeapon, enemyWeapon, status);
        if("EQUAL".equalsIgnoreCase(status))
            assertTrue(outContent.toString().contains("well done"));
        else if("MORE".equalsIgnoreCase(status))
            assertTrue(outContent.toString().contains("you used has killed Enemy weapon"));
        else assertTrue(outContent.toString().contains("used has been killed by Enemy in a fierce battle"));
    }

    @Test
    public void test04_testCheckGameStatus_lostFightNoWeapons() {
        Session.getInstance().getEnemyWeapons().clear();
        Session.getInstance().getSelectedWeapons().clear();
        fightModuleProcessorUnderTest.checkGameStatus();
        assertTrue(outContent.toString().contains("You loose, but fight is not"));
    }

    @Test
    public void test05_testCheckGameStatus_winAsEnemyHasNoWeapons() {
        Session.getInstance().getSelected().setStrength(100);
        Session.getInstance().getEnemyWeapons().clear();
        Session.getInstance().getSelectedWeapons().clear();
        fightModuleProcessorUnderTest.checkGameStatus();
        assertTrue(outContent.toString().contains("You win as you smashed you enemies"));
    }

    @Test
    public void test06_testCheckGameStatus_tieasBothHaveSameState() {
        Session.getInstance().getSelected().setStrength(100);
        Session.getInstance().getEnemy().setStrength(100);
        Session.getInstance().getEnemyWeapons().clear();
        Session.getInstance().getSelectedWeapons().clear();
        fightModuleProcessorUnderTest.checkGameStatus();
        assertTrue(outContent.toString().contains("you and your enemy has showed equal prowess"));
    }

    @Test
    public void test07_testPrecheckSelectedWeapon() {
        Session.getInstance().getEnemy().setStrength(0);
        final Weapon selectedWeapon = Session.getInstance().getEnemyWeapons().get(0);
        final Boolean result = fightModuleProcessorUnderTest.precheckSelectedWeapon(selectedWeapon);
        assertFalse(result);
    }

    @Test
    public void test08_testPrecheckSelectedWeapon() {
        Session.getInstance().getEnemy().setStrength(100);
        final Weapon selectedWeapon = Session.getInstance().getEnemyWeapons().get(0);
        final Boolean result = fightModuleProcessorUnderTest.precheckSelectedWeapon(selectedWeapon);
        assertTrue(result);
    }

    @Test
    public void test09_testFightEnemy_battleFinished() {
        Session.getInstance().setCurrentGameOver(Boolean.TRUE);
        fightModuleProcessorUnderTest.fightEnemy("wildlings");
        assertTrue(outContent.toString().contains("the battle is already finished"));
    }

    @Test
    public void test10_testFightEnemy_noStrengthLeft() {
        Session.getInstance().getSelected().setStrength(0);
        fightModuleProcessorUnderTest.fightEnemy("wildlings");
        assertTrue(outContent.toString().contains("You lost game as you don't have enough strength"));
    }

    @Test
    public void test11_testFightEnemy_notEnoughStrengthForWeapon() {
        Session.getInstance().getSelected().setStrength(20);
        fightModuleProcessorUnderTest.fightEnemy("allies");
        assertTrue(outContent.toString().contains("My Lord, you don't have enough strength to play this weapon"));
    }

    @Test
    public void test12_testFightEnemy_notSuchWeapon() {
        fightModuleProcessorUnderTest.fightEnemy("dragons");
        assertTrue(outContent.toString().contains("there is no such weapon left in your inventory"));
    }

    @Test
    public void test13_testCheckGameStatus_winAsEnemyRanOutWeapons() {
        Session.getInstance().getEnemyWeapons().clear();
        fightModuleProcessorUnderTest.checkGameStatus();
        assertTrue(outContent.toString().contains("your enemy ran out of weapons to fight"));
    }

    @Test
    public void test14_testCheckGameStatus_lostAsNoWeaponsLeft() {
        Session.getInstance().getSelectedWeapons().clear();
        fightModuleProcessorUnderTest.checkGameStatus();
        assertTrue(outContent.toString().contains("your Inventory ran out of weapons to fight, you LOST"));
    }
}
