package nuclearscience.registers;

import electrodynamics.api.registration.BulkDeferredHolder;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.BlockMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.References;
import nuclearscience.common.block.*;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;

public class NuclearScienceBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, References.ID);

    public static final BulkDeferredHolder<Block, Block, SubtypeRadiationShielding> BLOCKS_RADIATION_SHIELDING = new BulkDeferredHolder<>(SubtypeRadiationShielding.values(), subtype -> BLOCKS.register(subtype.tag(), () -> {
        if (subtype == SubtypeRadiationShielding.door) {
            return new DoorBlock(BlockSetType.IRON, subtype.properties);
        } else if (subtype == SubtypeRadiationShielding.trapdoor) {
            return new TrapDoorBlock(BlockSetType.IRON, subtype.properties);
        } else if (subtype == SubtypeRadiationShielding.glass) {
            return new BlockCustomGlass(5.0f, 3.0f);
        } else {
            return new Block(subtype.properties);
        }
    }));

    public static final DeferredHolder<Block, BlockTurbine> BLOCK_TURBINE = BLOCKS.register("turbine", () -> new BlockTurbine());

    public static final BulkDeferredHolder<Block, BlockMachine, SubtypeNuclearMachine> BLOCKS_NUCLEARMACHINE = new BulkDeferredHolder<>(SubtypeNuclearMachine.values(), subtype -> BLOCKS.register(subtype.tag(), () -> {

        if (subtype == SubtypeNuclearMachine.chunkloader) {
            return new BlockMachine(subtype) {
                @Override
                public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
                    return 15;
                }
            };
        } else if (subtype == SubtypeNuclearMachine.fissionreactorcore) {
            return new BlockMachine(subtype) {
                @Override
                public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
                    if (world.getBlockEntity(pos) instanceof TileFissionReactorCore core) {
                        return (int) Math.max(0, Math.min(core.temperature.get() / TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL * 15, 15));
                    }
                    return super.getLightEmission(state, world, pos);
                }
            };
        } else {
            return new BlockMachine(subtype);
        }

    })

    );

    public static final DeferredHolder<Block, BlockQuantumTunnel> BLOCK_QUANTUMTUNNEL = BLOCKS.register("quantumcapacitor", () -> new BlockQuantumTunnel());

    public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNET = BLOCKS.register("electromagnet", () -> new BlockElectromagnet(Blocks.IRON_BLOCK.properties(), false));
    public static final DeferredHolder<Block, BlockElectromagnet> BLOCK_ELECTROMAGNETICGLASS = BLOCKS.register("electromagneticglass", () -> new BlockElectromagnet(Blocks.GLASS.properties(), true));
    public static final DeferredHolder<Block, BlockElectromagneticBooster> BLOCK_ELECTORMAGNETICBOOSTER = BLOCKS.register("electromagneticbooster", () -> new BlockElectromagneticBooster());
    public static final DeferredHolder<Block, BlockElectromagneticSwitch> BLOCK_ELECTROMAGNETICSWITCH = BLOCKS.register("electromagneticswitch", () -> new BlockElectromagneticSwitch());
    public static final DeferredHolder<Block, BlockPlasma> BLOCK_PLASMA = BLOCKS.register("plasma", () -> new BlockPlasma());
    public static final BulkDeferredHolder<Block, BlockMoltenSaltPipe, SubtypeMoltenSaltPipe> BLOCKS_MOLTENSALTPIPE = new BulkDeferredHolder<>(SubtypeMoltenSaltPipe.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockMoltenSaltPipe(subtype)));
    public static final DeferredHolder<Block, BlockMeltedReactor> BLOCK_MELTEDREACTOR = BLOCKS.register("meltedreactor", () -> new BlockMeltedReactor());
    public static final DeferredHolder<Block, BlockRadioactiveAir> BLOCK_RADIOACTIVEAIR = BLOCKS.register("radioactiveair", () -> new BlockRadioactiveAir());
    public static final BulkDeferredHolder<Block, BlockIrradiated, SubtypeIrradiatedBlock> BLOCKS_IRRADIATED = new BulkDeferredHolder<>(SubtypeIrradiatedBlock.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockIrradiated(subtype)));


}
