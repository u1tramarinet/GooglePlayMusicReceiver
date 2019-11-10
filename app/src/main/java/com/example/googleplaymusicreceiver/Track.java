package com.example.googleplaymusicreceiver;

import androidx.annotation.Nullable;

public class Track {
    public final int id;
    @Nullable
    public final String name;
    @Nullable
    public final String artist;
    @Nullable
    public final String album;

    public Track(int id,
                 @Nullable String name,
                 @Nullable String artist,
                 @Nullable String album) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }
}
