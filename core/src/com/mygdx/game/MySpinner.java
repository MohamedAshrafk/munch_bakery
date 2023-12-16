package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class MySpinner extends Table {

    private static final int SPINNER_DIMENSION = 55;
    private static final int SMALL_SPACING = 15;


    public int getValue() {
        return value;
    }

    private int value;
    private final int minDegreeVal;
    private final int maxDegreeVal;
    private final int stepSize;

    TextField spinnerTF;

    /**
     * Creates a new Spinner as structure of {@link Table}
     *
     * @param skin the skin to be used on making the spinner
     */
    public MySpinner(Skin skin) {
        this(skin, 0, 0, 100, 1);
    }

    /**
     * Creates a new Spinner as structure of {@link Table}
     *
     * @param skin         the skin to be used on making the spinner
     * @param startValue   the start value
     * @param minVal       the minimum value
     * @param maxVal       the maximum value
     * @param stepSizePara the step size between values
     */
    public MySpinner(Skin skin, int startValue, int minVal, int maxVal, int stepSizePara) {

        this.value = startValue;
        this.minDegreeVal = minVal;
        this.maxDegreeVal = maxVal;
        this.stepSize = stepSizePara;

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

        // Create a skin (you can use the default skin or create your own)
        Skin localSkin = new Skin(Gdx.files.internal("uiskin.json"));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = localSkin.getDrawable("textfield");
        textFieldStyle.font = localSkin.getFont("default-font");
        textFieldStyle.fontColor = localSkin.getColor("white");
        textFieldStyle.font.getData().setScale(2.1f);

        spinnerTF = new TextField(String.valueOf(value), textFieldStyle);
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

        add(incrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).align(Align.center).row();
        add().padTop(2f).row();
        add(spinnerTF).prefHeight(SPINNER_DIMENSION).align(Align.center).row();
        add().padTop(2f).row();
        add(decrementButton).prefWidth(SPINNER_DIMENSION).prefHeight(SPINNER_DIMENSION).align(Align.center);
    }

    private boolean validateValue(int value) {
        return value <= maxDegreeVal && value >= minDegreeVal;
    }
}