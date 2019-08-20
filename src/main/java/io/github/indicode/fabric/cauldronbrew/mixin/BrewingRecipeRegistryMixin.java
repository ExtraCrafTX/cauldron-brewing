package io.github.indicode.fabric.cauldronbrew.mixin;

import io.github.indicode.fabric.cauldronbrew.recipe.CauldronRecipeManager;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Indigo Amann
 */
@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    @Inject(method = "registerPotionRecipe", at = @At("INVOKE"))
    private static void registerCauldron(Potion potion_1, Item item_1, Potion potion_2, CallbackInfo ci) {
        CauldronRecipeManager.addRecipe(potion_1, potion_2, item_1);
    }
}
