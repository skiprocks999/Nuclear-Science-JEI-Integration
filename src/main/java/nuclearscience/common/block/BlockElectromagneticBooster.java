package nuclearscience.common.block;

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
import nuclearscience.common.block.states.NuclearScienceBlockStates;
import nuclearscience.common.block.states.facing.FacingDirection;

public class BlockElectromagneticBooster extends Block implements IWrenchable {


	public BlockElectromagneticBooster() {
		super(Blocks.GLASS.properties().strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((x, y, z) -> false));
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.FACING, Direction.NORTH).setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(ElectrodynamicsBlockStates.FACING, rot.rotate(state.getValue(ElectrodynamicsBlockStates.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(ElectrodynamicsBlockStates.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction movingdirection = context.getHorizontalDirection();
		BlockState state = super.getStateForPlacement(context).setValue(ElectrodynamicsBlockStates.FACING, context.getHorizontalDirection().getOpposite());
		// left check first in front
		BlockState check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise().getOpposite()));
		if (check.getBlock() == this && check.getValue(ElectrodynamicsBlockStates.FACING).getOpposite() == movingdirection.getClockWise().getOpposite()) {
			state = state.setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT);
		} else {
			check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise()));
			if (check.getBlock() == this && check.getValue(ElectrodynamicsBlockStates.FACING).getOpposite() == movingdirection.getClockWise()) {
				state = state.setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT);
			}
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ElectrodynamicsBlockStates.FACING);
		builder.add(NuclearScienceBlockStates.FACINGDIRECTION);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, Player player) {
		player.level().setBlockAndUpdate(pos, rotate(player.level().getBlockState(pos), Rotation.CLOCKWISE_90));
	}

	@Override
	public void onPickup(ItemStack stack, BlockPos pos, Player player) {
		Level world = player.level();
		BlockState current = world.getBlockState(pos);
		FacingDirection face = current.getValue(NuclearScienceBlockStates.FACINGDIRECTION);
		if (face != FacingDirection.NONE) {
			current = current.setValue(NuclearScienceBlockStates.FACINGDIRECTION, face == FacingDirection.LEFT ? FacingDirection.RIGHT : FacingDirection.LEFT);
			if (face == FacingDirection.RIGHT) {
				current = rotate(current, Rotation.CLOCKWISE_180);
			}
			current = rotate(current, Rotation.CLOCKWISE_90);
		} else {
			current = rotate(current, Rotation.CLOCKWISE_180);
		}
		world.setBlockAndUpdate(pos, current);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}
}
