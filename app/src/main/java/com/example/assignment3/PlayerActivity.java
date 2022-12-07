package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerActivity extends AppCompatActivity implements
        NetworkingService.NetworkingListener{

    NetworkingService networkingService;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        imageView = findViewById(R.id.image);

        if(getIntent().hasExtra("spid")) {
            Bundle bundle = getIntent().getExtras();
            String spid = bundle.getString("spid");
            networkingService=((MyApp)getApplication()).networkingService;
            networkingService.listener = this;
            networkingService.getPlayerImage(spid);
        }
    }

    @Override
    public void connectionISDoneWithResult(JSONObject json) throws JSONException {

    }

    @Override
    public void connectionIsDoneWithRank(String json) {

    }

    @Override
    public void connectionIsDoneWithMarketHistory(String json) {

    }

    @Override
    public void playerActionShotDownloadede(Bitmap img) {
        imageView.setImageBitmap(img);
    }
}