package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import forestry.api.circuits.ChipsetManager;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class CircuitGarden extends BinnieCircuit {
	private Class<? extends FarmLogic> logicClass;
	private boolean isManual;
	private boolean isFertilised;
	private ItemStack icon;
	private EnumMoisture moisture;
	private EnumAcidity acidity;
	private GardenLogic logic;
    
	public CircuitGarden(EnumMoisture moisture, EnumAcidity ph, boolean manual, boolean fertilised, ItemStack recipe, ItemStack icon) {
		super(getName(moisture, ph, manual, fertilised), 4, manual ? ChipsetManager.circuitRegistry.getLayout("forestry.farms.manual") : ChipsetManager.circuitRegistry.getLayout("forestry.farms.managed"), recipe);
		this.isFertilised = false;
		this.isManual = manual;
		this.isFertilised = fertilised;
		this.moisture = moisture;
		this.acidity = ph;
		this.icon = icon;
		this.logic = new GardenLogic(this.moisture, this.acidity, this.isManual, this.isFertilised, this.icon, Binnie.Language.localise(this.getUnlocalizedName()));
		String info = "";
		if (moisture != EnumMoisture.Normal) {
			info+=TextFormatting.GRAY + Binnie.Language.localise("botany.moisture") + ": " + moisture.getTranslated(true);
		}
		if (info.length() > 0) {
			info += ", ";
		}
		if (ph != null) {
			info+=TextFormatting.GRAY + Binnie.Language.localise("botany.ph") + ": " + ph.getTranslated(true);
		}
		if (info.length() > 0) {
			info = " (" + info + TextFormatting.RESET + ")";
		}
		this.addTooltipString("Flowers" + info);
	}
	
	private static String getName(EnumMoisture moisture, EnumAcidity ph, boolean manual, boolean fertilised){
		String name = "garden." + moisture.getName();
		if(ph != null){
			name += "." + ph.getName();
		}
		if(manual){
			name += ".manual";
		}
		if(fertilised){
			name += ".fert";
		}
		return name;
	}
	
	public CircuitGarden setManual() {
		isManual = true;
		return this;
	}

	@Override
	public boolean isCircuitable(Object tile) {
		return tile instanceof IFarmHousing;
	}

	private IFarmHousing getCircuitable(Object tile) {
		if (!isCircuitable(tile)) {
			return null;
		}
		return (IFarmHousing) tile;
	}

	@Override
	public void onInsertion(int slot, Object tile) {
		IFarmHousing housing = getCircuitable(tile);
		if (housing == null) {
			return;
		}

		logic.setManual(isManual);

		housing.setFarmLogic(FarmDirection.values()[slot], logic);
	}

	@Override
	public void onLoad(int slot, Object tile) {
		onInsertion(slot, tile);
	}

	@Override
	public void onRemoval(int slot, Object tile) {
		IFarmHousing farmHousing = getCircuitable(tile);
		if (farmHousing == null) {
			return;
		}

		farmHousing.resetFarmLogic(FarmDirection.values()[slot]);
	}

	@Override
	public void onTick(int slot, Object tile) {
	}

}
