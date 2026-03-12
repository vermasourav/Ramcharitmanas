package com.verma.android.ramcharitmanas.ui.menu.verse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerseGroup {

    @SerializedName("verses")
    @Expose
    private List<Verse> verses;


    public List<Verse> getVerses() {
        return verses;
    }

    public void setVerses(List<Verse> verses) {
        this.verses = verses;
    }


}