package com.example.bai2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {
    Calendar calendar = Calendar.getInstance();
    int Year, Month, Day;

    SharedPreferences mSharedPref;
    SharedPreferences.Editor mSharedPrefEditor;

    public interface DatePickerListener {
        void onDateSetListener(int year, int month, int dayOfMonth);
    }

    DatePickerListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (DatePickerListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + "DatePickerListener must be implemented!");
        }

        mSharedPref = context.getSharedPreferences(context.getClass().getSimpleName(), Activity.MODE_PRIVATE);
        mSharedPrefEditor = mSharedPref.edit();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance();
        sharedPrefManager.putSharedPref(context, mSharedPref);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Year = mSharedPref.getInt("Year", calendar.get(Calendar.YEAR));
        Month = mSharedPref.getInt("Month", calendar.get(Calendar.MONTH));
        Day = mSharedPref.getInt("Day", calendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Year = year;
                Month = month;
                Day = dayOfMonth;

                mSharedPrefEditor.putInt("Year", Year);
                mSharedPrefEditor.putInt("Month", Month);
                mSharedPrefEditor.putInt("Day", Day);
                mSharedPrefEditor.commit();

                mListener.onDateSetListener(Year, Month, Day);
            }
        }, Year, Month, Day);

        datePickerDialog.updateDate(Year, Month, Day);

        return datePickerDialog;
    }
}
