package net.chaz2117.dlib.init;

import net.chaz2117.dlib.networking.packets.SaveSandboxNBTC2S;
import net.chaz2117.dlib.networking.packets.SaveSandboxTypeC2S;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModNetworking {
    public static void registerServerPackets() {
        SaveSandboxNBTC2S.init();
        SaveSandboxTypeC2S.init();
    }
}
