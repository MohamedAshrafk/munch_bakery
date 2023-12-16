package com.mygdx.game;

public class Product {
    private final String name;
    private final String cost;

    public Product(String name, String cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }
}
