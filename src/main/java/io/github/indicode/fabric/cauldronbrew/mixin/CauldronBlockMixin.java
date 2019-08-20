package io.github.indicode.fabric.cauldronbrew.mixin;

import io.github.indicode.fabric.cauldronbrew.CauldronBrewing;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Indigo Amann
 */
@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
    @Inject(method = "activate", at = @At("INVOKE"))
    public void activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable ci) {
        ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
        if (itemStack_1.getItem() == Items.BLAZE_POWDER) {
            world_1.setBlockState(blockPos_1, CauldronBrewing.BREWING_CAULDRON.getDefaultState());
            world_1.playSound(null, blockPos_1, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.5f, 1f);
        }
    }
}
