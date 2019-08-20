package io.github.indicode.fabric.cauldronbrew;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.server.world.ChunkHolder;

/**
 * @author Indigo Amann
 */
public interface ITACSM {
    Long2ObjectLinkedOpenHashMap<ChunkHolder> getChunkHolders();
}
