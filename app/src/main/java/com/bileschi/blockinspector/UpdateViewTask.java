package com.bileschi.blockinspector;

import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Tree;

public class UpdateViewTask extends AsyncTask<Pair<CodeView, String>, Void, View> {
    final ViewUpdater viewUpdater;

    public UpdateViewTask(ViewUpdater viewUpdater) {
        this.viewUpdater = viewUpdater;
    }

    @Override
    protected View doInBackground(Pair<CodeView, String>... params) {
        CodeView codeView = params[0].first;
        Tree<String> node = codeView.codeTree.findTreeNode(params[0].second);
        node.collapsed = !node.collapsed;

        return codeView.render();
    }

    @Override
    public void onPostExecute(View view) {
        viewUpdater.setView(view);
    }
}
