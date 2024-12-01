package nuclearscience.api.radiation.util;

import net.minecraft.world.entity.LivingEntity;

/**
 * Instead of the radiation manager directly apply affects like radiation and hunger to radiation recipients,
 * this class is intended to allow individual recipients to handle their infections.
 *
 *
 */
public interface IRadiationRecipient {

    void recieveRadiation(LivingEntity entity, double rads, double strength);

    RadioactiveObject getRecievedRadiation(LivingEntity entity);

    void tick(LivingEntity entity);


}
