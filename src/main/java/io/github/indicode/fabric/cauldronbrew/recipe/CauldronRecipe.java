package io.github.indicode.fabric.cauldronbrew.recipe;

import com.google.common.collect.Lists;
import io.github.indicode.fabric.cauldronbrew.AbstractCauldronFluid;
import io.github.indicode.fabric.cauldronbrew.CauldronState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Random;

/**
 * @author Indigo Amann
 */
public abstract class CauldronRecipe extends AbstractCauldronRecipe {
    public final int completionTime;
    public final BrewHandler[] handlers;
    public final Identifier identifier;
    public CauldronRecipe(Identifier identifier, int completionTime, BrewHandler... handlers) {
        this.completionTime = completionTime;
        this.handlers = handlers;
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void onBrewTick(ActiveCauldronRecipe recipe, CauldronState state) {
        for (BrewHandler handler: handlers) {
            if (handler == null) continue;
            if (handler.stage == BrewHandler.Stage.TICK) handler.run(state, recipe);
        }
        if (!recipe.newItems.isEmpty()) for (BrewHandler handler: handlers) {
            if (handler == null) continue;
            if (handler.stage == BrewHandler.Stage.INGREDIENT_ADDED) handler.run(state, recipe);
        }
    }

    @Override
    public void onBrewEnd(ActiveCauldronRecipe recipe, CauldronState state) {
        for (BrewHandler handler: handlers) {
            if (handler == null) continue;
            if (handler.stage == BrewHandler.Stage.END) handler.run(state, recipe);
        }
    }

    @Override
    public int getTicksToComplete() {
        return completionTime;
    }

    public static abstract class BrewHandler {
        public static enum Stage {
            TICK, END, INGREDIENT_ADDED
        }
        public Stage stage;
        public abstract void run(CauldronState state,  ActiveCauldronRecipe recipe);
        public BrewHandler(Stage stage) {
            this.stage = stage;
        }
        public static class ClearIngredients extends BrewHandler {
            public List<ItemConvertible> exclusions;
            public ClearIngredients(Stage stage, ItemConvertible... exclusions) {
                super(stage);
                this.exclusions = Lists.newArrayList(exclusions);
            }

            @Override
            public void run(CauldronState state, ActiveCauldronRecipe recipe) {
                for (ItemEntity item: state.ingredients) {
                    if (!exclusions.contains(item.getStack().getItem())) item.kill();
                }
            }
        }
        public static class ChangeFluid extends BrewHandler {
            public AbstractCauldronFluid fluid;
            public ChangeFluid(Stage stage, AbstractCauldronFluid fluid) {
                super(stage);
                this.fluid = fluid;
            }

            @Override
            public void run(CauldronState state, ActiveCauldronRecipe recipe) {
                state.fluid = fluid;
            }
        }
        public static class Ruin extends BrewHandler {
            public List<ItemConvertible> exclusions;
            public boolean killItems;
            public Ruin(boolean killItems, ItemConvertible... exclusions) {
                super(Stage.INGREDIENT_ADDED);
                this.exclusions = Lists.newArrayList(exclusions);
                this.killItems = killItems;
            }

            @Override
            public void run(CauldronState state, ActiveCauldronRecipe recipe) {
                boolean uhoh = false;
                for (ItemEntity item: recipe.newItems) {
                    if (!exclusions.contains(item.getStack().getItem()))uhoh = true;
                }
                if (uhoh) {
                    state.world.playSound(state.pos.getX(), state.pos.getY(), state.pos.getZ(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.BLOCKS, 5f, 1f, false);
                    Random random = new Random();
                    state.fluid = new AbstractCauldronFluid[]{CauldronRecipeManager.Fluid.MUNDANE, CauldronRecipeManager.Fluid.THICK}[random.nextInt(1)];
                    for (ItemEntity item: state.ingredients) {
                        if (!exclusions.contains(item.getStack().getItem())) item.kill();
                    }
                }
            }
        }
    }
}
