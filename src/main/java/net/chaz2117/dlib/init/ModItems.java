package net.chaz2117.dlib.init;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.Item.SandboxItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
//    public static SandboxItem SANDBOX_ITEM = (SandboxItem) registerItem("sandbox", new SandboxItem(new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DungeonLib.MODID, name), item);
    }

    public static void register() {}
}
