package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
import static android.media.AudioManager.STREAM_MUSIC;

public class NumbersActivity extends AppCompatActivity {

    // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;

    //Handles audio focus for the sounds
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // Pause the sound and play it from the beginning if we lose audio focus temporarily
            if(focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
                // Resume playback if we have the focus now
            } else if(focusChange == AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
                // Stop playback and release resources if we've lost audio focus
            } else if(focusChange == AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words and make the list final to set OnItemClickListener
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.string.number_one, R.string.miwok_number_one,
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word(R.string.number_two, R.string.miwok_number_two,
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word(R.string.number_three, R.string.miwok_number_three,
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word(R.string.number_four, R.string.miwok_number_four,
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word(R.string.number_five, R.string.miwok_number_five,
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word(R.string.number_six, R.string.miwok_number_six,
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word(R.string.number_seven, R.string.miwok_number_seven,
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(R.string.number_eight, R.string.miwok_number_eight,
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(R.string.number_nine, R.string.miwok_number_nine,
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(R.string.number_ten, R.string.miwok_number_ten,
                R.drawable.number_ten, R.raw.number_ten));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(wordAdapter);

        // Set an click listener to the list view to play the audio file for each word
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus to play the audio file. Since the audio is a short file,
                // request focus for a short time with AUDIOFOCUS_GAIN_TRANSIENT
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, STREAM_MUSIC, AUDIOFOCUS_GAIN_TRANSIENT);

                // If audio focus is granted, setup the media player and play the sound
                if (result == AUDIOFOCUS_REQUEST_GRANTED) {
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    /**
     * Release media player resources when user moves out of the app
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}
