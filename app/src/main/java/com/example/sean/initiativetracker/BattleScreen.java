package com.example.sean.initiativetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class BattleScreen extends AppCompatActivity {

    private int roundNum = 1;
    private int turnNum = 0;

    private Typeface face;

    // ok, long story short, abilities = actual ability information,
    // abilityMod = the buttons, abilityList is the layout
    private ArrayList<Ability> abilities = new ArrayList<>();
    private ArrayList<Button> abilityMod = new ArrayList<>();
    private ArrayList<CharacterInfo> characters = new ArrayList<>();
    private ArrayList<Button> characterMod = new ArrayList<>();

    TextView roundCount;

    ScrollView characterScroll;

    LinearLayout abilityList;
    LinearLayout characterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_screen);

        face = Typeface.createFromAsset(getAssets(), "fonts/TrajanPro-Regular.ttf");

        roundCount = (TextView)findViewById(R.id.roundCount);
        abilityList = (LinearLayout)findViewById(R.id.abilityLayout);
        characterList = (LinearLayout)findViewById(R.id.characterLayout);
        characterScroll = (ScrollView)findViewById(R.id.characterScroll);

        roundCount.setTypeface(face);

        roundCount.setTextSize(24);

        //Gets the sorted array of names
        Bundle bundle = getIntent().getExtras();
        characters = bundle.getParcelableArrayList("characterList");

        //Sets up all the text fields
        roundCount.setText("Round " + roundNum);

        displayAbilities();
        displayCharacters();
    }

    public void nextTurn(View view) {

        //Increases the turn number int, and if all characters have gone, resets the turn thing
        turnNum++;

        if(turnNum == characters.size()) {
            roundNum++;
            turnNum = 0;

            //Checks to see if any abilities have expired this turn
            for(int i = 0; i < abilities.size(); i++) {
                if(abilities.get(i).getDuration()-1 == 0) {
                    AlertDialog.Builder display = new AlertDialog.Builder(this);
                    display.setTitle("Timer ended");
                    display.setMessage("Ability: " + abilities.get(i).getAbilityName() + " has ended.");
                    display.show();

                    // Essentially removes the ability that expired from existence
                    abilities.remove(i);
                    abilityList.removeView(abilityMod.get(i));
                    abilityMod.remove(i);
                    i--;
                } else {
                    abilities.get(i).setDuration(abilities.get(i).getDuration()-1);
                }
            }

        }

        roundCount.setText("Round " + roundNum);
        shift();
    }

    public void addAbility(View view) {
        //Essentailly, creates some text fields for the user and puts in a message
        //Issue, looks awful
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText abilityName = new EditText(this);
        final EditText abilityDuration = new EditText(this);
        /*final ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(750,150);

        abilityName.setLayoutParams(param);
        abilityDuration.setLayoutParams(param);*/
        abilityDuration.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setMessage("Please enter the Ability name and duration in rounds (one round is 6 seconds)");
        alert.setTitle("Add Timer");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(abilityName);
        layout.addView(abilityDuration);

        alert.setView(layout);


        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Ability ability = new Ability(abilityName.getText().toString(), Integer.parseInt(abilityDuration.getText().toString()));
                // Adds ability to layout and displays it
                abilities.add(ability);
                addAbilityButton(ability);
                displayAbilities();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    public void addAbilityButton(Ability ability) {
        /*
         * Creates the button that will eventually be displayed on the list
         * Ok, I'm a dirty cheater and this basically lets me pass an instance of the ability
         * to the editor, there's probably a better way to do this but eh. It works.
         */
        final Ability a = ability;

        Button button = new Button(this);
        button.setText(ability.getAbilityName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAbility(a);
            }
        });

        abilityMod.add(button);
    }

    public void addCharacterButton(CharacterInfo character) {

        // Creates the button that will eventually be displayed on the list

        final CharacterInfo a = character;
        Button button = new Button(this);
        button.setText(character.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCharacter(a);
            }
        });

        characterMod.add(button);
    }

    public void editAbility(Ability ability) {
        final Ability a = ability;

        AlertDialog.Builder editAbility = new AlertDialog.Builder(this);

        editAbility.setMessage("Rounds remaining: " + a.getDuration() + "\nDo you want to cancel the ability?");

        editAbility.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abilityMod.remove(abilities.indexOf(a));
                abilities.remove(a);
                displayAbilities();
            }
        });

        editAbility.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        editAbility.show();
    }

    public void editCharacter(CharacterInfo character) {
        // Similar to ModifyCharacter, but only prompts user on whether or not to delete character from turn order.
        // Temporary value created to imitate character

        final CharacterInfo c = character;

        AlertDialog.Builder editCharacter = new AlertDialog.Builder(this);

        editCharacter.setMessage("Delete Character? ");

        editCharacter.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(characters.size() > 1) {
                    characterMod.remove(characters.indexOf(c));
                    characters.remove(c);
                    displayCharacters();
                } else {
                    AlertDialog duplicateError = new AlertDialog.Builder(BattleScreen.this).create();
                    duplicateError.setTitle("Error!");
                    duplicateError.setMessage("You cannot delete the last character in the initiative order!");
                    duplicateError.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    duplicateError.show();
                }
            }
        });

        editCharacter.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        editCharacter.show();
    }

    public void reset(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to restart?");

        final View v = view;

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(v.getContext(), MainScreen.class);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();


    }

    public void shift() {
        characterList.removeAllViews();

        for (int i = 0; i < characterMod.size()-1; i++) {
            // Rearranges the array of characterMod so that first is the next person and so on
            Button temp = characterMod.get(i);
            characterMod.set(i, characterMod.get(i+1));
            characterMod.set(i+1, temp);
            // Starts fixing the order
            System.out.println(i);
            characterList.addView(characterMod.get(i));
        }
        characterList.addView(characterMod.get(characterMod.size()-1));
        // Adds the last view since code above won't account for it
    }

    public void displayAbilities() {
        // Essentially "clears" the state first and then redisplays everything
        if(abilityList != null) abilityList.removeAllViews();
        for (int i = 0; i < abilityMod.size(); i++) {
            abilityList.addView(abilityMod.get(i));
        }
    }

    public void displayCharacters() {
        // Same thing as abilities, just with all the characters instead
        characterList.removeAllViews();
        characterScroll.removeAllViews();
        // This line saves the size of the array before it changes,
        // Lets the code know if it's been touched before
        int size = characterMod.size();
        for (int i = 0; i < characters.size(); i++) {
            /* Ok here's the issue
             * There's a parent view that's not removing the child's view, and it's trying to adjust
             * it. So I need to remove the child view. Welllll I figured it was the button but that
             * doesn't seem to be it (as I literally remove all views before executing these lines
             * of code)
             * So my conclusion is that the issue is with the scroll view parenting the linear view
             * And that since the linear view is being changed, it needs to be removed before anything
             * else
             */
            if(size < 1) addCharacterButton(characters.get(i));
            characterList.addView(characterMod.get(i));
        }
        characterScroll.addView(characterList);
    }

}
