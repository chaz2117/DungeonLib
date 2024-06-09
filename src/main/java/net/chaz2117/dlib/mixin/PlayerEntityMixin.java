package net.chaz2117.dlib.mixin;

import net.chaz2117.dlib.DungeonLib;
import net.chaz2117.dlib.entity.SandboxEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity{

    @Shadow private ItemStack selectedItem;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "attack", at= @At("HEAD"), cancellable = true)
    public void attackSandbox(Entity target, CallbackInfo ci) {
        DungeonLib.LOGGER.info("Attack on: " + (this.getWorld().isClient() ? "client" : "server"));
        if (target instanceof SandboxEntity) {
            if (!this.selectedItem.isIn(ItemTags.SWORDS)) {
                target.damage(this.getDamageSources().playerAttack((PlayerEntity) (Object) this), 1);
                ci.cancel();
            }
        }
    }
}
