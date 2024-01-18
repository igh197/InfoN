package com.example.infon;

import com.google.gson.annotations.SerializedName;

public class Reple {
    @SerializedName("contents")
    String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public Reple(String contents){
        this.contents=contents;

    }
    public Reple(){
    }
}
