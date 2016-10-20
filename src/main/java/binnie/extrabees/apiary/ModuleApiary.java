package binnie.extrabees.apiary;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuitLayout;
import binnie.core.machines.MachineGroup;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleApiary implements IInitializable {
    public static Block blockComponent;
    BinnieCircuitLayout stimulatorLayout;

    @Override
    public void preInit() {
        final MachineGroup machineGroup = new MachineGroup(ExtraBees.instance, "alveay", "alveary", AlvearyMachine.values());
        machineGroup.setCreativeTab(Tabs.tabApiculture);
        BinnieCore.proxy.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
        ModuleApiary.blockComponent = machineGroup.getBlock();
        AlvearyMutator.addMutationItem(new ItemStack(Blocks.SOUL_SAND), 1.5f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("UranFuel"), 4.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("MOXFuel"), 10.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Plutonium"), 8.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("smallPlutonium"), 5.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran235"), 4.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("smallUran235"), 2.5f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran238"), 2.0f);
        AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_PEARL), 2.0f);
        AlvearyMutator.addMutationItem(new ItemStack(Items.ENDER_EYE), 4.0f);
        for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
            GameRegistry.registerItem(frame.item = new ItemHiveFrame(frame), "hiveFrame." + frame.name().toLowerCase());
        }
    }

    @Override
    public void postInit() {
        EnumHiveFrame.init();
        GameRegistry.addRecipe(AlvearyMachine.Mutator.get(1), new Object[]{"g g", " a ", "t t", 'g', Items.GOLD_INGOT, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5)});
        GameRegistry.addRecipe(AlvearyMachine.Frame.get(1), new Object[]{"iii", "tat", " t ", 'i', Items.IRON_INGOT, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)});
        GameRegistry.addRecipe(AlvearyMachine.RainShield.get(1), new Object[]{" b ", "bab", "t t", 'b', Items.BRICK, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)});
        GameRegistry.addRecipe(AlvearyMachine.Lighting.get(1), new Object[]{"iii", "iai", " t ", 'i', Items.GLOWSTONE_DUST, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)});
        GameRegistry.addRecipe(AlvearyMachine.Stimulator.get(1), new Object[]{"kik", "iai", " t ", 'i', Items.GOLD_NUGGET, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4), 'k', new ItemStack(Mods.Forestry.item("chipsets"), 1, 2)});
        GameRegistry.addRecipe(AlvearyMachine.Hatchery.get(1), new Object[]{"i i", " a ", "iti", 'i', Blocks.GLASS_PANE, 'a', Mods.Forestry.block("alveary"), 't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5)});
        GameRegistry.addRecipe(new ShapedOreRecipe(AlvearyMachine.Transmission.get(1), new Object[]{" t ", "tat", " t ", 'a', Mods.Forestry.block("alveary"), 't', "gearTin"}));
        for (final AlvearyStimulator.CircuitType type : AlvearyStimulator.CircuitType.values()) {
            type.createCircuit(this.stimulatorLayout);
        }
    }

    @Override
    public void init() {
        this.stimulatorLayout = new BinnieCircuitLayout(ExtraBees.instance, "Stimulator");
    }
}
