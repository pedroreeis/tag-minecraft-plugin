package com.queendev.tag.models;

public class PlayerTagModel {

    private String name;
    private TagModel tag;

    public PlayerTagModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        this.tag = tag;
    }
}
