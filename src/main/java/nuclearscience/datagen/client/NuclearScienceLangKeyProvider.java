package nuclearscience.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import net.minecraft.data.PackOutput;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.recipe.categories.fluiditem2gas.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.FuelReprocessorRecipe;
import nuclearscience.registers.*;

public class NuclearScienceLangKeyProvider extends ElectrodynamicsLangKeyProvider {

	public NuclearScienceLangKeyProvider(PackOutput output, Locale locale) {
		super(output, locale, References.ID);
	}

	@Override
	protected void addTranslations() {

		switch (locale) {
		case EN_US:
		default:

			addCreativeTab("main", "Nuclear Science");

			addItem(NuclearScienceItems.ITEM_URANIUM235, "Uranium-235");
			addItem(NuclearScienceItems.ITEM_URANIUM238, "Uranium-238");
			addItem(NuclearScienceItems.ITEM_PLUTONIUM239, "Plutonium-239");
			addItem(NuclearScienceItems.ITEM_POLONIUM210, "Polonium-210");
			addItem(NuclearScienceItems.ITEM_POLONIUM210_CHUNK, "Polonium-210 Chunk");
			addItem(NuclearScienceItems.ITEM_ACTINIUM225, "Actinium-225");
			addItem(NuclearScienceItems.ITEM_ACTINIUMOXIDE, "Actinium Trioxide");
			addItem(NuclearScienceItems.ITEM_LIFHT4PUF3, "LiF-ThF4-UF4 Salt");
			addItem(NuclearScienceItems.ITEM_FLINAK, "FLiNaK Salt");
			addItem(NuclearScienceItems.ITEM_YELLOWCAKE, "Yellowcake");
			addItem(NuclearScienceItems.ITEM_FISSILEDUST, "Fissile Dust");
			addItem(NuclearScienceItems.ITEM_PLUTONIUMOXIDE, "Plutonium Tetroxide");
			addItem(NuclearScienceItems.ITEM_THORIANITEDUST, "Thorianite Dust");
			addItem(NuclearScienceItems.ITEM_FISSILE_SALT, "Fissile Salt");

			addItem(NuclearScienceItems.ITEM_CELLEMPTY, "Empty Cell");
			addItem(NuclearScienceItems.ITEM_CELLDEUTERIUM, "Deuterium Cell");
			addItem(NuclearScienceItems.ITEM_CELLTRITIUM, "Tritium Cell");
			addItem(NuclearScienceItems.ITEM_CELLHEAVYWATER, "Heavy Water Cell");
			addItem(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC, "Electromagnetic Cell");
			addItem(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL, "Anti-Matter Cell (500 mg)");
			addItem(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE, "Anti-Matter Cell (4 g)");
			addItem(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE, "Anti-Matter Cell (12 g)");
			addItem(NuclearScienceItems.ITEM_CELLDARKMATTER, "Dark Matter Cell");
			addItem(NuclearScienceItems.ITEM_FUELHEUO2, "Highly Enriched Fuel Rod");
			addItem(NuclearScienceItems.ITEM_FUELLEUO2, "Enriched Fuel Rod");
			addItem(NuclearScienceItems.ITEM_FUELSPENT, "Spent Fuel Rod");
			addItem(NuclearScienceItems.ITEM_FUELPLUTONIUM, "Plutonium Fuel Rod");

			addItem(NuclearScienceItems.ITEM_GEIGERCOUNTER, "Geiger Counter");

			addItem(NuclearScienceItems.ITEM_HAZMATHELMET, "Hazmat Hood");
			addItem(NuclearScienceItems.ITEM_HAZMATPLATE, "Hazmat Shroud");
			addItem(NuclearScienceItems.ITEM_HAZMATLEGS, "Hazmat Leggings");
			addItem(NuclearScienceItems.ITEM_HAZMATBOOTS, "Hazmat Boots");

			addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET, "Reinforced Hazmat Hood");
			addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE, "Reinforced Hazmat Shroud");
			addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS, "Reinforced Hazmat Leggings");
			addItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS, "Reinforced Hazmat Boots");

			addItem(NuclearScienceItems.ITEM_ANTIDOTE, "Antidote");
			addItem(NuclearScienceItems.ITEM_IODINETABLET, "Iodine Tablet");
			addItem(NuclearScienceItems.ITEM_FREQUENCYCARD, "Frequency Card");
			addItem(NuclearScienceItems.ITEM_CANISTERLEAD, "Lead-Lined Canister");

			addBlock(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE, "Gas Centrifuge");
			addBlock(NuclearScienceBlocks.BLOCK_NUCLEARBOILER, "Nuclear Boiler");
			addBlock(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR, "Chemical Extractor");
			addBlock(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR, "Radioisotope Generator");
			addBlock(NuclearScienceBlocks.BLOCK_TURBINE, "Steam Turbine");
			addBlock(NuclearScienceBlocks.BLOCK_STEAMFUNNEL, "Steam Funnel");
			addBlock(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE, "Fission Reactor Core");
			addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNET, "Electromagnet");
			addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS, "Electromagnetic Glass");
			addBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER, "Electromagnetic Booster");
			addBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH, "Electromagnetic Switch");
			addBlock(NuclearScienceBlocks.BLOCK_FUSIONREACTORCORE, "Fusion Reactor Core");
			addBlock(NuclearScienceBlocks.BLOCK_PLASMA, "Plasma");
			addBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR, "Melted Reactor Core");
			addBlock(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR, "Particle Injector");
			addBlock(NuclearScienceBlocks.BLOCK_QUANTUMCAPACITOR, "Quantum Capacitor");
			addBlock(NuclearScienceBlocks.BLOCK_TELEPORTER, "Teleporter");
			addBlock(NuclearScienceBlocks.BLOCK_CONTROLROD, "Control Rod");
			addBlock(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR, "Fuel Reprocessor");
			addBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEPROCESSOR, "Radioactive Processor");
			addBlock(NuclearScienceBlocks.BLOCK_MSRFUELPREPROCESSOR, "MSR Fuel Pre-Processor");
			addBlock(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG, "MSR Freeze Plug");
			addBlock(NuclearScienceBlocks.BLOCK_MSREACTORCORE, "MS Reactor Core");
			addBlock(NuclearScienceBlocks.BLOCK_HEATEXCHANGER, "Heat Exchanger");
			addBlock(NuclearScienceBlocks.BLOCK_SIREN, "Siren");
			addBlock(NuclearScienceBlocks.BLOCK_ATOMICASSEMBLER, "Atomic Assembler");
			addBlock(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER, "Molten Salt Supplier");
			addBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVESOIL, "Radioactive Soil");
			addBlock(NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR, "Radioactive Air");
			addBlock(NuclearScienceBlocks.BLOCK_LEAD, "Radiation Shielding");

			addBlock(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic), "VS-Ceramic Pipe");

			addFluid(NuclearScienceFluids.FLUID_IODINESOLUTION, "Iodine Solution");
			//addFluid(NuclearScienceFluids.fluidAmmonia, "Ammonia");

			addGas(NuclearScienceGases.URANIUM_HEXAFLUORIDE, "Uranium Hexafluoride");

			addContainer("gascentrifuge", "Gas Centrifuge");
			addContainer("nuclearboiler", "Nuclear Boiler");
			addContainer("chemicalextractor", "Chemical Extractor");
			addContainer("radioisotopegenerator", "Radioisotope Generator");
			addContainer("reactorcore", "Fission Reactor Core");
			addContainer("particleinjector", "Particle Injector");
			addContainer("quantumcapacitor", "Quantum Capacitor");
			addContainer("fuelreprocessor", "Fuel Reprocessor");
			addContainer("radioactiveprocessor", "Radioactive Processor");
			addContainer("msrfuelpreprocessor", "MSR Fuel Pre-Processor");
			addContainer("freezeplug", "MSR Freeze Plug");
			addContainer("msrreactorcore", "MSR Reactor Core");
			addContainer("moltensaltsupplier", "Molten Salt Supplier");
			addContainer("atomicassembler", "Atomic Assembler");

			addGuiLabel("machine.usage", "Usage: %s");
			addGuiLabel("machine.voltage", "Voltage: %s");
			addGuiLabel("machine.output", "Output: %s");
			addGuiLabel("machine.current", "Current: %s");
			addGuiLabel("machine.transfer", "Output: %s");
			addGuiLabel("machine.stored", "Stored: %s");
			addGuiLabel("particleinjector.charge", "Charge: %s");
			addGuiLabel("particleinjector.matter", "Matter");
			addGuiLabel("particleinjector.cells", "Cells");
			addGuiLabel("fissionreactor.deuterium", "Deuterium");
			addGuiLabel("fissionreactor.temperature", "Temp: %s");
			addGuiLabel("fissionreactor.warning", "OVERHEATING!");
			addGuiLabel("quantumcapacitor.joulesinput", "Joules");
			addGuiLabel("quantumcapacitor.frequency", "Frequency");
			addGuiLabel("msreactor.status", "Status:");
			addGuiLabel("msreactor.status.good", "Good");
			addGuiLabel("msreactor.status.nofreezeplug", "Freeze Plug Missing");
			addGuiLabel("msreactor.status.wastefull", "Waste Full");
			addGuiLabel("msreactor.warning", "Warning:");
			addGuiLabel("msreactor.warning.none", "None");
			addGuiLabel("msreactor.warning.freezeoff", "Freeze Plug Off");
			addGuiLabel("msreactor.warning.overheat", "OVERHEATING");
			addGuiLabel("saltsupplier.waste", "Waste");
			addGuiLabel("saltsupplier.wastecont", "Waste: %s");

			addGuiLabel("displayunit.radsymbol", "Rad");
			addGuiLabel("displayunit.radname", "Rad");
			addGuiLabel("displayunit.radnameplural", "Rads");

			addGuiLabel("freezeplug.status", "Status: %s");
			addGuiLabel("freezeplug.frozen", "FROZEN");
			addGuiLabel("freezeplug.off", "OFF");
			addGuiLabel("freezeplug.saltbonus", "Thermal Bonus: %s");

			addDamageSource("radiation", "%s just did a speedrun of evolution!");
			addDamageSource("plasma", "%s was ionized!");
			add("effect.nuclearscience.radiation", "Radiation");
			add("effect.nuclearscience.radiationresistance", "Radiation Resistance");

			addTooltip("frequencycard.linked", "Linked to %s");
			addTooltip("frequencycard.notag", "No Link");
			addTooltip("deuteriumlevel", "Deuterium: %s");
			addTooltip("tritiumlevel", "Tritium: %s");
			addTooltip("steamfunneluse", "Collects and emits steam");
			addTooltip("radiationshieldingamount", "Shielding Amount: %s");
			addTooltip("radiationshieldinglevel", "Shielding Level: %s");

			addSubtitle(NuclearScienceSounds.SOUND_GASCENTRIFUGE, "Gas Centrifuge spins");
			addSubtitle(NuclearScienceSounds.SOUND_NUCLEARBOILER, "Nuclear Boiler boils");
			addSubtitle(NuclearScienceSounds.SOUND_TURBINE, "Steam Turbine spins");
			addSubtitle(NuclearScienceSounds.SOUND_SIREN, "Siren blares!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_1, "Geiger Counter ticks!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_2, "Geiger Counter ticks!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_3, "Geiger Counter ticks!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_4, "Geiger Counter ticks!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_5, "Geiger Counter ticks!");
			addSubtitle(NuclearScienceSounds.SOUND_GEIGERCOUNTER_6, "Geiger Counter ticks!");

			addJei(NuclearBoilerRecipe.RECIPE_GROUP, "Nuclear Boiler");
			addJei(ChemicalExtractorRecipe.RECIPE_GROUP, "Chemical Extractor");
			addJei(MSRFuelPreProcessorRecipe.RECIPE_GROUP, "MSR Fuel Pre-Processor");
			addJei(RadioactiveProcessorRecipe.RECIPE_GROUP, "Radioactive Processor");
			addJei(FissionReactorRecipe.RECIPE_GROUP, "Fission Reactor");
			addJei(FuelReprocessorRecipe.RECIPE_GROUP, "Fuel Reprocessor");
			addJei("gascentrifuge", "Gas Centrifuge");
			addJei("particalacceleratorantimatter", "Particle Collision");
			addJei("particalacceleratordarkmatter", "Particle Collision");

			addGuidebook(References.ID, "Nuclear Science");

			addGuidebook("chapter.radiation", "Radiation");
			addGuidebook("chapter.radiation.l1.1", "Radiation is one of the key mechanics of Nuclear Science. Radiation is measured in rads and is ray casting-based. Each radiation source will be cast out to entities within a certain radius from the source position. If you are outside the radius, you will not be affected by that particular source. Below, the following" +
					" radiation source has a value of 300 Rads and affects entities in a 3 block radius. By standing outside that 3 block radius, you are not afflicted by it:");

			addGuidebook("chapter.radiation.l1.2", "It is important to understand that the strength of a radiation source does not diminish while you are inside its area of affect. Further, radiation application takes into account how tall an entity is. For example, the below source is only 300 Rads strong, but because the player is two blocks tall, the total amount absorbed is 600 rads:");
			addGuidebook("chapter.radiation.l1.3", "It is worth noting before continuing however that the ray-casting system isn't perfect, and it will struggle in some edge cases!");
			addGuidebook("chapter.radiation.l2.1", "But you may be asking \"what emits radiation?\" Radiation is emitted by various items, blocks, and machines. To see how radioactive an item is, hover over it in your inventory and hold the Control key:");
			addGuidebook("chapter.radiation.l2.2", "It is left up to the player to discover what other sources of radiation there are! A good rule of thumb though is that if it works with radioactive materials, there is a good chance it emits radiation. A useful tool to craft early on is the %s. The Geiger Counter is an invaluable tool that will allow you to view the radiation exposure " +
					"at a current location. When in your inventory, it will tick if exposed to radiation, making it a useful warning device.");

			addGuidebook("chapter.radiation.l3", "Up until now, we have discussed how radiation works and what emits it, but there has been no mention of if it is possible to mitigate it. Radiation is lethal if exposed to it for long enough. Fortunately, there are various methods offered to deal with this.");
			addGuidebook("chapter.radiation.l4", "The first option is to consume an %1$s. Iodine Tablets offer limited radiation resistance, and are capable of protecting you from up to %2$s Rads for up to %3$s Minutes per pill. Even if you are exposed to radiation greater than what the Iodine is rated for, the tablet will still reduce received rads by %4$s. Keep in mind however that the " +
					"effect is limited, so you will need to keep eating Iodine Tablets!");
			addGuidebook("chapter.radiation.l5", "The next option is to wear a %1$s. The Hazmat Suit is far more robust than an Iodine Tablet, and will protect you from radiation at the expense of durability. You can craft a reinforced variant with more durability, but ultimately it too can break. Fortunately, Hazmat Suits can be repaired using a %2$s in an Anvil.");
			addGuidebook("chapter.radiation.hazmatsuit", "Hazmat Suit");
			addGuidebook("chapter.radiation.l6.1", "But all these options seem temporary you might be saying. \"Surely there has to be a more permanent method.\" Well, you're in luck! The third option for radiation mitigation is to use blocks that provide radiation shielding. You can view how much shielding a block provides by hovering over it in your inventory and holding the Shift key:");
			addGuidebook("chapter.radiation.l6.2", "Shielding functions as an impediment, and will reduce radiation by the value displayed. When placing radiation shielding, it is important to remember that the radiation system takes height into account as discussed earlier. Remember the 300 Rad source we previously used? If you only place one block of shielding, it will only shield you " +
					"from half the radiation:");
			addGuidebook("chapter.radiation.l6.3", "If you wanted to fully shield yourself using blocks, you would need to use two:");

			addGuidebook("chapter.radiation.l7", "But what to do if all of this fails? What happens if you get exposed to radiation even after all of these precautions. To remove the radiation effect, you can consume %s, which will remove the radiation effect completely. Antidote is extracted from fish, so you will probably want to get good at fishing!");


			addGuidebook("chapter.turbines", "Steam Turbines");
			addGuidebook("chapter.turbines.l1", "Steam Turbines are what actually produce power in Nuclear Science. Turbines will produce at different voltages depending on what temperature of steam is venting through them. Turbines produce the following voltages under the following temperatures: ");
			addGuidebook("chapter.turbines.tempvoltage", "%1$s C : %2$s V");
			addGuidebook("chapter.turbines.l2", "This relationship is important, as Turbines produce more power the faster they spin. However, the only way to spin a turbine faster is to vent more steam through it. This means that as a turbine spins faster, it will produce more power, but also at an ever-increasing voltage. This can become a " + "major problem, especially in situations like a melting-down reactor, as not only will the reactor explode, but also any machinery that is connected downstream!");

			addGuidebook("chapter.turbines.l3", "To get power out of a turbine, connect a wire to the top of one. A 3x3 group of turbines can be upgraded into a large turbine by whacking the center one with a wrench.");

			addGuidebook("chapter.centrifuge", "Gas Centrifuge");
			addGuidebook("chapter.centrifuge.l1", "The Gas Centrifuge splits Uranium Hexafluoride into its isotopes of U235 and U238. There is a split of 17.5% to 82.5% respectively for each mB of Hexafluoride processed. The Centrifuge runs continuously as long as it has at least 42 mB of Uranium Hexafluoride in its input tank. 10% of each cycle " + "is also converted into waste. Note this is not subtracted from the aforementioned split, and is added on top of it.");
			addGuidebook("chapter.centrifuge.l2", "Once the Centrifuge has collected 2500 mB of a material, it will produce an item of the respective material. This corrosponds to the percentage counter for a category in the Centrifuge's GUI reaching 100%. While this is not 100% realistic, it is a legacy feature to pay tribute to " + "the respective block from Atomic Science. The waste material generated from each cycle will produce Fissile Dust, which can then be processed into other useful materials. Uranium Hexaflouride can either be piped into the back of the Gas Centrifuge, or the Nuclear Boiler can output " + "directly into it.");

			addGuidebook("chapter.fissionreactor", "Fission Reactor");
			addGuidebook("chapter.fissionreactor.l1", "The Fission Reactor is the first nuclear power source that you will be able to access as you progress in Nuclear Science. Crude and simple, the block heats the water surrounding it and converts it to steam, which is used to spin turbines. To construct the Fission Reactor, you will first need " + "to craft a Fission Reactor Core. Place the Core in the center of a 5x5 cube filled with water like so:");

			addGuidebook("chapter.fissionreactor.l2", " Next, cover the top of the water with Turbines. You can either have single turbines, or turn a group of 9 into a large 3x3 turbine using a wrench. The area around the Reactor can be larger than 5x5, but only the 5x5 section will make power.");

			addGuidebook("chapter.fissionreactor.l3", "To make steam, you will need a source of heat. This is where the Fission part of the name comes into play. The following fuel rods can be used to heat the reactor:");
			addGuidebook("chapter.fissionreactor.maxtemp", "Max Temp: %s C");
			addGuidebook("chapter.fissionreactor.cycles", "Cycles: %s");
			addGuidebook("chapter.fissionreactor.l4", "The temperature associated with each fuel type is the temperature the reactor core will reach when 4 rods of that type are used. The Core has a temperature limit of 1417 C, at which point it going above said temperature will begin overheating the core and placing it at risk of melting down. The longer it runs in an overheated state, the greater " + "the risk of meltdown becomes. Also, the hotter a reactor runs, the quicker the fuel source degrades! When a fuel rod is expended, it will leave behind a Spent Fuel Rod which can be processed into other valuable materials.");
			addGuidebook("chapter.fissionreactor.l5", "As you can see, some fuel types will actually cause the reactor to melt down if a set of 4 is used in their entirety. You have two methods of dealing with this. This first is to mix and match certain fuel types. However, this method is mostly a trial-by-error approach, and does not leave a large ammount " + "of room for error.");
			addGuidebook("chapter.fissionreactor.l6", "The second and more reliable approach is to use a ");
			addGuidebook("chapter.fissionreactor.controlrod", "Control Rod");
			addGuidebook("chapter.fissionreactor.l7", " to decrease the rate of the fission reaction. Place a Control Rod under the Reactor Core and Right-Click to extend it. The futher extended the rod is, the slower the reaction and thus cooler the reactor will run. Shift + Right-Click to retract the rod.");
			addGuidebook("chapter.fissionreactor.l8", "One final item of note is that by placing a Dueterium Cell in the reactor while running, it has a chance to be enriched and transformed into a Tritium Cell. Tritium is required in order to run a Fusion Reactor. The conversion process will occur when the temperature of the core is above 800 C.");

			addGuidebook("chapter.radiogen", "Radioisotropic Generator");
			addGuidebook("chapter.radiogen.l1", "The Radioisotropic Generator presents an alternative to the fission reaction. It instead uses the natural heat produced from the radioactive decay of items to directly generate electricity. Simply place a radioactive item in it, and it will begin to generate power. The more radioactive an item is, " + "and the more of said item there is, the more power will be produced!");

			addGuidebook("chapter.msreactor", "MS Reactor");
			addGuidebook("chapter.msreactor.l1", "The Molton Salt Reactor is a far more refined version of the crude Fission Reactor, but is also far more expensive. Instead of being directly cooled by water, the reactor core is cooled by molten salt, which is then ran through a water-cooled heat sink network to itself be cooled. This has the added " + "benefit of not needing to have the reactor core submersed in water and allowiong you to be flexable with turbine placement.");
			addGuidebook("chapter.msreactor.l2", "To build a Molten Salt Reactor, you will need 3 components:");
			addGuidebook("chapter.msreactor.l3", "Place a Freeze Plug, and then place a Reactor Core on top of it. Facing the green port on the core, place a Molten Salt Supplier so that its green port faces the Core's.");

			addGuidebook("chapter.msreactor.l4", "The MS Reactor isn't cooled by water and is instead cooled by FLiNaK salt, which the Freeze Plug supplies. Simply make the salt and place it in the Plug. The salt is not consumed by the reactor, but the more salt you add, the more heat it will be able to remove.");
			addGuidebook("chapter.msreactor.l5", "Unlike the Fission Reactor which uses solid fuel rods to produce heat, the Molten Salt Reactor uses a specially prepared radioactive salt, LiF-ThF4-UF4 Salt. However, as the name of the reactor implies, this salt must be molten in order to be used. Place the salt pellet " + "in the Molten Salt Supplier to melt it. Each salt pellet melts to %s mB, and the core has an internal capacity of 1000 mB. The salt is slowly consumed over time and produces waste which is also collected by the supplier. Just like the Fission Reactor, the hotter the reactor, the faster the fuel is consumed.");

			addGuidebook("chapter.msreactor.l6", "Now that the reactor is hot, you need to disperse the heat from the coolant. The MS Reactor has a melt-down temperature of 1000 C. To do this, you will need:");
			addGuidebook("chapter.msreactor.l7", "The VS Pipe is connected to the top of the Reactor Core and fed into the bottom of the Heat Exchanger. The Heat Exchanger is incredibally efficient, and very few are needed to keep the reactor cool. In fact, a single Heat Exchanger can almost keep a MS Reactor cool by itself! To effectively do this, " + "it must be placed in a 5x5x2 pool of water, and must be water-logged to function. The easiest way to do this is to fill the pool and then place the Exchanger. It should be noted that while the exchanger doesn't have to be directly above the reactor core, the longer the VS Pipe is, the more heat it will " + "lose before it reaches the Exchanger!");

			addGuidebook("chapter.msreactor.l8", "The Control Rod, like with the Fission Reactor, can also be used to slow the fuel use of the Core. Attach it to the side of the MS Reactor Core, and control it as with the Fission Reactor.");

			addGuidebook("chapter.fusionreactor", "Fusion Reactor");
			addGuidebook("chapter.fusionreactor.l1", "The Fusion Reactor is the ultimate source of power that Nuclear Science has to offer, and is able to produce over 6 MW of energy. However, harvesting this energy is very expensive. First, you will to construct 13x13 diamond of Electromagnets. They can be either glass or opaque. We will be using both. " + "Place the Fusion Reactor Core in the center of the diamond and remove the block below it.");
			addGuidebook("chapter.fusionreactor.l2", "Next, surround the side of the 13x13 diamond with a ring of Electromagnets.");
			addGuidebook("chapter.fusionreactor.l3", "Next, build another 13x13 diamond to act as the roof. Leave a hole in the middle for the Reactor Core like before.");
			addGuidebook("chapter.fusionreactor.l4", "Next, you will need to cover the top of the Electromagnets with water. The plasma of the reactor will heat the water generating steam, which can in turn be used for spinning turbines. Note the turbines will operate at 480V.");
			addGuidebook("chapter.fusionreactor.l5", "The core requires 50 kJ/t at 480V to operate. While the initial energy must come from somewhere else, you can use the outuput of the turbines to feed back into the reaction. The wire can be connected to the top or the bottom of the reactor. In this case, we will be using the top.");
			addGuidebook("chapter.fusionreactor.l6", "The Reactor uses Deuterium and Tritium Cells to fuel the reaction. To add fuel to the core, right-click a cell on it. This can be done through the convenient hole left in the bottom.");

			addGuidebook("chapter.particleaccelerator", "Particle Accelerator");
			addGuidebook("chapter.particleaccelerator.l1", "The Particle Accelerator is used to make Anti-Matter and Dark Matter. Both are generated when two Particles collide at great speeds. The result of a particle collision is determined by the following formula:");
			addGuidebook("chapter.particleaccelerator.formula", "((s1 + s2) / 4) ^ 2");
			addGuidebook("chapter.particleaccelerator.l2", "where s1 and s2 are the speeds of the two particles. Dark Matter is created when the resulting value is greater than 0.999, and has a 100% chance of being created. Otherwise, Anti-Matter is created from the collision. However, the chance of Anti-Matter being generated is not guaranteed, " + "and its chance of being created increases the closer the resulting value gets to 0.999.");

			addGuidebook("chapter.particleaccelerator.l3", "In order to collide particles, you will first need to create particles. For this, you will need a Particle Injector. The Injector uses matter to make a particle. Any block or item can be used to supply the matter. Place the matter in its respective slot in the Injector. To catch the result of the collision, " + "you will need to craft an Electromagnetic Cell and place it in its respective slot in the Injector as well. The Injector uses 200 MJ per particle at 960 V. This means you will need 400 MJ for each collision.");

			addGuidebook("chapter.particleaccelerator.l4", "While the Injector creates particles, it spawns them at very low speeds. In order to facilitate a collision, both particles will need to be moving at very high speeds. This can be accomplished by passing a Particle through an Electromagnetic Booster. If the Particle and Booster are facing the " + "same direction, the Booster will increase the Particle's speed by 0.33% for every tick the Particle is inside the Booster. If the Booster is a corner, it will increase the Particle's speed by 0.17% for every tick the Particle is inside the Booster. This means that the faster a Particle moves, " + "the less effective each successive Booster becomes. It is important to note that moving particles emit radiation.");

			addGuidebook("chapter.particleaccelerator.l5", "To reach 100% speed, it will take 200 Boosters in a straight line. However, this would require a very large amount of room. Fortunately, Boosters can be set up in a snake pattern to help compact them by converting a placed Booster to a corner variant. To make one a corner variant, stand on top " + "of Booster segment you wish to turn, and place a Booster to start the next segment after the corner. Looking at the outter bottom corner of the next segment's Booster, place the corner Booster. If you did it properly, you will not be able to directly see the inside of Booster.");

			addGuidebook("chapter.particleaccelerator.l6", "The Particle Accelerator setup pictured here with 60 Boosters has a roughly 1 in 5 chance of creating Anti Matter from a collision:");

			addGuidebook("chapter.particleaccelerator.l7", "But how do we get two particles to actually collide? This is where regular Electromagnets come in. You will need to construct a ring of Electromagnets to allow the Particles to circle and collide. It is incredibally important to note that particles will begin to lose speed the moment they exit " + "the Booster chain, so it is desirable to make the Electromagnet ring as small as possible. In this case, we will be using a 3x3 ring.");
			addGuidebook("chapter.particleaccelerator.l8", "Start by laying out the bottom of the ring at the end of the Booster chain like so:");

			addGuidebook("chapter.particleaccelerator.l9", "To ensure the particles actually collide, you will need to switch the direction of one. This is what the Electromagnetic Switch is for. The switch flips the direction of every other particle that crosses it. Place an Electromagnetic switch in front of the output of the Booster chain like so:");
			addGuidebook("chapter.particleaccelerator.l10", "Now, fill in the sides and top of the ring to create an enclosed structure for the Particles. Note either Electromagnets or Electromagnetic Glass can be used.");

			addGuidebook("chapter.othermachines", "Other Machines");
			addGuidebook("chapter.othermachines.quantumcapacitor1", "The Quantum Capacitor is the ultimate energy storage device. It has an unlimited energy storage capacity and the storage is shared across capacitors. The GUI has two programmable fields. The first is for the joules/tick output of the capacitor. Note, the capacitor outputs at 1.92 kV! " + "The second is for the frequency. The frequency of two capacitors must match for them to link. Every capacitor network is tied to a specific player, so don't worry about other players stealing your energy!");

			addGuidebook("chapter.othermachines.teleporter1", "The Teleporter requires 5 MJ to teleport a player. Two teleporters are needed: one to send the player, and one to recieve them. Only the sending end needs to be powered. To link two teleporters, Right-Click the recieving end with a Frequency Card. Then Right-Click the sending end with the " + "same card to link the two teleporters.");

			addGuidebook("chapter.othermachines.teleporter2", "After each teleport, there is 4 second cooldown applied to the sending end. Note, it is possible to link a teleporter to itself, so be careful! To wipe a Frequency Card, simply craft it in your inventory.");

			addGuidebook("chapter.othermachines.atomicassembler1", "The Atomic Assembler makes use of the strange properties of Dark Matter, and is able to duplicate items (we have made sure you can't dupe items with inventories, so no dupe bugs this time (: ). It requires 72 kW at 480 V to run. If it loses power, all progress is lost!");
			addGuidebook("chapter.othermachines.atomicassembler2", "To use it, take an item of your desire and place it in the Assembler. Surround the item with Dark Matter Cells like shown:");
			addGuidebook("chapter.othermachines.atomicassembler3", "Each duplication takes 3600 ticks, and will take one use of the Dark Matter Cells. Cells have 64 uses in total.");

			addGuidebook("chapter.misc", "Misc");
			addGuidebook("chapter.misc.l1", "Futurum Usui.");

			addJei("gui.reactorcore.info.temp", "Range: 0 C to 1400 C");

			addJei("gascentrifuge.info.power_usage", "240V 30kW");
			addJei("particalaccelerator.antimatter.collision", "Charge:100% Speed<100%");
			addJei("particalaccelerator.darkmatter.collision", "Charge:100% Speed:100%");

		}

	}

}
