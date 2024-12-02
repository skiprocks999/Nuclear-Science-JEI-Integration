package nuclearscience.api.radiation;

import electrodynamics.Electrodynamics;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.util.IHazmatSuit;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import nuclearscience.registers.NuclearScienceEffects;

public class CapabilityRadiationRecipient implements IRadiationRecipient {

    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @Override
    public void recieveRadiation(LivingEntity entity, double rads, double strength) {

        if (rads <= 0) {
            return;
        }

        if (entity instanceof Player player && player.isCreative()) {
            player.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT, player.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT) + rads);
            player.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, player.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH) + strength);
            return;
        }

        int count = 0;

        for (EquipmentSlot slot : ARMOR_SLOTS) {

            ItemStack stack = entity.getItemBySlot(slot);

            if (stack.getItem() instanceof IHazmatSuit) {

                //TODO implement damage reduction based on radiation amount and strength

                count++;

                float damage = (float) (rads * 2.15f) / 2169.9975f;

                if (Electrodynamics.RANDOM.nextFloat() >= damage) {
                    continue;
                }

                stack.hurtAndBreak((int) Math.ceil(damage), entity, slot);

            }

        }

        // Not Full Set
        if (count < 4) {

            int amplitude = getAmplitudeFromRadiation(rads, strength);
            int time = getDurationFromRadiation(rads);

            if (entity.hasEffect(NuclearScienceEffects.RADIATION)) {

                MobEffectInstance instance = entity.getEffect(NuclearScienceEffects.RADIATION);

                if (instance.getAmplifier() > amplitude) {
                    entity.addEffect(new MobEffectInstance(NuclearScienceEffects.RADIATION, time + instance.getDuration(), instance.getAmplifier(), false, true));
                } else {
                    entity.addEffect(new MobEffectInstance(NuclearScienceEffects.RADIATION, time + instance.getDuration(), amplitude, false, true));
                }

            } else {
                entity.addEffect(new MobEffectInstance(NuclearScienceEffects.RADIATION, time, amplitude, false, true));
            }
        }

        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT, entity.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT) + rads);
        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, entity.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH) + strength);

    }

    @Override
    public RadioactiveObject getRecievedRadiation(LivingEntity entity) {
        return new RadioactiveObject(entity.getData(NuclearScienceAttachmentTypes.OLD_RECIEVED_RADIATIONSTRENGTH), entity.getData(NuclearScienceAttachmentTypes.OLD_RECIEVED_RADIATIONAMOUNT));
    }

    @Override
    public void tick(LivingEntity entity) {

        entity.setData(NuclearScienceAttachmentTypes.OLD_RECIEVED_RADIATIONAMOUNT, entity.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT));
        entity.setData(NuclearScienceAttachmentTypes.OLD_RECIEVED_RADIATIONSTRENGTH, entity.getData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH));

        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONAMOUNT, 0.0);
        entity.setData(NuclearScienceAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, 0.0);

    }

    public static int getDurationFromRadiation(double radiation) {
        return (int) Math.max(20.0, radiation / 100.0 * 20.0);
    }

    public static int getAmplitudeFromRadiation(double radiation, double strength) {
        return (int) Math.min(40.0, radiation / 100.0 * strength);
    }

}
