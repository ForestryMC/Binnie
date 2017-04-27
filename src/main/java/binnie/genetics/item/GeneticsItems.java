package binnie.genetics.item;

import binnie.core.item.IItemMisc;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum GeneticsItems implements IItemMisc {
	LaboratoryCasing("Reinforced Casing", "casingIron"),
	DNADye("DNA Dye", "dnaDye"),
	FluorescentDye("Fluorescent Dye", "dyeFluor"),
	Enzyme("Enzyme", "enzyme"),
	GrowthMedium("Growth Medium", "growthMedium"),
	EmptySequencer("Blank Sequence", "sequencerEmpty"),
	EmptySerum("Empty Serum Vial", "serumEmpty"),
	EmptyGenome("Empty Serum Array", "genomeEmpty"),
	Cylinder("Glass Cylinder", "cylinderEmpty"),
	IntegratedCircuit("Integrated Circuit Board", "integratedCircuit"),
	IntegratedCPU("Integrated CPU", "integratedCPU"),
	IntegratedCasing("Integrated Casing", "casingCircuit");

	protected IIcon icon;
	protected String name;
	protected String iconPath;

	GeneticsItems(String name, String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public IIcon getIcon(ItemStack itemStack) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = Genetics.proxy.getIcon(register, iconPath);
	}

	@Override
	public void addInformation(List data) {
	}

	@Override
	public String getName(ItemStack itemStack) {
		return name;
	}

	@Override
	public ItemStack get(int count) {
		if (Genetics.itemGenetics == null) {
			return null;
		}
		return new ItemStack(Genetics.itemGenetics, count, ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
