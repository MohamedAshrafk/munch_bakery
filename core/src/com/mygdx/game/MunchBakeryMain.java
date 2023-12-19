package com.mygdx.game;

import static com.mygdx.game.ui.CartScreen.BACK_BUTTON_NAME;
import static com.mygdx.game.ui.ProductsScreen.CART_BUTTON_NAME;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.DataSource;
import com.mygdx.game.ui.CartScreen;
import com.mygdx.game.ui.ProductsScreen;

import java.util.Objects;

public class MunchBakeryMain extends ApplicationAdapter {
    public static final int SCREEN_WIDTH = 1080 + 100;
    public static final int SCREEN_HEIGHT = 2340 + 125;
    public static final int BOTTOM_PADDING = 150;
    public static final int HEADER_HEIGHT = 200;
    public static final int SCROLL_VIEW_HEIGHT = SCREEN_HEIGHT - HEADER_HEIGHT - BOTTOM_PADDING;
    public static final float SCROLL_VIEW_ITEMS_SPACING = 30f;
    public static final Color HEADER_COLOR = new Color(0x5f5f5fff);

    private Stage stage;
    private ProductsScreen productsScreen;
    private CartScreen cartScreen;

    @Override
    public void create() {

        Viewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(2.5f);

        DataSource dataSource = new DataSource();

        productsScreen = new ProductsScreen(dataSource, skin);
        productsScreen.setWidth(SCREEN_WIDTH);
        productsScreen.setHeight(SCREEN_HEIGHT);

        cartScreen = new CartScreen(dataSource, skin);
        cartScreen.setWidth(SCREEN_WIDTH);
        cartScreen.setHeight(SCREEN_HEIGHT);
        final boolean[] cartScreenShownBefore = {false};

        productsScreen.show(stage);

        productsScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Objects.equals(actor.getName(), CART_BUTTON_NAME)) {
                    if (!cartScreenShownBefore[0]) {
                        cartScreen.show(stage);
                        cartScreenShownBefore[0] = true;
                    }
                    productsScreen.setVisible(false);
                    cartScreen.setVisible(true);
                }
            }
        });
        cartScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Objects.equals(actor.getName(), BACK_BUTTON_NAME)) {
                    cartScreen.setVisible(false);
                    productsScreen.setVisible(true);
                }
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
