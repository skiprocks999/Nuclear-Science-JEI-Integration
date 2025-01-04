package nuclearscience.common.item;

import java.util.List;

import electrodynamics.common.item.ItemElectrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ItemFrequencyCard extends ItemElectrodynamics {

    public ItemFrequencyCard(Properties properties, Holder<CreativeModeTab> creativeTab) {
        super(properties.stacksTo(1), creativeTab);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        Level level = context.getLevel();

        if (level.isClientSide) {
            return super.onItemUseFirst(stack, context);
        }

        if(context.getPlayer().isShiftKeyDown()) {
            stack.remove(ElectrodynamicsDataComponentTypes.RESOURCE_LOCATION);
            stack.remove(ElectrodynamicsDataComponentTypes.BLOCK_POS);
        } else {
            stack.set(ElectrodynamicsDataComponentTypes.BLOCK_POS, context.getClickedPos().above());
            stack.set(ElectrodynamicsDataComponentTypes.RESOURCE_LOCATION, level.dimension().location());
        }

        return InteractionResult.SUCCESS;

        /*
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TileTeleporter teleporter) {



            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains(NBTUtils.DIMENSION)) {

                BlockPos pos = readBlockPos(stack);
                ResourceKey<Level> world = readDimension(stack);

                teleporter.destination.set(pos);
                teleporter.dimension = world;

                MutableComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : Component.literal(world.location().getPath());

                context.getPlayer().sendSystemMessage(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(" " + pos.toShortString())));

            } else {
                writeBlockPos(stack, teleporter.getBlockPos());
                writeDimension(stack, teleporter.getLevel().dimension());
            }

        }

        return super.onItemUseFirst(stack, context);

         */
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltips, tooltipFlag);
        if(stack.has(ElectrodynamicsDataComponentTypes.RESOURCE_LOCATION)) {
            BlockPos pos = stack.get(ElectrodynamicsDataComponentTypes.BLOCK_POS);
            ResourceKey<Level> world = ResourceKey.create(Registries.DIMENSION, stack.get(ElectrodynamicsDataComponentTypes.RESOURCE_LOCATION));

            MutableComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : Component.literal(world.location().getPath());

            tooltips.add(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(pos.toShortString())));
        } else {
            tooltips.add(NuclearTextUtils.tooltip("frequencycard.notag"));
        }
    }

}
