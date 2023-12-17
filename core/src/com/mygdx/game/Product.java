package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Objects;

public class Product {
    private final String name;
    private final Double cost;
    private int quantity;

    public Drawable getImage() {
        return image;
    }

    private final Drawable image;

    public Product(String name, Double cost, Drawable image) {
        this(name, cost, 1, image);
    }

    public Product(String name, Double cost, int quantity, Drawable image) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

}
