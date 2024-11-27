package nuclearscience.common.tile;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.registers.NuclearScienceTiles;

public class TileElectromagneticSwitch extends GenericTile {
	public Direction lastDirection;

	public TileElectromagneticSwitch(BlockPos worldPosition, BlockState blockState) {
		super(NuclearScienceTiles.TILE_ELECTROMAGNETICSWITCH.get(), worldPosition, blockState);
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		if (lastDirection != null) {
			compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
		}
		super.saveAdditional(compound, registries);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		if (compound.contains("lastDirectionOrdinal")) {
			lastDirection = Direction.from3DDataValue(compound.getInt("lastDirectionOrdinal"));
		}
		super.loadAdditional(compound, registries);
	}

	@Override
	public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}


}
