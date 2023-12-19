package com.mygdx.game.model;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Objects;

public class CartProduct {

    private final int id;
    private final String name;
    private final Double cost;
    private int quantity;

    private final Drawable image;

    public CartProduct(CartProduct cartProduct) {
        this(cartProduct.name, cartProduct.cost, cartProduct.quantity, cartProduct.image, cartProduct.id);
    }

    public CartProduct(Product product, int quantity) {
        this(product.getName(), product.getCost(), quantity, product.getImage(), product.getId());
    }

    public CartProduct(String name, Double cost, Drawable image, int id) {
        this(name, cost, 1, image, id);
    }

    public CartProduct(String name, Double cost, int quantity, Drawable image, int id) {
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
        CartProduct cartProduct = (CartProduct) o;
        return Objects.equals(id, cartProduct.id);
    }
}
