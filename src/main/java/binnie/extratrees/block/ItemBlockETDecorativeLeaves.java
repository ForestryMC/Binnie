package binnie.extratrees.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import binnie.extratrees.genetics.ETTreeDefinition;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.items.ItemBlockLeaves;
import forestry.core.items.IColoredItem;
import forestry.core.items.ItemBlockForestry;
import forestry.core.utils.Translator;

public class ItemBlockETDecorativeLeaves extends ItemBlockForestry<BlockETDecorativeLeaves> implements IColoredItem {
	public ItemBlockETDecorativeLeaves(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int meta = itemStack.getMetadata();
		BlockETDecorativeLeaves block = getBlock();
		ETTreeDefinition treeDefinition = block.getTreeType(meta);
		if (treeDefinition == null) {
			return Translator.translateToLocal("trees.grammar.leaves.type");
		}

		String unlocalizedSpeciesName = treeDefinition.getGenome().getPrimary().getUnlocalizedName();
		return ItemBlockLeaves.getDisplayName(unlocalizedSpeciesName);
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int renderPass) {
		int meta = itemStack.getMetadata();
		BlockETDecorativeLeaves block = getBlock();
		ETTreeDefinition treeDefinition = block.getTreeType(meta);
		if (treeDefinition == null) {
			return PluginArboriculture.proxy.getFoliageColorBasic();
		}

		ITreeGenome genome = treeDefinition.getGenome();

		if (renderPass == 0) {
			return genome.getPrimary().getLeafSpriteProvider().getColor(false);
		} else {
			IFruitProvider fruitProvider = genome.getFruitProvider();
			return fruitProvider.getDecorativeColor();
		}
	}
}