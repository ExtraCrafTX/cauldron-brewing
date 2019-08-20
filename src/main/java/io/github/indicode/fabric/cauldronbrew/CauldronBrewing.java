package io.github.indicode.fabric.cauldronbrew;

import io.github.indicode.fabric.cauldronbrew.block.BrewingCauldronBESR;
import io.github.indicode.fabric.cauldronbrew.block.BrewingCauldronBlock;
import io.github.indicode.fabric.cauldronbrew.block.BrewingCauldronBlockEntity;
import io.github.indicode.fabric.cauldronbrew.recipe.CauldronRecipeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Indigo Amann
 */
public class CauldronBrewing implements ModInitializer {
    public static BlockEntityType<BrewingCauldronBlockEntity> CAULDRON_BLOCK_ENTITY;
    public static Block BREWING_CAULDRON;
    @Override
    public void onInitialize() {
        CauldronRecipeManager.init();
        BREWING_CAULDRON = new BrewingCauldronBlock();
        Registry.BLOCK.add(new Identifier("cauldronbrew", "cauldron"), BREWING_CAULDRON);
        CAULDRON_BLOCK_ENTITY = BlockEntityType.Builder.create(BrewingCauldronBlockEntity::new, BREWING_CAULDRON).build(null);
        Registry.register(Registry.BLOCK_ENTITY, new Identifier("cauldronbrew", "cauldron"), CAULDRON_BLOCK_ENTITY);
        BlockEntityRendererRegistry.INSTANCE.register(BrewingCauldronBlockEntity.class, new BrewingCauldronBESR());
    }
}
