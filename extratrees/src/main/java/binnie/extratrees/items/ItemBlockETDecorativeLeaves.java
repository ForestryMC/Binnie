package binnie.extratrees.items;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.arboriculture.ModuleArboriculture;
import forestry.arboriculture.items.ItemBlockLeaves;
import forestry.core.items.IColoredItem;
import forestry.core.items.ItemBlockForestry;

import binnie.core.util.I18N;
import binnie.extratrees.blocks.BlockETDecorativeLeaves;
import binnie.extratrees.genetics.ETTreeDefinition;

public class ItemBlockETDecorativeLeaves extends ItemBlockForestry<BlockETDecorativeLeaves> implements IColoredItem {
	public ItemBlockETDecorativeLeaves(BlockETDecorativeLeaves block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int meta = itemStack.getMetadata();
		BlockETDecorativeLeaves block = getBlock();
		ETTreeDefinition treeDefinition = block.getTreeType(meta);
		if (treeDefinition == null || treeDefinition.getGenome() == null) {
			return I18N.localise("trees.grammar.leaves.type");
		}

		String unlocalizedSpeciesName = treeDefinition.getGenome().getPrimary().getUnlocalizedName();
		return ItemBlockLeaves.getDisplayName(unlocalizedSpeciesName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemstack(ItemStack itemStack, int renderPass) {
		int meta = itemStack.getMetadata();
		BlockETDecorativeLeaves block = getBlock();
		ETTreeDefinition treeDefinition = block.getTreeType(meta);
		if (treeDefinition == null) {
			return ModuleArboriculture.proxy.getFoliageColorBasic();
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