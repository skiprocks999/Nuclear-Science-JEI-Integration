package nuclearscience.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeIrradiatedBlock implements ISubtype {
    ;

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
