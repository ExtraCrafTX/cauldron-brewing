package io.github.indicode.fabric.cauldronbrew;

import io.github.indicode.fabric.cauldronbrew.recipe.AbstractCauldronRecipe;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Indigo Amann
 */
public class CauldronState {
    public boolean heated;
    public int level;
    public AbstractCauldronFluid fluid;
    public AbstractCauldronRecipe recipe;
    public List<ItemEntity> ingredients = new ArrayList();
    public final World world;
    public final BlockPos pos;
    public CauldronState(World world, BlockPos pos, AbstractCauldronFluid fluid, AbstractCauldronRecipe recipe, int level, boolean heated) {
        this.world = world;
        this.pos = pos;
        this.heated = heated;
        this.fluid = fluid;
        this.level = level;
        this.recipe = recipe;
    }
}
