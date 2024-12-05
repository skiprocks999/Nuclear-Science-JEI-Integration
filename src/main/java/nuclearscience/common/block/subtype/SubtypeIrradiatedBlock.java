package nuclearscience.common.block.subtype;

import electrodynamics.api.ISubtype;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public enum SubtypeIrradiatedBlock implements ISubtype {
    soil(Blocks.DIRT.properties().randomTicks()),
    grass(Blocks.GRASS_BLOCK.properties().randomTicks()),
    petrifiedwood(Blocks.OAK_WOOD.properties().randomTicks());

    public final BlockBehaviour.Properties properties;

    private SubtypeIrradiatedBlock(BlockBehaviour.Properties properties) {
        this.properties = properties;
    }

    @Override
    public String tag() {
        return "irradiatedblock" + name();
    }

    @Override
    public String forgeTag() {
        return "irradiatedblock/" + name();
    }

    @Override
    public boolean isItem() {
        return false;
    }
}
