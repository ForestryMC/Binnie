package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import forestry.api.circuits.ChipsetManager;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class CircuitGarden extends BinnieCircuit {
	private Class<? extends FarmLogic> logicClass;
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
			info += EnumChatFormatting.YELLOW + "Dry" + EnumChatFormatting.RESET;
		}

		if (moisture == EnumMoisture.Damp) {
			info += EnumChatFormatting.YELLOW + "Damp" + EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Acid) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.RED + "Acidic" + EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Neutral) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.GREEN + "Neutral" + EnumChatFormatting.RESET;
		}

		if (acidity == EnumAcidity.Alkaline) {
			if (info.length() > 0) {
				info += ", ";
			}
			info += EnumChatFormatting.AQUA + "Alkaline" + EnumChatFormatting.RESET;
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
		logic.setData(moisture, acidity, isManual, isFertilised, icon, Binnie.Language.localise(getName()));
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
	}

}
