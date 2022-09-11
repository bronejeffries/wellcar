package com.tambula.code.Objects;

import android.graphics.drawable.Drawable;


/**
 * Type of car object, contains the info of each type of car
 * to be used by the type adapter.
 */
public class TypeObject {

    String name, id;
    Drawable image;
    int people;
    String code;
    private int price;

    /**
     * TypeObject constructor
     * @param id - id of the type (type_1, type_2,....)
     * @param name - name of the type
     * @param image - image of the type as drawable
     * @param people . amount of people this type handles
     */
    public TypeObject(String id, String name, Drawable image, int people){
        this.id = id;
        this.name = name;
        this.image = image;
        this.people = people;
    }

    public TypeObject(String id, String name, Drawable image, int people,String code,int price){
        this.id = id;
        this.name = name;
        this.image = image;
        this.people = people;
        this.code =  code;
    }

    public String getId() {
        return id;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPeople() {
        return people;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
