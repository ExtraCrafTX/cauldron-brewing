package io.github.indicode.fabric.cauldronbrew.recipe;

import io.github.indicode.fabric.cauldronbrew.CauldronState;
import net.minecraft.util.Identifier;

/**
 * @author Indigo Amann
 */
public abstract class AbstractCauldronRecipe {
    public abstract boolean applies(CauldronState state);
    public abstract void onBrewTick(ActiveCauldronRecipe recipe, CauldronState state);
    public abstract void onBrewEnd(ActiveCauldronRecipe recipe, CauldronState state);
    public abstract int getTicksToComplete();
    public abstract Identifier getIdentifier();
}
