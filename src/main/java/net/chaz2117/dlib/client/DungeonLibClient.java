package net.chaz2117.dlib.client;

import net.chaz2117.dlib.entity.model.SandboxEntityModel;
import net.chaz2117.dlib.entity.renderer.SandboxEntityRenderer;
import net.chaz2117.dlib.init.ModEntities;
import net.chaz2117.dlib.init.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class DungeonLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SANDBOX, SandboxEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SANDBOX_ENTITY, SandboxEntityRenderer::new);
    }
}
