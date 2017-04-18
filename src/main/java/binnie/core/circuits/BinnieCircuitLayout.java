package binnie.core.circuits;

import binnie.Binnie;
import binnie.core.AbstractMod;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.CircuitSocketType;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

public class BinnieCircuitLayout implements ICircuitLayout {
	private String uid;
	private AbstractMod mod;

	public BinnieCircuitLayout(final AbstractMod mod, final String uid) {
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
