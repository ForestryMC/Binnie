package binnie.genetics.machine.analyser;

import java.util.Random;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;

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
			ParticleManager effectRenderer = BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer;
			effectRenderer.addEffect(new AnalyserParticle(world, pos, rand));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInWorld(final double x, final double y, final double z) {
		if (!this.getUtil().getProcess().isInProgress()) {
			return;
		}
		final ItemStack stack = this.getUtil().getStack(6);
		this.dummyEntityItem.world = this.getMachine().getWorld();
		this.dummyEntityItem.setItem(stack);
		final EntityItem dummyEntityItem = this.dummyEntityItem;
		dummyEntityItem.setAgeToCreativeDespawnTime(); //++dummyEntityItem.age;
		this.dummyEntityItem.hoverStart = 0.0f;
		if (stack.isEmpty()) {
			return;
		}
		final EntityPlayer player = BinnieCore.getBinnieProxy().getPlayer();
		final double dx = x + 0.5 - player.lastTickPosX;
		final double dz = z + 0.5 - player.lastTickPosZ;
		final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
		GlStateManager.translate(0.0f, -0.2f, 0.0f);
		BinnieCore.getBinnieProxy().getMinecraftInstance().getRenderItem().renderItem(dummyEntityItem.getItem(), ItemCameraTransforms.TransformType.FIXED);//doRender(this.dummyEntityItem.getEntityItem(), 0.0, 0.0, 0.0, 0.0f, 0.0f);
		GlStateManager.popMatrix();
	}

	@Override
	public void syncToNBT(final NBTTagCompound nbt) {
		final NBTTagCompound item = new NBTTagCompound();
		final ItemStack stack = this.getUtil().getStack(6);
		if (!stack.isEmpty()) {
			stack.writeToNBT(item);
			nbt.setTag("item", item);
		}
	}

	@Override
	public void syncFromNBT(final NBTTagCompound nbt) {
		if (nbt.hasKey("item")) {
			this.getUtil().setStack(6, new ItemStack(nbt.getCompoundTag("item")));
		} else {
			this.getUtil().setStack(6, ItemStack.EMPTY);
		}
	}

	@Override
	public void onInventoryUpdate() {
		if (this.getUtil().isServer()) {
			//TODO fix update
			//this.getMachine().getWorld().markBlockForUpdate(this.getMachine().getTileEntity().xCoord, this.getMachine().getTileEntity().yCoord, this.getMachine().getTileEntity().zCoord);
		}
	}

	private static class AnalyserParticle extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public AnalyserParticle(World world, BlockPos pos, Random rand) {
			super(world, pos.getX() + 0.5, pos.getY() + 1.3 + rand.nextDouble() * 0.2, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			axisX = this.posX;
			axisZ = this.posZ;
			angle = this.rand.nextDouble() * 2.0 * 3.1415;
			this.axisX = 0.0;
			this.axisZ = 0.0;
			this.angle = 0.0;
			this.motionX = 0.05 * (this.rand.nextDouble() - 0.5);
			this.motionZ = 0.05 * (this.rand.nextDouble() - 0.5);
			this.motionY = 0.0;
			this.particleMaxAge = 25;
			this.particleGravity = 0.05f;
			this.canCollide = true;
			this.setRBGColorF(0.6f, 0.0f, 1.0f);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onUpdate() {
			super.onUpdate();
			this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}
}
