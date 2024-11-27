package nuclearscience.common.tile;

import java.util.List;
import java.util.function.Function;

import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import nuclearscience.registers.NuclearScienceTiles;

public class TileTeleporter extends GenericTile {

	private static final DimensionManager MANAGER = new DimensionManager();

	public final Property<BlockPos> destination = property(new Property<>(PropertyTypes.BLOCK_POS, "location", getBlockPos()));
	public final Property<Integer> cooldown = property(new Property<>(PropertyTypes.INTEGER, "cooldown", 0));

	public ResourceKey<Level> dimension = Level.OVERWORLD;

	public TileTeleporter(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_TELEPORTER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(5000000).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));

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

		player.changeDimension(destinationLevel, MANAGER);

		BlockPos destPos = destination.get();

		player.teleportToWithTicket(destPos.getX() + 0.5, destPos.getY() + 1.0, destPos.getZ() + 0.5);

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
		ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(dimension);
		if (level == null) {
			return (ServerLevel) getLevel();
		}
		return level;
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		compound.put(NBTUtils.DIMENSION, NBTUtils.writeDimensionToTag(dimension));
		super.saveAdditional(compound, registries);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		dimension = NBTUtils.readDimensionFromTag(compound.getCompound(NBTUtils.DIMENSION));
		super.loadAdditional(compound, registries);
	}

	private static final class DimensionManager implements ITeleporter {

		@Override
		public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
			return repositionEntity.apply(false);
		}

		@Override
		public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
			return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
		}

		@Override
		public boolean isVanilla() {
			return false;
		}

		@Override
		public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
			return false;
		}

	}
}
