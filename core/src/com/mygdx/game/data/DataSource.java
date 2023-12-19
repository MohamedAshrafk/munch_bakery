package com.mygdx.game.data;

import static com.mygdx.game.Utilities.costFormat;
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

        int minCost = 10;
        int maxCost = 500;

        // Create a DecimalFormat with two decimal places

        for (int id = 0; id < 25; id++) {
            int randomInRange = random.nextInt(max - min) + min;

            // generate random double price
            double randomInRangeCost = minCost + random.nextDouble() * (maxCost - minCost);
            // Format the double value
            // then, Convert the formatted string back to a double if needed
            double roundedValue = Double.parseDouble(costFormat.format(randomInRangeCost));

            productsList.add(new Product("Product No: " + id, roundedValue, drawableList.get(randomInRange), id));
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
