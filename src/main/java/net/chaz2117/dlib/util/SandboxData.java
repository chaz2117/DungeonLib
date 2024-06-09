package net.chaz2117.dlib.util;

import net.minecraft.util.math.BlockPos;

public class SandboxData {
    BlockPos dimensions;
    BlockPos offset;
    String mode;

    public SandboxData(BlockPos dimensions, BlockPos offset, String mode) {
        this.dimensions = dimensions;
        this.offset = offset;
        this.mode = mode;
    }
}
