package com.example.sean.initiativetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddCharacters extends AppCompatActivity {

    /*private int numChar = 1;
    private SharedPreferences.Editor editor;

    private EditText mCharName;
    private EditText mDexScore;

    private String charName;
    private String dexScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_characters);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }

    public void addChar (View view) {
        mCharName = (EditText)findViewById(R.id.name);
        mDexScore = (EditText)findViewById(R.id.score);

        charName = mCharName.getText().toString();
        dexScore = mDexScore.getText().toString();

        editor.putString("char" + numChar, charName);
        editor.commit();

        numChar++;
    }

    public boolean duplicate(ArrayList check, Object name) {
        return (check.lastIndexOf(name) == -1);
    }*/

}
