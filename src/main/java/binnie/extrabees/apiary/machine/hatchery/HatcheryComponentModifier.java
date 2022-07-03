package binnie.extrabees.apiary.machine.hatchery;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeRoot;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class HatcheryComponentModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public HatcheryComponentModifier(Machine machine) {
        super(machine);
    }

    @Override
    public void onUpdate() {
        if (new Random().nextInt(2400) != 0) {
            return;
        }

        TileEntity tile = getMachine().getTileEntity();
        if (!(tile instanceof TileExtraBeeAlveary)) {
            return;
        }

        IBeeHousing house = ((TileExtraBeeAlveary) tile).getMultiblockLogic().getController();
        if (house == null || house.getErrorLogic().hasErrors()) {
            return;
        }

        ItemStack queenStack = house.getBeeInventory().getQueen();
        IBee queen = (queenStack == null) ? null : Binnie.Genetics.getBeeRoot().getMember(queenStack);
        if (queen == null) {
            return;
        }

        IBeeRoot beeRoot = Binnie.Genetics.getBeeRoot();
        ItemStack larvae = beeRoot.getMemberStack(
                beeRoot.getBee(getMachine().getWorld(), queen.getGenome()), EnumBeeType.LARVAE.ordinal());
        new TransferRequest(larvae, getInventory()).transfer(true);
    }
}
