package net.chaz2117.dlib.init;

import net.chaz2117.dlib.DungeonLib;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer SANDBOX =  new EntityModelLayer(new Identifier(DungeonLib.MODID, "sandbox"), "main");
}
