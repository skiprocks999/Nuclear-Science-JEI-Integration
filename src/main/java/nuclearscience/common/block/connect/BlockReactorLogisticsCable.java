package nuclearscience.common.block.connect;

import java.util.HashSet;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

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
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;

public class BlockReactorLogisticsCable extends AbstractRefreshingConnectBlock<TileReactorLogisticsCable> {

    public static final HashSet<Block> PIPESET = new HashSet<>();

    public final SubtypeReactorLogisticsCable cable;

    public BlockReactorLogisticsCable(SubtypeReactorLogisticsCable cable) {
        super(Blocks.IRON_BLOCK.properties().sound(SoundType.METAL).strength(0.15f).dynamicShape(), 5);
        this.cable = cable;
        PIPESET.add(this);
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, BlockEntity otherTile, TileReactorLogisticsCable thisCable, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof TileReactorLogisticsCable) {
            connection = EnumConnectType.WIRE;
        } else if (otherTile instanceof ILogisticsMember member && thisCable.getNetwork() != null && member.isValidConnection(dir.getOpposite()) && member.canConnect(thisCable.getNetwork())) {
            connection = EnumConnectType.INVENTORY;
        }
        return connection;
    }

    @Nullable
    @Override
    public TileReactorLogisticsCable getCableIfValid(BlockEntity tile) {
        if (tile instanceof TileReactorLogisticsCable cable) {
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
