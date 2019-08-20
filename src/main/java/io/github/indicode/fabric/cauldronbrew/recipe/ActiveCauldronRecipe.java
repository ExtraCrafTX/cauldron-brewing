package io.github.indicode.fabric.cauldronbrew.recipe;

import io.github.indicode.fabric.cauldronbrew.CauldronState;
import io.github.indicode.fabric.cauldronbrew.recipe.AbstractCauldronRecipe;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Indigo Amann
 */
public class ActiveCauldronRecipe {
    public final AbstractCauldronRecipe recipe;
    public int ticksUntilComplete;
    public List<ItemEntity> items = new ArrayList<>();
    public List<ItemEntity> newItems = new ArrayList<>();
    public ActiveCauldronRecipe(AbstractCauldronRecipe recipe) {
        this.recipe = recipe;
        this.ticksUntilComplete = recipe.getTicksToComplete();
    }
    public void update(CauldronState state) {
        if (ticksUntilComplete < 0) {
            state.recipe = null;
        } else if (ticksUntilComplete-- == 0) {
            recipe.onBrewEnd(this, state);
            state.world.playSound(null, state.pos, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 15f, 1f);
        } else {
            if (state.ingredients.size() > items.size()) for (ItemEntity itemEntity : state.ingredients) {
                if (!items.contains(itemEntity)) {
                    newItems.add(itemEntity);
                    items.add(itemEntity);
                }
            }
            recipe.onBrewTick(this, state);
            if (ticksUntilComplete > 30 && state.world.random.nextInt(6) == 0)state.world.playSound(null, state.pos, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 1.3f, 1f);
            newItems.clear();
        }
    }
    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("ticksLeft", ticksUntilComplete);
        tag.putString("recipe", recipe.getIdentifier().toString());
        return tag;
    }
    public static ActiveCauldronRecipe fromNBT(CompoundTag tag) {
        ActiveCauldronRecipe recipe = new ActiveCauldronRecipe(CauldronRecipeManager.RECIPES.get(new Identifier(tag.getString("recipe"))));
        recipe.ticksUntilComplete = tag.getInt("ticksLeft");
        return recipe;
    }
}
