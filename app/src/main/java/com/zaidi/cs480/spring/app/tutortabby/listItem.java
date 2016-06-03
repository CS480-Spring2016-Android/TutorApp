package com.zaidi.cs480.spring.app.tutortabby;

/**
 * Created by Jacob Romero on 6/2/2016.
 */
public class listItem {
    public String text;
    public int id;
    public String email;

    public listItem(int id, String text){
        this.text = text;
        this.id = id;
    }

    public listItem(int id, String text, String email){
        this.text = text;
        this.id = id;
        this.email = email;
    }

    public String toString(){
        return text;
    }
}
