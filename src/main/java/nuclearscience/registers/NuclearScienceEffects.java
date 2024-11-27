package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.api.radiation.EffectRadiation;

public class NuclearScienceEffects {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, References.ID);

	public static final DeferredHolder<MobEffect, EffectRadiation> RADIATION = EFFECTS.register("radiation", EffectRadiation::new);

}
