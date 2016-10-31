package binnie.genetics.machine.analyser;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class AnalyserFX extends MachineComponent implements IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
	private final EntityItem dummyEntityItem;

	public AnalyserFX(final IMachine machine) {
		super(machine);
		this.dummyEntityItem = new EntityItem(machine.getWorld());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		if (rand.nextFloat() < 1.0f && this.getUtil().getProcess().isInProgress()) {
			BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new Particle(world, pos.getX() + 0.5, pos.getY() + 1.3 + rand.nextDouble() * 0.2, pos.getZ() + 0.5, 0.0, 0.0, 0.0) {
				double axisX = this.posX;
				double axisZ = this.posZ;
				double angle = this.rand.nextDouble() * 2.0 * 3.1415;

				{
					this.axisX = 0.0;
					this.axisZ = 0.0;
					this.angle = 0.0;
					this.motionX = 0.05 * (this.rand.nextDouble() - 0.5);
					this.motionZ = 0.05 * (this.rand.nextDouble() - 0.5);
					this.motionY = 0.0;
					this.particleMaxAge = 25;
					this.particleGravity = 0.05f;
					this.field_190017_n = true;
					this.setRBGColorF(0.6f, 0.0f, 1.0f);
				}

				@Override
				public void onUpdate() {
					super.onUpdate();
					this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
				}

				@Override
				public int getFXLayer() {
					return 0;
				}
			});
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInWorld(final double x, final double y, final double z) {
		if (!this.getUtil().getProcess().isInProgress()) {
			return;
		}
		final ItemStack stack = this.getUtil().getStack(6);
		this.dummyEntityItem.worldObj = this.getMachine().getWorld();
		this.dummyEntityItem.setEntityItemStack(stack);
		final EntityItem dummyEntityItem = this.dummyEntityItem;
		dummyEntityItem.setAgeToCreativeDespawnTime(); //++dummyEntityItem.age;
		this.dummyEntityItem.hoverStart = 0.0f;
		if (stack == null) {
			return;
		}
		final EntityPlayer player = BinnieCore.proxy.getPlayer();
		final double dx = x + 0.5 - player.lastTickPosX;
		final double dz = z + 0.5 - player.lastTickPosZ;
		final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
		GL11.glPushMatrix();
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.0f, -0.2f, 0.0f);
		BinnieCore.proxy.getMinecraftInstance().getRenderItem().renderItem(dummyEntityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);//doRender(this.dummyEntityItem.getEntityItem(), 0.0, 0.0, 0.0, 0.0f, 0.0f);
		GL11.glPopMatrix();
	}

	@Override
	public void syncToNBT(final NBTTagCompound nbt) {
		final NBTTagCompound item = new NBTTagCompound();
		final ItemStack stack = this.getUtil().getStack(6);
		if (stack != null) {
			stack.writeToNBT(item);
			nbt.setTag("item", item);
		}
	}

	@Override
	public void syncFromNBT(final NBTTagCompound nbt) {
		if (nbt.hasKey("item")) {
			this.getUtil().setStack(6, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
		} else {
			this.getUtil().setStack(6, null);
		}
	}

	@Override
	public void onInventoryUpdate() {
		if (this.getUtil().isServer()) {
			//TODO fix update
			//this.getMachine().getWorld().markBlockForUpdate(this.getMachine().getTileEntity().xCoord, this.getMachine().getTileEntity().yCoord, this.getMachine().getTileEntity().zCoord);
		}
	}
}
