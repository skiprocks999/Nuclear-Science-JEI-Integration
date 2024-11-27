package nuclearscience.client;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import nuclearscience.References;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderAtomicAssembler;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderFissionReactorCore;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumCapacitor;
import nuclearscience.client.render.tile.RenderRadioactiveProcessor;
import nuclearscience.client.render.tile.RenderRodAssembly;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenAtomicAssembler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenFissionReactorCore;
import nuclearscience.client.screen.ScreenFreezePlug;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenMSReactorCore;
import nuclearscience.client.screen.ScreenMoltenSaltSupplier;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenQuantumCapacitor;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.registers.NuclearScienceTiles;
import nuclearscience.registers.NuclearScienceEntities;
import nuclearscience.registers.NuclearScienceMenuTypes;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClientRegister {

    public static final ModelResourceLocation MODEL_GASCENTRIFUGECENTER = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/gascentrifugecenter"));
    public static final ModelResourceLocation MODEL_TURBINECASING = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/turbinecasing"));
    public static final ModelResourceLocation MODEL_TURBINEROTORLAYER = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/turbinerotorlayer"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORCORE = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactorcore"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORFUELROD = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactorfuelrod"));
    public static final ModelResourceLocation MODEL_FISSIONREACTORDEUTERIUM = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/fissionreactordeuterium"));
    public static final ModelResourceLocation MODEL_CONTROLRODASSEMBLYSTRUCTURE = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/controlrodassemblystructure"));
    public static final ModelResourceLocation MODEL_CONTROLRODASSEMBLYSROD = ModelResourceLocation.standalone(ResourceLocation.parse(References.ID + ":block/controlrodassemblyrod"));

    public static final ResourceLocation TEXTURE_JEIBLACKHOLE = ResourceLocation.fromNamespaceAndPath(References.ID, "custom/particleaccelerator_dmblackhole");

    public static void setup() {


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
        event.register(MODEL_CONTROLRODASSEMBLYSTRUCTURE);
        event.register(MODEL_CONTROLRODASSEMBLYSROD);
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
        event.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), ScreenQuantumCapacitor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
        event.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);
    }

    @SubscribeEvent
    public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TURBINE.get(), RenderTurbine::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), RenderQuantumCapacitor::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TELEPORTER.get(), RenderTeleporter::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CONTROLRODASSEMBLY.get(), RenderRodAssembly::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);
        event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), RenderRadioactiveProcessor::new);

        event.registerEntityRenderer(NuclearScienceEntities.ENTITY_PARTICLE.get(), RenderParticle::new);

    }

    public static boolean shouldMultilayerRender(RenderType type) {
        return type == RenderType.translucent() || type == RenderType.solid();
    }

}
