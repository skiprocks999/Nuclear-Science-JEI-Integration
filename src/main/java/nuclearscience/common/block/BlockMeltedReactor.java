package nuclearscience.common.block;

import com.mojang.serialization.MapCodec;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.reactor.fission.TileMeltedReactor;

public class BlockMeltedReactor extends GenericEntityBlockWaterloggable {

	public BlockMeltedReactor() {
		super(Blocks.IRON_BLOCK.properties().strength(250.0f, 999.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileMeltedReactor(pos, state);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}
}
