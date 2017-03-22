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
	void getInfo(List<String> info);

	int getColour(int p0);

	void writeToItem(ItemStack itemStack);

	void addGene(IGene gene);
}
