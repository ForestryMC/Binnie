package binnie.extratrees.machines;

import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.Tabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
    @Override
    public void preInit() {
        MachineGroup machineGroup =
                new MachineGroup(ExtraTrees.instance, "machine", "machine", ExtraTreeMachine.values());
        machineGroup.setCreativeTab(Tabs.tabArboriculture);
        ExtraTrees.blockMachine = machineGroup.getBlock();
    }

    @Override
    public void init() {
        // ignore
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Lumbermill.get(1), new Object[] {
            "gAg",
            "GsG",
            "gPg",
            'G',
            Blocks.glass,
            'g',
            ExtraTreeItems.ProvenGear.get(1),
            'A',
            Items.iron_axe,
            's',
            Mods.forestry.stack("sturdyMachine"),
            'P',
            "gearBronze"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                ExtraTreeMachine.Woodworker.get(1),
                "wGw",
                "GsG",
                "ggg",
                'G',
                Blocks.glass,
                'g',
                ExtraTreeItems.ProvenGear.get(1),
                'w',
                Blocks.planks,
                's',
                Mods.forestry.stack("impregnatedCasing")));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                ExtraTreeMachine.Panelworker.get(1),
                "wGw",
                "GsG",
                "ggg",
                'G',
                Blocks.glass,
                'g',
                ExtraTreeItems.ProvenGear.get(1),
                'w',
                Blocks.wooden_slab,
                's',
                Mods.forestry.stack("impregnatedCasing")));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                ExtraTreeMachine.Glassworker.get(1),
                "wGw",
                "GsG",
                "ggg",
                'G',
                Blocks.glass,
                'g',
                ExtraTreeItems.ProvenGear.get(1),
                'w',
                Blocks.glass,
                's',
                Mods.forestry.stack("impregnatedCasing")));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                ExtraTreeMachine.Tileworker.get(1),
                "wGw",
                "GsG",
                "ggg",
                'G',
                Blocks.glass,
                'g',
                ExtraTreeItems.ProvenGear.get(1),
                'w',
                Items.clay_ball,
                's',
                Mods.forestry.stack("impregnatedCasing")));
    }
}
