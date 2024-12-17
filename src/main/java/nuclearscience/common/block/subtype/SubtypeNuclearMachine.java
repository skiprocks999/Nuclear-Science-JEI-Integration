package nuclearscience.common.block.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.api.multiblock.subnodebased.Subnode;
import electrodynamics.api.tile.IMachine;
import electrodynamics.api.tile.MachineProperties;
import electrodynamics.common.block.voxelshapes.VoxelShapeProvider;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapeRegistry;
import nuclearscience.common.tile.*;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.*;

public enum SubtypeNuclearMachine implements ISubtype, IMachine {

    gascentrifuge(true, TileGasCentrifuge::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.GAS_CENTRIFUGE)),
    nuclearboiler(true, TileNuclearBoiler::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.NUCLEAR_BOILER)),
    chemicalextractor(true, TileChemicalExtractor::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.CHEMICAL_EXTRACTOR)),
    fuelreprocessor(true, TileFuelReprocessor::new, MachineProperties.builder().setLitBrightness(15).setShapeProvider(NuclearScienceVoxelShapeRegistry.FUEL_REPROCESSOR)),
    radioactiveprocessor(true, TileRadioactiveProcessor::new, MachineProperties.builder().setLitBrightness(15)),
    cloudchamber(true, TileCloudChamber::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.CLOUD_CHAMBER)),
    radioisotopegenerator(true, TileRadioisotopeGenerator::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.RADIOISOTROPIC_GENERATOR)),
    fissionreactorcore(true, TileFissionReactorCore::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.FISSION_REACTOR_CORE)),
    fissioncontrolrod(true, TileControlRod.TileFissionControlRod::new),
    siren(true, TileSiren::new),
    steamfunnel(true, TileSteamFunnel::new),
    msrfuelpreprocessor(true, TileMSRFuelPreProcessor::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.MSR_FUEL_PREPROCESSOR)),
    freezeplug(true, TileFreezePlug::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.FREEZE_PLUG)),
    msreactorcore(true, TileMSReactorCore::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.MS_REACTOR_CORE)),
    heatexchanger(true, TileHeatExchanger::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.HEAT_EXCHANGER)),
    moltensaltsupplier(true, TileMoltenSaltSupplier::new, MachineProperties.builder().setLitBrightness(15).setShapeProvider(NuclearScienceVoxelShapeRegistry.MOLTEN_SALT_SUPPLIER)),
    mscontrolrod(true, TileControlRod.TileMSControlRod::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.MS_CONTROL_ROD)),
    fusionreactorcore(true, TileFusionReactorCore::new, MachineProperties.builder().setLitBrightness(15)),
    particleinjector(true, TileParticleInjector::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.PARTICLE_INJECTOR)),
    teleporter(true, TileTeleporter::new, MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapeRegistry.TELEPORTER).setLitBrightness(15)),
    chunkloader(true, TileChunkloader::new),
    atomicassembler(true, TileAtomicAssembler::new),
    quantumcapacitor(true, TileQuantumTunnel::new);

    private final BlockEntityType.BlockEntitySupplier<BlockEntity> blockEntitySupplier;
    private final boolean showInItemGroup;
    private final MachineProperties properties;

    private SubtypeNuclearMachine(boolean showInItemGroup, BlockEntityType.BlockEntitySupplier blockEntitySupplier) {
        this(showInItemGroup, blockEntitySupplier, MachineProperties.DEFAULT);
    }

    private SubtypeNuclearMachine(boolean showInItemGroup, BlockEntityType.BlockEntitySupplier blockEntitySupplier, MachineProperties properties) {
        this.showInItemGroup = showInItemGroup;
        this.blockEntitySupplier = blockEntitySupplier;
        this.properties = properties;
    }

    public BlockEntityType.BlockEntitySupplier<BlockEntity> getBlockEntitySupplier() {
        return this.blockEntitySupplier;
    }

    public int getLitBrightness() {
        return this.properties.litBrightness;
    }

    public RenderShape getRenderShape() {
        return this.properties.renderShape;
    }

    public boolean isMultiblock() {
        return this.properties.isMultiblock;
    }

    public boolean propegatesLightDown() {
        return this.properties.propegatesLightDown;
    }

    public String tag() {
        return this.name();
    }

    public String forgeTag() {
        return this.tag();
    }

    public boolean isItem() {
        return false;
    }

    public boolean isPlayerStorable() {
        return false;
    }

    public Subnode[] getSubnodes() {
        return this.properties.subnodes;
    }

    public VoxelShapeProvider getVoxelShapeProvider() {
        return this.properties.provider;
    }

    public boolean showInItemGroup() {
        return this.showInItemGroup;
    }
}
