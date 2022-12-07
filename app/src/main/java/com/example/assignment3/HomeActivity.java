package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity
        implements DBManager.DataBaseListener,
        UsersRecyclerViewAdapter.UsersClickListener {
    UsersRecyclerViewAdapter adapter;
    RecyclerView rv;
    ArrayList<User> users = new ArrayList<>(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv = findViewById(R.id.usersList);
        adapter = new UsersRecyclerViewAdapter(users, this);
        adapter.listener = this;
        ((MyApp)getApplication()).dbManager.listener = this;
        ((MyApp)getApplication()).dbManager.getDB(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ((MyApp)getApplication()).dbManager.deleteUserAsync(users.get(viewHolder.getAdapterPosition()));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.listener = this;
        ((MyApp)getApplication()).dbManager.listener = this;
        ((MyApp)getApplication()).dbManager.getAllUsersAsync();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.addNewUser:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void insertingUserCompleted() {

    }

    @Override
    public void gettingAllUsersCompleted(User[] u) {
        users = new ArrayList<>(Arrays.asList(u));
        adapter.list = users;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deletingUserCompleted() {
        ((MyApp)getApplication()).dbManager.getAllUsersAsync();
    }

    @Override
    public void onUserClicked(User selectedUser) {
        Intent intent = new Intent(this, UserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("accessId", selectedUser.accessId);
        bundle.putString("nickName", selectedUser.nickName);
        bundle.putInt("level", selectedUser.level);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}