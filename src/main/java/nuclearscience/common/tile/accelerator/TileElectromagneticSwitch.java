package nuclearscience.common.tile.accelerator;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.registers.NuclearScienceTiles;

public class TileElectromagneticSwitch extends GenericTile {

	public TileElectromagneticSwitch(BlockPos worldPosition, BlockState blockState) {
		super(NuclearScienceTiles.TILE_ELECTROMAGNETICSWITCH.get(), worldPosition, blockState);
	}

	public Direction readLastDirection() {
		return Direction.UP;//Direction.values()[lastDirection.get()];
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
