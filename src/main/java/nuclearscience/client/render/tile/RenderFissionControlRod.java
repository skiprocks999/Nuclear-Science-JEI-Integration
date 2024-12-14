package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;

public class RenderFissionControlRod extends AbstractTileRenderer<TileControlRod.TileFissionControlRod> {

	private static final double START_Y = 0;
	private static final double MAX_Y = 13.0 / 16.0;

	public RenderFissionControlRod(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileControlRod.TileFissionControlRod tileEntityIn, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		double insertion = tileEntityIn.insertion.get() / (double) TileControlRod.MAX_EXTENSION;

		stack.translate(0, START_Y + MAX_Y * insertion, 0);

		RenderingUtils.renderModel(getModel(ClientRegister.MODEL_FISSIONCONTROLROD_ROD), tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

		stack.popPose();
	}
}
