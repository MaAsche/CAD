package com.mabeto.backend.picture.model;


public class PictureInformation {
    public final String id;

    public final String description;

    public final long createdAt;

    public PictureInformation(String id, String description, long createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
    }
}
