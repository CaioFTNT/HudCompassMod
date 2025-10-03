package com.johndue.hudcompassmod.config.widgets;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ConfigSliderWidget extends SliderWidget {

    private final int max_value;
    private final int min_value;

    private final java.util.function.DoubleConsumer onValueChange;

    public ConfigSliderWidget(int x, int y, int width, int height, double value, int min_value, int max_value, java.util.function.DoubleConsumer onValueChange) {
        super(x, y, width, height, Text.of(""+(int)((max_value - min_value) * value + min_value)), (value-min_value)/(max_value-min_value));
        this.max_value = max_value;
        this.min_value = min_value;
        this.onValueChange = onValueChange;

        updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.of(""+getValue()));
    }

    @Override
    protected void applyValue() {
        onValueChange.accept(getValue());
    }

    public int getValue() {
        return (int)((max_value - min_value) * value + min_value);
    }
}
