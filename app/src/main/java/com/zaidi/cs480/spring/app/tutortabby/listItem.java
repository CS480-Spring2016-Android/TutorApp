package com.zaidi.cs480.spring.app.tutortabby;

/**
 * Created by Jacob Romero on 6/2/2016.
 */
public class listItem {
    public String text;
    public int id;
    public listItem(int id, String text){
        this.text = text;
        this.id = id;
    }

    public String toString(){
        return text;
    }
}
