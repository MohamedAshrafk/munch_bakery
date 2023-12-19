package com.mygdx.game;

import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.model.CartProduct;
import com.mygdx.game.model.Product;
import com.mygdx.game.ui.CartScreen;
import com.mygdx.game.ui.ProductsScreen;

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
    private List<CartProduct> inCartList;

    public ProductsScreen getProductsScreen() {
        return productsScreen;
    }

    public CartScreen getCartScreen() {
        return cartScreen;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public List<CartProduct> getInCartList() {
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

        // Generate a random integer in the range [min, max)
        // notice max) is not included
        int min = 0;
        int max = drawableList.size();

        for (int id = 0; id < 25; id++) {

            int randomInRange = random.nextInt(max - min) + min;

            productsList.add(new Product("Product No: " + id, (double) (32 + (id * 5)), drawableList.get(randomInRange), id));
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
