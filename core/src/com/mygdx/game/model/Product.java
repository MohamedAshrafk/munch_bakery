package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final Double cost;
    private int quantity;

    private final Drawable image;

    public Product(Product product) {
        this(product.name, product.cost, product.quantity, product.image, product.id);
    }

    public Product(String name, Double cost, Drawable image, int id) {
        this(name, cost, 1, image, id);
    }

    public Product(String name, Double cost, int quantity, Drawable image, int id) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.image = image;
        this.id = id;
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

    public Drawable getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

}
