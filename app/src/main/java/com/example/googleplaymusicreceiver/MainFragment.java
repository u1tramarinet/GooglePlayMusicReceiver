package com.example.googleplaymusicreceiver;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainFragment extends Fragment {

    private TextView textViewId;
    private TextView textViewTrack;
    private TextView textViewArtist;
    private TextView textViewAlbum;
    private EditText editText;
    private MaterialButton addIdButton;
    private MaterialButton addTrackButton;
    private MaterialButton addArtistButton;
    private MaterialButton addAlbumButton;
    private MaterialButton addHyphenButton;
    private MaterialButton addSlashButton;
    private MaterialButton addBracketButton;
    private MaterialButton copyButton;
    private MaterialButton tweetButton;
    private final String format = "#NowPlaying %s - %s";

    MainViewModel viewModel;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = MainActivity.obtainViewModel(getActivity());
        viewModel.getTrackData().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                setTrack(track);
            }
        });
        viewModel.insertText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                insertTextIfFocused(s);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View root) {
        textViewId = root.findViewById(R.id.contentId);
        textViewTrack = root.findViewById(R.id.contentTrack);
        textViewArtist = root.findViewById(R.id.contentArtist);
        textViewAlbum = root.findViewById(R.id.contentAlbum);
        editText = root.findViewById(R.id.text);
        addIdButton = root.findViewById(R.id.addIdButton);
        addIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(textViewId.getText().toString());
            }
        });
        addTrackButton = root.findViewById(R.id.addTrackButton);
        addTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(textViewTrack.getText().toString());
            }
        });
        addArtistButton = root.findViewById(R.id.addArtistButton);
        addArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(textViewArtist.getText().toString());
            }
        });
        addAlbumButton = root.findViewById(R.id.addAlbumButton);
        addAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(textViewAlbum.getText().toString());
            }
        });
        addHyphenButton = root.findViewById(R.id.addHyphenButton);
        addHyphenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(" - ");
            }
        });
        addSlashButton = root.findViewById(R.id.addSlashButton);
        addSlashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText(" / ");
            }
        });
        addBracketButton = root.findViewById(R.id.addBracketButton);
        addBracketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertText("()");
            }
        });
        copyButton = root.findViewById(R.id.copyButton);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.copyToClipboard(editText.getText().toString());
            }
        });
        tweetButton = root.findViewById(R.id.tweetButton);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.shareToTwitter(editText.getText().toString());
            }
        });
    }

    private void setTrack(Track track) {
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

    private void insertTextIfFocused(String text) {
        if (!editText.isFocused()) {
            return;
        }
        String str = editText.getText().toString();
        int start = Math.max(editText.getSelectionStart(), 0);
        int end = Math.max(editText.getSelectionEnd(), 0);
        String str1 = str.substring(0, start);
        String str2 = str.substring(end);

        str = str1 + text + str2;
        editText.setText(str);
        editText.setSelection(str1.length() + text.length());
    }
}
