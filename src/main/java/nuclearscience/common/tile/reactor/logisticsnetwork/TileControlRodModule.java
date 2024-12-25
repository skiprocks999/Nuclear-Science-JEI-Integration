package nuclearscience.common.tile.reactor.logisticsnetwork;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.inventory.container.ContainerControlRodModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;

public class TileControlRodModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public final Property<Integer> insertion = property(new Property<>(PropertyTypes.INTEGER, "insertion", 0));
    public final Property<Integer> redstoneSignal = property(new Property<>(PropertyTypes.INTEGER, "redstonesignal", 0)).onChange((prop, oldVal) -> {
        if (!level.isClientSide && prop.get() != oldVal) {

            double perc = (double) prop.get() / 15.0;

            double tot = perc * (double) TileControlRod.MAX_EXTENSION;

            int mult = (int) (tot / (double) TileControlRod.EXTENSION_PER_CLICK);

            insertion.set(mult * TileControlRod.EXTENSION_PER_CLICK);

        }
    });

    public TileControlRodModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_CONTROLRODMODULE.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentContainerProvider("container.controlrodmodule", this).createMenu((id, player) -> new ContainerControlRodModule(id, player, new SimpleContainer(0), getCoordsArray())));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    public void tickServer(ComponentTickable tickable) {

        super.tickServer(tickable);

        if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        GenericTileInterface inter = network.getInterface(interfaceLocation.get());

        if (inter == null) {
            return;
        }

        if(inter.getInterfaceType() != GenericTileInterface.InterfaceType.values()[interfaceType.get()]) {
            interfaceLocation.set(BlockEntityUtils.OUT_OF_REACH);
            interfaceType.set(GenericTileInterface.InterfaceType.NONE.ordinal());
        }


    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(ElectrodynamicsBlockStates.FACING) && newState.hasProperty(ElectrodynamicsBlockStates.FACING) && oldState.getValue(ElectrodynamicsBlockStates.FACING) != newState.getValue(ElectrodynamicsBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        super.onNeightborChanged(neighbor, blockStateTrigger);
        if (!level.isClientSide) {
            redstoneSignal.set(getLevel().getBestNeighborSignal(getBlockPos()));
        }
    }

    @Override
    public void onBlockDestroyed() {
        super.onBlockDestroyed();
        if(!level.isClientSide()) {

            if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
                return;
            }

            TileReactorLogisticsCable cable = networkCable.getSafe();

            if (cable.isRemoved()) {
                return;
            }

            ReactorLogisticsNetwork network = cable.getNetwork();

            GenericTileInterface inter = network.getInterface(interfaceLocation.get());

            if (inter == null) {
                return;
            }

            inter.controlRodLocation.set(BlockEntityUtils.OUT_OF_REACH);
        }
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return CONTROL_RODS;
    }

    @Override
    public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).is(ElectrodynamicsItems.ITEM_WRENCH)) {
            if (this.hasComponent(IComponentType.ContainerProvider)) {
                if (!this.level.isClientSide) {
                    player.openMenu(this.getComponent(IComponentType.ContainerProvider));
                    player.awardStat(Stats.INTERACT_WITH_FURNACE);
                }

                return ItemInteractionResult.CONSUME;
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }
        return super.useWithItem(used, player, hand, hit);
    }

    @Override
    public int getComparatorSignal() {
        return (int) (((double) insertion.get() / (double) TileControlRod.MAX_EXTENSION) * 15);
    }

    @Override
    public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (player.isShiftKeyDown()) {
            insertion.set(insertion.get() - TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.get() < 0) {
                insertion.set(TileControlRod.TileFissionControlRod.MAX_EXTENSION);
            }
        } else {
            insertion.set(insertion.get() + TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.get() > TileControlRod.TileFissionControlRod.MAX_EXTENSION) {
                insertion.set(0);
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void onInterfacePropChange(Property<BlockPos> prop, BlockPos old) {

        super.onInterfacePropChange(prop, old);

        boolean oldInval = old.equals(BlockEntityUtils.OUT_OF_REACH);
        boolean newInval = prop.get().equals(BlockEntityUtils.OUT_OF_REACH);

        if(oldInval && newInval) {
            return;
        }

        if (networkCable == null || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if(oldInval && !newInval) {
            GenericTileInterface inter = network.getInterface(prop.get());

            if(inter != null) {
                inter.controlRodLocation.set(getBlockPos());
            }
        } else if (!oldInval && newInval) {
            GenericTileInterface inter = network.getInterface(old);

            if(inter != null) {
                inter.controlRodLocation.set(BlockEntityUtils.OUT_OF_REACH);
            }
        }

    }
}
