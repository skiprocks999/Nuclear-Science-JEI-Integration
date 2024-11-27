package nuclearscience.common.tile.fusionreactor;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileFusionReactorCore extends GenericTile {

    public final Property<Integer> deuterium = property(new Property<>(PropertyTypes.INTEGER, "deuterium", 0));
    public final Property<Integer> tritium = property(new Property<>(PropertyTypes.INTEGER, "tritium", 0));
    public final Property<Integer> timeLeft = property(new Property<>(PropertyTypes.INTEGER, "timeleft", 0));

    public TileFusionReactorCore(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get(), pos, state);

        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM).maxJoules(Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4));
    }

    public void tickServer(ComponentTickable tick) {
        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if (tritium.get() > 0 && deuterium.get() > 0 && timeLeft.get() <= 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
            deuterium.set(deuterium.get() - 1);
            tritium.set(tritium.get() - 1);
            timeLeft.set(15 * 20);
        }

        if (timeLeft.get() <= 0) {
            if (BlockEntityUtils.isLit(this)) {
                BlockEntityUtils.updateLit(this, false);
            }
            return;
        }
        if (!BlockEntityUtils.isLit(this)) {
            BlockEntityUtils.updateLit(this, true);
        }
        timeLeft.set(timeLeft.get() - 1);

        if (electro.getJoulesStored() < Constants.FUSIONREACTOR_USAGE_PER_TICK) {
            return;
        }

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos offset = worldPosition.relative(dir);
            BlockState state = level.getBlockState(offset);
            if (state.getBlock() == NuclearScienceBlocks.BLOCK_PLASMA.get()) {
                BlockEntity tile = level.getBlockEntity(offset);
                if (tile instanceof TilePlasma plasma && plasma.ticksExisted.get() > 30) {
                    plasma.ticksExisted.set(0);
                }
            } else if (state.getBlock() == Blocks.AIR) {
                level.setBlockAndUpdate(offset, NuclearScienceBlocks.BLOCK_PLASMA.get().defaultBlockState());
            }
        }
        electro.joules(electro.getJoulesStored() - Constants.FUSIONREACTOR_USAGE_PER_TICK);
    }

    @Override
    public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack inHand = player.getItemInHand(hand);

        Item itemInHand = inHand.getItem();

        if (itemInHand == NuclearScienceItems.ITEM_CELLDEUTERIUM.get() || itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get()) {

            boolean isTritium = itemInHand == NuclearScienceItems.ITEM_CELLTRITIUM.get();

            int count = isTritium ? tritium.get() : deuterium.get();

            int added = Math.min(inHand.getCount(), Constants.FUSIONREACTOR_MAXSTORAGE - count);

            if (added > 0) {
                if (!level.isClientSide()) {
                    inHand.setCount(inHand.getCount() - added);

                    if (isTritium) {
                        tritium.set(tritium.get() + added);
                    } else {
                        deuterium.set(deuterium.get() + added);
                    }
                }

                return ItemInteractionResult.CONSUME;
            }

        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

}
