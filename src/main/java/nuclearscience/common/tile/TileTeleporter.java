package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import nuclearscience.common.inventory.container.ContainerTeleporter;
import nuclearscience.registers.NuclearScienceTiles;

public class TileTeleporter extends GenericTile {

	public final Property<BlockPos> destination = property(new Property<>(PropertyTypes.BLOCK_POS, "location", getBlockPos()));
	public final Property<Integer> cooldown = property(new Property<>(PropertyTypes.INTEGER, "cooldown", 0));
	public final Property<ResourceLocation> dimension = property(new Property<>(PropertyTypes.RESOURCE_LOCATION, "dimension", Level.OVERWORLD.location()));

	public TileTeleporter(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_TELEPORTER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(5000000).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1)));
		addComponent(new ComponentContainerProvider("container.teleporter", this).createMenu((id, player) -> new ContainerTeleporter(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

	protected void tickServer(ComponentTickable tickable) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		boolean powered = electro.getJoulesStored() > 0;

		if (BlockEntityUtils.isLit(this) ^ powered) {
			BlockEntityUtils.updateLit(this, powered);
		}

		if (destination.get().equals(getBlockPos()) || electro.getJoulesStored() < electro.getMaxJoulesStored()) {
			return;
		}

		if (cooldown.get() > 0) {
			cooldown.set(cooldown.get() - 1);
			return;
		}

		AABB entityCheckArea = AABB.encapsulatingFullBlocks(getBlockPos(), getBlockPos().offset(1, 2, 1));

		List<Player> players = getLevel().getEntities(EntityType.PLAYER, entityCheckArea, en -> true);

		if (players.isEmpty()) {
			cooldown.set(5);
			return;
		}

		ServerLevel destinationLevel = getDestinationLevel();

		Player player = players.get(0);

		BlockPos destPos = destination.get();

		player.changeDimension(new DimensionTransition(destinationLevel, new Vec3(destPos.getX(), destPos.getY(), destPos.getZ()), Vec3.ZERO, player.getXRot(), player.getYRot(), false, DimensionTransition.PLACE_PORTAL_TICKET));

		cooldown.set(80);

		electro.joules(0);

	}

	@Override
	public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	private ServerLevel getDestinationLevel() {
		ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(ResourceKey.create(Registries.DIMENSION, dimension.get()));
		if (level == null) {
			return (ServerLevel) getLevel();
		}
		return level;
	}

}
