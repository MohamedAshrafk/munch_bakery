package com.mygdx.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class HSpinner extends Spinner{
    public HSpinner(Skin skin) {
        super(skin);
    }

    public HSpinner(Skin skin, int startValue, int minVal, int maxVal, int stepSizePara) {
        super(skin, startValue, minVal, maxVal, stepSizePara);

        // Create a skin (you can use the default skin or create your own)
        Skin localSkin = new Skin(Gdx.files.internal("uiskin.json"));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = localSkin.getDrawable("textfield");
        textFieldStyle.font = localSkin.getFont("default-font");
        textFieldStyle.fontColor = localSkin.getColor("white");
        textFieldStyle.font.getData().setScale(2.1f);
        final TextField spinnerTF = new TextField(String.valueOf(value), textFieldStyle);

        // making the spinner using two TextButtons and a TextField
        Drawable minusIconDrawable = skin.getDrawable("tree-minus");
        Drawable plusIconDrawable = skin.getDrawable("tree-plus");
        Drawable selectedIconDrawable = skin.getDrawable("default-round-down");

        Button decrementButton = new Button(new Button.ButtonStyle(minusIconDrawable, selectedIconDrawable, null));
        decrementButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (validateValue(value - stepSize)) {
                    value -= stepSize;
                    spinnerTF.setText(String.valueOf(value));
                } else {
                    event.stop();
                }
            }
        });
        decrementButton.setName(DECREMENT_BUTTON_NAME);

        spinnerTF.setAlignment(Align.center);
        spinnerTF.setDisabled(true);

        Button incrementButton = new Button(new Button.ButtonStyle(plusIconDrawable, selectedIconDrawable, null));
        incrementButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (validateValue(value + stepSize)) {
                    value += stepSize;
                    spinnerTF.setText(String.valueOf(value));
                } else {
                    event.stop();
                }
            }
        });
        incrementButton.setName(INCREMENT_BUTTON_NAME);

//        debug();

        add(decrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).padRight(SMALL_SPACING);
        add(spinnerTF).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).padRight(SMALL_SPACING);
        add(incrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION);
    }
}
