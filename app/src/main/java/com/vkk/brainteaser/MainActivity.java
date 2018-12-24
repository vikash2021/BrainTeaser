package com.vkk.brainteaser;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    Button startButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    MediaPlayer mplayer;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    RelativeLayout closeLayout;

    ArrayList<Integer> answers=new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score=0;
    int numberOfQuestions=0;



    public void playAgain(final View view)
    {
        score=0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        numberOfQuestions=0;
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();
        new CountDownTimer(30100,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score:"+Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

            }
        }.start();
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);




    }

    public void generateQuestion()
    {
        Random rand=new Random();
        int a=rand.nextInt(90);
        int b=rand.nextInt(90);
        int c=rand.nextInt(90);
        sumTextView.setText(Integer.toString(a)+"+"+Integer.toString(b)+"-"+Integer.toString(c)+"=");
        locationOfCorrectAnswer=rand.nextInt(4);
        answers.clear();
        int incorrectAnswer;
        for(int i=0;i<4;i++)
        {
            if(i==locationOfCorrectAnswer)
            {
                answers.add(a+b-c);
            }else
            {
                incorrectAnswer=rand.nextInt(200);
                while(incorrectAnswer==(a+b-c))
                {
                    incorrectAnswer=rand.nextInt(200);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view)
    {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
          score++;
          resultTextView.setText("Correct!");
        }
        else
        {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void START(View view)
    {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        mplayer = MediaPlayer.create(getApplicationContext(), R.raw.analog);
        mplayer.setLooping(true);
        mplayer.start();
    }


    public void CLOSE(View view)
    {
        finish();
        System.exit(0);
    }
    public void NOCLOSE(View view)
    {
        closeLayout.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton=(Button)findViewById(R.id.playAgainButton);
        gameRelativeLayout=(RelativeLayout)findViewById(R.id.gameRelativeLayout);



    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        mplayer.reset();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
