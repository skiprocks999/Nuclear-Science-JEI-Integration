package nuclearscience.common.block;

import com.mojang.serialization.MapCodec;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.TileTurbine;

public class BlockTurbine extends GenericEntityBlockWaterloggable {

    private static final VoxelShape SHAPE = Shapes.or(
            //
            Block.box(1.25, 2.5, 6, 14.75, 13.5, 10),
            //
            Block.box(6, 2.5, 1.25, 10, 13.5, 14.75),
            //
            Block.box(2, 2.5, 4, 14, 13.5, 12),
            //
            Block.box(4, 2.5, 2, 12, 13.5, 14),
            //
            Block.box(3, 2.5, 3, 13, 13.5, 13),
            //
            Block.box(4.65, 0.75, 4.65, 11.35, 2.5, 11.35),
            //
            Block.box(4.3, 13.5, 4.3, 11.7, 15, 11.7),
            //
            Block.box(5.7, 15, 5.7, 10.3, 16, 10.3));

    public static final BooleanProperty RENDER = BooleanProperty.create("render");

    public BlockTurbine() {
        super(Blocks.IRON_BLOCK.properties().strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(RENDER, true));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileTurbine(pos, state);
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, Player player) {
        super.onRotate(stack, pos, player);
        if (player.level().isClientSide()) {
            return;
        }
        TileTurbine turbine = (TileTurbine) player.level().getBlockEntity(pos);
        if (turbine != null) {
            if (turbine.isCore.get()) {
                turbine.deconstructStructure();
            } else {
                turbine.constructStructure();
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (!state.getValue(RENDER)) {
            return RenderShape.INVISIBLE;
        }
        return super.getRenderShape(state);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(RENDER, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(RENDER);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(RENDER)) {
            return SHAPE;
        }

        return Shapes.block();
    }
}
