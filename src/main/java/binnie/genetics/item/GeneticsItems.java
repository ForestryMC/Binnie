// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.item;

import java.util.List;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import binnie.core.item.IItemMisc;

public enum GeneticsItems implements IItemMisc
{
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

	IIcon icon;
	String name;
	String iconPath;

	private GeneticsItems(final String name, final String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public IIcon getIcon(final ItemStack itemStack) {
		return this.icon;
	}

	@Override
	public void registerIcons(final IIconRegister register) {
		this.icon = Genetics.proxy.getIcon(register, this.iconPath);
	}

	@Override
	public void addInformation(final List data) {
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return this.name;
	}

	@Override
	public ItemStack get(final int count) {
		return (Genetics.itemGenetics == null) ? null : new ItemStack(Genetics.itemGenetics, count, this.ordinal());
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
