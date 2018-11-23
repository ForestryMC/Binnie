package binnie.genetics.machine.lab;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.util.EntityItemRenderer;

public class LabFX extends MachineComponent implements IRender.Render {
	private final EntityItemRenderer entityItemRenderer;

	public LabFX(IMachine machine) {
		super(machine);
		this.entityItemRenderer = new EntityItemRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInWorld(double x, double y, double z) {
		ItemStack stack = this.getUtil().getStack(0);
		World world = this.getMachine().getWorld();
		this.entityItemRenderer.renderInWorld(stack, world, x + 0.5, y + 0.8, z + 0.5);
	}

	@Override
	public void onInventoryUpdate() {
		this.getUtil().refreshBlock();
	}
}
