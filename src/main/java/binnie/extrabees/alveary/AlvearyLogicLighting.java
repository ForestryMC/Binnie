package binnie.extrabees.alveary;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicLighting extends AbstractAlvearyLogic {

	public AlvearyLogicLighting(){
		this.energyStorage = new EnergyStorage(2000);
	}

	private final IEnergyStorage energyStorage;
	private boolean lighted;

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		lighted = energyStorage.extractEnergy(10, false) >= 10;
	}

	@Override
	public boolean isSelfLighted() {
		return lighted;
	}

}
