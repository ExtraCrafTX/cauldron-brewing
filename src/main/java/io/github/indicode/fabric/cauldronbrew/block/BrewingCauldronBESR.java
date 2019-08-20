package io.github.indicode.fabric.cauldronbrew.block;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

/**
 * @author Indigo Amann
 */
public class BrewingCauldronBESR extends BlockEntityRenderer<BrewingCauldronBlockEntity> {
    private static final Identifier POTION_TEXTURE = new Identifier("block/water_still");
    @Override
    public void render(BrewingCauldronBlockEntity blockEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        if (blockEntity.level > 0 && blockEntity.fluid != null) {
            // Stolen from beacon
            GlStateManager.disableLighting();
            //GlStateManager.disableCull();
            //GlStateManager.disableBlend();
            //GlStateManager.depthMask(true);
            //GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            //
            GlStateManager.enableAlphaTest();
            GlStateManager.pushMatrix();
            GlStateManager.translated(x, y, z);
            GlStateManager.scalef(1f / 16f, 1f / 16f, 1f / 16f);

            Color color = new Color(blockEntity.fluid.getColor());
            GlStateManager.color4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
            Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(POTION_TEXTURE);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();
            bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV);
            int int_1 = 2, int_2 = 14, int_3 = 2, int_4 = 14, int_5 = blockEntity.level == 1 ? 9 : blockEntity.level == 2 ? 12 : 15;
            float float_1 = sprite.getU(2), float_2 = sprite.getU(14), float_3 = sprite.getV(2), float_4 = sprite.getV(14);
            bufferBuilder.vertex((double) int_1, (double) int_5, (double) int_3).texture((double) float_1, (double) float_4).next();
            bufferBuilder.vertex((double) int_1, (double) int_5, (double) int_4).texture((double) float_2, (double) float_4).next();
            bufferBuilder.vertex((double) int_2, (double) int_5, (double) int_4).texture((double) float_2, (double) float_3).next();
            bufferBuilder.vertex((double) int_2, (double) int_5, (double) int_3).texture((double) float_1, (double) float_3).next();
            Tessellator.getInstance().draw();
            GlStateManager.popMatrix();
        }
    }
}
