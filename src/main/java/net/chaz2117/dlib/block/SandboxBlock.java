package net.chaz2117.dlib.block;

import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.init.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SandboxBlock extends Block {
    public SandboxBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//        SandboxEntity newEntity = new SandboxEntity(ModEntities.SANDBOX_ENTITY, world);
//        double yDif = world.getBlockState(pos).isSolid() ? 1.125 : 0.125;
//        newEntity.setPos(pos.getX() + 0.5, pos.getY() + yDif, pos.getZ() + 0.5);
//        world.spawnEntity(newEntity);
//        return false;
//    }
}
