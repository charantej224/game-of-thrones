package com.thrones.of.game.utils;

import com.thrones.of.game.domain.Member;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InputOutputHelperTest {

    private InputOutputHelper<Member> inputOutputHelperUnderTest;

    @Before
    public void setUp() {
        inputOutputHelperUnderTest = new InputOutputHelper<>();
    }

    @Test
    public void test1_testWriteFile() throws Exception {
        String fileName = "member.ser";
        Member member = new Member();
        member.setStrength(100);
        member.setName("TestMember");
        inputOutputHelperUnderTest.writeFile(fileName,member);
        assertTrue(Files.exists(Paths.get("member.ser")));
        assertTrue(Files.deleteIfExists(Paths.get(fileName)));
    }

    @Test
    public void test2_testReadFile() throws Exception {
        String fileName = "member.ser";
        Member member = new Member();
        member.setStrength(100);
        member.setName("TestMember");
        inputOutputHelperUnderTest.writeFile(fileName,member);
        Member result = inputOutputHelperUnderTest.readFile(fileName);
        assertEquals(100,result.getStrength().intValue());
        assertEquals("TestMember",result.getName());
        assertTrue(Files.deleteIfExists(Paths.get(fileName)));

    }


}
