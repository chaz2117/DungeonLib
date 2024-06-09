package net.chaz2117.dlib.mixin;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.util.ITextFieldWidgetBorderAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.BiFunction;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin extends ClickableWidget implements Drawable, ITextFieldWidgetBorderAccessor {


    @Shadow public abstract boolean isVisible();

    @Shadow protected abstract boolean drawsBackground();

    @Shadow private boolean editable;

    @Shadow private int editableColor;

    @Shadow private int uneditableColor;

    @Shadow private int selectionStart;

    @Shadow private int selectionEnd;

    @Shadow private int firstCharacterIndex;

    @Shadow @Final private TextRenderer textRenderer;

    @Shadow private String text;

    @Shadow public abstract int getInnerWidth();

    @Shadow private int focusedTicks;

    @Shadow private boolean drawsBackground;

    @Shadow private BiFunction<String, Integer, OrderedText> renderTextProvider;

    @Shadow protected abstract int getMaxLength();

    @Shadow @Nullable private Text placeholder;

    @Shadow @Nullable private String suggestion;

    private int borderColor = -1;

    @Shadow protected abstract void drawSelectionHighlight(DrawContext context, int x1, int y1, int x2, int y2);

    @Shadow public abstract void setEditable(boolean editable);

    @Shadow public abstract void setCursor(int cursor);

    public TextFieldWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Override
    public void setBorderColor(int color) {
        this.setEditable(true);
        this.setCursor(0);
        this.borderColor = color;
    }

    @Override
    public int getBorderColor() {
        return this.borderColor;
    }

    @Override
    public boolean isHovered() {
        return super.isHovered();
    }

    @Inject(method = "renderButton", at= @At("HEAD"), cancellable = true)
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        if (this.isVisible()) {
//            DungeonLib.LOGGER.info(this.width + " " + this.isFocused());
            int i;
            if (this.drawsBackground()) {
                i = this.isFocused() ? this.borderColor :
//                        this.borderColor;
                        -6250336;
                context.fill(this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY() + this.height + 1, i);
                context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, -16777216);
            }

            i = this.editable ? this.editableColor : this.uneditableColor;
            int j = this.selectionStart - this.firstCharacterIndex;
            int k = this.selectionEnd - this.firstCharacterIndex;
            String string = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
            boolean bl = j >= 0 && j <= string.length();
            boolean bl2 = this.isFocused() && this.focusedTicks / 6 % 2 == 0 && bl;
            int l = this.drawsBackground ? this.getX() + 4 : this.getX();
            int m = this.drawsBackground ? this.getY() + (this.height - 8) / 2 : this.getY();
            int n = l;
            if (k > string.length()) {
                k = string.length();
            }

            if (!string.isEmpty()) {
                String string2 = bl ? string.substring(0, j) : string;
                n = context.drawTextWithShadow(this.textRenderer, this.renderTextProvider.apply(string2, this.firstCharacterIndex), n, m, i);
            }

            boolean bl3 = this.selectionStart < this.text.length() || this.text.length() >= this.getMaxLength();
            int o = n;
            if (!bl) {
                o = j > 0 ? l + this.width : l;
            } else if (bl3) {
                --o;
                --n;
            }

            if (!string.isEmpty() && bl && j < string.length()) {
                context.drawTextWithShadow(this.textRenderer, this.renderTextProvider.apply(string.substring(j), this.selectionStart), n, m, i);
            }

            if (this.placeholder != null && string.isEmpty() && !this.isFocused()) {
                context.drawTextWithShadow(this.textRenderer, this.placeholder, n, m, i);
            }

            if (!bl3 && this.suggestion != null) {
                context.drawTextWithShadow(this.textRenderer, this.suggestion, o - 1, m, -8355712);
            }

            int var10003;
            int var10004;
            int var10005;
            if (bl2) {
                if (bl3) {
                    RenderLayer var10001 = RenderLayer.getGuiOverlay();
                    var10003 = m - 1;
                    var10004 = o + 1;
                    var10005 = m + 1;
                    Objects.requireNonNull(this.textRenderer);
                    context.fill(var10001, o, var10003, var10004, var10005 + 9, -3092272);
                } else {
                    context.drawTextWithShadow(this.textRenderer, "_", o, m, i);
                }
            }

            if (k != j) {
                int p = l + this.textRenderer.getWidth(string.substring(0, k));
                var10003 = m - 1;
                var10004 = p - 1;
                var10005 = m + 1;
                Objects.requireNonNull(this.textRenderer);
                this.drawSelectionHighlight(context, o, var10003, var10004, var10005 + 9);
            }

        }
        callbackInfo.cancel();
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
