package com.mygdx.game.widgets;

import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.Utilities.createRoundedDrawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.model.Product;

import javax.swing.text.LabelView;

public class ProductWidget extends Table {

    public static final String ADD_TO_CART_BUTTON_NAME = "add to cart button";
    private static final int PRODUCT_WIDGET_WIDTH = SCREEN_WIDTH - 100;
    private static final int PRODUCT_WIDGET_HEIGHT = 300;
    private static final int WINDOW_ROUNDING_RADIUS = 50;
    private static final int SPINNER_WIDTH = 300;
    private static final int SPINNER_HEIGHT = 100;
    private static final float SPLITTING_RATIO_LEFT = 0.40F;
    private static final float SPLITTING_RATIO_RIGHT = 1 - SPLITTING_RATIO_LEFT;
    private static final int HORIZONTAL_SPACING = 50;
    private static final int ADD_BUTTON_HEIGHT = 100;

    private final Spinner spinner;

    public int getQuantity() {
        return spinner.getValue();
    }

    /**
     * The appropriate widget to hold the product in products screen.
     * Extension of {@link Table}
     *
     * @param product The product to be presented in this widget
     * @param skin    The skin used
     */
    public ProductWidget(Product product, Skin skin) {
        setSkin(skin);

        spinner = new HSpinner(skin, 1, 1, 20, 1);

        align(Align.left);
        padTop(HORIZONTAL_SPACING / 2f);
        padRight(HORIZONTAL_SPACING);
        padLeft(HORIZONTAL_SPACING);
        padBottom(HORIZONTAL_SPACING / 2f);

        // Labels styles and settings
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("default.fnt"));
        labelStyle.font.getData().setScale(2.5f);

        background(createRoundedDrawable(Color.DARK_GRAY, PRODUCT_WIDGET_WIDTH, PRODUCT_WIDGET_HEIGHT, WINDOW_ROUNDING_RADIUS));
//        background(createRoundedDrawable(Color.WHITE, PRODUCT_WIDGET_WIDTH, PRODUCT_WIDGET_HEIGHT, WINDOW_ROUNDING_RADIUS));

        Table rightTable = new Table();

        Label nameLabel = new Label(product.getName(), labelStyle);
        nameLabel.setWrap(true);
        rightTable.add(nameLabel).prefWidth(nameLabel.getWidth()).colspan(2).align(Align.right).row();
        rightTable.add().padTop(10).row();
        rightTable.add(new Label(String.valueOf(product.getCost()), labelStyle)).colspan(2).align(Align.right).row();
        rightTable.add().padTop(10).row();
        rightTable.add(spinner).padRight(HORIZONTAL_SPACING).align(Align.center);

        TextButton addToCartButton = new TextButton("Add to Cart", skin);
        addToCartButton.setName(ADD_TO_CART_BUTTON_NAME);
        rightTable.add(addToCartButton).prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_RIGHT - SPINNER_WIDTH).prefHeight(ADD_BUTTON_HEIGHT)
                .align(Align.left);

//        rightTable.debug();
//        debug();

        Image productImage = new Image(product.getImage());
        productImage.setAlign(Align.center);
        productImage.setScaling(Scaling.fit);

        Container<Image> imageContainer = new Container<>(productImage);
        imageContainer.fill();

        add(imageContainer).prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_LEFT - HORIZONTAL_SPACING).prefHeight(PRODUCT_WIDGET_HEIGHT).padRight(HORIZONTAL_SPACING / 2f);
        add(rightTable).prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_RIGHT).align(Align.left);
    }
}
