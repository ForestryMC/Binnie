package binnie.genetics.item;

import binnie.core.item.IItemMiscProvider;
import binnie.genetics.modules.ModuleCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public enum GeneticsItems implements IItemMiscProvider {
	LaboratoryCasing("Reinforced Casing", "casing_iron"),
	DNADye("DNA Dye", "dna_dye"),
	FluorescentDye("Fluorescent Dye", "dye_fluor"),
	Enzyme("Enzyme", "enzyme"),
	GrowthMedium("Growth Medium", "growth_medium"),
	EmptySequencer("Blank Sequence", "sequencer_empty"),
	EMPTY_SERUM("Empty Serum Vial", "serum_empty"),
	EMPTY_GENOME("Empty Serum Array", "genome_empty"),
	IntegratedCircuit("Integrated Circuit Board", "integrated_circuit"),
	IntegratedCPU("Integrated CPU", "integrated_cpu"),
	IntegratedCasing("Integrated Casing", "casing_circuit");

	private final String name;
	private final String modelPath;

	GeneticsItems(final String name, final String modelPath) {
		this.name = name;
		this.modelPath = modelPath;
	}

	@Override
	public String getModelPath() {
		return modelPath;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final List<String> tooltip) {
	}

	@Override
	public String getDisplayName(final ItemStack stack) {
		return this.name;
	}

	@Override
	public ItemStack get(final int size) {
		return new ItemStack(ModuleCore.itemGenetics, size, this.ordinal());
	}

	public ItemStack get(Item itemGenetics, final int size) {
		return new ItemStack(itemGenetics, size, this.ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
