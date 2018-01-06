package binnie.core.machines.render;

import binnie.core.machines.Machine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.TileEntityTESRMachine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTESRMachine extends TileEntitySpecialRenderer<TileEntityTESRMachine> {
	@Override
	public void render(TileEntityTESRMachine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Machine machine = te.getMachine();
		if (machine != null) {
			for (IRender.Render renders : machine.getInterfaces(IRender.Render.class)) {
				renders.renderInWorld(x, y, z);
			}
		}
	}
}
