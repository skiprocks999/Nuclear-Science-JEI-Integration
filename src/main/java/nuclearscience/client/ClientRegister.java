package nuclearscience.client;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.misc.SWBFClientExtensions;
import electrodynamics.common.fluid.SimpleWaterBasedFluidType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import nuclearscience.References;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.particle.smoke.ParticleSmoke;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.*;
import nuclearscience.client.screen.*;
import nuclearscience.registers.*;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClientRegister {

    public static final ModelResourceLocation MODEL_GASCENTRIFUGECENTER = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/gascentrifugecenter"));
    public static final ModelResourceLocation MODEL_TURBINECASING = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/turbinecasing"));
    public static final ModelResourceLocation MODEL_TURBINEROTORLAYER = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/turbinerotorlayer"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORCORE = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactorcore"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORFUELROD = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactorfuelrod"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORDEUTERIUM = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactordeuterium"));
    public static final ModelResourceLocation MODEL_FISSIONCONTROLROD_ROD = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissioncontrolrodrod"));
    public static final ModelResourceLocation MODEL_MSCONTROLROD_ROD = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/mscontrolrodrod"));
    public static final ModelResourceLocation MODEL_FALLOUTSCRUBBER_FAN = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/falloutscrubberfan"));


    public static final ResourceLocation TEXTURE_JEIBLACKHOLE = ResourceLocation.fromNamespaceAndPath(References.ID, "block/custom/particleaccelerator_dmblackhole");

    public static void setup() {

        ClientEvents.init();
        ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
    }

    @SubscribeEvent
    public static void onModelEvent(ModelEvent.RegisterAdditional event) {
        event.register(MODEL_GASCENTRIFUGECENTER);
        event.register(MODEL_TURBINECASING);
        event.register(MODEL_TURBINEROTORLAYER);
        event.register(MODEL_FISSIONREACTORCORE);
        event.register(MODEL_FISSIONREACTORFUELROD);
        event.register(MODEL_FISSIONREACTORDEUTERIUM);
        event.register(MODEL_FISSIONCONTROLROD_ROD);
        event.register(MODEL_MSCONTROLROD_ROD);
        event.register(MODEL_FALLOUTSCRUBBER_FAN);
    }

    @SubscribeEvent
    public static void registerMenus(RegisterMenuScreensEvent event) {
        event.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenFissionReactorCore::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMTUNNEL.get(), ScreenQuantumTunnel::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_TELEPORTER.get(), ScreenTeleporter::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_CLOUDCHAMBER.get(), ScreenCloudChamber::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_FALLOUTSCRUBBER.get(), ScreenFalloutScrubber::new);
    }

    @SubscribeEvent
    public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TURBINE.get(), RenderTurbine::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), RenderQuantumTunnel::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TELEPORTER.get(), RenderTeleporter::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get(), RenderFissionControlRod::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_MSCONTROLROD.get(), RenderMSControlRod::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), RenderRadioactiveProcessor::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), RenderCloudChamber::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FALLOUTSCRUBBER.get(), RenderFalloutScrubber::new);
        event.registerEntityRenderer(NuclearScienceEntities.ENTITY_PARTICLE.get(), RenderParticle::new);

    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {

        NuclearScienceFluids.FLUIDS.getEntries().forEach((fluid) -> {
            event.registerFluidType(new SWBFClientExtensions((SimpleWaterBasedFluidType) fluid.get().getFluidType()), fluid.get().getFluidType());
        });

    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(NuclearScienceParticles.PARTICLE_SMOKE.get(), ParticleSmoke.Factory::new);
    }

}
