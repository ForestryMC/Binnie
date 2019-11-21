package binnie.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityItemRenderer {
	private final EntityItem dummyEntityItem;
	private long lastTick;

	public EntityItemRenderer() {
		this.dummyEntityItem = new EntityItem(null);
	}

	@SideOnly(Side.CLIENT)
	public void renderInWorld(ItemStack itemStack, World world, double x, double y, double z) {
		if (!itemStack.isEmpty() && world != null) {
			dummyEntityItem.world = world;

			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(x, y, z);
				dummyEntityItem.setItem(itemStack);

				if (world.getTotalWorldTime() != lastTick) {
					lastTick = world.getTotalWorldTime();
					dummyEntityItem.onUpdate();
				}

				RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
				rendermanager.renderEntity(dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);

				//				rendermanager.renderEntity(dummyEntityItem, 0, 2, 0);
			}
			GlStateManager.popMatrix();

			dummyEntityItem.world = null; // prevent leaking the world object
		}
	}
}
