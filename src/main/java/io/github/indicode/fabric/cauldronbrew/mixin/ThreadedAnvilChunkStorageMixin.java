package io.github.indicode.fabric.cauldronbrew.mixin;

import io.github.indicode.fabric.cauldronbrew.ITACSM;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Indigo Amann
 */
@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin implements ITACSM {
    @Accessor("chunkHolders")
    public Long2ObjectLinkedOpenHashMap<ChunkHolder> getChunkHolders() {return null;}
}
