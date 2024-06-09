package net.chaz2117.dlib.init;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.Item.SandboxItem;
import net.chaz2117.dlib.block.SandboxBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    private static final Block SANDBOX_BASE = new Block(FabricBlockSettings.create());
    public static final Block SANDBOX = registerBlockWithItem("sandbox_block", SANDBOX_BASE, new SandboxItem(SANDBOX_BASE, new FabricItemSettings()));

    public static Block registerBlock(String name, Block block) {
        ModItems.registerItem(name, new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, new Identifier(DungeonLib.MODID, name), block);
    }
    public static Block registerBlockWithItem(String name, Block block, BlockItem blockItem) {
        ModItems.registerItem(name, blockItem);
        return Registry.register(Registries.BLOCK, new Identifier(DungeonLib.MODID, name), block);
    }

    public static void register() {}
}
