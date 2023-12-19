package com.mygdx.game.widgets;

import static com.mygdx.game.Utilities.getDrawableFromPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class VSpinner extends Spinner{
    public VSpinner(Skin skin) {
        super(skin);
    }

    public VSpinner(Skin skin, int startValue, int minVal, int maxVal, int stepSizePara) {
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
        Drawable minusIconDrawable = getDrawableFromPath("arrow_down_icon.png");
        Drawable plusIconDrawable = getDrawableFromPath("arrow_up_icon.png");
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

        add(incrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).align(Align.center).row();
        add().padTop(2f).row();
        add(spinnerTF).prefHeight(SPINNER_DIMENSION).align(Align.center).row();
        add().padTop(2f).row();
        add(decrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).align(Align.center);
    }
}
