package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderControlRodModule extends AbstractTileRenderer<TileControlRodModule> {

    private static final double MAX_DELTA = 13.0 / 16.0;

    public RenderControlRodModule(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileControlRodModule tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        stack.pushPose();

        stack.translate(0.5, 0.5, 0.5);

        Direction facing = tile.getFacing();

        int sign = 1;

        if(facing == Direction.WEST || facing == Direction.EAST) {
            sign = -1;
        }

        stack.mulPose(MathUtils.rotQuaternionDeg(0, facing.toYRot() - sign * 90, 0));

        double insertion = tile.insertion.get() / (double) TileControlRod.MAX_EXTENSION;

        stack.translate(0, 0, -MAX_DELTA * insertion);

        RenderingUtils.renderModel(getModel(ClientRegister.MODEL_CONTROLRODMODULE_ROD), tile, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

        stack.popPose();





        Font font = Minecraft.getInstance().font;

        stack.pushPose();

        stack.translate(0.5, 0.5, 0.5);

        rotateMatrix(stack, facing);

        stack.translate(0, 0.175, 0.1775);

        Component transfer = ChatFormatter.getChatDisplayShort((double) tile.insertion.get() / (double) TileControlRod.MAX_EXTENSION * 100.0, DisplayUnit.PERCENTAGE);

        float scale = 0.0215F / (font.width(transfer) / 16.0F);

        stack.scale(-scale, -scale, -scale);

        Matrix4f matrix4f = stack.last().pose();

        float textX = -font.width(transfer) / 2.0f;

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
