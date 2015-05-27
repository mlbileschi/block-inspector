package com.bileschi.blockinspector;

import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Tree;

public class UpdateViewTask extends AsyncTask<Pair<CodeView, TextView>, Void, View> {
    final ViewUpdater viewUpdater;

    public UpdateViewTask(ViewUpdater viewUpdater) {
        this.viewUpdater = viewUpdater;
    }

    @Override
    protected View doInBackground(Pair<CodeView, TextView>... params) {
        CodeView codeView = params[0].first;
        TextView textView = params[0].second;
        Tree<String> node = codeView.codeTree.findTreeNode(textView.getText().toString());
        node.collapsed = !node.collapsed;

        return codeView.render();
    }

    @Override
    public void onPostExecute(View view) {
        viewUpdater.setView(view);
    }
}
