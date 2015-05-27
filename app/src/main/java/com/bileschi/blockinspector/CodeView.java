package com.bileschi.blockinspector;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Tree;
import com.google.common.annotations.VisibleForTesting;

public class CodeView {

    final Tree<String> codeTree;
    final MainActivity context;

    public CodeView(Tree<String> codeTree, MainActivity context) {
        this.codeTree = codeTree;
        this.context = context;
    }


    public ViewGroup render() {
        ViewGroup toReturn = renderHelper(codeTree);
        if(codeTree.collapsed) {
            toReturn.setBackgroundColor(Color.BLACK);
        }
        return toReturn;
    }

    // consider consolidating logic here so it's not duplicated
    @VisibleForTesting
    ViewGroup renderHelper(Tree<String> codeSubTree) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView textView = new TextView(context);
        textView.setTextSize(40F);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateViewTask asyncTask = new UpdateViewTask(CodeView.this.context);
                asyncTask.execute(new Pair<>(CodeView.this, textView));
            }
        });

        textView.setText(codeSubTree.data);
        layout.addView(textView);

        for (Tree<String> child : codeSubTree.children) {
            if(child.collapsed) {
                View v = renderHelper(child);
                v.setBackgroundColor(Color.BLACK);
                layout.addView(v);
            } else {
                layout.addView(renderHelper(child));
            }
        }

        return layout;
    }
}
