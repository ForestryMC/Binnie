package binnie.extrabees.apiary;

import binnie.core.IInitializable;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorSprite;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.client.GuiHack;
import binnie.extrabees.circuit.BinnieCircuitLayout;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleApiary implements IInitializable {

	public static Block blockComponent;
	public static ValidatorSprite spriteMutator;

	@Override
	public void preInit() {
		final MachineGroup machineGroup = new MachineGroup(GuiHack.INSTANCE, "alveay", "alveary", AlvearyMachine.values());
		machineGroup.setCreativeTab(Tabs.tabApiculture);
		GameRegistry.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary");
		ModuleApiary.blockComponent = machineGroup.getBlock();

	}

	@Override
	public void postInit() {
	}

	@Override
	public void init() {
		ModuleApiary.spriteMutator = new ValidatorSprite(GuiHack.INSTANCE, "validator/mutator.0", "validator/mutator.1");
	}
}
