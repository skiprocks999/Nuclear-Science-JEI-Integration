package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.SimpleRadiationSource;

public class BlockRadioactiveSoil extends SnowyDirtBlock {

	public BlockRadioactiveSoil() {
		super(Blocks.GRASS_BLOCK.properties().randomTicks().strength(0.6F).sound(SoundType.GRASS));
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		if(level.isClientSide()) {
			return;
		}
		RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(300, 1, 3, false, 100, pos, true));
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		super.onRemove(state, level, pos, newState, movedByPiston);
		RadiationSystem.removeRadiationSource(level, pos, true);
	}

}
