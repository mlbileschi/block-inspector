package com.bileschi.blockinspector.parse;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class Indentation {

    public static Tree<String> parseText(String code) {
        return parseTextHelper(code.split("\n"), new Tree("code root"));
    }

    // Figure out a way to make this method prettier. What can we factor out?
    private static Tree<String> parseTextHelper(String[] codeLines, Tree<String> tree) {
        if(codeLines.length == 0) {
            return tree;
        }

        String curLine = codeLines[0];
        tree.addChild(codeLines[0]);

        if(codeLines.length == 1) {
            return tree;
        }

        // need to be able to handle newlines in here

        int numTabsTopLine = tabsAtFront(codeLines[0]);
        int numTabsSecondLine = tabsAtFront(codeLines[1]);

        if (numTabsSecondLine > numTabsTopLine) {
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), tree.findTreeNode(curLine));
        } else if (numTabsSecondLine == numTabsTopLine) {
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), tree);
        } else {
            int amountToUnindent = numTabsTopLine - numTabsSecondLine;
            Tree<String> newRoot = tree;
            for (int i = 0; i < amountToUnindent; i++) {
                newRoot = newRoot.parent;
            }
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), newRoot);
        }

        return tree;
    }

    private static int tabsAtFront(String s) {
        int count = 0;
        for (int i = 0; i < s.length() && s.charAt(i) == '\t'; i++) {
            count++;
        }
        return count;
    }
}
