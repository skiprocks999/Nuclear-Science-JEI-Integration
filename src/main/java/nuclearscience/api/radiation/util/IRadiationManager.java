package nuclearscience.api.radiation.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.SimpleRadiationSource;

import java.util.List;

/**
 * An abstraction and refactor of Radiation System by AurilisDev
 * <p>
 * Implementations of this are capability-based and will handle the distribution of radiation to radiation recipients
 * within its perview. The assumption is that implementations of this will use ray casting to apply radiation effects
 * to surrounding recipients, however the logic is left to the implementer. It is also assumed this manager will be
 * ticked to perform its logic.
 *
 * @author skip999
 */
public interface IRadiationManager {

    /**
     * A list of all permanent radiation sources currently handled by this manager. You should not
     * remove items from this list.
     *
     * @return
     */
    public List<SimpleRadiationSource> getPermanentSources();


    /**
     * Adds a radiation source to this manager. The manager will then categorize it accordingly
     *
     * @param source
     */
    public void addRadiationSource(SimpleRadiationSource source);

    /**
     * The default rate at which this manager will disipate any FadingRadiationSources
     *
     * @param radiationDisipation
     */
    public void setDisipation(double radiationDisipation);

    public void setLocalizedDisipation(double disipation, BlockPosVolume area);

    public void removeLocalizedDisipation(BlockPosVolume area);

    /**
     * Removes a radiation source from this manager
     *
     * @param pos                     The location of the source
     * @param shouldLeaveFadingSource Whether the removed source should leave behind a fading radiation source
     * @return
     */
    public boolean removeRadiationSource(BlockPos pos, boolean shouldLeaveFadingSource);


    public void tick(Level world);


    /**
     * A wrapper class that represents a radiation source that has been removed. Just because the source
     * of radiation itself is gone doesn't mean the radiation immediately disipates.
     *
     * @author skip999
     */
    public static class FadingRadiationSource {

        public static final Codec<FadingRadiationSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("distance").forGetter(instance0 -> instance0.distance),
                Codec.DOUBLE.fieldOf("strength").forGetter(instance0 -> instance0.strength),
                Codec.DOUBLE.fieldOf("amount").forGetter(instance0 -> instance0.radiation)

        ).apply(instance, FadingRadiationSource::new));


        public final int distance;
        public double strength;
        public double radiation;

        public FadingRadiationSource(int distance, double strength, double radiation) {
            this.distance = distance;
            this.strength = strength;
            this.radiation = radiation;
        }

    }

    public static class TemporaryRadiationSource {

        public static final Codec<TemporaryRadiationSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("ticks").forGetter(instance0 -> instance0.ticks),
                Codec.DOUBLE.fieldOf("strength").forGetter(instance0 -> instance0.strength),
                Codec.DOUBLE.fieldOf("amount").forGetter(instance0 -> instance0.radiation),
                Codec.BOOL.fieldOf("leavefading").forGetter(instance0 -> instance0.leaveFading),
                Codec.INT.fieldOf("distance").forGetter(instance0 -> instance0.distance)

        ).apply(instance, TemporaryRadiationSource::new));

        public int ticks;
        public final double strength;
        public final double radiation;
        public final boolean leaveFading;
        public final int distance;

        public TemporaryRadiationSource(int ticks, double strength, double radiation, boolean leaveFading, int distance) {
            this.ticks = ticks;
            this.strength = strength;
            this.radiation = radiation;
            this.leaveFading = leaveFading;
            this.distance = distance;
        }


    }


}
