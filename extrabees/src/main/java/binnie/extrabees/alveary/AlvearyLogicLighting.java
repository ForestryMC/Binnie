package binnie.extrabees.alveary;

public class AlvearyLogicLighting extends AlvearyLogicElectrical {

	private boolean lighted;

	public AlvearyLogicLighting() {
		super(2000);
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
