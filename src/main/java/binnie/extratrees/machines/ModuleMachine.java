package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import forestry.api.core.Tabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "machine", "machine", ExtraTreeMachine.values());
		machineGroup.setCreativeTab(Tabs.tabArboriculture);
		ExtraTrees.blockMachine = machineGroup.getBlock();
		BinnieCore.proxy.registerTileEntity(TileEntityNursery.class, "binnie.tile.nursery", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Lumbermill.get(1), "gAg", "GsG", "gPg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'A', Items.IRON_AXE, 's', Mods.Forestry.stack("sturdyMachine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Press.get(1), "iGi", "tSt", "tPt", 'i', "ingotIron", 'G', Blocks.GLASS, 't', "ingotTin", 'S', Mods.Forestry.stack("sturdyMachine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Brewery.get(1), "bGb", "iSi", "bPb", 'i', "ingotIron", 'G', Blocks.GLASS, 'b', "gearBronze", 'S', Mods.Forestry.stack("sturdyMachine"), 'P', "gearBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Distillery.get(1), "rGr", "iSi", "rPr", 'i', "ingotIron", 'G', Blocks.GLASS, 'r', "dustRedstone", 'S', Mods.Forestry.stack("sturdyMachine"), 'P', "gearBronze"));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Woodworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.PLANKS, 's', Mods.Forestry.stack("impregnatedCasing")));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Panelworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.WOODEN_SLAB, 's', Mods.Forestry.stack("impregnatedCasing")));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Glassworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Blocks.GLASS, 's', Mods.Forestry.stack("impregnatedCasing")));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Tileworker.get(1), "wGw", "GsG", "ggg", 'G', Blocks.GLASS, 'g', ExtraTreeItems.ProvenGear.get(1), 'w', Items.CLAY_BALL, 's', Mods.Forestry.stack("impregnatedCasing")));
	}
}
