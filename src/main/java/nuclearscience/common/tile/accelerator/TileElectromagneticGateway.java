package nuclearscience.common.tile.accelerator;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.registers.NuclearScienceTiles;

public class TileElectromagneticGateway extends GenericTile {

    public final Property<Float> targetSpeed = property(new Property<>(PropertyTypes.FLOAT, "targetspeed", 0.0F));

    public TileElectromagneticGateway(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_ELECTROMAGNETICGATEWAY.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentContainerProvider("container.electromaneticgateway", this).createMenu((id, player) -> new ContainerElectromagneticGateway(id, player, new SimpleContainer(0), getCoordsArray())));
    }

    public boolean mayPassThrough(float speed) {
        return speed >= targetSpeed.get();
    }

}
