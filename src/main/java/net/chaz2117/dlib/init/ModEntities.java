package net.chaz2117.dlib.init;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<SandboxEntity> SANDBOX_ENTITY = registerEntity("sandbox", FabricEntityTypeBuilder.create(SpawnGroup.MISC, SandboxEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)));

    public static <T extends Entity> EntityType<T> registerEntity(String name, FabricEntityTypeBuilder builder) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(DungeonLib.MODID, name), builder.build());
    }
}
