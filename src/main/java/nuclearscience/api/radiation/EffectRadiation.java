package nuclearscience.api.radiation;

import java.util.Set;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;
import nuclearscience.registers.NuclearScienceDamageTypes;

public class EffectRadiation extends MobEffect {

	public static final EffectCure CURE = EffectCure.get("radiationcure");

	public EffectRadiation(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	public EffectRadiation() {
		this(MobEffectCategory.HARMFUL, 5149489);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().random.nextFloat() < 0.033) {
			entity.hurt(entity.damageSources().source(NuclearScienceDamageTypes.RADIATION, entity), (float) (Math.pow(amplifier, 1.3) + 1));
			if (entity instanceof Player pl) {
				pl.causeFoodExhaustion(0.05F * (amplifier + 1));
			}
		}
		return true;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		cures.clear();
		cures.add(CURE);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

}
