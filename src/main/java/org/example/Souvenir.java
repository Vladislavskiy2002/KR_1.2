package org.example;

import com.google.gson.Gson;

import java.time.LocalDate;

public class Souvenir {
    private String name;
    private Producer details;
    private String localDate;
    private Double price;

    public Souvenir() {
        name = "Car";
        details = new Producer("ll", "UA");
        price = 222222.2;
        localDate = LocalDate.now().toString();
    }

    public Souvenir(String name, Producer details, Double price) {
        this.name = name;
        this.details = details;
        this.price = price;
        localDate = LocalDate.now().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Producer getDetails() {
        return details;
    }

    public void setDetails(Producer details) {
        this.details = details;
    }

    public String getDate() {
        return localDate;
    }
    public Integer getDateYear() {
        LocalDate localDate1 = LocalDate.parse(localDate);
        return localDate1.getYear();
    }

    public void setDate(LocalDate date) {
        this.localDate = date.toString();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
