package com.example.googleplaymusicreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewId;
    private TextView textViewTrack;
    private TextView textViewArtist;
    private TextView textViewAlbum;
    private EditText editText;
    private MaterialButton addIdButton;
    private MaterialButton addTrackButton;
    private MaterialButton addArtistButton;
    private MaterialButton addAlbumButton;
    private MaterialButton copyButton;
    private MaterialButton tweetButton;
    private final String format = "#NowPlaying %s - %s";

    private GooglePlayMusicBroadcastReceiver receiver = new GooglePlayMusicBroadcastReceiver() {
        @Override
        public void onTrackReceive(Context context, @NonNull Track track, String action) {
            notifyTrackUpdated(track);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewId = findViewById(R.id.contentId);
        textViewTrack = findViewById(R.id.contentTrack);
        textViewArtist = findViewById(R.id.contentArtist);
        textViewAlbum = findViewById(R.id.contentAlbum);
        editText = findViewById(R.id.text);
        addIdButton = findViewById(R.id.addIdButton);
        addTrackButton = findViewById(R.id.addTrackButton);
        addArtistButton = findViewById(R.id.addArtistButton);
        addAlbumButton = findViewById(R.id.addAlbumButton);
        copyButton = findViewById(R.id.copyButton);
        tweetButton = findViewById(R.id.tweetButton);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = copyToClipBoard(MainActivity.this, getContent());
                notifyResult("Copy", result);
            }
        });

        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareToTwitter(getContent());
            }
        });

        addIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextIfFocused(textViewId.getText().toString());
            }
        });

        addTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextIfFocused(textViewTrack.getText().toString());
            }
        });

        addArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextIfFocused(textViewArtist.getText().toString());
            }
        });

        addAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextIfFocused(textViewAlbum.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, receiver.getIntentFilter());
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void notifyTrackUpdated(@NonNull Track track) {
        textViewId.setText(String.valueOf(track.id));
        textViewTrack.setText(track.name);
        textViewArtist.setText(track.artist);
        textViewAlbum.setText(track.album);

        String content = String.format(Locale.JAPAN, format, track.name, track.artist);
        editText.setText(content);

        if (editText.isFocused()) {
            editText.setSelection(content.length());
        }
    }

    private boolean copyToClipBoard(Context context, String text) {
        if (context == null) {
            return false;
        }

        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null) {
            return false;
        }

        manager.setPrimaryClip(ClipData.newPlainText("", text));
        return true;
    }

    private void shareToTwitter(String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    private String getContent() {
        return editText.getText().toString();
    }

    private void insertTextIfFocused(String text) {
        if (!editText.isFocused()) {
            return;
        }
        Editable editable = editText.getText();
        String str = editable.toString();
        int posStart = editText.getSelectionStart();
        int posEnd = editText.getSelectionEnd();
        String str1 = str.substring(0, posStart);
        String str2 = str.substring(posEnd);

        str = str1 + text + str2;
        editText.setText(str);
        editText.setSelection(str1.length() + text.length());
    }

    private void notifyResult(String content, boolean result) {
        if (result) {
            content += " is success.";
        } else {
            content += " is failure...";
        }
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
