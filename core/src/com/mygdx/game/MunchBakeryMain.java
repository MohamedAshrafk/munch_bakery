package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MunchBakeryMain extends ApplicationAdapter {
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 2340;
    private static final int BOTTOM_PADDING = 150;
    private static final int HEADER_HEIGHT = 200;
    private static final int SCROLL_VIEW_HEIGHT = SCREEN_HEIGHT - HEADER_HEIGHT - BOTTOM_PADDING;
    private static final float SCROLL_VIEW_ITEMS_SPACING = 30f;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private LabelStyle labelStyle;


    @Override
    public void create() {

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(2.5f);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.topLeft);
        table.padBottom(BOTTOM_PADDING);

        // Labels styles and settings
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("default.fnt"));
        labelStyle.font.getData().setScale(3f);

        configureHeader();
        configureBody();

        // adding everything to the stage
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private void configureHeader() {
        Table headerTable = new Table();
        headerTable.background(skin.getDrawable("default-slider"));

        headerTable.add(new Label("Products", labelStyle)).align(Align.center);

        table.add(headerTable).prefWidth(SCREEN_WIDTH).prefHeight(HEADER_HEIGHT).align(Align.center).row();
        table.add().padTop(50).row();
    }

    private void configureBody() {
        VerticalGroup widgetGroup = new VerticalGroup();

        for (int i = 0; i < 50; i++) {
            ProductWidget w = new ProductWidget("Product " + (i + 1), 32 + (i * 5) + ".00", skin);
            widgetGroup.addActor(w);
            widgetGroup.space(SCROLL_VIEW_ITEMS_SPACING);
        }

        ScrollPane scrollPane = new ScrollPane(widgetGroup);
        table.add(scrollPane).prefHeight(SCROLL_VIEW_HEIGHT).prefWidth(SCREEN_WIDTH).align(Align.left);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
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
    public void dispose() {
        stage.dispose();
    }
}
