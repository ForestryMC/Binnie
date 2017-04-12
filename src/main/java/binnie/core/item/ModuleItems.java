package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleItems implements IInitializable {
	@Override
	public void preInit() {
		BinnieCore.setFieldKit(new ItemFieldKit());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getFieldKit());
		BinnieCore.setGenesis(new ItemGenesis());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getGenesis());
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ItemStack(BinnieCore.getFieldKit(), 1, 63), "g  ", " is", " pi", 'g', Blocks.GLASS_PANE, 'i', Items.IRON_INGOT, 'p', Items.PAPER, 's', new ItemStack(Items.DYE, 1));
	}
}
