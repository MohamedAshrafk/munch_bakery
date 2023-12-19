package com.mygdx.game.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Spinner extends Table {

    protected static final int SPINNER_DIMENSION = 55;
    protected static final int SMALL_SPACING = 15;

    public static final String DECREMENT_BUTTON_NAME = "spinner decrement button";
    public static final String INCREMENT_BUTTON_NAME = "spinner increment button";

    protected int value;
    protected final int minDegreeVal;
    protected final int maxDegreeVal;
    protected final int stepSize;

    public int getValue() {
        return value;
    }

    /**
     * Creates a new Spinner as structure of {@link Table}
     *
     * @param skin the skin to be used on making the spinner
     */
    public Spinner(Skin skin) {
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
    public Spinner(Skin skin, int startValue, int minVal, int maxVal, int stepSizePara) {

        this.value = startValue;
        this.minDegreeVal = minVal;
        this.maxDegreeVal = maxVal;
        this.stepSize = stepSizePara;
    }

    protected boolean validateValue(int value) {
        return value <= maxDegreeVal && value >= minDegreeVal;
    }
}