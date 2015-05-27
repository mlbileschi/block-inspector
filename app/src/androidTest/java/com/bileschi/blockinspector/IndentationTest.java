package com.bileschi.blockinspector;

import android.util.Pair;

import com.bileschi.blockinspector.parse.Indentation;
import com.bileschi.blockinspector.parse.Tree;

import junit.framework.TestCase;

public class IndentationTest extends TestCase {

    public void test_success_oneIndent() {
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

    public void test_success_onlyOneLine() {
        String testInput =
                "first block thing";

        Tree<String> expected = new Tree<>("code root");
        expected.addChild("first block thing");

        Tree<String> actual = Indentation.parseText(testInput);
        assertEquals(expected, actual);
    }

    public void test_success_newlineAtEnd() {
        String testInput =
                "line1\n";

        Tree<String> expected = new Tree<>("code root");
        expected.addChild("line1");

        Tree<String> actual = Indentation.parseText(testInput);
        assertEquals(expected, actual);
    }

    public void test_success_emptyLine() {
        String testInput =
                "line1\n" +
                "\n" +
                "line2";

        Tree<String> expected = new Tree<>("code root");
        expected.addChild("line1");
        expected.addChild("");
        expected.addChild("line2");

        Tree<String> actual = Indentation.parseText(testInput);
        assertEquals(expected, actual);
    }

    public void test_success_multipleUnindent() {
        String testInput =
                "first block thing\n" +
                "\tinside block\n" +
                "\t\tvery inside block\n" +
                "end of first block";

        Tree<String> expected = new Tree<>("code root");
        expected.addChild("first block thing");
        expected.findTreeNode("first block thing")
                .addChild("\tinside block");
        expected.findTreeNode("\tinside block")
                .addChild("\t\tvery inside block");
        expected.addChild("end of first block");

        Tree<String> actual = Indentation.parseText(testInput);
        assertEquals(expected, actual);
    }
}
