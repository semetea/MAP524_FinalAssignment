package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class UserActivity extends AppCompatActivity implements DBManager.DataBaseListener {

    TextView nickname;
    TextView level;
    User user;
    ArrayList<User> users = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        nickname = findViewById(R.id.nickname);
        level = findViewById(R.id.level);
        user = ((MyApp)getApplication()).user;
        ((MyApp)getApplication()).dbManager.getDB(this);
        ((MyApp)getApplication()).dbManager.listener = this;

        if(getIntent().hasExtra("nickName")) {
            Bundle bundle = getIntent().getExtras();
            String accessId = bundle.getString("accessId");
            String nickName = bundle.getString("nickName");
            int b_level = bundle.getInt("level");
            user.setAccessId(accessId);
            user.setNickName(nickName);
            user.setLevel(b_level);
            nickname.setText(nickName);
            level.setText(Integer.toString(b_level));
        }
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
        Bundle bundle = new Bundle();
        bundle.putString("accessId", user.accessId);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void insertingUserCompleted() {

    }

    @Override
    public void gettingAllUsersCompleted(User[] u) {
        users = new ArrayList<>(Arrays.asList(u));
    }

    @Override
    public void deletingUserCompleted() {
        ((MyApp)getApplication()).dbManager.listener = this;
        ((MyApp)getApplication()).dbManager.getAllUsersAsync();
        finish();
    }
}