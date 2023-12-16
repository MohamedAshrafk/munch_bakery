package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.List;

public class MunchBakeryMain extends Game {
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 2340;
    public static final int BOTTOM_PADDING = 150;
    public static final int HEADER_HEIGHT = 200;
    public static final int SCROLL_VIEW_HEIGHT = SCREEN_HEIGHT - HEADER_HEIGHT - BOTTOM_PADDING;
    public static final float SCROLL_VIEW_ITEMS_SPACING = 30f;

    private ProductsScreen productsScreen;
    private CartScreen cartScreen;
    private List<Product> productsList;
    private List<Product> inCartList;

    public ProductsScreen getProductsScreen() {
        return productsScreen;
    }

    public CartScreen getCartScreen() {
        return cartScreen;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public List<Product> getInCartList() {
        return inCartList;
    }


    @Override
    public void create() {

        productsList = new ArrayList<>();
        inCartList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            productsList.add(new Product("Product No: " + i, (double) (32 + (i * 5))));
        }

//        for (int i = 0; i < 4; i++) {
//            inCartList.add(new Product("Product No: " + i, 32 + (i * 5) + ".00"));
//        }

        productsScreen = new ProductsScreen(this);
        cartScreen = new CartScreen(this);
        setScreen(productsScreen);
    }

    @Override
    public void render() {
        super.render();
    }
}
