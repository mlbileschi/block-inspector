package com.bileschi.blockinspector;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Tree;
import com.google.common.annotations.VisibleForTesting;

public class CodeView {

    public static final float TEXT_SIZE = 40F;
    final Tree<String> codeTree;
    final MainActivity context;

    public CodeView(Tree<String> codeTree, MainActivity context) {
        this.codeTree = codeTree;
        this.context = context;
    }


    public View render() {
        if(codeTree.collapsed) {
            TextView t = new TextView(context);
            t.setText(codeTree.data);
            t.setTextSize(TEXT_SIZE);
            t.setOnClickListener(onClickListener(codeTree.data));
            t.setBackgroundColor(context.getResources().getColor(R.color.code_block));
            return t;
        } else {
            return renderHelper(codeTree);
        }
    }

    // consider consolidating logic here so it's not duplicated
    @VisibleForTesting
    ViewGroup renderHelper(Tree<String> codeSubTree) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView textView = new TextView(context);
        textView.setTextSize(TEXT_SIZE);

        textView.setOnClickListener(onClickListener(codeSubTree.data));

        textView.setText(codeSubTree.data);
        layout.addView(textView);

        for (Tree<String> child : codeSubTree.children) {
            if(child.collapsed) {
                TextView t = new TextView(context);
                t.setText(child.data);
                t.setTextSize(TEXT_SIZE);
                t.setOnClickListener(onClickListener(child.data));
                t.setBackgroundColor(context.getResources().getColor(R.color.code_block));
                layout.addView(t);
            } else {
                layout.addView(renderHelper(child));
            }
        }

        return layout;
    }

    View.OnClickListener onClickListener(final String s) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("in here", s);
                UpdateViewTask asyncTask = new UpdateViewTask(CodeView.this.context);
                asyncTask.execute(new Pair<>(CodeView.this, s));
            }
        };
    }
}
