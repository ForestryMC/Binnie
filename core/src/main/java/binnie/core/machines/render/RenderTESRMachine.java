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
	public void render(final TileEntityTESRMachine te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
		final Machine machine = te.getMachine();
		if (machine != null) {
			for (final IRender.Render renders : machine.getInterfaces(IRender.Render.class)) {
				renders.renderInWorld(x, y, z);
			}
		}
	}
}
