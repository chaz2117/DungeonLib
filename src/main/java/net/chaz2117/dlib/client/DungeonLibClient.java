package net.chaz2117.dlib.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.entity.model.SandboxEntityModel;
import net.chaz2117.dlib.entity.renderer.SandboxEntityRenderer;
import net.chaz2117.dlib.init.ModEntities;
import net.chaz2117.dlib.init.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DungeonLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SANDBOX, SandboxEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SANDBOX_ENTITY, SandboxEntityRenderer::new);

//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((entityType, entityRenderer, registrationHelper, context) -> {
//            if (context.en) {
//
//            }
//        }));


//        WorldRenderEvents.AFTER_ENTITIES.register((context -> {
//            MinecraftClient client = MinecraftClient.getInstance();
//            PlayerEntity player = client.player;
//            ClientWorld world = client.world;
//
//            world.getEntities().forEach(entity -> {
//                if (entity instanceof SandboxEntity) {
//                    context.matrixStack().push();
//                    RenderSystem.disableDepthTest();
//
//                    context.matrixStack().translate(0f, -0.625, 0);
//
//                    client.getEntityRenderDispatcher().render((SandboxEntity) entity, entity.getX(), entity.getY(), entity.getZ(), player.getYaw(), context.tickDelta(), context.matrixStack(), context.consumers(), 15728640);
//
////                    EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
////                    EntityRendererFactory.Context entityRenderContext = new EntityRendererFactory.Context(entityRenderDispatcher, client.getItemRenderer(), client.getBlockRenderManager(), entityRenderDispatcher.getHeldItemRenderer(), client.getResourceManager(), client.getEntityModelLoader(), client.textRenderer);
////                    SandboxEntityRenderer renderer = new SandboxEntityRenderer(entityRenderContext);
////                    renderer.render((SandboxEntity) entity, player.getYaw(), context.tickDelta(), context.matrixStack(), context.consumers(), 15728640);
//
//                    RenderSystem.enableDepthTest();
//                    context.matrixStack().pop();
//                }
//            });
////            for (SandboxEntity entity : context.world().getEntitiesByType(SandboxEntity))
//        }));
    }
}
