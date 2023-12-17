package com.mygdx.game;

import static com.mygdx.game.MunchBakeryMain.BOTTOM_PADDING;
import static com.mygdx.game.MunchBakeryMain.HEADER_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_ITEMS_SPACING;
import static com.mygdx.game.Utilities.createRoundedDrawable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ProductsScreen implements Screen {

    public static final int GENERAL_HEIGHT_SPACING = 50;
    public static final int TABLE_HORIZONTAL_PADDING = 30;
    public static final int DIALOG_WIDTH = 900;

    public static final int BUTTON_HEIGHT = 70;
    public static final int BUTTON_WIDTH = 140;

    private final Game game;
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label.LabelStyle labelStyle;

    public ProductsScreen(Game game) {
        this.game = game;
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

        TextButton cartButton = new TextButton("Cart", skin);
        cartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(((MunchBakeryMain) game).getCartScreen());
            }
        });
        headerTable.add(cartButton).prefWidth(150).prefHeight(70).padRight(250).align(Align.left);
        headerTable.add(new Label("Products", labelStyle)).padRight(350).align(Align.center);

        table.add(headerTable).prefWidth(SCREEN_WIDTH).prefHeight(HEADER_HEIGHT).align(Align.center).row();
        table.add().padTop(50).row();
    }

    private void configureBody() {
        VerticalGroup widgetGroup = new VerticalGroup();

        for (final Product product : ((MunchBakeryMain) game).getProductsList()) {
            final ProductWidget productWidget = new ProductWidget(product, skin);
            productWidget.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!((MunchBakeryMain) game).getInCartList().contains(product)) {
                        Product newProduct = new Product(product.getName(), product.getCost(), productWidget.getQuantity());
                        ((MunchBakeryMain) game).getInCartList().add(newProduct);
                        showDialogWithText("The Product was added successfully");
                    } else {
                        showDialogWithText("Already in the Cart");
                    }
                }
            });
            widgetGroup.addActor(productWidget);
            widgetGroup.space(SCROLL_VIEW_ITEMS_SPACING);
        }

        ScrollPane scrollPane = new ScrollPane(widgetGroup);
        table.add(scrollPane).prefHeight(SCROLL_VIEW_HEIGHT).prefWidth(SCREEN_WIDTH).align(Align.left);
    }

    private void showDialogWithText(String textShown) {
        // Labels styles and settings
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        titleLabelStyle.font = new BitmapFont(Gdx.files.internal("default.fnt"));
        titleLabelStyle.font.getData().setScale(3f);

        // Create a rounded background for the dialog
        Window.WindowStyle dialogStyle = new Window.WindowStyle();
        dialogStyle.background = createRoundedDrawable(new Color(0x2f2f2fff), 500, 300, 25);
        dialogStyle.titleFont = skin.getFont("default-font");
        dialogStyle.titleFontColor = Color.WHITE;

        final Dialog dialog = new Dialog("", dialogStyle);

        Table localTable = new Table();
//        localTable.setFillParent(true);
        localTable.align(Align.center);
        localTable.padTop(GENERAL_HEIGHT_SPACING / 2f);
        localTable.padRight(TABLE_HORIZONTAL_PADDING);
        localTable.padLeft(TABLE_HORIZONTAL_PADDING);
        localTable.padBottom(TABLE_HORIZONTAL_PADDING);

        localTable.add(new Label(textShown, titleLabelStyle)).align(Align.center).row();
        localTable.add().padTop(GENERAL_HEIGHT_SPACING).row();

        TextButton cancelButton = new TextButton("OK", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        localTable.add(cancelButton).prefWidth(BUTTON_WIDTH).prefHeight(BUTTON_HEIGHT).align(Align.center).row();
        dialog.getContentTable().add(localTable).prefWidth(DIALOG_WIDTH - 200f).align(Align.center);
        dialog.show(stage);
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
