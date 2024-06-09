package net.chaz2117.dlib.networking.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.networking.ModPackets;
import net.chaz2117.dlib.util.SandboxData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.storage.LevelStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveSandboxTypeC2S {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.SAVE_SANDBOX_TYPE, (server, player, handler, buf, responseSender) -> {
            DungeonLib.LOGGER.info("Got packet to write type");
            Identifier identifier = buf.readIdentifier();
            BlockPos dimensions = buf.readBlockPos();
            BlockPos offset = buf.readBlockPos();
            String mode = buf.readString();

            server.execute(() -> {
                Path runDirectory = new File(server.getRunDirectory().toString()).toPath();
                Path sandboxesDirectory = runDirectory.resolve("generated/sandboxes");
                if (!Files.exists(sandboxesDirectory)) {
                    try {
                        Files.createDirectories(sandboxesDirectory);
                        DungeonLib.LOGGER.info("Created generated/sandboxes in instance directory");
                    } catch (IOException e) {
                        player.sendMessage(Text.translatable("dlib.sandbox.wrote_type_fail"));
                        DungeonLib.LOGGER.error("Could not create generated/sandboxes in instance directory");
                    }
                }

                Path namespace = sandboxesDirectory.resolve(identifier.getNamespace());
                if (!Files.exists(namespace)) {
                    try {
                        Files.createDirectories(namespace);
                        DungeonLib.LOGGER.info("Created " + identifier.getNamespace() + " in instance directory");
                    } catch (IOException e) {
                        player.sendMessage(Text.translatable("dlib.sandbox.wrote_type_fail"));
                        DungeonLib.LOGGER.error("Could not create " + identifier.getNamespace() + " in instance directory");
                    }
                }

                Path filePath = namespace.resolve(identifier.getPath() + ".json");

                SandboxData data = new SandboxData(dimensions, offset, mode);


                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try (FileWriter writer = new FileWriter(filePath.toFile())) {
                    gson.toJson(data, writer);
                    player.sendMessage(Text.translatable("dlib.sandbox.wrote_type_success"));
                } catch (IOException e) {
                    DungeonLib.LOGGER.error("Failed to write JSON file: " + filePath);
                    player.sendMessage(Text.translatable("dlib.sandbox.wrote_type_fail"));
                    e.printStackTrace();
                }
            });
        });
    }
}
