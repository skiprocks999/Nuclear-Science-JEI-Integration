package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import nuclearscience.common.tile.TileQuantumTunnel;

public class BlockQuantumTunnel extends GenericMachineBlock {

	public BlockQuantumTunnel() {
		super(TileQuantumTunnel::new);
	}

	/*

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		ItemStack addstack = new ItemStack(this);
		BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (tile instanceof ICapabilityElectrodynamic electro) {
			double joules = electro.getJoulesStored();
			if (joules > 0) {
				addstack.getOrCreateTag().putDouble("joules", joules);
			}
		}
		if (tile instanceof TileQuantumCapacitor cap) {
			addstack.getOrCreateTag().putInt("frequency", cap.frequency.get());
			addstack.getOrCreateTag().putUUID("uuid", cap.uuid.get());
		}
		return Arrays.asList(addstack);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (tile instanceof TileQuantumCapacitor cap) {
			cap.frequency.set(stack.getOrCreateTag().getInt("frequency"));
			if (stack.getOrCreateTag().contains("uuid")) {
				cap.uuid.set(stack.getOrCreateTag().getUUID("uuid"));
			} else if (placer instanceof Player pl) {
				cap.uuid.set(pl.getGameProfile().getId());
			}
		} else {
			super.setPlacedBy(worldIn, pos, state, placer, stack);
		}
	}

	 */
}
