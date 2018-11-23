package binnie.extrabees.machines.hatchery;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;
import binnie.extrabees.machines.TileExtraBeeAlveary;
import binnie.extrabees.utils.ComponentBeeModifier;
import binnie.extrabees.utils.Utils;

public class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentFrameModifier(Machine machine) {
		super(machine);
	}

	@Override
	public void onUpdate() {
		if (new Random().nextInt(2400) == 0) {
			TileEntity tile = this.getMachine().getTileEntity();
			if (tile instanceof TileExtraBeeAlveary) {
				IBeeHousing house = ((TileExtraBeeAlveary) tile).getMultiblockLogic().getController();
				if (house != null && !house.getErrorLogic().hasErrors()) {
					ItemStack queenStack = house.getBeeInventory().getQueen();
					IBee queen = (queenStack.isEmpty()) ? null : Utils.getBeeRoot().getMember(queenStack);
					if (queen != null) {
						ItemStack larvae = Utils.getBeeRoot().getMemberStack(Utils.getBeeRoot().getBee(queen.getGenome()), EnumBeeType.LARVAE);
						new TransferRequest(larvae, this.getInventory()).transfer(null, true);
					}
				}
			}
		}
	}
}
