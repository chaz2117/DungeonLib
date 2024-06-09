package net.chaz2117.dlib.screen.screens;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.networking.ModPackets;
import net.chaz2117.dlib.util.ITextFieldWidgetBorderAccessor;
import net.chaz2117.dlib.util.IntTextField;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SandboxScreen extends Screen {
    private SandboxEntity entity;

    Text TITLE_PATH = Text.translatable("gui.sandbox.sandbox_id");
    Text TITLE_DIMENSIONS = Text.translatable("gui.sandbox.sandbox_dimensions");
    Text TITLE_OFFSET = Text.translatable("gui.sandbox.sandbox_offset");

    Text ALLOW = Text.translatable("gui.sandbox.allow");
    Text PREVENT = Text.translatable("gui.sandbox.prevent");
    Text SAVE = Text.translatable("gui.sandbox.save");

    String MODE;

    private TextFieldWidget sandboxID;
    private IntTextField dimensionX;
    private IntTextField dimensionY;
    private IntTextField dimensionZ;
    private IntTextField offsetX;
    private IntTextField offsetY;
    private IntTextField offsetZ;
    private ButtonWidget allowButton;
    private ButtonWidget preventButton;
    private ButtonWidget saveButton;

    private final List<Object> buttons = new ArrayList<>();

    public SandboxScreen(Text title, SandboxEntity entity) {
        super(title);
        this.entity = entity;
        this.MODE = entity.mode;
    }

    @Override
    protected void init() {
        int centredX = this.width / 2;
        int centredY = this.height / 2;

        sandboxID =new TextFieldWidget(this.textRenderer, centredX - 57, centredY - 48, 114, 20, Text.translatable("gui.sandbox.sandbox_id_input"));
        sandboxID.setMaxLength(32);
        sandboxID.setText(entity.sandboxID);

        dimensionX = new IntTextField(this.textRenderer, centredX - 57, centredY - 2, 30, 20, Text.translatable("gui.sandbox.sandbox_dimension_x"));
        dimensionX.setMaxLength(3);
        dimensionX.setPlaceholder(Text.literal("X"));
        dimensionX.setText(entity.dimensionX == 0 ? "" : "" + entity.dimensionX);
        dimensionY = new IntTextField(this.textRenderer, centredX - 15, centredY - 2, 30, 20, Text.translatable("gui.sandbox.sandbox_dimension_y"));
        dimensionY.setMaxLength(3);
        dimensionY.setPlaceholder(Text.literal("Y"));
        dimensionY.setText(entity.dimensionY == 0 ? "" : "" + entity.dimensionY);
        dimensionZ = new IntTextField(this.textRenderer, centredX + 27 , centredY - 2, 30, 20, Text.translatable("gui.sandbox.sandbox_dimension_z"));
        dimensionZ.setMaxLength(3);
        dimensionZ.setPlaceholder(Text.literal("Z"));
        dimensionZ.setText(entity.dimensionZ == 0 ? "" : "" + entity.dimensionZ);

        offsetX = new IntTextField(this.textRenderer, centredX - 57, centredY + 42, 30, 20, Text.translatable("gui.sandbox.sandbox_offset_x"));
        offsetX.setMaxLength(3);
        offsetX.setPlaceholder(Text.literal("X"));
        offsetX.setText(entity.offsetX == 0 ? "" : "" + entity.offsetX);
        offsetY = new IntTextField(this.textRenderer, centredX - 15, centredY + 42, 30, 20, Text.translatable("gui.sandbox.sandbox_offset_y"));
        offsetY.setMaxLength(3);
        offsetY.setPlaceholder(Text.literal("Y"));
        offsetY.setText(entity.offsetY == 0 ? "" : "" + entity.offsetY);
        offsetZ = new IntTextField(this.textRenderer, centredX + 27 , centredY + 42, 30, 20, Text.translatable("gui.sandbox.sandbox_offset_z"));
        offsetZ.setMaxLength(3);
        offsetZ.setPlaceholder(Text.literal("Z"));
        offsetZ.setText(entity.offsetZ == 0 ? "" : "" + entity.offsetZ);

        allowButton = ButtonWidget.builder(ALLOW, (button) -> {
            allowButton.visible = false;
            preventButton.visible = true;
            MODE = "PREVENT";
            allowButton.setFocused(false);
        }).dimensions(centredX - 57, centredY + 70, 60, 20).build();

        preventButton = ButtonWidget.builder(PREVENT, (button) -> {
            preventButton.visible = false;
            allowButton.visible = true;
            MODE = "ALLOW";
            preventButton.setFocused(false);
        }).dimensions(centredX - 57, centredY + 70, 60, 20).build();

        saveButton = ButtonWidget.builder(SAVE, (button) -> {
            boolean result = saveSandbox();
            if (result) {
                this.close();
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("gui.sandbox.invalid_path"));
                sandboxID.setFocusUnlocked(false);
                sandboxID.setFocused(true);
                DungeonLib.LOGGER.info(this.getFocused() + " " +  saveButton.isFocused());
            }
        }).dimensions(centredX + 24, centredY + 70, 35, 20).build();

        addSelectableChild(sandboxID).setVisible(true);
        addSelectableChild(dimensionX).setVisible(true);
        addSelectableChild(dimensionY).setVisible(true);
        addSelectableChild(dimensionZ).setVisible(true);
        addSelectableChild(offsetX).setVisible(true);
        addSelectableChild(offsetY).setVisible(true);
        addSelectableChild(offsetZ).setVisible(true);

        addDrawableChild(allowButton);
        addDrawableChild(preventButton);
        addDrawableChild(saveButton);

        if (MODE.equals("ALLOW")) {
            preventButton.visible = false;
        } else {
            allowButton.visible = false;
        }

        buttons.add(sandboxID);
        buttons.add(dimensionX);
        buttons.add(dimensionY);
        buttons.add(dimensionZ);
        buttons.add(offsetX);
        buttons.add(offsetY);
        buttons.add(offsetZ);
        buttons.add(allowButton);
        buttons.add(preventButton);
        buttons.add(saveButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawTextWithShadow(this.textRenderer, this.TITLE_PATH, this.width / 2 - 57, this.height / 2 - 60, 16777215);
        this.sandboxID.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer, this.TITLE_DIMENSIONS, this.width / 2 - 57, this.height / 2 - 16, 16777215);
        this.dimensionX.render(context, mouseX, mouseY, delta);
        this.dimensionY.render(context, mouseX, mouseY, delta);
        this.dimensionZ.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer, this.TITLE_OFFSET, this.width / 2 - 57, this.height / 2 + 30, 16777215);
        this.offsetX.render(context, mouseX, mouseY, delta);
        this.offsetY.render(context, mouseX, mouseY, delta);
        this.offsetZ.render(context, mouseX, mouseY, delta);

        this.allowButton.render(context, mouseX, mouseY, delta);
        this.preventButton.render(context, mouseX, mouseY, delta);
        this.saveButton.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

//        DungeonLib.LOGGER.info(this.allowButton.getWidth() + " " + this.allowButton.isFocused());
//        DungeonLib.LOGGER.info(this.preventButton.getWidth() + " " + this.preventButton.isFocused());
//        DungeonLib.LOGGER.info(this.saveButton.getWidth() + " " + this.saveButton.isFocused());
    }

    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        int dimX = dimensionX.getText() == "" ? 0 : Integer.parseInt(dimensionX.getText());
        int dimY = dimensionY.getText() == "" ? 0 : Integer.parseInt(dimensionY.getText());
        int dimZ = dimensionZ.getText() == "" ? 0 : Integer.parseInt(dimensionZ.getText());
        BlockPos dimensions = new BlockPos(dimX, dimY, dimZ);

        int offX = offsetX.getText() == "" ? 0 : Integer.parseInt(offsetX.getText());
        int offY = offsetY.getText() == "" ? 0 : Integer.parseInt(offsetY.getText());
        int offZ = offsetZ.getText() == "" ? 0 : Integer.parseInt(offsetZ.getText());
        BlockPos offset = new BlockPos(offX, offY, offZ);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(sandboxID.getText().isEmpty() ? "" : sandboxID.getText());
        buf.writeBlockPos(dimensions);
        buf.writeBlockPos(offset);
        buf.writeString(MODE);
        buf.writeInt(this.entity.getId());

        ClientPlayNetworking.send(ModPackets.SAVE_SANDBOX_NBT, buf);
        super.close();
    }

    private boolean saveSandbox() {
        int dimX = dimensionX.getText() == "" ? 0 : Integer.parseInt(dimensionX.getText());
        int dimY = dimensionY.getText() == "" ? 0 : Integer.parseInt(dimensionY.getText());
        int dimZ = dimensionZ.getText() == "" ? 0 : Integer.parseInt(dimensionZ.getText());
        BlockPos dimensions = new BlockPos(dimX, dimY, dimZ);

        int offX = offsetX.getText() == "" ? 0 : Integer.parseInt(offsetX.getText());
        int offY = offsetY.getText() == "" ? 0 : Integer.parseInt(offsetY.getText());
        int offZ = offsetZ.getText() == "" ? 0 : Integer.parseInt(offsetZ.getText());
        BlockPos offset = new BlockPos(offX, offY, offZ);

        if (sandboxID.getText().isEmpty()) {
            ((ITextFieldWidgetBorderAccessor) sandboxID).setBorderColor(-65536);
            return false;
        } else {
            ((ITextFieldWidgetBorderAccessor) sandboxID).setBorderColor(-1);
            Identifier identifier;
            String sandboxIdentifier = sandboxID.getText();

            if (sandboxIdentifier.contains(":")) {
                String[] sandboxIdentifierParts = sandboxIdentifier.split(":");
                if (sandboxIdentifierParts.length == 2) {
                    identifier = new Identifier(sandboxIdentifierParts[0], sandboxIdentifierParts[1]);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeIdentifier(identifier);
                    buf.writeBlockPos(dimensions);
                    buf.writeBlockPos(offset);
                    buf.writeString(MODE);
                    ClientPlayNetworking.send(ModPackets.SAVE_SANDBOX_TYPE, buf);

                    entity.sandboxID = identifier.toString();
                    entity.setDimensions(dimensions);
                    entity.setOffset(offset);
                    entity.mode = MODE;
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void tick() {
        allowButton.setFocused(false);
        preventButton.setFocused(false);
        saveButton.setFocused(false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean result = super.mouseClicked(mouseX, mouseY, button);

        // Check if the click occurred over any GUI element
        boolean clickedOverElement = false;
        for (Object element : this.buttons) {
            if (((ClickableWidget) element).isMouseOver(mouseX, mouseY)) {
                clickedOverElement = true;
                break;
            }
        }

        // Unfocus all GUI elements if the click did not occur over any element
        if (!clickedOverElement) {
            for (Object element : this.buttons) {
                ((ClickableWidget) element).setFocused(false);
            }
        }

        return result;
    }
}
