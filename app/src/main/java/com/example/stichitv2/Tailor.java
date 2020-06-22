package com.example.stichitv2;

public class Tailor {
    String id, name, lati, longi,rating,image,abc;

    public Tailor(String id, String name, String lati, String longi) {
        this.id = id;
        this.name = name;
        this.lati = lati;
        this.longi = longi;

    }
    public Tailor(String id,String name,String image,String rating,String abc)
    {
        this.id=id;
        this.name=name;
        this.image=image;
        this.rating=rating;
        this.abc=abc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLati() {
        return lati;
    }

    public String getLongi() {
        return longi;
    }

    public String getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }
}
