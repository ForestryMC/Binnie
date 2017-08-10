package binnie.genetics.genetics;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.ISpeciesRoot;

import binnie.api.genetics.IGene;

public interface IGeneItem {
	@Nullable
	ISpeciesRoot getSpeciesRoot();

	@SideOnly(Side.CLIENT)
	void getInfo(List<String> info);

	int getColor(int renderPass);

	void writeToItem(ItemStack stack);

	void addGene(IGene gene);
}
