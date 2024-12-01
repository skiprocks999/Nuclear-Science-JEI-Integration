package nuclearscience.common.item;

import java.util.EnumMap;

import electrodynamics.common.item.gear.armor.ItemElectrodynamicsArmor;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import nuclearscience.api.radiation.util.IHazmatSuit;
import nuclearscience.registers.NuclearScienceArmorMaterials;

public class ItemHazmatArmor extends ItemElectrodynamicsArmor implements IHazmatSuit {

	public static final EnumMap<Type, Integer> DEFENSE_MAP_BASE = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(Type.HELMET, 2);
		map.put(Type.CHESTPLATE, 2);
		map.put(Type.LEGGINGS, 2);
		map.put(Type.BOOTS, 2);
	});

	public static final EnumMap<Type, Integer> DEFENSE_MAP_REINFORCED = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
		map.put(Type.HELMET, 4);
		map.put(Type.CHESTPLATE, 4);
		map.put(Type.LEGGINGS, 4);
		map.put(Type.BOOTS, 4);
	});

	private final double radiationProtection;
	private final double radiationStrengthProtection;

	public ItemHazmatArmor(Holder<ArmorMaterial> materialIn, Type slot, Properties properties, Holder<CreativeModeTab> creativeTab, double radiationProtection, double radiationStrengthRating) {
		super(materialIn, slot, properties, creativeTab);
		this.radiationProtection = radiationProtection;
		this.radiationStrengthProtection = radiationStrengthRating;
	}

	@Override
	public double getRadResistance() {
		return radiationProtection;
	}

	@Override
	public double getRadStrengthProtection() {
		return radiationStrengthProtection;
	}

	/*

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return References.ID + ":textures/block/model/" + (material == ArmorMaterialHazmat.hazmat ? "" : "reinforced") + "hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements ArmorMaterial {
		hazmat,
		reinforcedhazmat;

		@Override
		public int getDurabilityForType(Type type) {
			return this == hazmat ? 37500 : 37500 * 5;
		}

		@Override
		public int getDefenseForType(Type type) {
			return this == hazmat ? 2 : 4;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(this == hazmat ? Items.LEATHER : ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypePlate.lead).get());
		}

		@Override
		public String getName() {
			return super.name();
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}

	}

	 */
}
