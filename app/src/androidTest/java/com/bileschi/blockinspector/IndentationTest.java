package com.bileschi.blockinspector;

import android.util.Pair;

import com.bileschi.blockinspector.parse.Indentation;
import com.bileschi.blockinspector.parse.Tree;

import junit.framework.TestCase;

public class IndentationTest extends TestCase {

    //think about pair strings
    public void testSomething() {
        String testInput =
                "first block thing\n" +
                "\tinside block\n" +
                "end of first block";

        Tree<String> expected = new Tree<>("code root");
        expected.addChild("first block thing");
        expected.findTreeNode("first block thing").addChild("\tinside block");
        expected.addChild("end of first block");

        Tree<String> actual = Indentation.parseText(testInput);
        assertEquals(expected, actual);
    }
}
