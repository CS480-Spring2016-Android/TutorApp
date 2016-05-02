package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import com.zaidi.cs480.spring.app.tutortabby.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class Profile extends AppCompatActivity {

    TextView t = null;

    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        t = (TextView)findViewById(R.id.detailView);
        t.setText("Downloading");
        try {
            URL url = new URL("http://romerosoftware.me:8080/movies/movie?%22title%22={%27title%27:{%27$regex%27:%27(?i)^The.*%27}}&keys={%27title%27:1}&sort_by=date");
            new DownloadWebpageTask().execute(url);
        }
        catch (Exception e){
            t.setText(e.toString());
        }

        // Added feature to change animation transition to changeable colors instead.
        // You can now specify which colors you want to transition to for your background.
        //background = findViewById(R.id.profile_layout);
        //animateBackground(R.color.colorPrimary, R.color.colorLoginBackgroundDark);
    }

    private void animateBackground(int idFrom, int idTo) {
        int colorFrom = ContextCompat.getColor(this, idFrom);
        int colorTo = ContextCompat.getColor(this, idTo);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(5000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                background.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private class DownloadWebpageTask extends AsyncTask<URL, Void, JSONObject> {
        String result = "";

        @Override
        protected JSONObject doInBackground(URL... urls) {
            InputStream is;
            // params comes from the execute() call: params[0] is the url.
            try {

                HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                is = conn.getInputStream();



                BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);

                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                is.close();

                return new JSONObject(sBuilder.toString());
            } catch (IOException e) {
                return null;
            }
            catch (Exception e){
                return null;
            }
        }

        protected void onPostExecute(JSONObject result) {
            t = (TextView) findViewById(R.id.detailView);
            try {
                t.setText("");
                result = (JSONObject) result.get("_embedded");
                JSONArray ja = (JSONArray) result.get("rh:doc");
                for(int i = 0; i < ja.length(); i++){
                    JSONObject tmp = ja.getJSONObject(i);
                    JSONObject jo = tmp.getJSONObject("_id");
                    t.append("id: " + jo.get("$oid")  + " | title:  " + tmp.get("title") + "\n\n");
                }
            }
            catch (Exception e){
                t.setText(e.toString());
            }
        }
    }

}
