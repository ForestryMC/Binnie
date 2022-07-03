package binnie.extrabees.apiary.machine.transmission;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import cofh.api.energy.IEnergyHandler;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TransmissionModifierComponent extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public TransmissionModifierComponent(Machine machine) {
        super(machine);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int energy = getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
        if (energy == 0) {
            return;
        }

        TileExtraBeeAlveary tile = (TileExtraBeeAlveary) getMachine().getTileEntity();
        List<IEnergyHandler> handlers = new ArrayList<>();
        for (TileEntity alvearyTile : tile.getAlvearyBlocks()) {
            if (alvearyTile instanceof IEnergyHandler && alvearyTile != tile) {
                handlers.add((IEnergyHandler) alvearyTile);
            }
        }

        if (handlers.isEmpty()) {
            return;
        }

        int maxOutput = 500;
        int output = energy / handlers.size();
        if (output > maxOutput) {
            output = maxOutput;
        } else if (output < 1) {
            output = 1;
        }

        for (IEnergyHandler handler : handlers) {
            int recieved = handler.receiveEnergy(ForgeDirection.NORTH, output, false);
            getUtil().getPoweredMachine().receiveEnergy(ForgeDirection.NORTH, -recieved, false);
            energy = getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
            if (energy <= 0) {
                return;
            }
        }
    }
}
