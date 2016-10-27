package binnie.genetics.machine;

import org.lwjgl.opengl.GL11;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import binnie.genetics.Genetics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineRendererLab {
	public static MachineRendererLab instance = new MachineRendererLab();
	private final EntityItem dummyEntityItem;
	private final EntityItem[] itemSides;
	private long lastTick;

	public MachineRendererLab() {
		World world = Minecraft.getMinecraft().theWorld;
		this.dummyEntityItem = new EntityItem(world);
		this.itemSides = new EntityItem[]{new EntityItem(world), new EntityItem(world), new EntityItem(world), new EntityItem(world)};
	}

	public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glPushMatrix();
		int i1 = 0;
		BlockPos pos = machine.getTileEntity().getPos();
		int ix = pos.getX();
		int iy = pos.getY();
		int iz = pos.getZ();
		if (machine.getTileEntity() != null) {
			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
		}
		float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		World world = machine.getWorld();
		LaboratoryMachine.ComponentGUIHolder holder = Machine.getInterface(LaboratoryMachine.ComponentGUIHolder.class, machine);
		Label_0591: {
			if (world != null && holder != null && holder.getStack() != null) {
				BinnieCore.proxy.getMinecraftInstance();
				if (Minecraft.isFancyGraphicsEnabled()) {
					final ItemStack stack = holder.getStack();
					this.dummyEntityItem.worldObj = world;
					this.dummyEntityItem.setEntityItemStack(stack);
					if (world.getTotalWorldTime() != this.lastTick) {
						this.lastTick = world.getTotalWorldTime();
						this.dummyEntityItem.onUpdate();
					}
					GL11.glPushMatrix();
					final EntityPlayer player = BinnieCore.proxy.getPlayer();
					final double dx = ix + 0.5 - player.lastTickPosX;
					final double dz = iz + 0.5 - player.lastTickPosZ;
					final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
					GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
					GL11.glTranslatef(0.0f, 0.0f, -0.55f);
					GL11.glRotatef(90.0f + (float) (-t), 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(0.0f, -0.125f, 0.0f);
					GL11.glScalef(1.2f, 1.2f, 1.2f);
					GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
					GL11.glTranslatef(0.0f, 0.1f, 0.1f);
					RenderManager renderManager = BinnieCore.proxy.getMinecraftInstance().getRenderManager();
					renderManager.doRenderEntity(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
					GL11.glPopMatrix();
					int rot = 0;
					for (EntityItem item : this.itemSides) {
						GL11.glPushMatrix();
						item.worldObj = world;
						item.setEntityItemStack(stack);
						GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
						GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
						GL11.glTranslated(0.0, -1.13, 0.4);
						GL11.glScalef(0.8f, 0.8f, 0.8f);
						renderManager.doRenderEntity(item, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
						rot += 90;
						GL11.glPopMatrix();
					}
					break Label_0591;
				}
			}
			this.dummyEntityItem.setEntityItemStack((ItemStack) null);
			for (final EntityItem item2 : this.itemSides) {
				item2.setEntityItemStack((ItemStack) null);
			}
		}
		GL11.glPopMatrix();
	}

}
