package io.github.indicode.fabric.cauldronbrew;

import io.github.indicode.fabric.cauldronbrew.recipe.CauldronRecipeManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Indigo Amann
 */
public class CauldronFluid extends AbstractCauldronFluid {
    public final Identifier identifier;
    public final int color;
    public final Potion potion;
    public CauldronFluid(Identifier identifier, int color, Potion potion) {
        this.identifier = identifier;
        this.color = color;
        this.potion = potion;
    }
    public CauldronFluid(Identifier identifier, Potion potion) {
        this.identifier = identifier;
        this.color = CauldronRecipeManager.getPotionColor(potion);
        this.potion = potion;
    }
    public CauldronFluid(Potion potion) {
        this.identifier = Registry.POTION.getId(potion);
        this.color = CauldronRecipeManager.getPotionColor(potion);
        this.potion = potion;
    }
    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Potion getReturnedPotion() {
        return potion;
    }
}
