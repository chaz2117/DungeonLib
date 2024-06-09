package net.chaz2117.dlib.networking.packets;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.networking.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class SaveSandboxNBTC2S {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.SAVE_SANDBOX_NBT, (server, player, handler, buf, responseSender) -> {
            DungeonLib.LOGGER.info("Got packet to write data");
            String sandboxID = buf.readString();
            BlockPos dimensions = buf.readBlockPos();
            BlockPos offset = buf.readBlockPos();
            String mode = buf.readString();
            int entityID = buf.readInt();
            SandboxEntity entity = (SandboxEntity) player.getWorld().getEntityById(entityID);

            server.execute(() -> {
                if (entity != null) {
                    DungeonLib.LOGGER.info("Executing write");
                    entity.sandboxID = sandboxID;
                    entity.setDimensions(dimensions);
                    entity.setOffset(offset);
                    entity.mode = mode;
                }
            });
        });
    }
}
