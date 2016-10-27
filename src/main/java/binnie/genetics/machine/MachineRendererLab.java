package binnie.genetics.machine;

import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineRendererLab {
	public static MachineRendererLab instance = new MachineRendererLab();
	private final EntityItem dummyEntityItem;
	private final EntityItem[] itemSides;
	//	private final RenderItem customRenderItem;
	private long lastTick;
	private ModelMachine model;

	public MachineRendererLab() {
		World world = Minecraft.getMinecraft().theWorld;
		this.dummyEntityItem = new EntityItem(world);
		this.itemSides = new EntityItem[]{new EntityItem(world), new EntityItem(world), new EntityItem(world), new EntityItem(world)};
		this.model = new ModelMachine();
//		(this.customRenderItem = new RenderItem() {
//			@Override
//			public boolean shouldBob() {
//				return false;
//			}
//
//			@Override
//			public boolean shouldSpreadItems() {
//				return false;
//			}
//		}).setRenderManager(RenderManager.instance);
	}

	public void renderMachine(final Machine machine, final int colour, final BinnieResource texture, final double x, final double y, final double z, final float var8) {
//		GL11.glPushMatrix();
//		int i1 = 0;
//		final int ix = machine.getTileEntity().xCoord;
//		final int iy = machine.getTileEntity().yCoord;
//		final int iz = machine.getTileEntity().zCoord;
//		if (machine.getTileEntity() != null) {
//			i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
//		}
//		final float phase = (float) Math.max(0.0, Math.sin((System.currentTimeMillis() + i1) * 0.003));
//		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
//		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
//		BinnieCore.proxy.bindTexture(texture);
//		GL11.glPushMatrix();
//		this.model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
//		GL11.glPopMatrix();
//		final World world = machine.getWorld();
//		final LaboratoryMachine.ComponentGUIHolder holder = Machine.getInterface(LaboratoryMachine.ComponentGUIHolder.class, machine);
//		Label_0591: {
//			if (world != null && holder != null && holder.getStack() != null) {
//				BinnieCore.proxy.getMinecraftInstance();
//				if (Minecraft.isFancyGraphicsEnabled()) {
//					final ItemStack stack = holder.getStack();
//					this.dummyEntityItem.worldObj = world;
//					this.dummyEntityItem.setEntityItemStack(stack);
//					if (world.getTotalWorldTime() != this.lastTick) {
//						this.lastTick = world.getTotalWorldTime();
//						this.dummyEntityItem.onUpdate();
//					}
//					this.dummyEntityItem.age = 0;
//					this.dummyEntityItem.hoverStart = 0.0f;
//					GL11.glPushMatrix();
//					final EntityPlayer player = BinnieCore.proxy.getPlayer();
//					final double dx = ix + 0.5 - player.lastTickPosX;
//					final double dz = iz + 0.5 - player.lastTickPosZ;
//					final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
//					GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
//					GL11.glTranslatef(0.0f, 0.0f, -0.55f);
//					GL11.glRotatef(90.0f + (float) (-t), 0.0f, 0.0f, 1.0f);
//					GL11.glTranslatef(0.0f, -0.125f, 0.0f);
//					GL11.glScalef(1.2f, 1.2f, 1.2f);
//					GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
//					GL11.glTranslatef(0.0f, 0.1f, 0.1f);
//					this.customRenderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
//					GL11.glPopMatrix();
//					int rot = 0;
//					for (final EntityItem item : this.itemSides) {
//						GL11.glPushMatrix();
//						item.worldObj = world;
//						item.setEntityItemStack(stack);
//						item.age = 0;
//						item.hoverStart = 0.0f;
//						GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
//						GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
//						GL11.glTranslated(0.0, -1.13, 0.4);
//						GL11.glScalef(0.8f, 0.8f, 0.8f);
//						this.customRenderItem.doRender(item, 0.0, 0.0, 0.0, 0.0f, 0.0f);
//						rot += 90;
//						GL11.glPopMatrix();
//					}
//					break Label_0591;
//				}
//			}
//			this.dummyEntityItem.setEntityItemStack((ItemStack) null);
//			for (final EntityItem item2 : this.itemSides) {
//				item2.setEntityItemStack((ItemStack) null);
//			}
//		}
//		GL11.glPopMatrix();
	}

}
