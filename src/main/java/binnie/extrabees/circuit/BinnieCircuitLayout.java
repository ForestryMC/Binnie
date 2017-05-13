package binnie.extrabees.circuit;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;
import net.minecraft.util.text.translation.I18n;

public class BinnieCircuitLayout implements ICircuitLayout {

	public BinnieCircuitLayout(final String uid, final ICircuitSocketType socketType) {
		this.uid = uid;
		this.socketType = socketType;
		ChipsetManager.circuitRegistry.registerLayout(this);
	}

	private final String uid;
	private final ICircuitSocketType socketType;

	@Override
	public String getUID() {
		return "binnie.circuitLayout" + this.uid;
	}

	@Override
	public String getName() {
		return I18n.translateToLocal("circuit.layout." + this.uid.toLowerCase());
	}

	@Override
	public String getUsage() {
		return I18n.translateToLocal("circuit.layout." + this.uid.toLowerCase() + ".usage");
	}

	@Override
	public ICircuitSocketType getSocketType() {
		return this.socketType;
	}

}
