package nuclearscience.api.radiation.rework;

/**
 * Instead of the radiation manager directly apply affects like radiation and hunger to radiation recipients,
 * this class is intended to allow individual recipients to handle their infections.
 *
 * @author skip999
 *
 */
public interface IRadiationRecipient {

    void setRadiation(double rads);

    double getCurrentRadiation(double rads);

    void setDisipation(double disipation);

    void setTolerance(double tolerance);

    double getTolerance();

    void tick();


}
