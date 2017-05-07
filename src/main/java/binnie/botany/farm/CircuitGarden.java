package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
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

	public CircuitGarden(EnumMoisture moisture, EnumAcidity ph, boolean manual, boolean fertilised, ItemStack recipe, ItemStack icon) {
		super("garden." + moisture.getID() + ((ph != null) ? ("." + ph.getID()) : "") + (manual ? ".manual" : "") + (fertilised ? ".fert" : ""), 4, manual ? ChipsetManager.circuitRegistry.getLayout("forestry.farms.manual") : ChipsetManager.circuitRegistry.getLayout("forestry.farms.managed"), recipe);
		this.moisture = moisture;
		this.icon = icon;
		isManual = manual;
		isFertilised = fertilised;
		acidity = ph;
		String info = "";

		if (moisture == EnumMoisture.Dry) {
			info += EnumChatFormatting.YELLOW
				+ Binnie.I18N.localise(Botany.instance, "moisture.dry")
				+ EnumChatFormatting.RESET;
		}

		if (moisture == EnumMoisture.Damp) {
			info += EnumChatFormatting.YELLOW
				+ Binnie.I18N.localise(Botany.instance, "moisture.damp")
				+ EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Acid) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.RED
				+ Binnie.I18N.localise(Botany.instance, "ph.acid")
				+ EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Neutral) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.GREEN
				+ Binnie.I18N.localise(Botany.instance, "ph.neutral")
				+ EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Alkaline) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.AQUA
				+ Binnie.I18N.localise(Botany.instance, "ph.alkaline")
				+ EnumChatFormatting.RESET;
		}

		if (info.length() > 0) {
			info = " (" + info + EnumChatFormatting.RESET + ")";
		}
		addTooltipString("Flowers" + info);
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
		logic.setData(moisture, acidity, isManual, isFertilised, icon, Binnie.I18N.localise(getName()));
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
