package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;
import nuclearscience.prefab.utils.RadiationUtils;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceItems;

public class TileParticleInjector extends GenericTile {

	public static final int INPUT_SLOT = 0;
	public static final int ELECTRO_CELL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	public static final int TIME_PER_PARTICLE = 100;

	private EntityParticle[] particles = new EntityParticle[2];
	private int timeSinceSpawn = 0;

	public TileParticleInjector(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_PARTICLEINJECTOR.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(2).outputs(1)).valid((index, stack, i) -> index != 1 || stack.getItem() == NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get()).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1)
				//
				.setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 0, 1).setDirectionsBySlot(2, BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.LEFT));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 8).setInputDirections(BlockEntityUtils.MachineDirection.TOP).maxJoules(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 10));
		addComponent(new ComponentContainerProvider("container.particleinjector", this).createMenu((id, player) -> new ContainerParticleInjector(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable componentTickable) {

		RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), Constants.PARTICLE_INJECTOR_RADIATION_RADIUS, true, 0, false);

		if (particles[0] != null && !particles[0].isAlive()) {
			particles[0] = null;
		}
		if (particles[1] != null && !particles[1].isAlive()) {
			particles[1] = null;
		}

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);

		if(timeSinceSpawn > 0) {
			timeSinceSpawn--;
			return;
		}

		ItemStack input = inv.getItem(INPUT_SLOT);

		if(electro.getJoulesStored() < Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE || input.isEmpty() || (particles[0] != null && particles[1] != null)) {
			return;
		}

		ItemStack resultStack = inv.getItem(OUTPUT_SLOT);

		if(resultStack.getCount() >= resultStack.getMaxStackSize()) {
			return;
		}

		timeSinceSpawn = TIME_PER_PARTICLE;

		input.shrink(1);

		Direction dir = getFacing();

		EntityParticle particle = new EntityParticle(dir, level, new Location(worldPosition.getX() + 0.5f + dir.getStepX() * 1.5f, worldPosition.getY() + 0.5f + dir.getStepY() * 1.5f, worldPosition.getZ() + 0.5f + dir.getStepZ() * 1.5f));

		addParticle(particle);

		level.addFreshEntity(particle);

		electro.setJoulesStored(electro.getJoulesStored() - Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE);


	}

	public void handleCollision() {

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack resultStack = inv.getItem(OUTPUT_SLOT);
		ItemStack cellStack = inv.getItem(ELECTRO_CELL_SLOT);

		if(cellStack.isEmpty() || resultStack.getCount() >= resultStack.getMaxStackSize() || particles[0] == null && particles[1] == null) {
			return;
		}

		EntityParticle one = particles[0];
		EntityParticle two = particles[1];

		if(one.distanceTo(two) >= 1) {
			return;
		}

		double speedOfMax = Math.pow((one.speed + two.speed) / 4.0, 2);

		one.remove(RemovalReason.KILLED);
		two.remove(RemovalReason.KILLED);

		particles[0] = particles[1] = null;

		double mod = level.random.nextDouble();

		if (speedOfMax > 0.999) {
			if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLDARKMATTER.get()) {
				resultStack.setCount(resultStack.getCount() + 1);
				cellStack.shrink(1);
			} else if (resultStack.isEmpty()) {
				inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
				cellStack.shrink(1);
			}
		} else if (speedOfMax > mod) {
			if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()) {
				resultStack.setCount(resultStack.getCount() + 1);
				cellStack.shrink(1);
			} else if (resultStack.isEmpty()) {
				inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()));
				cellStack.shrink(1);
			}
		}


	}


	public void addParticle(EntityParticle particle) {
		if (particles[0] != particle && particles[1] != particle) {
			if (particles[0] == null) {
				particles[0] = particle;
			} else if (particles[1] == null) {
				particles[1] = particle;
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.saveAdditional(compound, registries);
		compound.putInt("timesincespawn", timeSinceSpawn);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.loadAdditional(compound, registries);
		timeSinceSpawn = compound.getInt("timesincespawn");
	}
}
