package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NetworkingService.NetworkingListener {

    NetworkingService networkingService;
    User user;
    TextView nickname;
    TextView level;
    Button rank;
    Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService =((MyApp)getApplication()).networkingService;
        networkingService.listener = this;
        user = ((MyApp) getApplication()).user;
        nickname = findViewById(R.id.nickname);
        level = findViewById(R.id.level);
        rank = findViewById(R.id.rank);
        history = findViewById(R.id.history);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fifa_munu,menu);
        MenuItem searchViewmenue = menu.findItem(R.id.searchbar);

        SearchView searchView = (SearchView) searchViewmenue.getActionView();

        //String cityQuery = searchView.getQuery().toString();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                networkingService.getAccessId(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


        return true;
    }


    @Override
    public void connectionISDoneWithResult(JSONObject json) throws JSONException {
        user = JsonService.getUserFromJsonOBject(json);
        nickname.setText(user.nickName);
        level.setText("Level: " + user.level.toString());
        rank.setVisibility(View.VISIBLE);
        history.setVisibility(View.VISIBLE);
    }

    @Override
    public void connectionIsDoneWithRank(String json) {

    }

    public void onRankClicked(View view) {
        Intent intent = new Intent(this, RankActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("accessId", user.accessId);
        bundle.putString("nickName", user.nickName);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void onHistoryClicked(View view) {
        Intent intent = new Intent(this, MatchHistoryActivity.class);
        startActivity(intent);
    }
}