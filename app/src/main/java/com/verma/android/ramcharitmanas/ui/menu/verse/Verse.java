package com.verma.android.ramcharitmanas.ui.menu.verse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verse {

    @SerializedName("verse-number")
    @Expose
    private Double verseNumber;
    @SerializedName("content")
    @Expose
    private String content;

    public Double getVerseNumber() {
        return verseNumber;
    }

    public void setVerseNumber(Double verseNumber) {
        this.verseNumber = verseNumber;
    }

    public Verse withVerseNumber(Double verseNumber) {
        this.verseNumber = verseNumber;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Verse withContent(String content) {
        this.content = content;
        return this;
    }

}