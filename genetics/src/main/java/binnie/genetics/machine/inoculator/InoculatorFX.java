package binnie.genetics.machine.inoculator;

import java.util.Random;

import binnie.core.util.EntityItemRenderer;
import net.minecraft.client.particle.Particle;
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

public class InoculatorFX extends MachineComponent implements IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
	private final EntityItemRenderer entityItemRenderer;

	public InoculatorFX(final IMachine machine) {
		super(machine);
		this.entityItemRenderer = new EntityItemRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		if (!this.getUtil().getProcess().isInProgress()) return;
		final int tick = (int) (world.getTotalWorldTime() % 3L);
		if (tick == 0) {
			final Particle particle = new InoculatorParticle(world, pos);
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInWorld(final double x, final double y, final double z) {
		if (!this.getUtil().getProcess().isInProgress()) {
			return;
		}
		final ItemStack stack = this.getUtil().getStack(9);
		World world = this.getMachine().getWorld();
		this.entityItemRenderer.renderInWorld(stack, world, x + 0.5, y + 0.8, z + 0.5);
	}

	@Override
	public void syncToNBT(final NBTTagCompound nbt) {
		final NBTTagCompound item = new NBTTagCompound();
		final ItemStack stack = this.getUtil().getStack(9);
		if (!stack.isEmpty()) {
			stack.writeToNBT(item);
			nbt.setTag("item", item);
		}
	}

	@Override
	public void syncFromNBT(final NBTTagCompound nbt) {
		if (nbt.hasKey("item")) {
			this.getUtil().setStack(9, new ItemStack(nbt.getCompoundTag("item")));
		} else {
			this.getUtil().setStack(9, ItemStack.EMPTY);
		}
	}

	@Override
	public void onInventoryUpdate() {
		if (!this.getUtil().isServer()) {
			return;
		}
		this.getUtil().refreshBlock();
	}

	@SideOnly(Side.CLIENT)
	private static class InoculatorParticle extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public InoculatorParticle(World world, BlockPos pos) {
			super(world, pos.getX() + 0.5, pos.getY() + 0.92, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			this.axisX = this.posX;
			this.axisZ = this.posZ;
			this.angle = (int) (this.world.getTotalWorldTime() % 4L) * 0.5 * Math.PI;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = 0.007 + this.rand.nextDouble() * 0.002;
			this.particleMaxAge = 240;
			this.particleGravity = 0.0f;
			this.canCollide = true;
			this.setRBGColorF(0.8f, 0.0f, 1.0f);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			double speed = 5.0E-4;
			if (this.particleAge > 60) {
				speed += (this.particleAge - 60) / 4000.0f;
			}
			this.angle += speed;
			final double dist = 0.27;
			this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
			this.setAlphaF((float) Math.cos(Math.PI / 2f * this.particleAge / this.particleMaxAge));
			if (this.particleAge > 40) {
				this.setRBGColorF(this.particleRed + (1.0f - this.particleRed) / 10.0f, this.particleGreen + (0.0f - this.particleGreen) / 10.0f, this.particleBlue + (0.0f - this.particleBlue) / 10.0f);
			}
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}
}
