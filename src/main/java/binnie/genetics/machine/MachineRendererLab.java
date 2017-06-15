package binnie.genetics.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;

@SideOnly(Side.CLIENT)
public class MachineRendererLab {
	public static MachineRendererLab instance = new MachineRendererLab();
	private final EntityItem dummyEntityItem;
	private final EntityItem[] itemSides;
	private long lastTick;

	public MachineRendererLab() {
		World world = Minecraft.getMinecraft().world;
		this.dummyEntityItem = new EntityItem(world);
		this.itemSides = new EntityItem[]{new EntityItem(world), new EntityItem(world), new EntityItem(world), new EntityItem(world)};
	}

	public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		int i1 = 0;
		BlockPos pos = machine.getTileEntity().getPos();
		int ix = pos.getX();
		int iy = pos.getY();
		int iz = pos.getZ();
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
		World world = machine.getWorld();
		LaboratoryMachine.ComponentGUIHolder holder = Machine.getInterface(LaboratoryMachine.ComponentGUIHolder.class, machine);
		Label_0591:
		{
			if (world != null && holder != null && !holder.getStack().isEmpty()) {
				BinnieCore.getBinnieProxy().getMinecraftInstance();
				if (Minecraft.isFancyGraphicsEnabled()) {
					final ItemStack stack = holder.getStack();
					this.dummyEntityItem.world = world;
					this.dummyEntityItem.setEntityItemStack(stack);
					if (world.getTotalWorldTime() != this.lastTick) {
						this.lastTick = world.getTotalWorldTime();
						this.dummyEntityItem.onUpdate();
					}
					GlStateManager.pushMatrix();
					final EntityPlayer player = BinnieCore.getBinnieProxy().getPlayer();
					final double dx = ix + 0.5 - player.lastTickPosX;
					final double dz = iz + 0.5 - player.lastTickPosZ;
					final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
					GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
					GlStateManager.translate(0.0f, 0.0f, -0.55f);
					GlStateManager.rotate(90.0f + (float) (-t), 0.0f, 0.0f, 1.0f);
					GlStateManager.translate(0.0f, -0.125f, 0.0f);
					GlStateManager.scale(1.2f, 1.2f, 1.2f);
					GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
					GlStateManager.translate(0.0f, 0.1f, 0.1f);
					RenderManager renderManager = BinnieCore.getBinnieProxy().getMinecraftInstance().getRenderManager();
					renderManager.doRenderEntity(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
					GlStateManager.popMatrix();
					int rot = 0;
					for (EntityItem item : this.itemSides) {
						GlStateManager.pushMatrix();
						item.world = world;
						item.setEntityItemStack(stack);
						GlStateManager.rotate(rot, 0.0f, 1.0f, 0.0f);
						GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
						GlStateManager.translate(0.0, -1.13, 0.4);
						GlStateManager.scale(0.8f, 0.8f, 0.8f);
						renderManager.doRenderEntity(item, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
						rot += 90;
						GlStateManager.popMatrix();
					}
					break Label_0591;
				}
			}
			this.dummyEntityItem.setEntityItemStack(ItemStack.EMPTY);
			for (final EntityItem item2 : this.itemSides) {
				item2.setEntityItemStack(ItemStack.EMPTY);
			}
		}
		GlStateManager.popMatrix();
	}
}
