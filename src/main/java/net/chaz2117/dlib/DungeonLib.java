package net.chaz2117.dlib;

import net.chaz2117.dlib.init.ModBlocks;
import net.chaz2117.dlib.init.ModItems;
import net.chaz2117.dlib.init.ModNetworking;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DungeonLib implements ModInitializer {
    public static final String MODID = "dlib";
    public static final Logger LOGGER = LoggerFactory.getLogger("Resourceful Dungeons");

    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();

        ModNetworking.registerServerPackets();
    }
}
