package binnie.botany.api.genetics;

import forestry.core.genetics.IGeneticDefinition;
import net.minecraft.item.ItemStack;

public interface IFlowerDefinition extends IGeneticDefinition {
	@Override
	IFlowerGenome getGenome();

	@Override
	IFlower getIndividual();

	ItemStack getMemberStack(EnumFlowerStage flowerStage);
}
