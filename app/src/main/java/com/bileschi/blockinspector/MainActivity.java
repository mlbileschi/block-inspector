package com.bileschi.blockinspector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Indentation;
import com.bileschi.blockinspector.parse.Tree;


public class MainActivity extends ActionBarActivity implements ViewUpdater {
    CodeView codeView;
    ViewGroup linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTextView();
    }

    public void setTextView() {
        String s = "first block thing\n" +
                "\tinside block\n" +
                "end of first block";

        CodeView codeView = new CodeView(Indentation.parseText(s), this);
        View layout = codeView.render();

        final LinearLayout outer = (LinearLayout) findViewById(R.id.code_block);
        this.linearLayout = outer;
        this.codeView = codeView;
        outer.addView(layout);
        outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outer.postInvalidate();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setView(View view) {
        final LinearLayout outer = (LinearLayout) findViewById(R.id.code_block);

        outer.removeAllViews();
        outer.addView(view);
    }
}
