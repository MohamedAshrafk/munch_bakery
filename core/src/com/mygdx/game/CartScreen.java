package com.mygdx.game;

import static com.mygdx.game.MunchBakeryMain.BOTTOM_PADDING;
import static com.mygdx.game.MunchBakeryMain.HEADER_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_ITEMS_SPACING;
import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Objects;

public class CartScreen implements Screen {

    private static final int TOTAL_COST_TABLE_HEIGHT = 170;
    private static final int TOTAL_COST_RIGHT_PADDING = 300;

    // considering the main class as the data source (should be replaced by appropriate data source like internet or local database)
    private final MunchBakeryMain munchBakeryMain;
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label.LabelStyle labelStyle;

    private Label totalCostLabel;

    public CartScreen(MunchBakeryMain munchBakeryMain) {
        this.munchBakeryMain = munchBakeryMain;
    }

    @Override
    public void show() {
        Viewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
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
//        headerTable.background(getTexturedColor(getColorFromRGB(255,181,170,255)));

        Drawable arrowIcon = getDrawableFromPath("arrow_left_icon_170px.png");

        Button backButton = new Button(new Button.ButtonStyle(arrowIcon, arrowIcon, null));

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                munchBakeryMain.setScreen(munchBakeryMain.getProductsScreen());
            }
        });
        headerTable.add(backButton).padRight(220).align(Align.left);
        headerTable.add(new Label("Your Cart", labelStyle)).padRight(380).align(Align.center);

        table.add(headerTable).prefWidth(SCREEN_WIDTH).prefHeight(HEADER_HEIGHT).align(Align.center).row();
        table.add().padTop(50).row();
    }

    private void configureBody() {
        final VerticalGroup widgetGroup = new VerticalGroup();
        double totalCost = 0;

        for (final Product product : munchBakeryMain.getInCartList()) {
            final CartItemWidget cartItemWidget = new CartItemWidget(product, skin);
            totalCost += product.getCost() * product.getQuantity();

            widgetGroup.addActor(cartItemWidget);
            widgetGroup.space(SCROLL_VIEW_ITEMS_SPACING);

            cartItemWidget.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    double localTotalCost = 0;
                    boolean reCalculateCost = false;
                    // If the spinner value of any cartItemWidget is changed or we removed any item, the total cost should be updated
                    if (Objects.equals(actor.getName(), MySpinner.INCREMENT_BUTTON_NAME) || Objects.equals(actor.getName(), MySpinner.DECREMENT_BUTTON_NAME)) {
                        reCalculateCost = true;
                    }
                    if (Objects.equals(actor.getName(), CartItemWidget.REMOVE_ITEM_BUTTON_NAME)) {
                        munchBakeryMain.getInCartList().remove(product);
                        widgetGroup.removeActor(cartItemWidget);

                        reCalculateCost = true;
                    }

                    // looping on the (in cart) products list to calculate the new total cost
                    if (reCalculateCost) {
                        for (final Product product : munchBakeryMain.getInCartList()) {
                            localTotalCost += product.getCost() * product.getQuantity();
                        }
                        totalCostLabel.setText(String.valueOf(localTotalCost));
                    }
                }
            });
        }

        totalCostLabel = new Label(String.valueOf(totalCost), labelStyle);

        Table totalCostTable = new Table();
        totalCostTable.align(Align.center);
        totalCostTable.background(skin.getDrawable("default-slider"));
//        totalCostTable.background(getTexturedColor(getColorFromRGB(255, 181, 170, 255)));

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
//        Gdx.gl.glClearColor(255 / 255f, 247 / 255f, 248 / 255f, 1);
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
        dispose();
    }
}
