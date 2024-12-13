package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileControlRodAssembly;

public class RenderRodAssembly extends AbstractTileRenderer<TileControlRodAssembly> {

	private static final double START_Y = 0;
	private static final double MAX_Y = 13.0 / 16.0;

	public RenderRodAssembly(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileControlRodAssembly tileEntityIn, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		if (tileEntityIn.isMSR.get()) {

			Direction dir = Direction.values()[tileEntityIn.direction.get()];

			stack.mulPose(MathUtils.rotQuaternionDeg(90, 0, dir.toYRot()));

			// stack.mulPose(new Quaternion(90, 0, dir.toYRot(), true));
		}

		RenderingUtils.renderModel(getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSTRUCTURE), tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

		double insertion = tileEntityIn.insertion.get() / 100.0;

		stack.translate(0, START_Y + MAX_Y * insertion, 0);

		RenderingUtils.renderModel(getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSROD), tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

		stack.popPose();
	}
}
