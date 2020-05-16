package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.media.audiofx.PresetReverb;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG ="Whack-A-Mole 2.0!";
    /* Hint
            - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
            - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
            - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
            - Feel free to modify the function to suit your program.
        */
    private Integer randomLocation;
    private Integer score=0;
    private TextView result;
    private List<Button> ButtonList2 = new ArrayList<>();
    private CountDownTimer myCountDown;


    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        myCountDown = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished)
            {
			    Long RemainingTime=millisUntilFinished/1000;
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
               Toast.makeText(getApplicationContext(),"Get Ready In "+RemainingTime.toString()+"seconds",Toast.LENGTH_SHORT).show();
        }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(),"GO!",Toast.LENGTH_SHORT).show();
                setNewMole();
                placeMoleTimer();
            }
        };
        myCountDown.start();


    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        myCountDown=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
                Long RemainingTime=l/1000;
                Log.v(TAG, "New Mole Location!");
                ResetMole();
                setNewMole();

            }

            @Override
            public void onFinish() {
                myCountDown.start();
            }
        };
        myCountDown.start();

    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent getscore = getIntent();
        score = getscore.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + score.toString());//get score from MainActivity & Display it
        result=findViewById(R.id.result);
        result.setText(score.toString());

        View.OnClickListener Listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button ButtonPress=(Button) view;
                doCheck(ButtonPress);

            }
        };
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button button=(Button) findViewById(id);
            button.setOnClickListener(Listener);
            ButtonList2.add(button);

        }
        readyTimer();
    }
    @Override
    protected void onStart(){
        super.onStart();

    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        switch (checkButton.getText().toString()) {
            case "0":
                Log.v(TAG, "Missed, score deducted!");
                score -= 1;
                result.setText(score.toString());
                ResetMole();
                setNewMole();
                myCountDown.cancel();
                placeMoleTimer();
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                score += 1;
                result.setText(score.toString());
                ResetMole();
                setNewMole();
                myCountDown.cancel();
                placeMoleTimer();
                break;
            default:
                Log.v(TAG, "Unknown button pressed.");
        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        randomLocation = ran.nextInt(9);
        ButtonList2.get(randomLocation).setText("*");
    }

    public void ResetMole()
    {
        ButtonList2.get(randomLocation).setText("0");
    }
}

