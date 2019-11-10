package com.example.googleplaymusicreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

public abstract class GooglePlayMusicBroadcastReceiver extends BroadcastReceiver {

    private static final String[] INTENT_FILTER_ACTIONS = {
            "com.android.music.metachanged",
            "com.android.music.queuechanged",
            "com.android.music.playstatechanged",
            "com.android.music.playbackcomplete"};

    private static final String KEY_ID = "id";
    private static final String KEY_TRACK = "track";
    private static final String KEY_ARTIST= "artist";
    private static final String KEY_ALBUM = "album";

    @Override
    public void onReceive(Context context, Intent intent) {
        onTrackReceive(context, createTrackFromIntent(intent), intent.getAction());
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        for (String action: INTENT_FILTER_ACTIONS) {
            intentFilter.addAction(action);
        }
        return intentFilter;
    }

    public abstract void onTrackReceive(Context context, @NonNull Track track, String action);

    private Track createTrackFromIntent(@NonNull Intent intent) {
        return new Track(
                intent.getIntExtra(KEY_ID, -1),
                intent.getStringExtra(KEY_TRACK),
                intent.getStringExtra(KEY_ARTIST),
                intent.getStringExtra(KEY_ALBUM));
    }
}
