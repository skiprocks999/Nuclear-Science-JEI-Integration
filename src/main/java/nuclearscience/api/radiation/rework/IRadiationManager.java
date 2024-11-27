package nuclearscience.api.radiation.rework;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * An abstraction and refactor of Radiation System by AurilisDev
 *
 * Implementations of this are capability-based and will handle the distribution of radiation to radiation recipients
 * within its perview. The assumption is that implementations of this will use ray casting to apply radiation effects
 * to surrounding recipients, however the logic is left to the implementer. It is also assumed this manager will be
 * ticked to perform its logic.
 *
 * @author skip999
 *
 */
public interface IRadiationManager {


    /**
     * All List of all radiation sources currently handled by this manager. You should not
     * remove sources from this list.
     *
     * @return
     */
    public List<IRadiationSource> getAllRadiationSources();


    /**
     * A List of all temporary radiation sources currently handled by this manager. You should
     * not remove items from this list.
     *
     * @return
     */
    public List<IRadiationSource> getTemporarySources();

    /**
     * A list of all permanent radiation sources currently handled by this manager. You should not
     * remove items from this list.
     * @return
     */
    public List<IRadiationSource> getPermanentSources();


    /**
     * Adds a radiation source to this manager. The manager will then categorize it accordingly
     * @param source
     */
    public void addRadiationSource(IRadiationSource source);

    /**
     * The rate at which this manager will disipate any FadingRadiationSources
     *
     * @param radiationDisipation
     */
    public void setDisipation(double radiationDisipation);

    /**
     * Removes a radiation source from this manager
     *
     * @param pos The location of the source
     * @param shouldLeaveFadingSource Whether the removed source should leave behind a fading radiation source
     * @return
     */
    public boolean removeRadiationSource(BlockPos pos, boolean shouldLeaveFadingSource);


    public void tick();


    /**
     *
     * A wrapper class that represents a radiation source that has been removed. Just because the source of radiation
     * itself is gone doesn't mean the radiation immediately disipates.
     *
     * @author skip999
     */
    public static class FadingRadiationSource {

        public double strength;
        public double radiation;
        public final BlockPos pos;

        public FadingRadiationSource(double strength, double radiation, BlockPos pos) {
            this.strength = strength;
            this.radiation = radiation;
            this.pos = pos;
        }

    }



}
