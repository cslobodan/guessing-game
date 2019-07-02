package com.svetkovic.android.guessingnumbergame;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

public class Level3Activity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private int kontrola = 10;

    //********menja se pri promeni nivoa********
    private int minVal = 0;
    private int maxVal = 500;
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

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        // My ID: ca-app-pub-9210810404570470~1259386528
        MobileAds.initialize(this, this.getResources().getString(R.string.app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.app_big_banner));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //********menja se pri promeni nivoa********
        ImageView breadcrumbs = findViewById(R.id.breadcrumbs);
        breadcrumbs.setImageResource(R.drawable.flow3);

        TextView initialText = findViewById(R.id.initial_text);
        initialText.setText(R.string.info_text_one_l3);
        //********menja se pri promeni nivoa********

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

        //********menja se pri promeni nivoa********
        String invalidMessage = this.getString(R.string.invalidMessage_l3) + "!";
        //********menja se pri promeni nivoa********

        if(kontrola>0){

            //********menja se pri promeni nivoa********
            int minVal = 0;
            int maxVal = 500;
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
                    Intent i = new Intent(Level3Activity.this, Level4Activity.class);
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

                    //********menja se pri promeni nivoa********
                    Intent i = new Intent(Level3Activity.this, MainActivity.class);
                    //********menja se pri promeni nivoa********

                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
            });
        }
    }
}