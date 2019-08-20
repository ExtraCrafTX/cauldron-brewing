package io.github.indicode.fabric.cauldronbrew.block;

import io.github.indicode.fabric.cauldronbrew.AbstractCauldronFluid;
import io.github.indicode.fabric.cauldronbrew.CauldronBrewing;
import io.github.indicode.fabric.cauldronbrew.recipe.ActiveCauldronRecipe;
import io.github.indicode.fabric.cauldronbrew.recipe.CauldronRecipeManager;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Indigo Amann
 */
public class BrewingCauldronBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {
    public BrewingCauldronBlockEntity() {
        super(CauldronBrewing.CAULDRON_BLOCK_ENTITY);
    }
    public int level = 0;
    public AbstractCauldronFluid fluid = null;
    public ActiveCauldronRecipe recipe = null;
    private boolean newTick = true;
    public boolean isFluidWater() {
        return fluid == null || CauldronRecipeManager.Fluid.WATER.equals(fluid);
    }
    public void setLevel(int level) {
        this.level = level;
        if (level == 0) fluid = null;
    }
    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        if(!world.isClient){
            setLevel((level%3)+1);
            fluid = CauldronRecipeManager.Fluid.WATER;
            markDirty();
            ((ServerWorld)world).method_14178().markForUpdate(blockPos_1);
        }
        return true;
        // ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
        // if (itemStack_1.isEmpty()) {
        //     return true;
        // } else {
        //     Item item_1 = itemStack_1.getItem();
        //     if (item_1 == Items.WATER_BUCKET) {
        //         if (level < 3 && isFluidWater()) {
        //             if (!playerEntity_1.abilities.creativeMode) {
        //                 playerEntity_1.setStackInHand(hand_1, new ItemStack(Items.BUCKET));
        //             }

        //             playerEntity_1.incrementStat(Stats.FILL_CAULDRON);
        //             setLevel(3);
        //             fluid = CauldronRecipeManager.Fluid.WATER;
        //             world_1.playSound((PlayerEntity)null, blockPos_1, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //             markDirty();
        //         }
        //         return true;
        //     } else if (item_1 == Items.BUCKET) {
        //         if (level == 3 && isFluidWater()) {
        //             if (!playerEntity_1.abilities.creativeMode) {
        //                 itemStack_1.decrement(1);
        //                 if (itemStack_1.isEmpty()) {
        //                     playerEntity_1.setStackInHand(hand_1, new ItemStack(Items.WATER_BUCKET));
        //                 } else if (!playerEntity_1.inventory.insertStack(new ItemStack(Items.WATER_BUCKET))) {
        //                     playerEntity_1.dropItem(new ItemStack(Items.WATER_BUCKET), false);
        //                 }
        //             }

        //             playerEntity_1.incrementStat(Stats.USE_CAULDRON);
        //             setLevel(0);
        //             fluid = null;
        //             world_1.playSound((PlayerEntity)null, blockPos_1, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //             markDirty();
        //         }
        //         return true;
        //     } else {
        //         ItemStack itemStack_4;
        //         if (item_1 == Items.GLASS_BOTTLE) {
        //             if (level > 0) {
        //                 if (!playerEntity_1.abilities.creativeMode) {
        //                     itemStack_4 = PotionUtil.setPotion(new ItemStack(Items.POTION), fluid.getReturnedPotion());
        //                     playerEntity_1.incrementStat(Stats.USE_CAULDRON);
        //                     itemStack_1.decrement(1);
        //                     if (itemStack_1.isEmpty()) {
        //                         playerEntity_1.setStackInHand(hand_1, itemStack_4);
        //                     } else if (!playerEntity_1.inventory.insertStack(itemStack_4)) {
        //                         playerEntity_1.dropItem(itemStack_4, false);
        //                     } else if (playerEntity_1 instanceof ServerPlayerEntity) {
        //                         ((ServerPlayerEntity)playerEntity_1).openContainer(playerEntity_1.playerContainer);
        //                     }
        //                 }

        //                 world_1.playSound((PlayerEntity)null, blockPos_1, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //                 setLevel(level - 1);
        //                 markDirty();
        //             }
        //             return true;
        //         } else if (item_1 == Items.POTION && (CauldronRecipeManager.getPotionFluid(PotionUtil.getPotion(itemStack_1)).equals(fluid) || fluid == null) ) {
        //             if (level < 3) {
        //                 if (!playerEntity_1.abilities.creativeMode) {
        //                     itemStack_4 = new ItemStack(Items.GLASS_BOTTLE);
        //                     playerEntity_1.incrementStat(Stats.USE_CAULDRON);
        //                     playerEntity_1.setStackInHand(hand_1, itemStack_4);
        //                     if (playerEntity_1 instanceof ServerPlayerEntity) {
        //                         ((ServerPlayerEntity)playerEntity_1).openContainer(playerEntity_1.playerContainer);
        //                     }
        //                 }
        //                 world_1.playSound((PlayerEntity)null, blockPos_1, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //                 if (level == 0) fluid = CauldronRecipeManager.getPotionFluid(PotionUtil.getPotion(itemStack_1));
        //                 setLevel(level + 1);
        //                 markDirty();
        //             }
        //             return true;
        //         } else {
        //             if (level > 0 && isFluidWater() && item_1 instanceof DyeableItem) {
        //                 DyeableItem dyeableItem_1 = (DyeableItem)item_1;
        //                 if (dyeableItem_1.hasColor(itemStack_1)) {
        //                     dyeableItem_1.removeColor(itemStack_1);
        //                     setLevel(level - 1);
        //                     playerEntity_1.incrementStat(Stats.CLEAN_ARMOR);
        //                     markDirty();
        //                     return true;
        //                 }
        //             }

        //             if (level > 0 && isFluidWater() && item_1 instanceof BannerItem) {
        //                 if (BannerBlockEntity.getPatternCount(itemStack_1) > 0) {
        //                     itemStack_4 = itemStack_1.copy();
        //                     itemStack_4.setCount(1);
        //                     BannerBlockEntity.loadFromItemStack(itemStack_4);
        //                     playerEntity_1.incrementStat(Stats.CLEAN_BANNER);
        //                     if (!playerEntity_1.abilities.creativeMode) {
        //                         itemStack_1.decrement(1);
        //                         setLevel(level - 1);
        //                     }

        //                     if (itemStack_1.isEmpty()) {
        //                         playerEntity_1.setStackInHand(hand_1, itemStack_4);
        //                     } else if (!playerEntity_1.inventory.insertStack(itemStack_4)) {
        //                         playerEntity_1.dropItem(itemStack_4, false);
        //                     } else if (playerEntity_1 instanceof ServerPlayerEntity) {
        //                         ((ServerPlayerEntity)playerEntity_1).openContainer(playerEntity_1.playerContainer);
        //                     }
        //                     markDirty();
        //                 }
        //                 return true;
        //             } else if (level > 0 && isFluidWater() && item_1 instanceof BlockItem) {
        //                 Block block_1 = ((BlockItem)item_1).getBlock();
        //                 if (block_1 instanceof ShulkerBoxBlock) {
        //                     ItemStack itemStack_5 = new ItemStack(Blocks.SHULKER_BOX, 1);
        //                     if (itemStack_1.hasTag()) {
        //                         itemStack_5.setTag(itemStack_1.getTag().method_10553());
        //                     }

        //                     playerEntity_1.setStackInHand(hand_1, itemStack_5);
        //                     setLevel(level - 1);
        //                     playerEntity_1.incrementStat(Stats.CLEAN_SHULKER_BOX);
        //                     markDirty();
        //                 }
        //                 return true;
        //             } else {
        //                 return false;
        //             }
        //         }
        //     }
        // }
    }

    public CompoundTag toTag(CompoundTag tag) {
        System.out.println("toTag");
        super.toTag(tag);

        tag.putInt("level", level);
        if (fluid != null) tag.putString("fluid", fluid.getIdentifier().toString());
        if (recipe != null) tag.put("recipe", recipe.toNBT());
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        System.out.println("fromTag");
        super.fromTag(tag);

        this.level = tag.getInt("level");
        if (tag.containsKey("fluid")) this.fluid = CauldronRecipeManager.FLUIDS.get(new Identifier(tag.getString("fluid")));
        if (tag.containsKey("recipe"))this.recipe = ActiveCauldronRecipe.fromNBT((CompoundTag) tag.getTag("recipe"));
    }

    @Override
    public void tick() {
        // List<Entity> items = this.world.getEntities(EntityType.ITEM, new Box(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.getPos().getX() + 1, this.getPos().getY() + 1, this.getPos().getZ() + 1), input -> true);
        // CauldronState state = new CauldronState(world, pos, fluid, recipe == null ? null : recipe.recipe, level, true);
        // for (Entity entity: items) {
        //     state.ingredients.add((ItemEntity) entity);
        // }
        // if (recipe == null) {
        //     AbstractCauldronRecipe r = CauldronRecipeManager.getRecipeToUse(state);
        //     if (r != null) {
        //         recipe = new ActiveCauldronRecipe(r);
        //         state.recipe = recipe.recipe;
        //         markDirty();

        //     }
        // } else {
        //     if (newTick) recipe.items.addAll(state.ingredients);
        //     recipe.update(state);
        //     if (recipe == null) markDirty();
        // }
        // if (state.level != this.level) {
        //     this.level = state.level;
        //     markDirty();
        // }
        // if (state.fluid != this.fluid) {
        //     this.fluid = state.fluid;
        //     markDirty();
        // }
        // if (state.recipe == null) this.recipe = null;
        // newTick = false;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(tag);
        System.out.println("Test");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        System.out.println("toClientTag");
        return toTag(tag);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        System.out.println("markDirty");
    }

}
