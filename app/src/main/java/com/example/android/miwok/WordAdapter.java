package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Word} objects.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param words is the list of {@link Word}s to be displayed.
     * @param colorResourceId is the resource ID for the background color for this list of words
     */
    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        final Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID english_word
        TextView defaultTranslationView = listItemView.findViewById(R.id.english_word);
        defaultTranslationView.setText(currentWord.getDefaultTranslationId());


        // Find the TextView in the list_item.xml layout with the ID miwok_word
        TextView miwokTranslationView = listItemView.findViewById(R.id.miwok_word);
        miwokTranslationView.setText(currentWord.getMiwokTranslationId());

        ImageView wordImageView = listItemView.findViewById(R.id.word_image);

        if(currentWord.hasImage()) {
            wordImageView.setImageResource(currentWord.getImageResourceId());
            wordImageView.setVisibility(View.VISIBLE);
        } else {
            wordImageView.setVisibility(View.GONE);
        }

        // Find the text container layout
        View textContainer = listItemView.findViewById(R.id.text_container);

        // Find the category color that the resource ID maps to
        int color = listItemView.getResources().getColor(mColorResourceId);

        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the list item layout w/ the two translations and the image for the word
        return listItemView;
    }
}
