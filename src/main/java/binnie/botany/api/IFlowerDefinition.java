package binnie.botany.api;

import net.minecraft.item.ItemStack;

import forestry.core.genetics.IGeneticDefinition;

public interface IFlowerDefinition extends IGeneticDefinition {
	@Override
	IFlowerGenome getGenome();
	
	@Override
	IFlower getIndividual();
	
	ItemStack getMemberStack(EnumFlowerStage flowerStage);
}
