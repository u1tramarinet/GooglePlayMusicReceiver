package com.example.googleplaymusicreceiver;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Track> trackData = new MutableLiveData<>();

    private MutableLiveData<String> insertText = new MutableLiveData<>();

    private MutableLiveData<String> shareToTwitter = new MutableLiveData<>();

    private MutableLiveData<String> copyToClipboard = new MutableLiveData<>();

    public MainViewModel() {

    }

    public LiveData<Track> getTrackData() {
        return trackData;
    }

    public LiveData<String> insertText() {
        return insertText;
    }

    public LiveData<String> shareToTwitter() {
        return shareToTwitter;
    }

    public LiveData<String> copyToClipboard() {
        return copyToClipboard;
    }

    public void updateTrack(@Nullable Track next) {
        if (next == null) {
            return;
        }

        Track current = trackData.getValue();
        if (!next.equals(current)) {
            trackData.postValue(next);
        }
    }

    public void insertText(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        insertText.postValue(text);
    }

    public void shareToTwitter(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        shareToTwitter.postValue(text);
    }

    public void copyToClipboard(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        copyToClipboard.postValue(text);
    }
}
