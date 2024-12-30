package nuclearscience.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import nuclearscience.common.entity.EntityParticle;
import org.joml.Vector3f;

public class RenderParticle extends EntityRenderer<EntityParticle> {

	public RenderParticle(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EntityParticle entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();

		double perc = (500 - System.currentTimeMillis() % 1000) / 500.0;

		float minScale = 0.01F;

		if(perc < 0) {

		} else {

		}



		float maxScale = 0.02F;

		float scale = maxScale;

		matrixStackIn.scale(scale, scale, scale);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, entityIn.tickCount + partialTicks, 60, 1, 1, 1, 0.3f, true);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityParticle entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}

}
