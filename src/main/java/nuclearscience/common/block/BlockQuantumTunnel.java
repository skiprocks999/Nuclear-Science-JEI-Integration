package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import nuclearscience.References;
import nuclearscience.common.tile.TileQuantumTunnel;

import java.util.HashSet;

public class BlockQuantumTunnel extends GenericMachineBlock {

	private static final Color NONE = new Color(114, 114, 114, 255);
	public static final Color INPUT = new Color(167, 223, 248, 255);
	public static final Color OUTPUT = new Color(255, 120, 46, 255);

	private static final HashSet<Block> BLOCKS = new HashSet<>();

	public BlockQuantumTunnel() {
		super(TileQuantumTunnel::new);
		BLOCKS.add(this);
	}

	@EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
	private static class ColorHandlerInternal {

		@SubscribeEvent
		public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
			BLOCKS.forEach(block -> event.register((state, level, pos, tintIndex) -> {
				if (tintIndex >= 1) {

					BlockEntity tile = level.getBlockEntity(pos);

					if(tile instanceof TileQuantumTunnel tunnel) {

						Direction dir = getDirFromIndex(tintIndex);

						if(tunnel.readInputDirections().contains(dir)) {
							return INPUT.color();
						} else if (tunnel.readOutputDirections().contains(dir)) {
							return OUTPUT.color();
						} else {
							return NONE.color();
						}

					} else {
						return NONE.color();
					}
				}
				return Color.WHITE.color();
			}, block));
		}
	}

	private static Direction getDirFromIndex(int index) {
		if(index == 1) {
			return Direction.SOUTH.getCounterClockWise();
		} else if (index == 2) {
			return Direction.NORTH.getCounterClockWise();
		} else if (index == 3) {
			return Direction.EAST.getCounterClockWise();
		} else if (index == 4) {
			return Direction.WEST.getCounterClockWise();
		} else if (index == 5) {
			return Direction.UP;
		} else if (index == 6) {
			return Direction.DOWN;
		}
		return Direction.UP;
	}
}
