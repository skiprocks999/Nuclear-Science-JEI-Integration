package nuclearscience.common.block.connect;

import com.mojang.serialization.MapCodec;
import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.api.network.reactorlogistics.IReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class BlockReactorLogisticsCable extends AbstractRefreshingConnectBlock {

    public static final HashSet<Block> PIPESET = new HashSet<>();

    public final SubtypeReactorLogisticsCable cable;

    public BlockReactorLogisticsCable(SubtypeReactorLogisticsCable cable) {
        super(Blocks.IRON_BLOCK.properties().sound(SoundType.METAL).strength(0.15f).dynamicShape(), 5);
        this.cable = cable;
        PIPESET.add(this);
    }

    @Override
    public BlockState refreshConnections(BlockState otherState, BlockEntity otherTile, BlockState state, BlockEntity thisTile, Direction dir) {
        if(!(thisTile instanceof TileReactorLogisticsCable)) {
            return state;
        }
        TileReactorLogisticsCable thisConnect = (TileReactorLogisticsCable) thisTile;
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof IReactorLogisticsCable) {
            connection = EnumConnectType.WIRE;
        } else if (otherTile instanceof ILogisticsMember member && thisConnect.getNetwork(false) != null && member.isValidConnection(dir.getOpposite()) && member.canConnect(thisConnect.getNetwork(false))) {
            connection = EnumConnectType.INVENTORY;
        }
        thisConnect.writeConnection(dir, connection);
        return state;
    }

    @Nullable
    @Override
    public IRefreshableCable getCableIfValid(BlockEntity tile) {
        if(tile instanceof IReactorLogisticsCable cable) {
            return cable;
        }
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileReactorLogisticsCable(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }
}
