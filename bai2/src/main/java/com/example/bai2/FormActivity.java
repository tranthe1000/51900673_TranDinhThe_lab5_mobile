package com.example.bai2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormActivity
        extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener,
        View.OnClickListener,
        SingleChoiceDialogFragment.SingleChoiceListener,
        DatePickerDialogFragment.DatePickerListener,
        TimePickerDialogFragment.TimePickerListener {

    private Toolbar toolbar;
    private EditText edtName;
    private EditText edtPlace;
    private EditText edtDate;
    private EditText edtTime;

    public static final String KEY_DATA_RETURN = "KEY_DATA_RETURN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initView();

        edtName.addTextChangedListener(new CustomTextWatcher(edtName));

        edtPlace.setOnClickListener(this);
        edtPlace.addTextChangedListener(new CustomTextWatcher(edtPlace));

        edtDate.setOnClickListener(this);
        edtDate.addTextChangedListener(new CustomTextWatcher(edtDate));

        edtTime.setOnClickListener(this);
        edtTime.addTextChangedListener(new CustomTextWatcher(edtTime));

        toolbar.setOnMenuItemClickListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtName = findViewById(R.id.edtName);
        edtPlace = findViewById(R.id.edtPlace);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.form_saveItem) {
            if (edtName.getText().toString().trim().length() == 0) {
                edtName.setError("Please enter event name!");
            }
            if (edtPlace.getText().toString().trim().length() == 0) {
                edtPlace.setError("Please choose place!");
            }
            if (edtDate.getText().toString().trim().length() == 0) {
                edtDate.setError("Please choose date!");
            }
            if (edtTime.getText().toString().trim().length() == 0) {
                edtTime.setError("Please choose time!");
            } else {
                Intent returnIntent = new Intent();
                //
                String title = edtName.getText().toString();
                String room = edtPlace.getText().toString();
                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                Event event = new Event(title, room, date, time);
                //
                returnIntent.putExtra(KEY_DATA_RETURN, event);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edtPlace) {
            DialogFragment singleChoiceDialogFragment = new SingleChoiceDialogFragment();
            singleChoiceDialogFragment.show(getSupportFragmentManager(), "Single Choice Dialog");
        } else if (view.getId() == R.id.edtDate) {
            DialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.show(getSupportFragmentManager(), "Date Picker Dialog");
        } else if (view.getId() == R.id.edtTime) {
            DialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
            timePickerDialogFragment.show(getSupportFragmentManager(), "Time Picker Dialog");
        }
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (edtName.getText().hashCode() == s.hashCode()) {
                if (edtName.getText().toString().trim().length() > 0) {
                    edtName.setError(null);
                }
            }
            if (edtPlace.getText().hashCode() == s.hashCode()) {
                if (edtPlace.getText().toString().trim().length() > 0) {
                    edtPlace.setError(null);
                }
            }
            if (edtDate.getText().hashCode() == s.hashCode()) {
                if (edtDate.getText().toString().trim().length() > 0) {
                    edtDate.setError(null);
                }
            }
            if (edtTime.getText().hashCode() == s.hashCode()) {
                if (edtTime.getText().toString().trim().length() > 0) {
                    edtTime.setError(null);
                }
            }
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list_places, int position) {
        edtPlace.setText(list_places[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
    }

    @Override
    public void onDateSetListener(int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        edtDate.setText(date);
    }

    @Override
    public void onTimeSetListener(Calendar calendar) {
        String time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(calendar.getTimeInMillis());
        edtTime.setText(time);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance();
        sharedPrefManager.removeAllSharedPref();
    }
}