package nuclearscience.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.IntValue;

@Configuration(name = "Nuclear Science")
public class Constants {

	@DoubleValue(def = 1000000.0)
	public static double TELEPORTER_USAGE_PER_TELEPORT = 1000000.0;
	@DoubleValue(def = 120.0)
	public static double RADIOISOTOPEGENERATOR_VOLTAGE = 120.0;
	@DoubleValue(def = 0.35)
	public static double RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER = 0.35f;
	@DoubleValue(def = 350000.0)
	public static double FISSIONREACTOR_MAXENERGYTARGET = 350000.0;
	@DoubleValue(def = 850000.0)
	public static double MSRREACTOR_MAXENERGYTARGET = 850000.0;
	@DoubleValue(def = 6000000.0)
	public static double FUSIONREACTOR_MAXENERGYTARGET = 6000000.0;
	@DoubleValue(def = 50000.0)
	public static double FUSIONREACTOR_USAGE_PER_TICK = 50000.0;
	@DoubleValue(def = 200000000.0)
	public static double PARTICLEINJECTOR_USAGE_PER_PARTICLE = 200000000.0;
	@IntValue(def = 1024)
	public static int FUSIONREACTOR_MAXSTORAGE = 1024;
	@DoubleValue(def = 1500)
	public static double GASCENTRIFUGE_USAGE_PER_TICK = 1500.0;
	@IntValue(def = 20)
	public static int GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING = 20;
	@DoubleValue(def = 1200.0)
	public static double FREEZEPLUG_USAGE_PER_TICK = 200.0;
	@DoubleValue(def = 120.0)
	public static double MOLTENSALTSUPPLIER_VOLTAGE = 120.0;
	@DoubleValue(def = 200.0)
	public static double MOLTENSALTSUPPLIER_USAGE_PER_TICK = 200.0;
	@DoubleValue(def = 1250.0)
	public static double ATOMICASSEMBLER_USAGE_PER_TICK = 6000.0;
	@DoubleValue(def = 480.0)
	public static double ATOMICASSEMBLER_VOLTAGE = 480.0;
	@IntValue(def = 1200)
	public static int ATOMICASSEMBLER_REQUIRED_TICKS = 12000;
	@DoubleValue(def = 100)
	public static double BACKROUND_RADIATION_DISSIPATION = 100;
	@DoubleValue(def = 300)
	public static double IODINE_RESISTANCE_THRESHHOLD = 300;
	@DoubleValue(def = 0.8)
	public static double IODINE_RAD_REDUCTION = 0.8;
	@IntValue(def = 40)
	public static int QUANTUM_TUNNEL_FREQUENCY_CAP_PER_PLAYER = 40;
	@IntValue(def = 40)
	public static int ANTIMATTER_TICKS_ON_GROUND = 40;


	@IntValue(def = 5)
	public static int ATOMIC_ASSEMBLER_RADIATION_RADIUS = 5;
	@IntValue(def = 3)
	public static int CHEMICAL_EXTRACTOR_RADIATION_RADIUS = 3;
	@IntValue(def = 10)
	public static int FUEL_REPROCESSOR_RADIATION_RADIUS = 10;
	@IntValue(def = 5)
	public static int GAS_CENTRIFUGE_RADIATION_RADIUS = 5;
	@IntValue(def = 5)
	public static int NUCLEAR_BOILER_RADIATION_RADIUS = 5;
	@IntValue(def = 3)
	public static int PARTICLE_INJECTOR_RADIATION_RADIUS = 3;
	@IntValue(def = 5)
	public static int RADIOACTIVE_PROCESSOR_RADIATION_RADIUS = 5;
	@IntValue(def = 10)
	public static int RADIO_GENATOR_RADIATION_RADIUS = 10;

}
