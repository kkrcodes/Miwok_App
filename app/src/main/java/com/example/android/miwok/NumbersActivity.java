package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        //Store list of words in ArrayList
        ArrayList<String> words = new ArrayList<>(
            Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"));
        Log.v("NumbersActivity", "The ArrayList contains " + words.size() + " words.");
        for(int i = 0; i < words.size(); i++)
            Log.v("NumbersActivity", "The word at index " + i + " is: " + words.get(i));
    }
}
