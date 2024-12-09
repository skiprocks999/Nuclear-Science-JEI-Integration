package nuclearscience.common.tile;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import nuclearscience.api.quantumtunnel.FrequencyConnectionManager;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.prefab.NuclearPropertyTypes;
import nuclearscience.registers.NuclearScienceTiles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TileQuantumTunnel extends GenericTile {

    // DUNSWE

    public static final int DOWN_MASK = 0b00000000000000000000000000001111;
    public static final int UP_MASK = 0b00000000000000000000000011110000;
    public static final int NORTH_MASK = 0b00000000000000000000111100000000;
    public static final int SOUTH_MASK = 0b00000000000000001111000000000000;
    public static final int WEST_MASK = 0b00000000000011110000000000000000;
    public static final int EAST_MASK = 0b00000000111100000000000000000000;


    public Property<TunnelFrequency> frequency = property(new Property<>(NuclearPropertyTypes.TUNNEL_FREQUENCY, "frequency", TunnelFrequency.NO_FREQUENCY));
    public Property<Integer> inputDirections = property(new Property<>(PropertyTypes.INTEGER, "inputdirections", 0)).onChange((prop, val) -> {
        if (!level.isClientSide()) {
            refreshCapabilities();
        }
    });
    public Property<Integer> outputDirections = property(new Property<>(PropertyTypes.INTEGER, "outputdirections", 0)).onChange((prop, val) -> {
        if (!level.isClientSide()) {
            refreshCapabilities();
        }
    });

    private CachedTileOutput[] outputCache = new CachedTileOutput[6];

    private ItemHandler[] itemHandlers = new ItemHandler[6];
    private FluidHandler[] fluidHandlers = new FluidHandler[6];
    private GasHandler[] gasHandlers = new GasHandler[6];
    private ElectrodynamicHandler[] electrodynamicHandlers = new ElectrodynamicHandler[6];
    private FEHandler[] feHandlers = new FEHandler[6];

    public HashMap<UUID, HashSet<TunnelFrequency>> clientFrequencies = new HashMap<>();

    public TileQuantumTunnel(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), pos, state);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentInventory(this));
        addComponent(new ComponentContainerProvider("container.quantumcapacitor", this).createMenu((id, player) -> new ContainerQuantumTunnel(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

    }

    public void tickServer(ComponentTickable tickable) {

        if(!TunnelFrequencyManager.doesFrequencyExist(frequency.get())) {
            frequency.set(TunnelFrequency.NO_FREQUENCY);
        }

        if(frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return;
        }

        for (int i = 0; i < 6; i++) {
            if (outputCache[i] == null) {
                outputCache[i] = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.values()[i]));
            }
        }
        if (tickable.getTicks() % 40 == 0) {
            for (int i = 0; i < 6; i++) {
                if (outputCache[i] == null) {
                    outputCache[i].update(new BlockPos(worldPosition).relative(Direction.values()[i]));
                }
            }
        }

        if (frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return;
        }

        for (Direction dir : readOutputDirections()) {
            CachedTileOutput output = outputCache[dir.ordinal()];
            if (!output.valid()) {
                continue;
            }
            BlockEntity tile = output.getSafe();

            IItemHandler itemCap = level.getCapability(Capabilities.ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, dir.getOpposite());

            if (itemCap != null) {
                ItemStack bufferedItem = FrequencyConnectionManager.getBufferedItem(frequency.get()).copy();
                ItemStack formerBufferedItem = bufferedItem.copy();

                if (!bufferedItem.isEmpty()) {
                    for (int i = 0; i < itemCap.getSlots(); i++) {

                        bufferedItem.setCount(bufferedItem.getCount() - itemCap.insertItem(i, bufferedItem, false).getCount());
                        if (bufferedItem.getCount() <= 0) {
                            break;
                        }

                    }
                    int delta = formerBufferedItem.getCount() - bufferedItem.getCount();
                    if (delta > 0) {
                        formerBufferedItem.setCount(delta);
                        FrequencyConnectionManager.extractItem(frequency.get(), formerBufferedItem, false);
                    }
                }
            }

            IFluidHandler fluidCap = level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, dir.getOpposite());

            if (fluidCap != null) {
                FluidStack bufferedFluid = FrequencyConnectionManager.getBufferedFluid(frequency.get()).copy();

                if (!bufferedFluid.isEmpty()) {
                    int taken = fluidCap.fill(bufferedFluid, IFluidHandler.FluidAction.EXECUTE);
                    if (taken > 0) {
                        bufferedFluid.setAmount(taken);
                        FrequencyConnectionManager.extractFluid(frequency.get(), bufferedFluid, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }

            IGasHandler gasCap = level.getCapability(ElectrodynamicsCapabilities.CAPABILITY_GASHANDLER_BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, dir.getOpposite());

            if (gasCap != null) {
                GasStack bufferedGas = FrequencyConnectionManager.getBufferedGas(frequency.get()).copy();

                if (!bufferedGas.isEmpty()) {
                    int taken = gasCap.fill(bufferedGas, GasAction.EXECUTE);
                    if (taken > 0) {
                        bufferedGas.setAmount(taken);
                        FrequencyConnectionManager.extractGas(frequency.get(), bufferedGas, GasAction.EXECUTE);
                    }
                }
            }

            ICapabilityElectrodynamic electroCap = level.getCapability(ElectrodynamicsCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, dir.getOpposite());

            if (electroCap != null && electroCap.isEnergyReceiver()) {
                TransferPack bufferedEnergy = FrequencyConnectionManager.getBufferedEnergy(frequency.get());
                if (bufferedEnergy.getJoules() > 0) {
                    TransferPack taken = electroCap.receivePower(bufferedEnergy, false);
                    if (taken.getJoules() > 0) {
                        FrequencyConnectionManager.extractEnergy(frequency.get(), taken, false);
                    }
                }
            }

            IEnergyStorage feCap = level.getCapability(Capabilities.EnergyStorage.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, dir.getOpposite());

            if (feCap != null && feCap.canReceive()) {
                TransferPack bufferedEnergy = FrequencyConnectionManager.getBufferedEnergy(frequency.get());
                if (bufferedEnergy.getJoules() > 0 && bufferedEnergy.getVoltage() == ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) {
                    int taken = feCap.receiveEnergy((int) bufferedEnergy.getJoules(), false);
                    if (taken > 0) {
                        FrequencyConnectionManager.extractEnergy(frequency.get(), TransferPack.joulesVoltage(taken, ElectrodynamicsCapabilities.DEFAULT_VOLTAGE), false);
                    }
                }
            }


        }

    }

    @Nullable
    public IEnergyStorage getFECapability(@Nullable Direction side) {
        if (side == null || frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return null;
        }
        return feHandlers[side.ordinal()];
    }

    @Override
    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if (side == null || frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return null;
        }
        return itemHandlers[side.ordinal()];
    }

    @Override
    public @Nullable IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
        if (side == null || frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return null;
        }
        return fluidHandlers[side.ordinal()];
    }

    @Override
    public @Nullable ICapabilityElectrodynamic getElectrodynamicCapability(@Nullable Direction side) {
        if (side == null || frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return null;
        }
        return electrodynamicHandlers[side.ordinal()];
    }

    @Override
    public @Nullable IGasHandler getGasHandlerCapability(@Nullable Direction side) {
        if (side == null || frequency.get().equals(TunnelFrequency.NO_FREQUENCY)) {
            return null;
        }
        return gasHandlers[side.ordinal()];
    }

    @Override
    public void onLoad() {
        super.onLoad();
        refreshCapabilities();
    }

    private void refreshCapabilities() {
        itemHandlers = new ItemHandler[6];
        fluidHandlers = new FluidHandler[6];
        gasHandlers = new GasHandler[6];
        electrodynamicHandlers = new ElectrodynamicHandler[6];
        feHandlers = new FEHandler[6];

        for (Direction dir : readInputDirections()) {
            int index = BlockEntityUtils.getRelativeSide(getFacing(), dir).ordinal();
            itemHandlers[index] = new ItemHandler(true);
            fluidHandlers[index] = new FluidHandler(true);
            gasHandlers[index] = new GasHandler(true);
            electrodynamicHandlers[index] = new ElectrodynamicHandler(true);
            feHandlers[index] = new FEHandler(true);
        }

        for (Direction dir : readInputDirections()) {
            int index = BlockEntityUtils.getRelativeSide(getFacing(), dir).ordinal();
            itemHandlers[index] = new ItemHandler(false);
            fluidHandlers[index] = new FluidHandler(false);
            gasHandlers[index] = new GasHandler(false);
            electrodynamicHandlers[index] = new ElectrodynamicHandler(false);
            feHandlers[index] = new FEHandler(false);
        }

    }

    public List<Direction> readInputDirections() {
        return readDirections(inputDirections.get(), 1);
    }

    public List<Direction> readOutputDirections() {
        return readDirections(outputDirections.get(), 2);
    }

    public void writeInputDirection(Direction dir) {
        inputDirections.set(writeDirection(inputDirections.get(), dir, 1));
    }

    public void writeOutputDirection(Direction dir) {
        outputDirections.set(writeDirection(outputDirections.get(), dir, 2));
    }

    public void removeInputDirection(Direction dir) {
        inputDirections.set(removeDirection(inputDirections.get(), dir));
    }

    public void removeOutputDirection(Direction dir) {
        outputDirections.set(removeDirection(outputDirections.get(), dir));
    }

    private List<Direction> readDirections(int directions, int checkValue) {
        List<Direction> values = new ArrayList<>();
        if ((directions & DOWN_MASK) >> Direction.DOWN.ordinal() * 4 == checkValue) {
            values.add(Direction.DOWN);
        }
        if ((directions & UP_MASK) >> Direction.UP.ordinal() * 4 == checkValue) {
            values.add(Direction.UP);
        }
        if ((directions & NORTH_MASK) >> Direction.NORTH.ordinal() * 4 == checkValue) {
            values.add(Direction.NORTH);
        }
        if ((directions & SOUTH_MASK) >> Direction.SOUTH.ordinal() * 4 == checkValue) {
            values.add(Direction.SOUTH);
        }
        if ((directions & WEST_MASK) >> Direction.WEST.ordinal() * 4 == checkValue) {
            values.add(Direction.WEST);
        }
        if ((directions & EAST_MASK) >> Direction.EAST.ordinal() * 4 == checkValue) {
            values.add(Direction.EAST);
        }
        return values;
    }

    private int writeDirection(int directions, Direction dir, int value) {

        int masked;

        switch (dir) {
            case DOWN:
                masked = directions & ~DOWN_MASK;
                break;
            case UP:
                masked = directions & ~UP_MASK;
                break;
            case NORTH:
                masked = directions & ~NORTH_MASK;
                break;
            case SOUTH:
                masked = directions & ~SOUTH_MASK;
                break;
            case WEST:
                masked = directions & ~WEST_MASK;
                break;
            case EAST:
                masked = directions & ~EAST_MASK;
                break;
            default:
                masked = 0;
                break;
        }

        return masked | (value << (dir.ordinal() * 4));

    }

    private int removeDirection(int directions, Direction dir) {
        return switch (dir) {
            case DOWN -> directions & ~DOWN_MASK;
            case UP -> directions & ~UP_MASK;
            case NORTH -> directions & ~NORTH_MASK;
            case SOUTH -> directions & ~SOUTH_MASK;
            case WEST -> directions & ~WEST_MASK;
            case EAST -> directions & ~EAST_MASK;
        };
    }

    private class ItemHandler implements IItemHandler {

        private final boolean isReciever;

        public ItemHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return FrequencyConnectionManager.getBufferedItem(frequency.get());
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return isReciever ? FrequencyConnectionManager.recieveItem(frequency.get(), stack, simulate) : ItemStack.EMPTY;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack buffered = getStackInSlot(0);
            if (buffered.isEmpty()) {
                return ItemStack.EMPTY;
            }
            ItemStack toExtract = buffered.copy();
            buffered.setCount(amount);
            return isReciever ? ItemStack.EMPTY : FrequencyConnectionManager.extractItem(frequency.get(), buffered, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return TunnelFrequencyBuffer.MAX_ITEM_STACK_SIZE;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
    }

    private class FluidHandler implements IFluidHandler {

        private final boolean isReciever;

        public FluidHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return FrequencyConnectionManager.getBufferedFluid(frequency.get());
        }

        @Override
        public int getTankCapacity(int tank) {
            return TunnelFrequencyBuffer.MAX_FLUID_CAP;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return true;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return isReciever ? FrequencyConnectionManager.recieveFluid(frequency.get(), resource, action).getAmount() : 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return isReciever ? FluidStack.EMPTY : FrequencyConnectionManager.extractFluid(frequency.get(), resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            FluidStack buffered = FrequencyConnectionManager.getBufferedFluid(frequency.get());
            if (buffered.isEmpty()) {
                return FluidStack.EMPTY;
            }
            return drain(new FluidStack(buffered.getFluid(), maxDrain), action);
        }
    }

    private class GasHandler implements IGasHandler {

        private final boolean isReciever;

        public GasHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public GasStack getGasInTank(int i) {
            return FrequencyConnectionManager.getBufferedGas(frequency.get());
        }

        @Override
        public int getTankCapacity(int i) {
            return TunnelFrequencyBuffer.MAX_GAS_CAP;
        }

        @Override
        public int getTankMaxTemperature(int i) {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getTankMaxPressure(int i) {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isGasValid(int i, @NotNull GasStack gasStack) {
            return true;
        }

        @Override
        public int fill(GasStack gasStack, GasAction gasAction) {
            return isReciever ? FrequencyConnectionManager.recieveGas(frequency.get(), gasStack, gasAction).getAmount() : 0;
        }

        @Override
        public GasStack drain(GasStack gasStack, GasAction gasAction) {
            return isReciever ? GasStack.EMPTY : FrequencyConnectionManager.extractGas(frequency.get(), gasStack, gasAction);
        }

        @Override
        public GasStack drain(int i, GasAction gasAction) {
            GasStack buffered = FrequencyConnectionManager.getBufferedGas(frequency.get());
            if (buffered.isEmpty()) {
                return GasStack.EMPTY;
            }
            return drain(new GasStack(buffered.getGas(), i, buffered.getTemperature(), buffered.getPressure()), gasAction);
        }

        @Override
        public int heat(int i, int i1, GasAction gasAction) {
            return -1;
        }

        @Override
        public int bringPressureTo(int i, int i1, GasAction gasAction) {
            return -1;
        }
    }

    private class ElectrodynamicHandler implements ICapabilityElectrodynamic {

        private final boolean isReciever;

        public ElectrodynamicHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public double getJoulesStored() {
            return FrequencyConnectionManager.getBufferedEnergy(frequency.get()).getJoules();
        }

        @Override
        public double getMaxJoulesStored() {
            return TunnelFrequencyBuffer.MAX_JOULES_CAP;
        }

        @Override
        public void setJoulesStored(double v) {

        }

        @Override
        public double getVoltage() {
            return -1;
        }

        @Override
        public double getMinimumVoltage() {
            return -1;
        }

        @Override
        public double getMaximumVoltage() {
            return Double.MAX_VALUE;
        }

        @Override
        public double getAmpacity() {
            return -1;
        }

        @Override
        public boolean isEnergyReceiver() {
            return isReciever;
        }

        @Override
        public boolean isEnergyProducer() {
            return !isReciever;
        }

        @Override
        public TransferPack extractPower(TransferPack transfer, boolean debug) {
            return FrequencyConnectionManager.extractEnergy(frequency.get(), transfer, debug);
        }

        @Override
        public TransferPack receivePower(TransferPack transfer, boolean debug) {
            return FrequencyConnectionManager.recieveEnergy(frequency.get(), transfer, debug);
        }

        @Override
        public void overVoltage(TransferPack transfer) {

        }

        @Override
        public void onChange() {

        }

        @Override
        public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction direction) {
            return isReciever ? TransferPack.joulesVoltage(TunnelFrequencyBuffer.MAX_JOULES_CAP - FrequencyConnectionManager.getBufferedEnergy(frequency.get()).getJoules(), -1) : TransferPack.EMPTY;
        }
    }

    private class FEHandler implements IEnergyStorage {

        private final boolean isReciever;

        public FEHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            return isReciever ? (int) FrequencyConnectionManager.recieveEnergy(frequency.get(), TransferPack.joulesVoltage(toReceive, ElectrodynamicsCapabilities.DEFAULT_VOLTAGE), simulate).getJoules() : 0;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            return isReciever ? 0 : (int) FrequencyConnectionManager.recieveEnergy(frequency.get(), TransferPack.joulesVoltage(toExtract, ElectrodynamicsCapabilities.DEFAULT_VOLTAGE), simulate).getJoules();
        }

        @Override
        public int getEnergyStored() {
            return (int) FrequencyConnectionManager.getBufferedEnergy(frequency.get()).getJoules();
        }

        @Override
        public int getMaxEnergyStored() {
            return (int) TunnelFrequencyBuffer.MAX_JOULES_CAP;
        }

        @Override
        public boolean canExtract() {
            return !isReciever;
        }

        @Override
        public boolean canReceive() {
            return isReciever;
        }
    }


}
