package com.example.bai2;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {
    private Calendar calendar = Calendar.getInstance();
    private int Hour, Minute;

    SharedPreferences mSharedPref;
    SharedPreferences.Editor mSharedPrefEditor;

    public interface TimePickerListener {
        void onTimeSetListener(Calendar calendar);
    }

    TimePickerListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (TimePickerListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + "TimePickerListener must be implemented!");
        }

        mSharedPref = context.getSharedPreferences(context.getClass().getSimpleName(), Activity.MODE_PRIVATE);
        mSharedPrefEditor = mSharedPref.edit();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance();
        sharedPrefManager.putSharedPref(context, mSharedPref);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Hour = mSharedPref.getInt("Hour", 12);
        Minute = mSharedPref.getInt("Minute", 0);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Hour = hourOfDay;
                Minute = minute;

                mSharedPrefEditor.putInt("Hour", Hour);
                mSharedPrefEditor.putInt("Minute", Minute);
                mSharedPrefEditor.commit();

                calendar.set(0, 0, 0, hourOfDay, minute);
                mListener.onTimeSetListener(calendar);
            }
        }, Hour, Minute, false);

        timePickerDialog.updateTime(Hour, Minute);

        return timePickerDialog;
    }
}


