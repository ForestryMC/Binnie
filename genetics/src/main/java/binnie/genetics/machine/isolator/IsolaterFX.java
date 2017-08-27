package binnie.genetics.machine.isolator;

import java.util.Random;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;

public class IsolaterFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
	public IsolaterFX(final IMachine machine) {
		super(machine);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onRandomDisplayTick(World world, BlockPos pos, Random rand) {
		if (!this.getUtil().getProcess().isInProgress()) {
			return;
		}
		final Particle particle = new IsolaterParticleRandomTick(world, pos, rand);
		BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		final int tick = (int) (world.getTotalWorldTime() % 6L);
		if ((tick == 0 || tick == 5) && this.getUtil().getProcess().isInProgress()) {
			final Particle particle = new IsolaterParticle(world, pos);
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
		}
	}

	@SideOnly(Side.CLIENT)
	private static class IsolaterParticleRandomTick extends Particle {
		private final double axisX;
		private final double axisZ;
		private double angle;

		public IsolaterParticleRandomTick(World world, BlockPos pos, Random rand) {
			super(world, pos.getX() + 0.4 + 0.2 * rand.nextDouble(), pos.getY() + 1.6, pos.getZ() + 0.4 + rand.nextDouble() * 0.2, 0.0, 0.0, 0.0);
			axisX = 0.0;
			axisZ = 0.0;
			angle = 0.0;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = -0.012;
			this.particleMaxAge = 100;
			this.particleGravity = 0.0f;
			this.canCollide = true;
			this.setRBGColorF(0.8f, 0.4f, 0.0f);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.angle += 0.06;
			this.setAlphaF((float) Math.sin(3.14 * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class IsolaterParticle extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public IsolaterParticle(World world, BlockPos pos) {
			super(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			axisX = this.posX;
			axisZ = this.posZ;
			angle = 0.7 + (int) (this.world.getTotalWorldTime() % 2L) * 3.1415;
			this.axisX = 0.0;
			this.axisZ = 0.0;
			this.angle = 0.0;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = 0.012;
			this.particleMaxAge = 100;
			this.particleGravity = 0.0f;
			this.canCollide = true;
			this.setRBGColorF(0.8f, 0.0f, 1.0f);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.angle += 0.06;
			this.setPosition(this.axisX + 0.26 * Math.sin(this.angle), this.posY, this.axisZ + 0.26 * Math.cos(this.angle));
			this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}
}
