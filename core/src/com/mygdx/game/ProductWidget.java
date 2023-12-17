package com.mygdx.game;

import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.Utilities.createRoundedDrawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class ProductWidget extends Table {

    private static final int PRODUCT_WIDGET_WIDTH = SCREEN_WIDTH - 100;
    private static final int PRODUCT_WIDGET_HEIGHT = 300;
    private static final int WINDOW_ROUNDING_RADIUS = 50;
    private static final int SPINNER_WIDTH = 50;
    private static final int SPINNER_HEIGHT = 200;
    private static final float SPLITTING_RATIO_RIGHT = 0.45F;
    private static final float SPLITTING_RATIO_LEFT = 1 - SPLITTING_RATIO_RIGHT;
    private static final int HORIZONTAL_SPACING = 50;

    private final MySpinner spinner;

    public int getQuantity() {
        return spinner.getValue();
    }

    public ProductWidget(Product product, Skin skin) {
        setSkin(skin);

        spinner = new MySpinner(skin, product.getQuantity(), 1, 20, 1);
        spinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                event.stop();
            }
        });

        align(Align.left);
        padTop(HORIZONTAL_SPACING / 2f);
        padRight(HORIZONTAL_SPACING);
        padBottom(HORIZONTAL_SPACING / 2f);

        // Labels styles and settings
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("default.fnt"));
        labelStyle.font.getData().setScale(2.5f);

        background(createRoundedDrawable(Color.DARK_GRAY, PRODUCT_WIDGET_WIDTH, PRODUCT_WIDGET_HEIGHT, WINDOW_ROUNDING_RADIUS));
//        background(createRoundedDrawable(Color.WHITE, PRODUCT_WIDGET_WIDTH, PRODUCT_WIDGET_HEIGHT, WINDOW_ROUNDING_RADIUS));

        Table rightTable = new Table();

        rightTable.add(new Label(product.getName(), labelStyle)).colspan(2).align(Align.right).row();
        rightTable.add().padTop(10).row();
        rightTable.add(new Label(String.valueOf(product.getCost()), labelStyle)).colspan(2).align(Align.right).row();
        rightTable.add().padTop(10).row();
        rightTable.add(spinner).prefWidth(SPINNER_WIDTH).prefHeight(SPINNER_HEIGHT).padRight(HORIZONTAL_SPACING).align(Align.center);
        rightTable.add(new TextButton("Add to Cart", skin)).prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_LEFT - SPINNER_WIDTH - HORIZONTAL_SPACING * 2).prefHeight(SPINNER_HEIGHT - 100)
                .align(Align.left);

//        rightTable.debug();
//        debug();

        add().prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_RIGHT).prefHeight(PRODUCT_WIDGET_HEIGHT);
        add(rightTable).prefWidth(PRODUCT_WIDGET_WIDTH * SPLITTING_RATIO_LEFT).align(Align.left);
    }
}
