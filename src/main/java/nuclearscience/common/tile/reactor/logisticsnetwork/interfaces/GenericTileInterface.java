package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileLogisticsMember;
import nuclearscience.prefab.NuclearPropertyTypes;
import nuclearscience.registers.NuclearScienceItems;

public abstract class GenericTileInterface extends GenericTileLogisticsMember {

    public CachedTileOutput reactor;

    public final Property<HashSet<Integer>> queuedAnimations = property(new Property<>(NuclearPropertyTypes.INTEGER_SET, "queuedanimations", new HashSet<>()));

    public final Property<BlockPos> controlRodLocation = property(new Property<>(PropertyTypes.BLOCK_POS, "controlrodlocation", BlockEntityUtils.OUT_OF_REACH));
    public final Property<BlockPos> supplyModuleLocation = property(new Property<>(PropertyTypes.BLOCK_POS, "supplymodulelocation", BlockEntityUtils.OUT_OF_REACH));

    public final HashMap<InterfaceAnimation, Long> clientAnimations = new HashMap<>();
    //Nothing is rendered with this map; it is used to keep track of what the interface is doing only
    protected final HashMap<InterfaceAnimation, Long> serverAnimations = new HashMap<>();


    public GenericTileInterface(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
        super(tileEntityTypeIn, worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
    }

    @Override
    public void tickServer(ComponentTickable tickable) {

        super.tickServer(tickable);

        queuedAnimations.set(new HashSet<>());
        queuedAnimations.forceDirty();

        if (reactor == null) {
            reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
        }

        if (tickable.getTicks() % 20 == 0 && !reactor.valid()) {

            reactor.update(getBlockPos().relative(getReactorDirection()));

        }
    }

    private void tickClient(ComponentTickable tickable) {

        if (reactor == null) {
            reactor = new CachedTileOutput(getLevel(), getBlockPos().relative(getReactorDirection()));
        }

        if (tickable.getTicks() % 20 == 0 && !reactor.valid()) {

            reactor.update(getBlockPos().relative(getReactorDirection()));

        }

        long currTime = tickable.getTicks();

        queuedAnimations.get().forEach(val -> {

            InterfaceAnimation animation = InterfaceAnimation.values()[val];

            if (!clientAnimations.containsKey(animation)) {
                clientAnimations.put(animation, currTime);
            }

        });

        Iterator<Map.Entry<InterfaceAnimation, Long>> it = clientAnimations.entrySet().iterator();

        Map.Entry<InterfaceAnimation, Long> entry;

        while (it.hasNext()) {
            entry = it.next();

            if (currTime - entry.getValue() > entry.getKey().animationTime) {
                it.remove();
            }

        }

    }

    public abstract Direction getReactorDirection();

    public abstract InterfaceType getInterfaceType();

    protected void handleServerAnimations(ComponentTickable tickable) {
        long currTime = tickable.getTicks();

        queuedAnimations.get().forEach(val -> {

            InterfaceAnimation animation = InterfaceAnimation.values()[val];

            if (!serverAnimations.containsKey(animation)) {
                serverAnimations.put(animation, currTime);
            }

        });

        Iterator<Map.Entry<InterfaceAnimation, Long>> it = serverAnimations.entrySet().iterator();

        Map.Entry<InterfaceAnimation, Long> entry;

        while (it.hasNext()) {
            entry = it.next();

            if (currTime - entry.getValue() > entry.getKey().animationTime) {
                it.remove();
            }

        }
    }

    public static enum InterfaceType {
        NONE, FISSION, MS, FUSION;
    }

    public static enum InterfaceAnimation {

        FISSION_WASTE_1(80),//
        FISSION_WASTE_2(80),//
        FISSION_WASTE_3(80),//
        FISSION_WASTE_4(80),//
        FISSION_TRITIUM_EXTRACT(80),//
        FISSION_FUEL_1(80),//
        FISSION_FUEL_2(80),//
        FISSION_FUEL_3(80),//
        FISSION_FUEL_4(80),//
        FISSION_DEUTERIUM_INSERT(80),//
        FUSION_DEUTERIUM_INSERT(80),//
        FUSION_TRITIUM_INSERT(80)//
        ;


        public final int animationTime;

        private InterfaceAnimation(int timeTicks) {
            animationTime = timeTicks;
        }

    }

    public static ItemStack getItemFromType(InterfaceType type) {
        return switch(type) {
            case NONE -> ItemStack.EMPTY;
            case FISSION -> new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore));
            case MS -> new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore));
            case FUSION -> new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore));
        };
    }


}
