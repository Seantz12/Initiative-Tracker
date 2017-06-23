/*
 * This is the Initiative Tracker, users input character names plus their int base and any modifiers,
 * and the inputs get automatically sorted
 *
 * Currently done features:
 * Ability to sort character inputs
 * Goes through next turns and keeps track of rounds
 *
 * Features to add:
 * A nice looking UI
 * Ability to remove dead characters
 * Ability to modify entries
 *
 */


package com.example.sean.initiativetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void newBattle(View view) {
        Intent intent = new Intent(view.getContext(), CreateTurnOrder.class);
        startActivity(intent);
    }

}
