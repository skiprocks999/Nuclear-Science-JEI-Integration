package nuclearscience.common.block.voxelshapes;

import java.util.stream.Stream;

import electrodynamics.common.block.voxelshapes.VoxelShapes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceVoxelShapeRegistry {

    /**
     * By convention this will be in alphabetical order
     */
    public static void init() {

        /* CHEMICAL EXTRACTOR */

        VoxelShape chemicalextractor = Block.box(0, 0, 0, 16, 2, 16);
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(5, 2, 0, 11, 14, 16));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 3, 2.5, 5, 4, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 3, 5.5, 5, 4, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 3, 8.5, 5, 4, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 3, 11.5, 5, 4, 13.5));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2.5, 4, 3, 3.5, 4.75, 4));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2.5, 4, 6, 3.5, 4.75, 7));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2.5, 4, 9, 3.5, 4.75, 10));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2.5, 4, 12, 3.5, 4.75, 13));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 4.75, 2.25, 5, 6, 4.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 4.75, 5.25, 5, 6, 7.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 4.75, 8.25, 5, 6, 10.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 4.75, 11.25, 5, 6, 13.75));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 6, 2.5, 4, 11.75, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 6, 5.5, 4, 11.75, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 6, 8.5, 4, 11.75, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(2, 6, 11.5, 4, 11.75, 13.5));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 11.75, 2.25, 5, 13, 4.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 11.75, 5.25, 5, 13, 7.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 11.75, 8.25, 5, 13, 10.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(1.75, 11.75, 11.25, 5, 13, 13.75));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(3.5, 13, 2.5, 5, 14, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(3.5, 13, 5.5, 5, 14, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(3.5, 13, 8.5, 5, 14, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(3.5, 13, 11.5, 5, 14, 13.5));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 3, 2.5, 14, 4, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 3, 5.5, 14, 4, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 3, 8.5, 14, 4, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 3, 11.5, 14, 4, 13.5));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12.5, 4, 3, 13.5, 4.75, 4));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12.5, 4, 6, 13.5, 4.75, 7));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12.5, 4, 9, 13.5, 4.75, 10));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12.5, 4, 12, 13.5, 4.75, 13));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 4.75, 2.25, 14.25, 6, 4.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 4.75, 5.25, 14.25, 6, 7.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 4.75, 8.25, 14.25, 6, 10.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 4.75, 11.25, 14.25, 6, 13.75));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12, 6, 2.5, 14, 11.75, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12, 6, 5.5, 14, 11.75, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12, 6, 8.5, 14, 11.75, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(12, 6, 11.5, 14, 11.75, 13.5));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 11.75, 2.25, 14.25, 13, 4.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 11.75, 5.25, 14.25, 13, 7.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 11.75, 8.25, 14.25, 13, 10.75));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 11.75, 11.25, 14.25, 13, 13.75));

        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 13, 2.5, 12.5, 14, 4.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 13, 5.5, 12.5, 14, 7.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 13, 8.5, 12.5, 14, 10.5));
        chemicalextractor = Shapes.or(chemicalextractor, Block.box(11, 13, 11.5, 12.5, 14, 13.5));

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_CHEMICALEXTRACTOR.get(), chemicalextractor, Direction.EAST);

        /* FISSION REACTOR CORE */

        VoxelShape fissionreactorcore = Shapes.empty();

        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.40625, 0.115675, 0.40625, 0.59375, 0.7088875, 0.59375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.125, 0.75, 0.1148875, 0.1875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.84375, 0.7878, 0.25, 0.90625, 0.8370375, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.8125, 0.06565, 0.25, 0.875, 0.1148875, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.72215, 0.125, 0.125, 0.7878, 0.875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.875, 0.25, 0.72215, 0.9375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.0625, 0.25, 0.72215, 0.125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.25, 0.3125, 0.72215, 0.3125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.6875, 0.3125, 0.72215, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.6875, 0.06565, 0.25, 0.75, 0.72215, 0.3125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.6875, 0.06565, 0.6875, 0.75, 0.72215, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.75, 0.06565, 0.875, 0.8125, 0.72215, 0.9375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.75, 0.06565, 0.0625, 0.8125, 0.72215, 0.125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.06565, 0.75, 0.125, 0.72215, 0.8125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.06565, 0.1875, 0.125, 0.72215, 0.25), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.06565, 0.75, 0.9375, 0.72215, 0.8125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.06565, 0.1875, 0.9375, 0.72215, 0.25), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0, 0.125, 0.125, 0.06565, 0.875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.09375, 0.7878, 0.25, 0.15625, 0.8370375, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0.06565, 0.25, 0.1875, 0.1148875, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0, 0.125, 0.9375, 0.06565, 0.875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0, 0.0625, 0.875, 0.06565, 0.9375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0.72215, 0.0625, 0.875, 0.7878, 0.9375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.15625, 0.7878, 0.15625, 0.84375, 0.8370375, 0.84375), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.1875, 0.8125, 0.1148875, 0.8125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.8370375, 0.1875, 0.8125, 0.886275, 0.8125), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.886275, 0.25, 0.75, 0.9355125, 0.75), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.7878, 0.84375, 0.75, 0.8370375, 0.90625), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.8125, 0.75, 0.1148875, 0.875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.72215, 0.125, 0.9375, 0.7878, 0.875), BooleanOp.OR);
        fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.7878, 0.09375, 0.75, 0.8370375, 0.15625), BooleanOp.OR);
        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_FISSIONREACTORCORE.get(), fissionreactorcore, Direction.NORTH);

        /* FREEZE PLUG */

        VoxelShape freezeplug = Shapes.empty();
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.25, 0.15625, 0.25, 0.75, 0.28125, 0.75), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.25, 0.3125, 0.6875, 0.8125, 0.6875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.8125, 0.875, 1, 0.875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.125, 0.875, 1, 0.1875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.75, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.25, 0.8125, 0.25, 0.75, 0.875, 0.75), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.1875, 0.8125, 0.9375, 0.25), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.25, 0.25, 0.9375, 0.75), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.875, 0.25, 0.8125, 0.9375, 0.75), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.8125, 0.9375, 0.1875, 0.875, 1, 0.8125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.1875, 0.1875, 1, 0.8125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0.625, 0.296875, 0.703125, 0.6875, 0.703125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.28125, 0.5, 0.28125, 0.71875, 0.5625, 0.71875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0.375, 0.296875, 0.703125, 0.4375, 0.703125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.296875, 0.15625, 0.3125, 0.390625), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.453125, 0.15625, 0.3125, 0.546875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.609375, 0.15625, 0.3125, 0.703125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0, 0.84375, 0.390625, 0.3125, 0.921875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.453125, 0, 0.84375, 0.546875, 0.3125, 0.921875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.609375, 0, 0.84375, 0.703125, 0.3125, 0.921875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.609375, 0.921875, 0.3125, 0.703125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.453125, 0.921875, 0.3125, 0.546875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.296875, 0.921875, 0.3125, 0.390625), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.609375, 0, 0.078125, 0.703125, 0.3125, 0.15625), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.453125, 0, 0.078125, 0.546875, 0.3125, 0.15625), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0, 0.078125, 0.390625, 0.3125, 0.15625), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.625, 0.1875, 0.15625, 0.6875, 0.25, 0.25), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.46875, 0.1875, 0.15625, 0.53125, 0.25, 0.25), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.1875, 0.15625, 0.375, 0.25, 0.25), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.3125, 0.25, 0.25, 0.375), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.46875, 0.25, 0.25, 0.53125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.625, 0.25, 0.25, 0.6875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.1875, 0.75, 0.375, 0.25, 0.84375), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.46875, 0.1875, 0.75, 0.53125, 0.25, 0.84375), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.625, 0.1875, 0.75, 0.6875, 0.25, 0.84375), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.625, 0.84375, 0.25, 0.6875), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.46875, 0.84375, 0.25, 0.53125), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.3125, 0.84375, 0.25, 0.375), BooleanOp.OR);
        freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875), BooleanOp.OR);

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_MSRFREEZEPLUG.get(), freezeplug, Direction.EAST);

        /* FUEL REPROCESSOR */

        VoxelShape fuelreprocessor = Stream.of(
                //
                Block.box(14, 0, 15, 16, 16, 16),
                //
                Block.box(15, 0, 14, 16, 16, 15),
                //
                Block.box(0, 0, 0, 2, 16, 1),
                //
                Block.box(0, 0, 1, 1, 16, 2),
                //
                Block.box(0, 0, 14, 1, 16, 15),
                //
                Block.box(0, 0, 15, 2, 16, 16),
                //
                Block.box(15, 0, 1, 16, 16, 2),
                //
                Block.box(14, 0, 0, 16, 16, 1),
                //
                Block.box(1, 7, 13, 3, 9, 15),
                //
                Block.box(3, 7, 13, 4, 9, 14),
                //
                Block.box(3, 7, 12, 4, 9, 13),
                //
                Block.box(2, 7, 12, 3, 9, 13),
                //
                Block.box(3, 7, 11, 4, 9, 12),
                //
                Block.box(4, 7, 12, 5, 9, 13),
                //
                Block.box(13, 7, 13, 15, 9, 15),
                //
                Block.box(12, 7, 13, 13, 9, 14),
                //
                Block.box(13, 7, 12, 14, 9, 13),
                //
                Block.box(12, 7, 12, 13, 9, 13),
                //
                Block.box(12, 7, 11, 13, 9, 12),
                //
                Block.box(11, 7, 12, 12, 9, 13),
                //
                Block.box(1, 7, 1, 3, 9, 3),
                //
                Block.box(3, 7, 3, 4, 9, 4),
                //
                Block.box(3, 7, 2, 4, 9, 3),
                //
                Block.box(2, 7, 3, 3, 9, 4),
                //
                Block.box(3, 7, 4, 4, 9, 5),
                //
                Block.box(4, 7, 3, 5, 9, 4),
                //
                Block.box(13, 7, 1, 15, 9, 3),
                //
                Block.box(11, 7, 3, 12, 9, 4),
                //
                Block.box(12, 7, 2, 13, 9, 3),
                //
                Block.box(13, 7, 3, 14, 9, 4),
                //
                Block.box(12, 7, 4, 13, 9, 5),
                //
                Block.box(12, 7, 3, 13, 9, 4),
                //
                Block.box(5, 7, 2.5, 11, 9, 3),
                //
                Block.box(13, 7, 5, 13.5, 9, 11),
                //
                Block.box(5, 7, 13, 11, 9, 13.5),
                //
                Block.box(3, 2, 5, 13, 14, 11),
                //
                Block.box(4, 2, 11, 5, 14, 12),
                //
                Block.box(4, 2, 4, 5, 14, 5),
                //
                Block.box(11, 2, 4, 12, 14, 5),
                //
                Block.box(11, 2, 11, 12, 14, 12),
                //
                Block.box(5, 2, 11, 11, 14, 13),
                //
                Block.box(5, 2, 3, 11, 14, 5),
                //
                Block.box(4, 15, 4, 12, 16, 12),
                //
                Block.box(5, 14, 5, 11, 15, 11),
                //
                Block.box(0, 4, 4, 1, 12, 12),
                //
                Block.box(1, 5, 5, 3, 11, 11),
                //
                Block.box(5, 1, 5, 11, 2, 11),
                //
                Block.box(4, 0, 4, 12, 1, 12)
                //
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_FUELREPROCESSOR.get(), fuelreprocessor, Direction.EAST);

        /* GAS CENTRIFUGE */

        VoxelShape gascentriguge = Shapes.empty();
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.921875, 0.26875, 1.0234375, 1.03125, 0.73125), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0, 0.0625, 0, 1, 0.125, 1), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0, 0, 0, 0.0625, 0.0625, 1), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.0625, 0, 0, 0.9375, 0.0625, 0.0625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.0625, 1), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.9375, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.1875, 0.8715), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.75, 0.125, 0.28125, 1, 0.8125, 0.71875), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.234375, 0.125, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.390625, 0.875, 0.375, 0.5890625, 0.984375, 0.625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.3125, 0.5625, 0.8390625, 0.34375, 0.671875), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.3125, 0.609375, 0.3078125, 0.34375, 0.75), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.3125, 0.25, 0.3078125, 0.34375, 0.390625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.3125, 0.328125, 0.8390625, 0.34375, 0.4375), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.125, 0.5625, 0.8390625, 0.3125, 0.671875), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.125, 0.328125, 0.8390625, 0.3125, 0.4375), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.125, 0.609375, 0.3078125, 0.3125, 0.75), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.125, 0.25, 0.3078125, 0.3125, 0.390625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.375, 0.3125, 0.5625, 0.4375, 0.4375, 0.625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.3125, 0.5625, 0.625, 0.4375, 0.625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.4375, 0.3125, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.3125, 0.375, 0.625, 0.4375, 0.4375), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.34375, 0.3125, 0.46875, 0.40625, 0.4375, 0.53125), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.46875, 0.3125, 0.59375, 0.53125, 0.4375, 0.65625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.46875, 0.3125, 0.34375, 0.53125, 0.4375, 0.40625), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.375, 0.3125, 0.375, 0.4375, 0.4375, 0.4375), BooleanOp.OR);
        gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.59375, 0.3125, 0.46875, 0.65625, 0.4375, 0.53125), BooleanOp.OR);
        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_GASCENTRIFUGE.get(), gascentriguge, Direction.WEST);

        /* HEAT EXCHANGER */

        VoxelShape heatexchanger = Shapes.empty();
        heatexchanger = Shapes.join(heatexchanger, Block.box(0, 1, 0, 16, 3, 16), BooleanOp.OR);
        heatexchanger = Shapes.join(heatexchanger, Block.box(1, 4, 1, 15, 16, 15), BooleanOp.OR);
        heatexchanger = Shapes.join(heatexchanger, Block.box(0.5, 3, 0.5, 15.5, 4, 15.5), BooleanOp.OR);
        heatexchanger = Shapes.join(heatexchanger, Block.box(4, 0, 4, 12, 1, 12), BooleanOp.OR);

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_HEATEXCHANGER.get(), heatexchanger, Direction.WEST);

        /* MOLTEN SALT REACTOR CORE */

        VoxelShape msreactorcore = Shapes.empty();
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0, 0.125, 0.875, 0.8125, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.125, 0.1875, 0.125, 0.75, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.125, 0.1875, 0.9375, 0.75, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.125, 0.0625, 0.8125, 0.75, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.125, 0.875, 0.8125, 0.75, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.1875, 0.25, 0.0625, 0.6875, 0.75), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.8125, 0.1875, 0.8125, 0.875, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 1, 0.6875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, 0.875, 0.375, 0.3125, 1.125, 0.4375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, 0.875, 0.5625, 0.3125, 1.125, 0.625), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, 0.875, 0.5625, 0.75, 1.125, 0.625), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, 0.875, 0.375, 0.75, 1.125, 0.4375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.5625, 0.875, 0.25, 0.625, 1.125, 0.3125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.375, 0.875, 0.25, 0.4375, 1.125, 0.3125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.375, 0.875, 0.6875, 0.4375, 1.125, 0.75), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.5625, 0.875, 0.6875, 0.625, 1.125, 0.75), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.25, 0.1875, 0.0625, 0.3125, 0.25), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.40625, 0.1875, 0.0625, 0.46875, 0.25), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.5625, 0.1875, 0.0625, 0.625, 0.25), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.5625, 0.75, 0.0625, 0.625, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.40625, 0.75, 0.0625, 0.46875, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.25, 0.75, 0.0625, 0.3125, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.5625, 0.8125, 0.125, 0.625, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.40625, 0.8125, 0.125, 0.46875, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.25, 0.8125, 0.125, 0.3125, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.5625, 0.125, 0.125, 0.625, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.40625, 0.125, 0.125, 0.46875, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.25, 0.125, 0.125, 0.3125, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.40625, 0.125, 0.9375, 0.46875, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.25, 0.125, 0.9375, 0.3125, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.5625, 0.125, 0.9375, 0.625, 0.1875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.5625, 0.8125, 0.9375, 0.625, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.40625, 0.8125, 0.9375, 0.46875, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.25, 0.8125, 0.9375, 0.3125, 0.875), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.5625, 0.1875, 1, 0.625, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.40625, 0.1875, 1, 0.46875, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.25, 0.1875, 1, 0.3125, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.5625, 0.875, 0.875, 0.625, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.40625, 0.875, 0.875, 0.46875, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.25, 0.875, 0.875, 0.3125, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.5625, 0.875, 0.1875, 0.625, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.40625, 0.875, 0.1875, 0.46875, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.25, 0.875, 0.1875, 0.3125, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.5625, 0.0625, 0.1875, 0.625, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.40625, 0.0625, 0.1875, 0.46875, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.25, 0.0625, 0.1875, 0.3125, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.5625, 0.0625, 0.875, 0.625, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.40625, 0.0625, 0.875, 0.46875, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.25, 0.0625, 0.875, 0.3125, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.5625, 0, 0.8125, 0.625, 0.0625), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.40625, 0, 0.8125, 0.46875, 0.0625), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.25, 0, 0.8125, 0.3125, 0.0625), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.5625, 0.9375, 0.8125, 0.625, 1), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.40625, 0.9375, 0.8125, 0.46875, 1), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.25, 0.9375, 0.8125, 0.3125, 1), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, -0.125, 0.6875, 0.125, 0.125, 0.75), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.125, 0.875, 0.3125, 0.125, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.0625, 0.75, 0.75, 0, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, -0.125, 0.875, 0.75, 0.125, 0.9375), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, -0.125, 0.6875, 0.9375, 0.125, 0.75), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, -0.125, 0.25, 0.9375, 0.125, 0.3125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, -0.125, 0.25, 0.125, 0.125, 0.3125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, -0.0625, 0.1875, 0.25, 0, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.0625, 0.1875, 0.75, 0, 0.25), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.75, -0.0625, 0.1875, 0.8125, 0, 0.8125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.125, 0.0625, 0.3125, 0.125, 0.125), BooleanOp.OR);
        msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, -0.125, 0.0625, 0.75, 0.125, 0.125), BooleanOp.OR);
        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_MSREACTORCORE.get(), msreactorcore, Direction.WEST);

        /* MOLTEN SALT SUPPLIER */

        VoxelShape moltensaltsupplier = Stream.of(
                //
                Block.box(1, 2, 4, 15, 12, 12),
                //
                Block.box(1, 3, 3, 15, 11, 4),
                //
                Block.box(1, 3, 12, 15, 11, 13),
                //
                Block.box(0, 3, 4, 1, 11, 12),
                //
                Block.box(15, 3, 4, 16, 11, 12),
                //
                Block.box(4, 1, 4, 12, 2, 12),
                //
                Block.box(5, 0, 5, 11, 1, 11)
                //
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_MOLTENSALTSUPPLIER.get(), moltensaltsupplier, Direction.EAST);

        /* NUCLEAR BOILER */

        VoxelShape nuclearboiler = Block.box(0, 0, 0, 16, 2, 16);

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 2, 0, 9, 6, 1.5));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(6, 6, 0, 10, 10, 1.5));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 9.25, 0.25, 9, 11, 4.5));

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 2, 14.5, 9, 6, 16));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(6, 6, 14.5, 10, 10, 16));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 9.25, 11.5, 9, 11, 15.75));

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(2, 2, 2, 14, 3, 14));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(3.75, 3, 4, 12, 5, 12));

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(13, 2, 4.5, 16, 12, 11.5));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(10, 9, 5.5, 13, 11, 10.5));

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 2, 5, 9, 8, 6.5));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(6, 8, 4.5, 10, 12, 7.5));

        nuclearboiler = Shapes.or(nuclearboiler, Block.box(7, 2, 9.5, 9, 8, 11));
        nuclearboiler = Shapes.or(nuclearboiler, Block.box(6, 8, 8.5, 10, 12, 11.5));

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_NUCLEARBOILER.get(), nuclearboiler, Direction.WEST);

        /* PARTICLE INJECTOR */

        VoxelShape particleinjector = Stream.of(
                //
                Block.box(15, 4, 4, 16, 12, 12),
                //
                Block.box(12, 0, 0, 15, 16, 16),
                //
                Block.box(15, 0, 15, 16, 16, 16),
                //
                Block.box(15, 0, 0, 16, 16, 1),
                //
                Block.box(15, 15, 1, 16, 16, 15),
                //
                Block.box(15, 0, 1, 16, 1, 15),
                //
                Block.box(9, 1, 1, 12, 15, 15),
                //
                Block.box(4, 1, 1, 5, 15, 15),
                //
                Block.box(1.5, 3, 3, 4, 13, 13),
                //
                Block.box(0, 0, 0, 0.5, 16, 16),
                //
                Block.box(0.5, 0.5, 0.5, 1, 15.5, 15.25),
                //
                Block.box(1, 1.5, 1.5, 1.5, 14.5, 14.25),
                //
                Block.box(5, 0, 0, 9, 16, 16)
                //
        ).reduce(Shapes::or).get();

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_PARTICLEINJECTOR.get(), particleinjector, Direction.WEST);

        /* RADIOISOTROPIC GENERATOR */

        VoxelShape radiogenerator = Block.box(6, 0.25, 4, 10, 15.75, 12);
        radiogenerator = Shapes.or(radiogenerator, Block.box(4, 0.25, 6, 12, 15.75, 10));
        radiogenerator = Shapes.or(radiogenerator, Block.box(5, 0.25, 5, 11, 15.75, 11));

        radiogenerator = Shapes.or(radiogenerator, Block.box(5.25, 0, 5.25, 10.75, 0.25, 10.75));
        radiogenerator = Shapes.or(radiogenerator, Block.box(5.25, 15.75, 5.25, 10.75, 16, 10.75));

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_RADIOISOTOPEGENERATOR.get(), radiogenerator, Direction.EAST);

        /* TELEPORTER */

        VoxelShape teleporter = Stream.of(
                //
                Block.box(2, 1, 2, 14, 5, 14),
                //
                Block.box(0, 2, 14, 2, 5, 16),
                //
                Block.box(14, 2, 14, 16, 5, 16),
                //
                Block.box(14, 2, 0, 16, 5, 2),
                //
                Block.box(0, 2, 0, 2, 5, 2),
                //
                Block.box(0, 5, 0, 16, 7, 2),
                //
                Block.box(0, 0, 0, 16, 2, 2),
                //
                Block.box(0, 5, 14, 16, 7, 16),
                //
                Block.box(0, 0, 14, 16, 2, 16),
                //
                Block.box(0, 5, 2, 2, 7, 14),
                //
                Block.box(0, 0, 2, 2, 2, 14),
                //
                Block.box(14, 5, 2, 16, 7, 14),
                //
                Block.box(14, 0, 2, 16, 2, 14),
                //
                Block.box(7, 7, 1, 9, 8, 2),
                //
                Block.box(7, 7, 14, 9, 8, 15),
                //
                Block.box(7, 8, 14, 9, 9, 15),
                //
                Block.box(7, 8, 1, 9, 9, 2),
                //
                Block.box(7, 9, 2, 9, 10, 3),
                //
                Block.box(7, 9, 13, 9, 10, 14),
                //
                Block.box(7, 10, 13, 9, 11, 14),
                //
                Block.box(7, 10, 12, 9, 11, 13),
                //
                Block.box(7, 10, 3, 9, 11, 4),
                //
                Block.box(7, 10, 2, 9, 11, 3),
                //
                Block.box(1, 7, 7, 2, 8, 9),
                //
                Block.box(14, 7, 7, 15, 8, 9),
                //
                Block.box(1, 8, 7, 2, 9, 9),
                //
                Block.box(14, 8, 7, 15, 9, 9),
                //
                Block.box(2, 9, 7, 3, 10, 9),
                //
                Block.box(13, 9, 7, 14, 10, 9),
                //
                Block.box(2, 10, 7, 3, 11, 9),
                //
                Block.box(13, 10, 7, 14, 11, 9),
                //
                Block.box(3, 10, 7, 4, 11, 9),
                //
                Block.box(12, 10, 7, 13, 11, 9),
                //
                Block.box(3, 11, 3, 13, 12, 13),
                //
                Block.box(4, 12, 4, 12, 13, 12),
                //
                Block.box(5, 13, 5, 11, 14, 11),
                //
                Block.box(6, 5, 6, 10, 15, 10),
                //
                Block.box(3, 5, 3, 4, 11, 4),
                //
                Block.box(12, 5, 3, 13, 11, 4),
                //
                Block.box(3, 5, 12, 4, 11, 13),
                //
                Block.box(12, 5, 12, 13, 11, 13),
                //
                Block.box(1, 7, 1, 3, 8, 4),
                //
                Block.box(1, 7, 3, 3, 8, 4),
                //
                Block.box(3, 7, 1, 4, 8, 3),
                //
                Block.box(13, 7, 12, 15, 8, 15),
                //
                Block.box(1, 7, 12, 3, 8, 13),
                //
                Block.box(1, 7, 13, 4, 8, 15),
                //
                Block.box(12, 7, 13, 13, 8, 15),
                //
                Block.box(12, 7, 1, 15, 8, 3),
                //
                Block.box(13, 7, 3, 15, 8, 4),
                //
                Block.box(0, 15, 0, 16, 16, 16),
                //
                Block.box(4, 0, 4, 12, 1, 12),
                //
                Block.box(3, 12, 3, 4, 15, 4),
                //
                Block.box(12, 12, 3, 13, 15, 4),
                //
                Block.box(3, 12, 12, 4, 15, 13),
                //
                Block.box(12, 12, 12, 13, 15, 13)
                //
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        VoxelShapes.registerShape(NuclearScienceBlocks.BLOCK_TELEPORTER.get(), teleporter, Direction.UP);

    }

}
