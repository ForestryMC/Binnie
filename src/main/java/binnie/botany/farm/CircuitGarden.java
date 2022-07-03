package binnie.botany.farm;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.util.I18N;
import forestry.api.circuits.ChipsetManager;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class CircuitGarden extends BinnieCircuit {
    private boolean isManual;
    private boolean isFertilised;
    private ItemStack icon;
    private EnumMoisture moisture;
    private EnumAcidity acidity;

    public CircuitGarden(
            EnumMoisture moisture,
            EnumAcidity ph,
            boolean manual,
            boolean fertilised,
            ItemStack recipe,
            ItemStack icon) {
        super(
                "garden." + moisture.getID() + ((ph != null) ? ("." + ph.getID()) : "") + (manual ? ".manual" : "")
                        + (fertilised ? ".fert" : ""),
                4,
                manual
                        ? ChipsetManager.circuitRegistry.getLayout("forestry.farms.manual")
                        : ChipsetManager.circuitRegistry.getLayout("forestry.farms.managed"),
                recipe);
        this.moisture = moisture;
        this.icon = icon;
        isManual = manual;
        isFertilised = fertilised;
        acidity = ph;
        String info = "";

        if (moisture == EnumMoisture.DRY) {
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.moisture.dry") + EnumChatFormatting.RESET;
        }

        if (moisture == EnumMoisture.DAMP) {
            info += EnumChatFormatting.YELLOW + I18N.localise("botany.moisture.damp") + EnumChatFormatting.RESET;
        }

        if (acidity == EnumAcidity.ACID) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += EnumChatFormatting.RED + I18N.localise("botany.ph.acid") + EnumChatFormatting.RESET;
        }

        if (acidity == EnumAcidity.NEUTRAL) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += EnumChatFormatting.GREEN + I18N.localise("botany.ph.neutral") + EnumChatFormatting.RESET;
        }

        if (acidity == EnumAcidity.ALKALINE) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += EnumChatFormatting.AQUA + I18N.localise("botany.ph.alkaline") + EnumChatFormatting.RESET;
        }

        if (info.length() > 0) {
            info = " (" + info + EnumChatFormatting.RESET + ")";
        }
        addTooltipString(I18N.localise("botany.circuit.flowers") + info);
    }

    @Override
    public boolean isCircuitable(Object tile) {
        return tile instanceof IFarmHousing;
    }

    @Override
    public void onInsertion(int slot, Object tile) {
        if (!isCircuitable(tile)) {
            return;
        }

        GardenLogic logic = new GardenLogic((IFarmHousing) tile);
        logic.setData(moisture, acidity, isManual, isFertilised, icon, I18N.localise(getName()));
        ((IFarmHousing) tile).setFarmLogic(FarmDirection.values()[slot], logic);
    }

    @Override
    public void onLoad(int slot, Object tile) {
        onInsertion(slot, tile);
    }

    @Override
    public void onRemoval(int slot, Object tile) {
        if (!isCircuitable(tile)) {
            return;
        }
        ((IFarmHousing) tile).resetFarmLogic(FarmDirection.values()[slot]);
    }

    @Override
    public void onTick(int arg0, Object arg1) {
        // ignored
    }
}
