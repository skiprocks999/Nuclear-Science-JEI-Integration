package nuclearscience.api.network.reactorlogistics;

import electrodynamics.api.network.cable.IRefreshableCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;

public interface IReactorLogisticsCable extends IRefreshableCable {

    SubtypeReactorLogisticsCable getPipeType();

    @Override
    default Object getConductorType() {
        return getPipeType();
    }

    @Override
    default double getMaxTransfer() {
        return 0;
    }
}
