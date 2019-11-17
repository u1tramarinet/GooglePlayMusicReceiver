package com.example.googleplaymusicreceiver;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Track {
    public static final int INVALID_ID = -1;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Track)) {
            return false;
        }
        Track another = (Track)obj;

        if ((this.id == INVALID_ID) ||
                (another.id == INVALID_ID)) {
            return false;
        }
        return Objects.equals(this.name, another.name) &&
                Objects.equals(this.artist, another.artist) &&
                Objects.equals(this.album, another.album);
    }
}
