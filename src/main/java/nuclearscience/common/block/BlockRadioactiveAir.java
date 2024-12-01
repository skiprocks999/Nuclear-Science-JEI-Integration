package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.SimpleRadiationSource;

public class BlockRadioactiveAir extends AirBlock {

	public BlockRadioactiveAir() {
		super(Blocks.AIR.properties().noCollission().air().randomTicks());
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		if(level.isClientSide()) {
			return;
		}
		RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(500, 1, 3, false, 100, pos, true));
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		super.onRemove(state, level, pos, newState, movedByPiston);
		RadiationSystem.removeRadiationSource(level, pos, true);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		if (random.nextFloat() < 0.01F) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

}