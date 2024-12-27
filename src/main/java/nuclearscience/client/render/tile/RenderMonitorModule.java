package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderMonitorModule extends AbstractTileRenderer<TileMonitorModule> {

    public RenderMonitorModule(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileMonitorModule tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tile.linked.get()) {
            return;
        }

        long time = System.currentTimeMillis() % 1500 - 750;

        if(time < 0) {
            return;
        }

        Font font = Minecraft.getInstance().font;

        Direction facing = tile.getFacing();

        stack.pushPose();

        stack.translate(0.5, 0.5, 0.5);

        rotateMatrix(stack, facing);

        stack.translate(0.1, 0.3, -0.188125);

        Component transfer = Component.literal("_");

        int width = font.width(transfer);

        float scale = 0.0215F / (width / 8.0F);

        stack.scale(-scale, -scale, -scale);

        Matrix4f matrix4f = stack.last().pose();

        float textX = -width / 2.0f;

        font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, 0, combinedLightIn);

        stack.popPose();

    }

    private void rotateMatrix(PoseStack stack, Direction dir) {
        switch (dir) {
            case EAST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, -90, 0));// stack.mulPose(new Quaternion(0, -90, 0, true));
            case SOUTH -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));// stack.mulPose(new Quaternion(0, 180, 0, true));
            case WEST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));// stack.mulPose(new Quaternion(0, 90, 0, true));
            default -> {
            }
        }
    }
}
