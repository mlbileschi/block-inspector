package com.bileschi.blockinspector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bileschi.blockinspector.parse.Indentation;
import com.bileschi.blockinspector.parse.Tree;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTextView();
    }

    private void setTextView() {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.code_block);

        myAwesomeTextView.setText(
                "first block thing\n" +
                "\tinside block\n" +
                "end of first block");
    }

    public void setClickHandler(View v) {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.code_block);

        Tree<String> parsedText = Indentation.parseText(myAwesomeTextView.getText().toString());
        myAwesomeTextView.setText(Indentation.firstMemberOfBlock(parsedText));
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
}
