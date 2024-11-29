package nuclearscience.registers;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import nuclearscience.References;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.api.radiation.CapabilityRadiationRecipient;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class NuclearScienceCapabilities {

    public static final EntityCapability<IRadiationRecipient, Void> CAPABILITY_RADIATIONRECIPIENT = EntityCapability.createVoid(id("radiationrecipient"), IRadiationRecipient.class);

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        NuclearScienceTiles.BLOCK_ENTITY_TYPES.getEntries().forEach(entry -> {
            event.registerBlockEntity(ElectrodynamicsCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getElectrodynamicCapability(context));
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getFluidHandlerCapability(context));
            event.registerBlockEntity(ElectrodynamicsCapabilities.CAPABILITY_GASHANDLER_BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getGasHandlerCapability(context));
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getItemHandlerCapability(context));
        });

        BuiltInRegistries.ENTITY_TYPE.forEach(entry -> {

            if(entry.getBaseClass().equals(LivingEntity.class)) {
                event.registerEntity(CAPABILITY_RADIATIONRECIPIENT, entry, (entity, context) -> new CapabilityRadiationRecipient());
            }

        });

    }


    private static final ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(References.ID, name);
    }

}
