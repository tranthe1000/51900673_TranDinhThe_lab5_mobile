package com.example.bai2;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        CompoundButton.OnCheckedChangeListener,
        EventAdapter.LongTouchListener {
    private Toolbar toolbar;
    Switch switch_button;
    //tran dinh the

    private RecyclerView recyclerView;
    private EventManager eventManager;
    private EventAdapter adapter;

    public static final String KEY_DATA_PASSING = "KEY_DATA_PASSING";
    public static final int REQUEST_CODE_PASSING = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);

        eventManager = EventManager.getInstance();

        adapter = new EventAdapter(eventManager.getAllEvents(), this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem mySwitch = menu.findItem(R.id.main_switchItem);
        MenuItemCompat.setActionView(mySwitch, R.layout.switch_layout);
        RelativeLayout switch_layout = (RelativeLayout) MenuItemCompat.getActionView(mySwitch);
        switch_button = (Switch) switch_layout.findViewById(R.id.switchForActionBar);
        switch_button.setOnCheckedChangeListener(this);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        for (Event e : eventManager.getAllEvents()) {
            e.setEnable(switch_button.isChecked());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_addItem) {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            intent.putExtra(KEY_DATA_PASSING, "trash");
            startActivityForResult(intent, REQUEST_CODE_PASSING);
        } else if (id == R.id.main_removeAllItem) {
            DialogConfirmRemoveAll();
        }
        if (id == R.id.main_aboutItem) {
            Toast.makeText(this, "About nothing!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void DialogConfirmRemoveAll() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog
                .setCancelable(false)
                .setTitle("Confirm")
                .setMessage("Are you sure to remove all events?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (eventManager.getCount() != -1) {
                            eventManager.removeAllEvents();
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_PASSING && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Event event = data.getParcelableExtra(FormActivity.KEY_DATA_RETURN);
                if (event != null) {
                    eventManager.addEvent(event);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onLongTouchListener(int index) {
        DialogSelect(index);
    }

    private void DialogSelect(int index) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog
                .setCancelable(false)
                .setTitle("Select")
                .setMessage("What do you want to do?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eventManager.removeEvent(index);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Chức năng đang hoàn thiện, vui lòng quay lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.create().show();
    }
}