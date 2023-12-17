package com.mygdx.game;

import static com.mygdx.game.CartItemWidget.REMOVE_ITEM_BUTTON_NAME;
import static com.mygdx.game.MunchBakeryMain.BOTTOM_PADDING;
import static com.mygdx.game.MunchBakeryMain.HEADER_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_ITEMS_SPACING;
import static com.mygdx.game.MySpinner.DECREMENT_BUTTON_NAME;
import static com.mygdx.game.MySpinner.INCREMENT_BUTTON_NAME;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Objects;

public class CartScreen implements Screen {

    private static final int TOTAL_COST_TABLE_HEIGHT = 170;
    private static final int TOTAL_COST_RIGHT_PADDING = 300;

    private final Game game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private Label.LabelStyle labelStyle;

    private Label totalCostLabel;
    private double totalCost;

    public CartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(2.5f);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.topLeft);
        table.padBottom(BOTTOM_PADDING);

        // Labels styles and settings
        labelStyle = new Label.LabelStyle();
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

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(((MunchBakeryMain) game).getProductsScreen());
            }
        });
        headerTable.add(backButton).prefWidth(150).prefHeight(70).padRight(250).align(Align.left);
        headerTable.add(new Label("Your Cart", labelStyle)).padRight(350).align(Align.center);

        table.add(headerTable).prefWidth(SCREEN_WIDTH).prefHeight(HEADER_HEIGHT).align(Align.center).row();
        table.add().padTop(50).row();
    }

    private void configureBody() {
        final VerticalGroup widgetGroup = new VerticalGroup();
        totalCost = 0;

        for (final Product product : ((MunchBakeryMain) game).getInCartList()) {
            final CartItemWidget cartItemWidget = new CartItemWidget(product, skin);
            totalCost += cartItemWidget.getCalculatedCost();

            widgetGroup.addActor(cartItemWidget);
            widgetGroup.space(SCROLL_VIEW_ITEMS_SPACING);

            cartItemWidget.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    totalCost = 0;
                    if (Objects.equals(actor.getName(), INCREMENT_BUTTON_NAME) || Objects.equals(actor.getName(), DECREMENT_BUTTON_NAME)) {
                        for (Actor loopActor : widgetGroup.getChildren()) {
                            totalCost += ((CartItemWidget) loopActor).getCalculatedCost();
                        }
                        totalCostLabel.setText(String.valueOf(totalCost));
                    }
                    if (Objects.equals(actor.getName(), REMOVE_ITEM_BUTTON_NAME)){
                        ((MunchBakeryMain) game).getInCartList().remove(product);
                        widgetGroup.removeActor(cartItemWidget);
                    }
                }
            });
        }

        totalCostLabel = new Label(String.valueOf(totalCost), labelStyle);

        Table totalCostTable = new Table();
        totalCostTable.align(Align.center);
        totalCostTable.background(skin.getDrawable("default-slider"));

        totalCostTable.add(new Label("Total", labelStyle)).padRight(TOTAL_COST_RIGHT_PADDING).align(Align.left);
        totalCostTable.add(totalCostLabel).align(Align.left);

        ScrollPane scrollPane = new ScrollPane(widgetGroup);
        table.add(scrollPane).prefHeight(SCROLL_VIEW_HEIGHT - TOTAL_COST_TABLE_HEIGHT).prefWidth(SCREEN_WIDTH).align(Align.left).row();
        table.add(totalCostTable).prefHeight(TOTAL_COST_TABLE_HEIGHT).prefWidth(SCREEN_WIDTH - 100);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
