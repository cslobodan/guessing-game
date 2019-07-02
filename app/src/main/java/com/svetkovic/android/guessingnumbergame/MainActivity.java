package com.svetkovic.android.guessingnumbergame;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private int kontrola = 10;

    //********menja se pri promeni nivoa********
    private int minVal = 0;
    private int maxVal = 100;
    //********menja se pri promeni nivoa********

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    private int randomNumber = randomWithRange(minVal, maxVal);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, this.getResources().getString(R.string.app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void gameStart(View v){

        EditText guessField = findViewById(R.id.guess_field);
        TextView numberOfAttempts = findViewById(R.id.number_of_attempts);
        boolean hasWon = false;
        int guess;

        try {
            guess = (int) Integer.parseInt(guessField.getText().toString());
        }catch (Exception e){
            guess = 0;
        }


        String winMessage = this.getString(R.string.winMessage1) + " " +  (11-kontrola) + " " + this.getString(R.string.winMessage2) + ".";
        String overMessage = this.getString(R.string.endMessage) + " "+ randomNumber + ".";
        String higherMessage = this.getString(R.string.higherMessage) + " " + guess + ".";
        String lowerMessage= this.getString(R.string.lowerMessage) + " " + guess + ".";
        String invalidMessage = this.getString(R.string.invalidMessage) + "!";


        if(kontrola>0){

            //********menja se pri promeni nivoa********
            int minVal = 0;
            int maxVal = 100;
            //********menja se pri promeni nivoa********

            if(guess>maxVal || guess<minVal){
                Toast.makeText(this, invalidMessage, Toast.LENGTH_SHORT).show();
                guessField.getText().clear();
                guessField.requestFocus();
            }

            if((randomNumber < guess) && (guess<=maxVal && guess>=minVal) ){
                Toast.makeText(this, lowerMessage, Toast.LENGTH_SHORT).show();
                kontrola--;
                String kontrolniBroj = Integer.toString(kontrola);
                numberOfAttempts.setText(kontrolniBroj);
                guessField.getText().clear();
                guessField.requestFocus();
            }

            if((randomNumber > guess) && (guess<=maxVal && guess>=minVal) ){
                Toast.makeText(this, higherMessage, Toast.LENGTH_SHORT).show();
                kontrola--;
                String kontrolniBroj = Integer.toString(kontrola);
                numberOfAttempts.setText(kontrolniBroj);
                guessField.getText().clear();
                guessField.requestFocus();
            }

            if(randomNumber == guess){
                hasWon = true;
            }

        }

        if(hasWon){

            //pravim dialog box i setujem odgovarajući xml layout
            final Dialog dialog = new Dialog(this); // Context, this, etc.
            dialog.setContentView(R.layout.custom_dialog);

            //u castom dialogu pronalazim textualno polje i postavljam pobednički text
            TextView poruka = dialog.findViewById(R.id.dialog_info);
            poruka.setText(winMessage);

            //u castom dialgu pronalazim sliku i postavljam da bude happy face
            ImageView emoticon = dialog.findViewById(R.id.emoticon);
            emoticon.setImageResource(R.drawable.happy);

            //u castom dialogu pronalazim dugme i postavljam da piše "NEXT LEVEL"
            final Button resetDugme = dialog.findViewById(R.id.dugmeReset);
            resetDugme.setText(R.string.next_level);

            //pokazujem dialog korisniku
            dialog.show();

            //setujem da se na klik "NEXT LEVEL" gasi dialog i trenutna aktivnost, a pokreće se nova aktivnost tj level
            resetDugme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    //********menja se pri promeni nivoa********
                    Intent i = new Intent(MainActivity.this, Level2Activity.class);
                    //********menja se pri promeni nivoa********

                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            });
        }

        if(kontrola == 0) {

            //pravim dialog box i setujem odgovarajući xml layout
            final Dialog dialog = new Dialog(this); // Context, this, etc.
            dialog.setContentView(R.layout.custom_dialog);

            //u castom dialogu pronalazim textualno polje i postavljam gubitnički text
            TextView poruka = dialog.findViewById(R.id.dialog_info);
            poruka.setText(overMessage);

            //pokazujem dialog korisniku
            dialog.show();

            //u custom dialogu pronalazim dugme i postavljam klik listener kojim se sve resetuje na prvobitno stanje
            final Button resetDugme = dialog.findViewById(R.id.dugmeReset);
            resetDugme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    EditText guessField = findViewById(R.id.guess_field);
                    TextView numberOfAttempts = findViewById(R.id.number_of_attempts);

                    int minVal = 0;
                    int maxVal = 100;

                    randomNumber = randomWithRange(minVal, maxVal);
                    guessField.getText().clear();
                    guessField.requestFocus();
                    kontrola=10;
                    String kontrolniBroj = Integer.toString(kontrola);
                    numberOfAttempts.setText(kontrolniBroj);
                }
            });


            }

        }
}
