// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.circuits;

import binnie.Binnie;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.CircuitSocketType;
import forestry.api.circuits.ICircuitSocketType;
import binnie.core.AbstractMod;
import forestry.api.circuits.ICircuitLayout;

public class BinnieCircuitLayout implements ICircuitLayout
{
	private String uid;
	private AbstractMod mod;

	public BinnieCircuitLayout(final AbstractMod mod, final String uid) {
		this.uid = uid;
		this.mod = mod;
		ChipsetManager.circuitRegistry.registerLayout(this);
	}

	@Override
	public String getUID() {
		return "binnie.circuitLayout" + this.uid;
	}

	@Override
	public String getName() {
		return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase());
	}

	@Override
	public String getUsage() {
		return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase() + ".usage");
	}

	@Override
	public ICircuitSocketType getSocketType() {
		return CircuitSocketType.NONE;// TODO hmm? CircuitSocketType
	}
}
