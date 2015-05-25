package com.bileschi.blockinspector.parse;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class Indentation {

    public static Tree<String> parseText(String code) {
        return parseTextHelper(code.split("\n"), new Tree("code root"));
    }

    // worry about spaces as tabs
    // worry about empty newlines
    private static Tree<String> parseTextHelper(String[] codeLines, Tree<String> tree) {
        if(codeLines.length == 0) {
            return tree;
        }

        String curLine = codeLines[0];
        tree.addChild(codeLines[0]);

        if(codeLines.length == 1) {
            return tree;
        }

        int numTabsTopLine = tabsAtFront(codeLines[0]);
        int numTabsSecondLine = tabsAtFront(codeLines[1]);

        // instead of a simple lt, gt here, we need to pop off the appropriate number of times (difference in tabs of non-1)
        if (numTabsSecondLine > numTabsTopLine) {
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), tree.findTreeNode(curLine));
        } else if (numTabsSecondLine < numTabsTopLine) {
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), tree.parent);
        } else {
            parseTextHelper(Arrays.copyOfRange(codeLines, 1, codeLines.length), tree);
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
