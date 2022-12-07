package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
        NetworkingService.NetworkingListener,
        DBManager.DataBaseListener {

    NetworkingService networkingService;
    User user;
    TextView nickname;
    TextView level;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService =((MyApp)getApplication()).networkingService;
        networkingService.listener = this;
        user = ((MyApp) getApplication()).user;
        nickname = findViewById(R.id.nickname);
        level = findViewById(R.id.level);
        save = findViewById(R.id.save);
        ((MyApp)getApplication()).dbManager.getDB(this);
        ((MyApp)getApplication()).dbManager.listener = this;
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
        save.setVisibility(View.VISIBLE);
    }

    @Override
    public void connectionIsDoneWithRank(String json) {

    }

    @Override
    public void connectionIsDoneWithMarketHistory(String json) {

    }

    @Override
    public void playerActionShotDownloadede(Bitmap img) {

    }

    public void saveOnClicked(View view) {
        showAlert(user);
    }

    void showAlert(User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.yousure)+ user.nickName + getString(R.string.db));
        builder.setNegativeButton(R.string.no,null);
        builder.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // save the city into db.
                ((MyApp)getApplication()).dbManager.insertNewUserAsync(user);
            }
        });
        builder.create().show();

    }
    @Override
    public void insertingUserCompleted() {
        finish();
    }

    @Override
    public void gettingAllUsersCompleted(User[] u) {

    }

    @Override
    public void deletingUserCompleted() {

    }
}