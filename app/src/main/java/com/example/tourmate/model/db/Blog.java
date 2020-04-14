package com.example.tourmate.model.db;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Blog extends RealmObject {
    @PrimaryKey
    public long id;
    public String dateTime;
    public String text;
    public RealmList<String> imgList=new RealmList<>();

    public Blog(long id, String dateTime, String text) {
        this.id = id;
        this.dateTime = dateTime;
        this.text = text;
    }

    public Blog() {
    }
}
