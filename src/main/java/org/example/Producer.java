package org.example;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Producer {
    private String name;
    private String country;

    public Producer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
//        return "name= " + name +
//                " , country=" + country;
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
