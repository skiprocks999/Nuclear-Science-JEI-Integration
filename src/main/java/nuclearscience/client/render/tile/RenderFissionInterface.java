package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.AABB;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileInterface;
import org.jetbrains.annotations.NotNull;

public class RenderFissionInterface extends AbstractTileRenderer<TileInterface.TileFissionInterface> {

    private static final double START_Y = 0;
    private static final double MAX_Y = 13.0 / 16.0;
    private static final double FISSION_REACTOR_FLOOR_Y = 17.8 / 16.0;
    private static final double FLOOR_Y = 1.0 / 16.0;
    private static final double DELTA_FLOOR_Y = FISSION_REACTOR_FLOOR_Y - FLOOR_Y;

    public RenderFissionInterface(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TileInterface.@NotNull TileFissionInterface tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrix.pushPose();

        matrix.translate(0.5, 0.5, 0.5);

        double insertion = tile.insertion.get() / (double) TileControlRod.MAX_EXTENSION;

        matrix.translate(0, START_Y + MAX_Y * insertion, 0);

        RenderingUtils.renderModel(getModel(ClientRegister.MODEL_FISSIONINTERFACE_ROD), tile, RenderType.solid(), matrix, bufferIn, combinedLightIn, combinedOverlayIn);

        matrix.popPose();

        if(!tile.reactor.valid() || !(tile.reactor.getSafe() instanceof TileFissionReactorCore)) {
            return;
        }

        TileFissionReactorCore core = tile.reactor.getSafe();

        ComponentInventory coreInv = core.getComponent(IComponentType.Inventory);

        Long currTime = tile.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks();

        matrix.pushPose();

        //matrix.translate(0.5, 0.5, 0.5);

        TextureAtlasSprite fuelCell = ClientRegister.getSprite(ClientRegister.TEXTURE_FUELCELL);

        tile.clientAnimations.forEach((animation, recieved) -> {

            matrix.pushPose();

            double perc = (double) (currTime - recieved) / (double) animation.animationTime;
            double oneMinusPerc = 1.0 - perc;

            double x, y, z;

            switch (animation) {

                case FISSION_WASTE_1:

                    y = DELTA_FLOOR_Y * oneMinusPerc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_1, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_WASTE_2:

                    y = DELTA_FLOOR_Y * oneMinusPerc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_2, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_WASTE_3:

                    y = DELTA_FLOOR_Y * oneMinusPerc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_3, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_WASTE_4:

                    y = DELTA_FLOOR_Y * oneMinusPerc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_4, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_FUEL_1:

                    y = DELTA_FLOOR_Y * perc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_1, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(0)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_FUEL_2:

                    y = DELTA_FLOOR_Y * perc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_2, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(1)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_FUEL_3:

                    y = DELTA_FLOOR_Y * perc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_3, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(2)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_FUEL_4:

                    y = DELTA_FLOOR_Y * perc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_4, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(3)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_TRITIUM_EXTRACT:

                    y = DELTA_FLOOR_Y * oneMinusPerc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.TRITIUM_CELL, RenderFissionReactorCore.TRITIUM, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

                case FISSION_DEUTERIUM_INSERT:

                    y = DELTA_FLOOR_Y * perc;
                    matrix.translate(0, y, 0);
                    renderBox(matrix, RenderFissionReactorCore.TRITIUM_CELL, RenderFissionReactorCore.DEUTERIUM, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    break;

            }

            matrix.popPose();

        });


        matrix.popPose();
    }

    private void renderBox(PoseStack matrix, AABB box, Color color, TextureAtlasSprite texture, MultiBufferSource bufferIn, int combinedLightIn, boolean[] faces) {
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.SOLID), box, color.rFloat(), color.gFloat(), color.bFloat(), color.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, faces);
    }

    private void renderPiston()


}
