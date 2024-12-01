package nuclearscience.common.item;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.common.item.ItemElectrodynamics;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceCapabilities;
import nuclearscience.registers.NuclearScienceSounds;

public class ItemGeigerCounter extends ItemElectrodynamics {

    public ItemGeigerCounter(Properties properties, Holder<CreativeModeTab> creativeTab) {
        super(properties, creativeTab);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (worldIn.isClientSide()) {
            return;
        }

        if (entityIn instanceof Player player) {

            IRadiationRecipient capability = player.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
            if (capability == null) {
                return;
            }

            RadioactiveObject recievedRads = capability.getRecievedRadiation(player);

            if (isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter) {
                player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.text", ChatFormatter.formatDecimals(recievedRads.amount(), 3)), true);
            }

            if (worldIn.random.nextFloat() * 50 * 60.995 / 3 < recievedRads.amount()) {
                worldIn.playSound(null, player.blockPosition(), NuclearScienceSounds.SOUND_GEIGER.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                //SoundAPI.playSound(NuclearScienceSounds.SOUND_GEIGER.get(), SoundSource.BLOCKS, 1, 1, player.blockPosition());
            }


        }

		/*
		if (entityIn instanceof Player player) {
			if (!worldIn.isClientSide) {
				if ((isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter) && RadiationSystem.radiationMap.get().containsKey(entityIn)) {
					player.displayClientMessage(NuclearTextUtils.chatMessage("geigercounter.text", ChatFormatter.formatDecimals(RadiationSystem.radiationMap.get().get(entityIn), 3)), true);
				}
			}
			if (worldIn.isClientSide && RadiationSystem.radiationMap.get().containsKey(entityIn) && (isSelected || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter)) {
				double amount = RadiationSystem.radiationMap.get().get(entityIn);
				if (worldIn.random.nextFloat() * 50 * 60.995 / 3 < amount) {
					SoundAPI.playSound(NuclearScienceSounds.SOUND_GEIGER.get(), SoundSource.BLOCKS, 1, 1, player.blockPosition());
				}
			}
		}

		 */
    }
}
