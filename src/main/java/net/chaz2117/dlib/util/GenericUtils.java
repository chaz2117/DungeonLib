package net.chaz2117.dlib.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chaz2117.dlib.DungeonLib;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class GenericUtils {
    public static BlockPos getFaceOffset(BlockPos pos, Direction face) {
        if (face == Direction.NORTH) {
            return pos.add(0, 0, -1);
        } else if (face == Direction.EAST) {
            return pos.add(1, 0, 0);
        } else if (face == Direction.SOUTH) {
            return pos.add(0, 0, 1);
        } else if (face == Direction.WEST) {
            return pos.add(-1, 0, 0);
        } else if (face == Direction.UP) {
            return pos.add(0, 1, 0);
        } else if (face == Direction.DOWN) {
            return pos.add(0, -1, 0);
        }

        return pos;
    }

//    public static boolean entityAtLocation(World world, BlockPos pos) {
//        Box box = new Box(pos, pos);
//        DungeonLib.LOGGER.info(world.getOtherEntities(null, box).toString());
//        List<Entity> entities = world.getOtherEntities(null, box, entity -> true);
//
//        return entities.isEmpty();
//    }
}
