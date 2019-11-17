package com.example.googleplaymusicreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private GooglePlayMusicBroadcastReceiver receiver = new GooglePlayMusicBroadcastReceiver() {
        @Override
        public void onTrackReceive(Context context, @NonNull Track track, String action) {
            viewModel.updateTrack(track);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = obtainViewModel(this);
        viewModel.copyToClipboard().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                copyToClipboard(MainActivity.this, s);
            }
        });
        viewModel.shareToTwitter().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shareToTwitter(s);
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

    private boolean copyToClipboard(Context context, String text) {
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

    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }
}
