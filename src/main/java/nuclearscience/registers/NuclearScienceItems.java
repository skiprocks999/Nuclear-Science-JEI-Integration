package nuclearscience.registers;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.creativetab.CreativeTabSupplier;
import electrodynamics.api.registration.BulkDeferredHolder;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.common.item.ItemElectrodynamics;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.item.ItemAntidote;
import nuclearscience.common.item.ItemAntimatter;
import nuclearscience.common.item.ItemCanisterLead;
import nuclearscience.common.item.ItemFrequencyCard;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.common.item.ItemIodineTablet;
import nuclearscience.common.item.ItemRadioactive;

public class NuclearScienceItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, References.ID);

	/* BLOCKS */

	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeRadiationShielding> ITEMS_RADIATION_SHIELDING = new BulkDeferredHolder<>(SubtypeRadiationShielding.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(subtype), new Item.Properties(), NuclearScienceCreativeTabs.MAIN)));

	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_TURBINE = ITEMS.register("turbine", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_TURBINE.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));

	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeNuclearMachine> ITEMS_NUCLEARMACHINE = new BulkDeferredHolder<>(SubtypeNuclearMachine.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(subtype), new Item.Properties(), NuclearScienceCreativeTabs.MAIN)));
	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeMoltenSaltPipe> ITEMS_MOLTENSALTPIPTE = new BulkDeferredHolder<>(SubtypeMoltenSaltPipe.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(subtype), new Item.Properties(), NuclearScienceCreativeTabs.MAIN)));
	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeReactorLogisticsCable> ITEMS_REACTORLOGISTICSCABLE = new BulkDeferredHolder<>(SubtypeReactorLogisticsCable.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_REACTORLOGISTICSCABLE.getValue(subtype), new Item.Properties(), NuclearScienceCreativeTabs.MAIN)));

	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeElectromagent> ITEMS_ELECTROMAGNET = new BulkDeferredHolder<>(SubtypeElectromagent.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(subtype), new Item.Properties(), NuclearScienceCreativeTabs.MAIN)));

	//public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNET = ITEMS.register("electromagnet", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	//public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNETICGLASS = ITEMS.register("electromagneticglass", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNETICBOOSTER = ITEMS.register("electromagneticbooster", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNETICSWITCH = ITEMS.register("electromagneticswitch", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNETICGATEWAY = ITEMS.register("electromagneticgateway", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_ELECTROMAGNETICDIODE = ITEMS.register("electromagneticdiode", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE.get(), new Item.Properties(), NuclearScienceCreativeTabs.MAIN));

	public static final DeferredHolder<Item, BlockItemDescriptable> ITEM_MELTEDREACTOR = ITEMS.register("meltedreactor", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(), new Item.Properties(), null));
	public static final BulkDeferredHolder<Item, BlockItemDescriptable, SubtypeIrradiatedBlock> ITEMS_IRRADIATED = new BulkDeferredHolder<>(SubtypeIrradiatedBlock.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(subtype), new Item.Properties(), null)));

	/* ITEMS */

	public static final DeferredHolder<Item, Item> ITEM_URANIUM235 = ITEMS.register("uranium235", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_URANIUM238 = ITEMS.register("uranium238", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_POLONIUM210 = ITEMS.register("polonium210", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_ACTINIUM225 = ITEMS.register("actinium225", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_LIFHT4PUF3 = ITEMS.register("lifthf4uf4", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FLINAK = ITEMS.register("flinak", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FISSILE_SALT = ITEMS.register("fissilesalt", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_POLONIUM210_CHUNK = ITEMS.register("polonium210chunk", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_ACTINIUMOXIDE = ITEMS.register("actiniumoxide", () -> new ItemRadioactive(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));

	public static final DeferredHolder<Item, Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(26000), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(24000), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(120000), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FUELSPENT = ITEMS.register("fuelspent", () -> new ItemRadioactive(new Item.Properties().stacksTo(1), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLEMPTY = ITEMS.register("cellempty", () -> new ItemElectrodynamics(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium", () -> new ItemElectrodynamics(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium", () -> new ItemElectrodynamics(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater", () -> new ItemElectrodynamics(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic", () -> new ItemElectrodynamics(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall", () -> new ItemAntimatter(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge", () -> new ItemAntimatter(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLANTIMATTERVERYLARGE = ITEMS.register("cellantimatterverylarge", () -> new ItemAntimatter(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter", () -> new ItemAntimatter(new Item.Properties().durability(4), NuclearScienceCreativeTabs.MAIN));

	public static final DeferredHolder<Item, Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter", () -> new ItemGeigerCounter((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(694.4444444458333, 120.0)).receive(TransferPack.joulesVoltage(694.4444444458333, 120.0)).setIsEnergyStorageOnly().stacksTo(1), NuclearScienceCreativeTabs.MAIN, item -> ElectrodynamicsItems.ITEM_BATTERY.get()));
	public static final DeferredHolder<Item, Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_BASE, Type.HELMET, new Item.Properties().durability(26000), NuclearScienceCreativeTabs.MAIN, 1000, 1, "hazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_BASE, Type.CHESTPLATE, new Item.Properties().durability(26000), NuclearScienceCreativeTabs.MAIN, 1000, 1, "hazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_BASE, Type.LEGGINGS, new Item.Properties().durability(26000), NuclearScienceCreativeTabs.MAIN, 1000, 1, "hazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_BASE, Type.BOOTS, new Item.Properties().durability(26000), NuclearScienceCreativeTabs.MAIN, 1000, 1, "hazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_REINFORCEDHAZMATHELMET = ITEMS.register("reinforcedhazmathelmet", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_REINFORCED, Type.HELMET, new Item.Properties().stacksTo(1).durability(26000 * 5), NuclearScienceCreativeTabs.MAIN, 1000, 1, "reinforcedhazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_REINFORCEDHAZMATPLATE = ITEMS.register("reinforcedhazmatplate", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_REINFORCED, Type.CHESTPLATE, new Item.Properties().stacksTo(1).durability(26000 * 5), NuclearScienceCreativeTabs.MAIN, 1000, 1, "reinforcedhazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_REINFORCEDHAZMATLEGS = ITEMS.register("reinforcedhazmatlegs", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_REINFORCED, Type.LEGGINGS, new Item.Properties().stacksTo(1).durability(26000 * 5), NuclearScienceCreativeTabs.MAIN, 1000, 1, "reinforcedhazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_REINFORCEDHAZMATBOOTS = ITEMS.register("reinforcedhazmatboots", () -> new ItemHazmatArmor(NuclearScienceArmorMaterials.HAZMAT_REINFORCED, Type.BOOTS, new Item.Properties().stacksTo(1).durability(26000 * 5), NuclearScienceCreativeTabs.MAIN, 1000, 1, "reinforcedhazmatarmor"));
	public static final DeferredHolder<Item, Item> ITEM_ANTIDOTE = ITEMS.register("antidote", () -> new ItemAntidote(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_IODINETABLET = ITEMS.register("iodinetablet", () -> new ItemIodineTablet(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard", () -> new ItemFrequencyCard(new Item.Properties(), NuclearScienceCreativeTabs.MAIN));
	public static final DeferredHolder<Item, Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead", () -> new ItemCanisterLead(new Item.Properties().stacksTo(1), NuclearScienceCreativeTabs.MAIN));


	@EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
	private static class NuclearCreativeRegistry {

		@SubscribeEvent
		public static void registerItems(BuildCreativeModeTabContentsEvent event) {

			ITEMS.getEntries().forEach(reg -> {

				CreativeTabSupplier supplier = (CreativeTabSupplier) reg.get();

				if (supplier.hasCreativeTab() && supplier.isAllowedInCreativeTab(event.getTab())) {
					List<ItemStack> toAdd = new ArrayList<>();
					supplier.addCreativeModeItems(event.getTab(), toAdd);
					event.acceptAll(toAdd);
				}

			});

		}

	}

}
