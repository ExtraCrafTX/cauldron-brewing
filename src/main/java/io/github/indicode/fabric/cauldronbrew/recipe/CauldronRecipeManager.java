package io.github.indicode.fabric.cauldronbrew.recipe;

import com.google.common.collect.Lists;
import io.github.indicode.fabric.cauldronbrew.AbstractCauldronFluid;
import io.github.indicode.fabric.cauldronbrew.CauldronFluid;
import io.github.indicode.fabric.cauldronbrew.CauldronState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Indigo Amann
 */
public class CauldronRecipeManager {
    public static final Map<Identifier, AbstractCauldronRecipe> RECIPES = new HashMap<>();
    public static final Map<Identifier, AbstractCauldronFluid> FLUIDS = new HashMap<>();
    public static final Map<Potion, AbstractCauldronFluid> FLUID_POTION_MAP = new HashMap<>();


    public static AbstractCauldronFluid addFluid(AbstractCauldronFluid fluid, Potion potion) {
        FLUIDS.put(fluid.getIdentifier(), fluid);
        FLUID_POTION_MAP.put(potion, fluid);
        return fluid;
    }
    public static AbstractCauldronFluid addFluid(AbstractCauldronFluid fluid) {
        return addFluid(fluid, fluid.getReturnedPotion());
    }
    public static AbstractCauldronFluid getPotionFluid(Potion potion) {
        if (FLUID_POTION_MAP.containsKey(potion)) return FLUID_POTION_MAP.get(potion);
        else return addFluid(new CauldronFluid(potion));
    }
    public static AbstractCauldronRecipe getRecipeToUse(CauldronState state) {
        List<AbstractCauldronRecipe> passTest = new ArrayList<>();
        for (AbstractCauldronRecipe recipe: RECIPES.values()) if (recipe.applies(state)) passTest.add(recipe);
        int time = Integer.MAX_VALUE;
        AbstractCauldronRecipe winner = null;
        for (AbstractCauldronRecipe recipe: passTest) if (recipe.getTicksToComplete() < time) {
            time = recipe.getTicksToComplete();
            winner = recipe;
        }
        if (winner != null)System.out.println("PICKED: " + winner.getIdentifier());
        return winner;
    }

    public static void addRecipe(AbstractCauldronRecipe recipe) {
        RECIPES.put(recipe.getIdentifier(), recipe);
    }
    public static void addRecipe(Identifier identifier, AbstractCauldronFluid base, AbstractCauldronFluid result, int time, boolean extraItemsRuins, ItemStack... ingredients) {
        addRecipe(new CauldronRecipe(identifier, time, extraItemsRuins ? new CauldronRecipe.BrewHandler.Ruin(true) : null,
                new CauldronRecipe.BrewHandler.ClearIngredients(CauldronRecipe.BrewHandler.Stage.END),
                new CauldronRecipe.BrewHandler.ChangeFluid(CauldronRecipe.BrewHandler.Stage.END, result)) {

            @Override
            public boolean applies(CauldronState state) {
                List<ItemStack> ingredientList = Lists.newArrayList(ingredients);
                ItemStack toRemove = null;
                for (ItemEntity itemEntity: state.ingredients) {
                    for (ItemStack stack : ingredientList) {
                        if (itemEntity.getStack().getItem() == stack.getItem() && itemEntity.getStack().getCount() >= stack.getCount()) {
                            toRemove = stack;
                            break;
                        }
                    }
                    ingredientList.remove(toRemove);
                }
                return state.fluid != null && state.fluid.equals(base) && ingredientList.size() == 0;
            }
        });
    }
    public static void addRecipe(Potion base, Potion result, ItemConvertible item) {
        AbstractCauldronFluid basef = getPotionFluid(base);
        AbstractCauldronFluid resultf = getPotionFluid(result);
        addRecipe(new Identifier("brewing_" + basef.getIdentifier().toString().replace(":", ".") + "_" + resultf.getIdentifier().toString().replace(":", ".") + "_" + Registry.ITEM.getId(item.asItem()).toString().replace(":", "."))
                , basef, resultf, 150/*TODO*/, true, new ItemStack(item));
    }

    public static int getPotionColor(Potion potion) {
        return potion.getEffects().isEmpty() ? 3694022 : PotionUtil.getColor(potion);
    }

    public static class Fluid {
        public static AbstractCauldronFluid WATER, MUNDANE, THICK, AWKWARD;
        public static void init() {
            WATER = addFluid(new CauldronFluid(Potions.WATER));
            MUNDANE = addFluid(new CauldronFluid(Potions.MUNDANE));
            THICK = addFluid(new CauldronFluid(Potions.THICK));
            AWKWARD = addFluid(new CauldronFluid(Potions.AWKWARD));
        }
    }
    public static void init() {
        Fluid.init();
        Registry.POTION.forEach(CauldronRecipeManager::getPotionFluid);
    }
}
