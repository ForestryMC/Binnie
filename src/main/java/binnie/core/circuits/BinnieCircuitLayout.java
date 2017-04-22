package binnie.core.circuits;

import binnie.*;
import binnie.core.*;
import forestry.api.circuits.*;

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
		return Binnie.Language.localise(mod, "circuit.layout." + uid.toLowerCase());
	}

	@Override
	public String getUsage() {
		return Binnie.Language.localise(mod, "circuit.layout." + uid.toLowerCase() + ".usage");
	}

	@Override
	public ICircuitSocketType getSocketType() {
		// TODO hmm? CircuitSocketType
		return CircuitSocketType.NONE;
	}
}
