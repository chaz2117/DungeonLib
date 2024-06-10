package net.chaz2117.dlib.entity.renderer;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.entity.model.SandboxEntityModel;
import net.chaz2117.dlib.init.ModBlocks;
import net.chaz2117.dlib.init.ModModelLayers;
import net.chaz2117.dlib.util.EtherealRenderLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SandboxEntityRenderer extends EntityRenderer<SandboxEntity> {
    Identifier TEXTURE = new Identifier(DungeonLib.MODID, "textures/entity/sandbox.png");
    protected SandboxEntityModel model;

    public SandboxEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new SandboxEntityModel(ctx.getPart(ModModelLayers.SANDBOX));
    }

//    @Override
    public void render(SandboxEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, 15728640);
        if (MinecraftClient.getInstance().player.isHolding(ModBlocks.SANDBOX.asItem())) {
            matrices.push();

            matrices.translate(0f, -0.625, 0);

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(EtherealRenderLayer.getEthereal(TEXTURE));
            this.model.render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            matrices.pop();
        }
    }



    @Override
    public Identifier getTexture(SandboxEntity entity) {
        return TEXTURE;
    }
}
