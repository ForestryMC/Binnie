package binnie.core.circuits;

import binnie.Binnie;
import binnie.core.AbstractMod;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

import javax.annotation.Nonnull;

public class BinnieCircuitLayout implements ICircuitLayout {
	@Nonnull
	private final String uid;
	@Nonnull
	private final AbstractMod mod;
	@Nonnull
	private final ICircuitSocketType socketType;

	public BinnieCircuitLayout(@Nonnull final AbstractMod mod, @Nonnull final String uid, @Nonnull final ICircuitSocketType socketType) {
		this.uid = uid;
		this.mod = mod;
		this.socketType = socketType;
		ChipsetManager.circuitRegistry.registerLayout(this);
	}

	@Nonnull
	@Override
	public String getUID() {
		return "binnie.circuitLayout" + this.uid;
	}

	@Nonnull
	@Override
	public String getName() {
		return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase());
	}

	@Nonnull
	@Override
	public String getUsage() {
		return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase() + ".usage");
	}

	@Nonnull
	@Override
	public ICircuitSocketType getSocketType() {
		return this.socketType;
	}
}
