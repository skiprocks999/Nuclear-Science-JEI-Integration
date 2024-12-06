package nuclearscience.common.item;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.registers.ElectrodynamicsDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.prefab.utils.NuclearDisplayUnits;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceCapabilities;
import nuclearscience.registers.NuclearScienceSounds;

import java.util.function.Function;

public class ItemGeigerCounter extends ItemElectric {

    public static final double POWER_USAGE = 1666666.66667 / (120.0 * 20.0);

    public ItemGeigerCounter(ElectricItemProperties properties, Holder<CreativeModeTab> creativeTab, Function<Item, Item> getBatteryItem) {
        super(properties, creativeTab, getBatteryItem);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (worldIn.isClientSide()) {
            return;
        }

        if (entityIn instanceof Player player) {

            boolean noPower = getJoulesStored(stack) < POWER_USAGE;

            IRadiationRecipient capability = player.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
            if (capability == null) {
                return;
            }

            RadioactiveObject recievedRads = capability.getRecievedRadiation(player);

            if (isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter) {
                if(noPower) {
                    player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.nopower"), true);
                } else {
                    player.displayClientMessage(ChatFormatter.getChatDisplay(recievedRads.amount(), NuclearDisplayUnits.RAD, 3, true), true);
                }

            }

            if (!noPower && recievedRads.amount() > 0 && worldIn.random.nextFloat() * 50 * 60.995 / 3 < recievedRads.amount()) {

                SoundEvent sound = switch(worldIn.random.nextIntBetweenInclusive(1, 6)) {
                    case 2 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_2.get();
                    case 3 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_3.get();
                    case 4 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_4.get();
                    case 5 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_5.get();
                    case 6 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_6.get();
                    default -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_1.get();
                };

                worldIn.playSound(null, player.blockPosition(), sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                IItemElectric.setEnergyStored(stack, this.getJoulesStored(stack) - stack.getOrDefault(ElectrodynamicsDataComponentTypes.POWER_USAGE, POWER_USAGE));
                player.getInventory().setItem(itemSlot, stack);
                player.getInventory().setChanged();

            }


        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
