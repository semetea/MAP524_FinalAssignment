package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchHistoryActivity extends AppCompatActivity implements
        NetworkingService.NetworkingListener,
        HistoriesRecyclerViewAdapter.HistoriesClickListener {
    HistoriesRecyclerViewAdapter adapter;
    RecyclerView rv;
    NetworkingService networkingService;
    ArrayList<History> purchases = new ArrayList<>(0);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);
        rv = findViewById(R.id.historiesList);
        adapter = new HistoriesRecyclerViewAdapter(purchases, this);
        adapter.listener = this;
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        if(getIntent().hasExtra("accessId")) {
            Bundle bundle = getIntent().getExtras();
            String accessId = bundle.getString("accessId");
            networkingService=((MyApp)getApplication()).networkingService;
            networkingService.listener = this;
            networkingService.getMarketHistory(accessId);
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
        purchases = JsonService.getMarketHistoryFromJsonString(json);
        adapter.list = purchases;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void playerActionShotDownloadede(Bitmap img) {

    }

    @Override
    public void onHistoryClicked(History selectedHistory) {
        Intent intent = new Intent(this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("spid", Integer.toString(selectedHistory.spid));
        intent.putExtras(bundle);

        startActivity(intent);
    }
}