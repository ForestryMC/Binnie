package binnie.core.circuits;

import binnie.Binnie;
import binnie.core.AbstractMod;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

import javax.annotation.Nonnull;

public class BinnieCircuitLayout implements ICircuitLayout {
	private final String uid;
	private final AbstractMod mod;
	private final ICircuitSocketType socketType;

	public BinnieCircuitLayout(final AbstractMod mod, final String uid, final ICircuitSocketType socketType) {
		this.uid = uid;
		this.mod = mod;
		this.socketType = socketType;
		ChipsetManager.circuitRegistry.registerLayout(this);
	}

	@Override
	public String getUID() {
		return "binnie.circuitLayout" + this.uid;
	}

	@Override
	public String getName() {
		return Binnie.LANGUAGE.localise(this.mod, "circuit.layout." + this.uid.toLowerCase());
	}

	@Override
	public String getUsage() {
		return Binnie.LANGUAGE.localise(this.mod, "circuit.layout." + this.uid.toLowerCase() + ".usage");
	}

	@Override
	public ICircuitSocketType getSocketType() {
		return this.socketType;
	}
}
