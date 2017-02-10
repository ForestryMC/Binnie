package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public interface IGeneItem {
	@Nullable
	ISpeciesRoot getSpeciesRoot();

	@SideOnly(Side.CLIENT)
	void getInfo(final List<String> p0);

	int getColour(final int p0);

	void writeToItem(final ItemStack p0);

	void addGene(final IGene p0);
}
