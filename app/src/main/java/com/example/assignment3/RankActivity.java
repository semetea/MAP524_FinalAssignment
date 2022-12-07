package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RankActivity extends AppCompatActivity implements
        NetworkingService.NetworkingListener{

    NetworkingService networkingService;
    TextView rankName;
    TextView date;
    TextView userName;
    ImageView rankImage;
    String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        rankName = findViewById(R.id.rankName);
        date = findViewById(R.id.date);
        rankImage = findViewById(R.id.rankImage);
        userName = findViewById(R.id.userName);
        if(getIntent().hasExtra("accessId")) {
            Bundle bundle = getIntent().getExtras();
            String accessId = bundle.getString("accessId");
            nickName = bundle.getString("nickName");
            networkingService =((MyApp)getApplication()).networkingService;
            networkingService.listener = this;
            networkingService.getRank(accessId);
        }

    }

    @Override
    public void connectionISDoneWithResult(JSONObject json) throws JSONException {

    }

    @Override
    public void connectionIsDoneWithRank(String json) {
        userName.setText(nickName);
        Rank rank = JsonService.getdivisionFromJsonString(json);
        date.setText(getString(R.string.achievementDate) + rank.achievementDate.substring(0,9));
        if(rank.division == 800) {
            rankImage.setImageResource(R.drawable.superchamp);
            rankName.setText(R.string.superChamp);
        } else if(rank.division == 900) {
            rankImage.setImageResource(R.drawable.champions);
            rankName.setText(R.string.champions);
        } else if(rank.division == 1000) {
            rankImage.setImageResource(R.drawable.superchallenger);
            rankName.setText(R.string.superChanllenger);
        } else if(rank.division >= 1100 && rank.division <= 1300) {
            rankImage.setImageResource(R.drawable.challenger);
            rankName.setText(R.string.challenger);
        } else if(rank.division >= 2000 && rank.division <= 2200) {
            rankImage.setImageResource(R.drawable.worldclass);
            rankName.setText(R.string.wordclass);
        } else if(rank.division >= 2300 && rank.division <= 2500) {
            rankImage.setImageResource(R.drawable.pro);
            rankName.setText(R.string.pro);
        } else if(rank.division >= 2600 && rank.division <= 2800) {
            rankImage.setImageResource(R.drawable.semipro);
            rankName.setText(R.string.semipro);
        }

    }

    @Override
    public void connectionIsDoneWithMarketHistory(String json) {

    }

    @Override
    public void playerActionShotDownloadede(Bitmap img) {

    }
}