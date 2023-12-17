package com.mygdx.game;

import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MunchBakeryMain extends Game {
    public static final int SCREEN_WIDTH = 1080 + 100;
    public static final int SCREEN_HEIGHT = 2340 + 125;
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

    Random random = new Random();


    @Override
    public void create() {

        productsList = new ArrayList<>();
        inCartList = new ArrayList<>();

        List<Drawable> drawableList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            drawableList.add(getDrawableFromPath("sweet" + i + ".png"));
        }

        for (int i = 0; i < 25; i++) {

            // Generate a random integer in the range [min, max)
            // notice max) is not included
            int min = 0;
            int max = drawableList.size();
            int randomInRange = random.nextInt(max - min) + min;

            productsList.add(new Product("Product No: " + i, (double) (32 + (i * 5)), drawableList.get(randomInRange)));
        }

        productsScreen = new ProductsScreen(this);
        cartScreen = new CartScreen(this);
        setScreen(productsScreen);
    }

    @Override
    public void render() {
        super.render();
    }
}
