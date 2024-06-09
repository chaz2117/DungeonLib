package net.chaz2117.dlib.entity;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.init.ModBlocks;
import net.chaz2117.dlib.screen.screens.SandboxScreen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SandboxEntity extends Entity {
    public String sandboxID = "";
    public int dimensionX = 0;
    public int dimensionY = 0;
    public int dimensionZ = 0;
    public int offsetX = 0;
    public int offsetY = 0;
    public int offsetZ = 0;
    public String mode = "ALLOW";

    public SandboxEntity(EntityType<?> type, World world) {
        super(type, world);
    }


    public void setDimensions(BlockPos pos) {
        this.dimensionX = pos.getX();
        this.dimensionY = pos.getY();
        this.dimensionZ = pos.getZ();
    }

    public void setOffset(BlockPos offset) {
        this.offsetX = offset.getX();
        this.offsetY = offset.getY();
        this.offsetZ = offset.getZ();
    }


    /* Necessary Entity overrides */

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected void pushOutOfBlocks(double x, double y, double z) {
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public boolean doesNotCollide(double offsetX, double offsetY, double offsetZ) {
        return true;
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        if (movementType != MovementType.PISTON && movementType != MovementType.SHULKER_BOX && movementType != MovementType.SHULKER)
            super.move(movementType, movement);
    }

    @Override
    protected void checkBlockCollision() {
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean collidesWithStateAtPos(BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!player.isSneaking()) {
            if (player.getWorld().isClient) {
                if (player.isHolding(ModBlocks.SANDBOX.asItem())) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    client.execute(() -> {
                        client.setScreen(new SandboxScreen(Text.translatable("gui.sandbox.title"), this
                        ));
                    });
                }
            }
        }
        return super.interact(player, hand);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved() && MinecraftClient.getInstance().player.isHolding(ModBlocks.SANDBOX.asItem());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS);
            createBlockBreakParticles();
            this.discard();
            return true;
        }
    }

    private void createBlockBreakParticles() {
        for (int i = 0; i < 8; ++i) {
            double offsetX = this.random.nextGaussian() * 0.02D;
            double offsetY = this.random.nextGaussian() * 0.02D;
            double offsetZ = this.random.nextGaussian() * 0.02D;
            this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                    this.getX() + (double)(this.random.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(),
                    this.getY() + (double)(this.random.nextFloat() * this.getHeight()),
                    this.getZ() + (double)(this.random.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(),
                    offsetX, offsetY, offsetZ);
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Path")) this.sandboxID = nbt.getString("Path");
        if (nbt.contains("DimensionX")) this.dimensionX = nbt.getInt("DimensionX");
        if (nbt.contains("DimensionY")) this.dimensionY = nbt.getInt("DimensionY");
        if (nbt.contains("DimensionZ")) this.dimensionZ = nbt.getInt("DimensionZ");
        if (nbt.contains("OffsetX")) this.offsetX = nbt.getInt("OffsetX");
        if (nbt.contains("OffsetY")) this.offsetY = nbt.getInt("OffsetY");
        if (nbt.contains("OffsetZ")) this.offsetZ = nbt.getInt("OffsetZ");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.sandboxID != null) nbt.putString("Path", this.sandboxID);
        if (this.dimensionX != 0) nbt.putInt("DimensionX", this.dimensionX);
        if (this.dimensionY != 0) nbt.putInt("DimensionY", this.dimensionY);
        if (this.dimensionZ != 0) nbt.putInt("DimensionZ", this.dimensionZ);
        if (this.offsetX != 0) nbt.putInt("OffsetX", this.offsetX);
        if (this.offsetY != 0) nbt.putInt("OffsetY", this.offsetY);
        if (this.offsetZ != 0) nbt.putInt("OffsetZ", this.offsetZ);
    }
}
