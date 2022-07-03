package binnie.core.circuits;

import binnie.core.AbstractMod;
import binnie.core.util.I18N;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.CircuitSocketType;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

public class BinnieCircuitLayout implements ICircuitLayout {
    private String uid;
    private AbstractMod mod;

    public BinnieCircuitLayout(AbstractMod mod, String uid) {
        this.uid = uid;
        this.mod = mod;
        ChipsetManager.circuitRegistry.registerLayout(this);
    }

    @Override
    public String getUID() {
        return "binnie.circuitLayout" + uid;
    }

    @Override
    public String getName() {
        return I18N.localise(mod.getModID() + ".circuit.layout." + uid.toLowerCase());
    }

    @Override
    public String getUsage() {
        return I18N.localise(mod.getModID() + ".circuit.layout." + uid.toLowerCase() + ".usage");
    }

    @Override
    public ICircuitSocketType getSocketType() {
        // TODO hmm? CircuitSocketType
        return CircuitSocketType.NONE;
    }
}
