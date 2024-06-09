package net.chaz2117.dlib.util;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.function.Predicate;

public class IntTextField extends TextFieldWidget {
    public IntTextField(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);

        Predicate<String> textPredicate = this::isInteger;
        this.setTextPredicate(textPredicate);
    }

    private boolean isInteger(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException error) {
            return x == "";
        }
    }
}
