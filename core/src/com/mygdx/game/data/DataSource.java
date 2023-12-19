package com.mygdx.game.data;

import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.model.CartProduct;
import com.mygdx.game.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSource {

    private final List<Product> productsList;
    private final List<CartProduct> cartList;

    public DataSource() {
        productsList = new ArrayList<>();
        cartList = new ArrayList<>();

        List<Drawable> drawableList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            drawableList.add(getDrawableFromPath("sweet" + i + ".png"));
        }

        // Generate a random integer in the range [min, max)
        // notice max) is not included
        int min = 0;
        int max = drawableList.size();
        Random random = new Random();

        for (int id = 0; id < 25; id++) {

            int randomInRange = random.nextInt(max - min) + min;

            productsList.add(new Product("Product No: " + id, (double) (32 + (id * 5)), drawableList.get(randomInRange), id));
        }
    }

    public List<Product> getProductsList() {
        return new ArrayList<>(productsList);
    }

    public List<CartProduct> getCartList() {
        return new ArrayList<>(cartList);
    }

    public boolean addToCartWithQuantity(Product product, int quantity) {
        CartProduct newProduct = new CartProduct(product, quantity);

        if (!cartList.contains(newProduct)) {
            cartList.add(newProduct);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeProductFromCart(CartProduct cartProduct) {
        return cartList.remove(cartProduct);
    }
}
