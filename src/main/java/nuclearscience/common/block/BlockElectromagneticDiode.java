package nuclearscience.common.block;

import org.jetbrains.annotations.Nullable;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockElectromagneticDiode extends Block implements IWrenchable {

    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

    public BlockElectromagneticDiode() {
        super(Blocks.IRON_BLOCK.properties().strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((p1, p2, p3) -> false));
        registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(ElectrodynamicsBlockStates.FACING, rot.rotate(state.getValue(ElectrodynamicsBlockStates.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(ElectrodynamicsBlockStates.FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ElectrodynamicsBlockStates.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ElectrodynamicsBlockStates.FACING);
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, Player player) {
        player.level().setBlockAndUpdate(pos, rotate(player.level().getBlockState(pos), Rotation.CLOCKWISE_90));
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, Player player) {
        Level world = player.level();
        world.destroyBlock(pos, true, player);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
