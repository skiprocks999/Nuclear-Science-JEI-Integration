package nuclearscience.api.radiation;

import electrodynamics.Electrodynamics;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import nuclearscience.registers.NuclearScienceEffects;

public class CapabilityRadiationRecipient implements IRadiationRecipient {

    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @Override
    public void recieveRadiation(LivingEntity entity, double rads, double strength) {

        if(rads <= 0) {
            return;
        }

        if(entity instanceof Player player && player.isCreative()) {
            player.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATION, rads);
            return;
        }

        int count = 0;

        for(EquipmentSlot slot : ARMOR_SLOTS) {

            ItemStack stack = entity.getItemBySlot(slot);

            if(stack.getItem() instanceof ItemHazmatArmor) {

                count ++;

                float damage = (float) (rads * 2.15f) / 2169.9975f;

                if (Electrodynamics.RANDOM.nextFloat() >= damage) {
                    continue;
                }

                stack.hurtAndBreak((int) Math.ceil(damage), entity, slot);

            }

        }

        // Full Set
        if(count == 4) {
            entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATION, rads);
            return;
        }

        //TODO rework this perhaps?
        double modifier = strength / rads;
        int amplitude = (int) Math.max(0, Math.min(strength / modifier, 9));
        int time = (int) (strength / modifier / ((amplitude + 1)));
        if (amplitude == 0 && time <= 40) {
            return;
        }

        entity.addEffect(new MobEffectInstance(NuclearScienceEffects.RADIATION, time, Math.min(40, amplitude), false, true));

        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATION, rads);

    }

    @Override
    public void tick(LivingEntity entity) {
        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATION, 0.0);

    }

}
