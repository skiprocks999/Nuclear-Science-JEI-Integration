package nuclearscience.common.tags;

import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class NuclearScienceTags {

	public static void init() {
		Fluids.init();
		Items.init();
		Gases.init();
	}

	public static class Items {

		public static final TagKey<Item> CELL_EMPTY = forgeTag("cells/empty");
		public static final TagKey<Item> CELL_HEAVYWATER = forgeTag("cells/heavywater");
		public static final TagKey<Item> CELL_DEUTERIUM = forgeTag("cells/deuterium");
		public static final TagKey<Item> CELL_TRITIUM = forgeTag("cells/tritium");
		public static final TagKey<Item> CELL_ANTIMATTER_SMALL = forgeTag("cells/anti_matter_small");
		public static final TagKey<Item> CELL_ANTIMATTER_LARGE = forgeTag("cells/anti_matter_large");
		public static final TagKey<Item> CELL_ANTIMATTER_VERY_LARGE = forgeTag("cells/anti_matter_very_large");
		public static final TagKey<Item> CELL_DARK_MATTER = forgeTag("cells/dark_matter");

		public static final TagKey<Item> FUELROD_URANIUM_LOW_EN = forgeTag("fuel_rods/leuo2");
		public static final TagKey<Item> FUELROD_URANIUM_HIGH_EN = forgeTag("fuel_rods/heuo2");
		public static final TagKey<Item> FUELROD_PLUTONIUM = forgeTag("fuel_rods/plutonium");
		public static final TagKey<Item> FUELROD_SPENT = forgeTag("fuel_rods/spent");

		public static final TagKey<Item> DUST_THORIUM = forgeTag("dusts/thorium");
		public static final TagKey<Item> DUST_FISSILE = forgeTag("dusts/fissile");

		public static final TagKey<Item> SALT_FISSILE = forgeTag("salts/fissile");

		public static final TagKey<Item> OXIDE_PLUTONIUM = forgeTag("oxide/plutonium");
		public static final TagKey<Item> OXIDE_ACTINIUM = forgeTag("oxide/actinium");

		public static final TagKey<Item> NUGGET_POLONIUM = forgeTag("nuggets/polonium");

		public static final TagKey<Item> PELLET_URANIUM235 = forgeTag("pellets/uranium235");
		public static final TagKey<Item> PELLET_URANIUM238 = forgeTag("pellets/uranium238");
		public static final TagKey<Item> PELLET_PLUTONIUM = forgeTag("pellets/plutonium");
		public static final TagKey<Item> PELLET_POLONIUM = forgeTag("pellets/polonium");
		public static final TagKey<Item> PELLET_LIFHT4PUF3 = forgeTag("pellets/lifht4puf3");
		public static final TagKey<Item> PELLET_FLINAK = forgeTag("pellets/flinak");
		public static final TagKey<Item> PELLET_ACTINIUM225 = forgeTag("pellets/actinium225");

		public static final TagKey<Item> YELLOW_CAKE = forgeTag("yellow_cake_uranium");

		private static void init() {
		}

		private static TagKey<Item> forgeTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}

	}

	public static class Fluids {

		public static final TagKey<Fluid> IODINE_SOLUTION = forgeTag("iodine_solution");
		public static final TagKey<Fluid> METHANOL = forgeTag("methanol");

		private static void init() {

		}

		private static TagKey<Fluid> forgeTag(String name) {
			return FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
		}
	}

	public static class Gases {

		public static final TagKey<Gas> URANIUM_HEXAFLUORIDE = forgeTag("uranium_hexafluoride");

		private static void init() {

		}

		private static TagKey<Gas> forgeTag(String name) {
			return create(ResourceLocation.fromNamespaceAndPath("c", name));
		}

		public static TagKey<Gas> create(ResourceLocation loc) {
			return TagKey.create(ElectrodynamicsGases.GAS_REGISTRY_KEY, loc);
		}
	}

}
