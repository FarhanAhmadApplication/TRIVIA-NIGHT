package com.farhan.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.farhan.trivia.util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private TextView questions;
    private TextView questiontag;
    private CardView cardView;
    private Button truebutton;
    private Button falsebutton;
    private TextView score;
    private TextView highestscore;
    private Prefs prefs;
    int i=0,j=0;
    RequestQueue queue;
    String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue= Volley.newRequestQueue(this);
        questions=findViewById(R.id.textView2);
        questiontag=findViewById(R.id.textView4);
        cardView=findViewById(R.id.cardView);
        truebutton=findViewById(R.id.button2);
        falsebutton=findViewById(R.id.button);
        score=findViewById(R.id.textView5);
        highestscore=findViewById(R.id.textView6);
        prefs=new Prefs(MainActivity.this);


        score.setText("Score: 0");

        i=prefs.getState();

        highestscore.setText("Highest Score:"+String.valueOf(prefs.getHghestScore()));
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(i==response.length())
                        i=0;
                    questiontag.setText((CharSequence) response.getJSONArray(i).get(0));
                    questions.setText("Questions:"+(i+1)+"/"+response.length());
                    truebutton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            try {
                                Boolean n= (Boolean) response.getJSONArray(i).get(1);

                                if (n) {
                                    i++;
                                    j+=10;
                                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                    questiontag.setTextColor(Color.GREEN);
                                    questiontag.setTextColor(Color.WHITE);
                                    questiontag.setText((CharSequence) response.getJSONArray(i).get(0));
                                    questions.setText("Questions:" + (i + 1) + "/" + response.length());
                                    update();


                                }
                                else {
                                    j-=5;
                                    i++;
                                    Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                                    questiontag.setTextColor(Color.RED);
                                    questiontag.setTextColor(Color.WHITE);
                                    questiontag.setText((CharSequence) response.getJSONArray(i).get(0));
                                    questions.setText("Questions:" + (i + 1) + "/" + response.length());
                                    // shakeAnimation();
                                    update();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    falsebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Boolean n = (Boolean) response.getJSONArray(i).get(1);
                                if (!n) {
                                    i++;j+=10;
                                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                    questiontag.setTextColor(Color.GREEN);
                                    questiontag.setTextColor(Color.WHITE);
                                    questiontag.setText((CharSequence) response.getJSONArray(i).get(0));
                                    questions.setText("Questions:" + (i + 1) + "/" + response.length());
                                    update();
                                }
                                else {
                                    j-=5;i++;
                                    Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                                    questiontag.setTextColor(Color.RED);
                                    questiontag.setTextColor(Color.WHITE);
                                    questiontag.setText((CharSequence) response.getJSONArray(i).get(0));
                                    questions.setText("Questions:" + (i + 1) + "/" + response.length());

                                    update();
                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
    }

    private void update() {
        if(j>0) {

            score.setText("Score: " + String.valueOf(j));

        }
        else {
            score.setText("Score: 0");

        }

    }


    /*public  void shakeAnimation()
        {
            Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
            cardView.setAnimation(shake);
            shake.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation)
                {

                        questiontag.setTextColor(Color.RED);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    questiontag.setTextColor(Color.WHITE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }*/

    @Override
    protected void onPause() {
        prefs.saveHighestScore(j);
        prefs.setState(i);
        //Log.d("Pause","OnPause:savingsore"+prefs.getHghestScore());
        super.onPause();
    }


}


