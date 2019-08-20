package io.github.indicode.fabric.cauldronbrew;

import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;

/**
 * @author Indigo Amann
 */
public abstract class AbstractCauldronFluid {
    public abstract Identifier getIdentifier();
    public abstract int getColor();
    public abstract Potion getReturnedPotion();
    @Override
    public boolean equals(Object other) {
        return other instanceof AbstractCauldronFluid && ((AbstractCauldronFluid) other).getIdentifier().equals(this.getIdentifier());
    }
    @Override
    public String toString() {
        return getIdentifier().toString();
    }
}
