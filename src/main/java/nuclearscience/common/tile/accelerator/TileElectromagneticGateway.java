package nuclearscience.common.tile.accelerator;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.registers.NuclearScienceTiles;

public class TileElectromagneticGateway extends GenericTile {

    public final Property<Float> targetSpeed = property(new Property<>(PropertyTypes.FLOAT, "targetspeed", 0.0F));

    public TileElectromagneticGateway(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_ELECTROMAGNETICGATEWAY.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentContainerProvider("container.electromagneticgateway", this).createMenu((id, player) -> new ContainerElectromagneticGateway(id, player, new SimpleContainer(0), getCoordsArray())));
    }

    public boolean mayPassThrough(float speed) {
        return speed >= getActualSpeed(targetSpeed.get());
    }

    public static float getActualSpeed(float lightSpeedPerc) {
        return lightSpeedPerc / 100.0F * EntityParticle.MAX_SPEED;
    }

    public static float getLightSpeedPerc(float actualSpeed) {
        return actualSpeed / EntityParticle.MAX_SPEED * 100.0F;
    }

}
