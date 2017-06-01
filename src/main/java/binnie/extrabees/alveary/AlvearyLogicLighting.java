package binnie.extrabees.alveary;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class AlvearyLogicLighting extends AbstractAlvearyLogic {

	private final IEnergyStorage energyStorage;
	private boolean lighted;

	public AlvearyLogicLighting() {
		this.energyStorage = new EnergyStorage(2000);
	}

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		lighted = energyStorage.extractEnergy(10, false) >= 10;
	}

	@Override
	public boolean isSelfLighted() {
		return lighted;
	}
}
