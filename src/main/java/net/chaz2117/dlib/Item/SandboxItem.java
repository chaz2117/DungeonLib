package net.chaz2117.dlib.Item;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.chaz2117.dlib.init.ModEntities;
import net.chaz2117.dlib.util.GenericUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;

public class SandboxItem extends BlockItem {
    public SandboxItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos();
            Direction face = context.getSide();
            BlockPos correctedPos = GenericUtils.getFaceOffset(pos, face);
            int applyYDifFix = !context.getWorld().getBlockState(pos).isSolid() && face == Direction.UP ? -1: 0;

            correctedPos = correctedPos.add(0, applyYDifFix, 0);

            Box posCheck = new Box(correctedPos);
            if (world.isSpaceEmpty(posCheck) && world.getOtherEntities(null, posCheck).isEmpty()) {
                Consumer<SandboxEntity> consumer = EntityType.copier(world, context.getStack(), context.getPlayer());
                SandboxEntity newEntity = ModEntities.SANDBOX_ENTITY.create(world, context.getStack().getNbt(), consumer, correctedPos, SpawnReason.SPAWN_EGG, false, false);
                newEntity.setPosition(correctedPos.getX() + 0.5, correctedPos.getY() + 0.125, correctedPos.getZ() + 0.5);
                world.spawnEntityAndPassengers(newEntity);
                newEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                world.playSound(null, correctedPos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS);
                DungeonLib.LOGGER.info(newEntity.getBlockPos().toString() + correctedPos);
            }
        }

        return ActionResult.PASS;
    }
}

