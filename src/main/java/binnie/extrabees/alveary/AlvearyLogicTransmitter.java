package binnie.extrabees.alveary;

import com.google.common.collect.Lists;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicTransmitter extends AbstractAlvearyLogic {

	public AlvearyLogicTransmitter(){
		internalStorage = new EnergyStorage(2000);
	}

	private IEnergyStorage internalStorage;

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		if (internalStorage.getEnergyStored() < 2){
			return;
		}
		List<IEnergyStorage> esL = Lists.newArrayList();
		for (TileEntityExtraBeesAlvearyPart part : tile.getExtraBeesParts()){
			if (part.getAlvearyLogic().getEnergyStorage() != null){
				esL.add(part.getAlvearyLogic().getEnergyStorage());
			}
		}
		if (esL.isEmpty()){
			return;
		}
		int div = esL.size();
		int maxOutput = 500;
		int output = internalStorage.getEnergyStored() / div;
		if (output > maxOutput) {
			output = maxOutput;
		}
		if (output < 1) {
			return;
		}
		for (IEnergyStorage handler : esL) {
			int recieved = handler.receiveEnergy(output, false);
			internalStorage.extractEnergy(recieved, false);
			if (internalStorage.getEnergyStored() < output) {
				return;
			}
		}
	}

}
