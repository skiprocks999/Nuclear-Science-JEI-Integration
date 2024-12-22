package nuclearscience.common.tile.reactor;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.tile.reactor.fission.IFissionControlRod;
import nuclearscience.common.tile.reactor.moltensalt.IMSControlRod;
import nuclearscience.registers.NuclearScienceTiles;

public abstract class TileControlRod extends GenericTile {

    public static final int MAX_EXTENSION = 100;
    public static final int EXTENSION_PER_CLICK = 10;

    public final Property<Integer> insertion = property(new Property<>(PropertyTypes.INTEGER, "insertion", 0));

    public TileControlRod(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentPacketHandler(this));
    }

    @Override
    public int getComparatorSignal() {
        return (int) (((double) insertion.get() / (double) MAX_EXTENSION) * 15);
    }

    @Override
    public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (player.isShiftKeyDown()) {
            insertion.set(insertion.get() - TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.get() < 0) {
                insertion.set(TileFissionControlRod.MAX_EXTENSION);
            }
        } else {
            insertion.set(insertion.get() + TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.get() > TileFissionControlRod.MAX_EXTENSION) {
                insertion.set(0);
            }
        }

        return InteractionResult.CONSUME;
    }

    public static class TileFissionControlRod extends TileControlRod implements IFissionControlRod {

        public TileFissionControlRod(BlockPos pos, BlockState state) {
            super(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get(), pos, state);
        }

        @Override
        public int getInsertion() {
            return insertion.get();
        }
    }

    public static class TileMSControlRod extends TileControlRod implements IMSControlRod {

        public TileMSControlRod(BlockPos pos, BlockState state) {
            super(NuclearScienceTiles.TILE_MSCONTROLROD.get(), pos, state);
        }

        @Override
        public int getInsertion() {
            return insertion.get();
        }

        @Override
        public Direction facingDir() {
            return getFacing();
        }
    }


}
