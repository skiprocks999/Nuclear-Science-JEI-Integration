package nuclearscience.api.network.reactorlogistics;

import net.minecraft.core.Direction;
import nuclearscience.common.network.ReactorLogisticsNetwork;

public interface ILogisticsMember {

    boolean isValidConnection(Direction dir);

    boolean canConnect(ReactorLogisticsNetwork network);

}
