package com.mygdx.game;

import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.Utilities.createRoundedDrawable;
import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class CartItemWidget extends Table {
    public static final String REMOVE_ITEM_BUTTON_NAME = "cart remove item button";

    private static final int CART_ITEM_WIDGET_WIDTH = SCREEN_WIDTH - 70;
    private static final int PRODUCT_WIDGET_HEIGHT = 300;
    private static final int WINDOW_ROUNDING_RADIUS = 50;
    private static final int SPINNER_WIDTH = 50;
    private static final int SPINNER_HEIGHT = 200;
    private static final float SPLITTING_SEGMENT_REMOVE = 0.15f;
    private static final float SPLITTING_SEGMENT_COST = 0.15f;
    private static final float SPLITTING_SEGMENT_PRODUCT = 0.3f;
    private static final float SPLITTING_SEGMENT_IMAGE = 0.2f;
    private static final int HORIZONTAL_SPACING = 40;

    private final MySpinner spinner;
    private final Label calculatedCostLabel;

    /** The appropriate widget to hold the product in the cart.
     * Extension of {@link Table}
     * @param product The product to be presented in this widget
     * @param skin The skin used
     * */
    public CartItemWidget(final Product product, Skin skin) {
        setSkin(skin);

        spinner = new MySpinner(skin, product.getQuantity(), 1, 20, 1);

        // After the spinner value changed we must set the (in cart) list about the change and update the widget cost
        spinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                product.setQuantity(spinner.getValue());
                calculatedCostLabel.setText(String.valueOf(product.getCost() * product.getQuantity()));
            }
        });

        align(Align.left);
        padTop(HORIZONTAL_SPACING / 2f);
        padRight(HORIZONTAL_SPACING);
        padLeft(HORIZONTAL_SPACING);
        padBottom(HORIZONTAL_SPACING / 2f);

        // Labels styles and settings
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("default.fnt"));
        labelStyle.font.getData().setScale(2.5f);

        background(createRoundedDrawable(Color.DARK_GRAY, CART_ITEM_WIDGET_WIDTH, PRODUCT_WIDGET_HEIGHT, WINDOW_ROUNDING_RADIUS));

        Table productTable = new Table();
        productTable.align(Align.left);

        Label productNameLabel = new Label(product.getName(), labelStyle);
        productNameLabel.setAlignment(Align.right);
        productNameLabel.setWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_PRODUCT);
        productNameLabel.setWrap(true);
        productTable.add(productNameLabel).prefWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_PRODUCT).align(Align.left).row();
        productTable.add().padTop(5).row();

        Label productCostLabel = new Label(String.valueOf(product.getCost()), labelStyle);
        productCostLabel.setAlignment(Align.right);
        productTable.add(productCostLabel).prefWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_PRODUCT).align(Align.left).row();
        productTable.add().padTop(10).row();

//        productTable.debug();
//        debug();

        Drawable removeIconDrawable = getDrawableFromPath("remove_icon_130px.png");

        Button removeButton = new Button(new Button.ButtonStyle(removeIconDrawable, removeIconDrawable, null));
        removeButton.setName(REMOVE_ITEM_BUTTON_NAME);

        add(removeButton).align(Align.left);

        double calculatedCost = product.getCost() * product.getQuantity();
        calculatedCostLabel = new Label(String.valueOf(calculatedCost), labelStyle);
        calculatedCostLabel.setAlignment(Align.right);
        add(calculatedCostLabel).prefWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_COST).padRight(HORIZONTAL_SPACING / 2f).align(Align.left);

        add(spinner).prefWidth(SPINNER_WIDTH).prefHeight(SPINNER_HEIGHT).align(Align.left);
        add(productTable).prefWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_PRODUCT).padRight(HORIZONTAL_SPACING).align(Align.left);

        Image productImage = new Image(product.getImage());
        productImage.setAlign(Align.center);
        productImage.setScaling(Scaling.fit);

        Container<Image> imageContainer = new Container<>(productImage);
        imageContainer.fill();

        add(imageContainer).prefWidth(CART_ITEM_WIDGET_WIDTH * SPLITTING_SEGMENT_IMAGE).prefHeight(PRODUCT_WIDGET_HEIGHT - 70).align(Align.left);
    }
}
