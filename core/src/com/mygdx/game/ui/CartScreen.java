package com.mygdx.game.ui;

import static com.mygdx.game.MunchBakeryMain.BOTTOM_PADDING;
import static com.mygdx.game.MunchBakeryMain.HEADER_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCREEN_WIDTH;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_HEIGHT;
import static com.mygdx.game.MunchBakeryMain.SCROLL_VIEW_ITEMS_SPACING;
import static com.mygdx.game.Utilities.getColorFromRGB;
import static com.mygdx.game.Utilities.getDrawableFromPath;
import static com.mygdx.game.Utilities.getTexturedColor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.model.CartProduct;
import com.mygdx.game.widgets.CartItemWidget;
import com.mygdx.game.widgets.MySpinner;

import java.util.List;
import java.util.Objects;

public class CartScreen extends Window {

    public static final String BACK_BUTTON_NAME = "back button";
    private static final int TOTAL_COST_TABLE_HEIGHT = 170;
    private static final int TOTAL_COST_RIGHT_PADDING = 300;

    // considering the main class as the data source (should be replaced by appropriate data source like internet or local database)
    private Stage stage;
    private final Skin skin;
    private final Table table;
    private final Label.LabelStyle labelStyle;

    double totalCost;

    private Label totalCostLabel;
    private VerticalGroup widgetGroup;

    private final List<CartProduct> inCartList;

    public CartScreen(List<CartProduct> inCartList, Skin skin) {
        super("", skin);
        this.inCartList = inCartList;
        this.skin = skin;

        background(getTexturedColor(getColorFromRGB(0, 0, 0, 1)));

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
        add(table);
    }


    public Window show(Stage stage) {
        updateCartItems();
        stage.addActor(this);
        stage.cancelTouchFocus();
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }


    private void configureHeader() {
        Table headerTable = new Table();
        headerTable.background(skin.getDrawable("default-slider"));
//        headerTable.background(getTexturedColor(getColorFromRGB(255,181,170,255)));

        Drawable arrowIcon = getDrawableFromPath("arrow_left_icon_170px.png");

        ImageButton backButton = new ImageButton(arrowIcon);
        backButton.setName(BACK_BUTTON_NAME);

        headerTable.add(backButton).padRight(220).align(Align.left);
        headerTable.add(new Label("Your Cart", labelStyle)).padRight(380).align(Align.center);

        table.add(headerTable).prefWidth(SCREEN_WIDTH).prefHeight(HEADER_HEIGHT).align(Align.center).row();
        table.add().padTop(50).row();
    }

    private void configureBody() {
        widgetGroup = new VerticalGroup();
        totalCost = 0;
        totalCostLabel = new Label(String.valueOf(totalCost), labelStyle);

        updateCartItems();

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

    private void updateCartItems() {
        widgetGroup.clear();
        for (final CartProduct cartProduct : inCartList) {
            final CartItemWidget cartItemWidget = new CartItemWidget(cartProduct, skin);
            totalCost += cartProduct.getCost() * cartProduct.getQuantity();

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
                        inCartList.remove(cartProduct);
                        widgetGroup.removeActor(cartItemWidget);

                        reCalculateCost = true;
                    }

                    // looping on the (in cart) products list to calculate the new total cost
                    if (reCalculateCost) {
                        for (final CartProduct cartProductLocal : inCartList) {
                            localTotalCost += cartProductLocal.getCost() * cartProductLocal.getQuantity();
                        }
                        totalCostLabel.setText(String.valueOf(localTotalCost));
                    }
                }
            });
        }
        totalCostLabel.setText(String.valueOf(totalCost));
    }
}
