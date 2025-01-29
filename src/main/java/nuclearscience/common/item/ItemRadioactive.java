package nuclearscience.common.item;

import electrodynamics.common.item.ItemElectrodynamics;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;
import nuclearscience.registers.NuclearScienceCapabilities;

public class ItemRadioactive extends ItemElectrodynamics {

	public ItemRadioactive(Properties properties, Holder<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		Level world = entity.level();
		RadioactiveObject rad = RadioactiveItemRegister.getValue(stack.getItem());
		double amount = stack.getCount() * rad.amount();
		int range = (int) (Math.sqrt(amount) / (5 * Math.sqrt(2)) * 1.25);
		RadiationSystem.addRadiationSource(world, new SimpleRadiationSource(amount, rad.strength(), range, true, 0, entity.getOnPos(), false));
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
		RadioactiveObject rad = RadioactiveItemRegister.getValue(stack.getItem());

		if(entity instanceof LivingEntity living) {
			IRadiationRecipient cap = living.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
			if(cap == null) {
				return;
			}
			cap.recieveRadiation(living, stack.getCount() * rad.amount(), rad.strength());
		}
	}

}
