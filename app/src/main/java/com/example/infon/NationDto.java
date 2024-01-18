package com.example.infon;

import android.widget.EditText;

import com.google.gson.annotations.SerializedName;

public class NationDto {
    @SerializedName("name")
    private String name;
    @SerializedName("capital")
    private String capital;
    @SerializedName("currency")
    private String currency;
    @SerializedName("population")
    private String population;
    @SerializedName("contin")
    private String contin;
    @SerializedName("contents")
    private String contents;
    public NationDto(){

    }
    public NationDto(EditText name, EditText capital, EditText currency, EditText population, EditText contin, EditText contents) {
        this.name = name.getText().toString();
        this.capital=capital.getText().toString();
        this.currency=currency.getText().toString();
        this.population=population.getText().toString();
        this.contin=contin.getText().toString();
        this.contents=contents.getText().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getContin() {
        return contin;
    }

    public void setContin(String contin) {
        this.contin = contin;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
