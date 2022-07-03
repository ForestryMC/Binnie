package binnie.extratrees.machines.designer;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;

public abstract class CarpenterPackage extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
    private DesignerType type;

    public CarpenterPackage(DesignerType type) {
        super(type.name, type.texture, false);
        this.type = type;
    }

    @Override
    public void createMachine(Machine machine) {
        new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Woodworker);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

        inventory.addSlot(Designer.GLUE_SLOT, "polish").setValidator(new PolishSlotValidator(type));

        inventory.addSlot(Designer.DESIGN_1_SLOT, "wood").setValidator(new PlanksSlotValidator(type));

        inventory.addSlot(Designer.DESIGN_2_SLOT, "wood").setValidator(new PlanksSlotValidator(type));

        new WoodworkerRecipeComponent(machine, type);
    }
}
