package com.example.itemrenderfix.mixin;

import com.example.itemrenderfix.util.InstantSwapExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void onDoAttack(CallbackInfoReturnable<Boolean> info) {
        Minecraft client = (Minecraft) (Object) this;

        if (client.player == null) return;

        boolean holdingWindCharge = client.player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.WIND_CHARGE);
        boolean hasTargetInRange = client.hitResult != null && client.hitResult.getType() == HitResult.Type.ENTITY;

        if (holdingWindCharge && !hasTargetInRange) {
            InstantSwapExecutor.executeInstantSwap(client);
            
            info.setReturnValue(false);
            info.cancel();
        }
    }
}
