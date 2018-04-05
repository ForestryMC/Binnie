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

public class IsolatorFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
	public IsolatorFX(final IMachine machine) {
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
		if (!this.getUtil().getProcess().isInProgress()) return;
		final int tick = (int) (world.getTotalWorldTime() % 6L);
		if (tick == 0 || tick == 5) {
			final Particle particle = new IsolatorParticle(world, pos);
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
		}
	}

	@SideOnly(Side.CLIENT)
	private static class IsolaterParticleRandomTick extends Particle {
		public IsolaterParticleRandomTick(World world, BlockPos pos, Random rand) {
			super(world, pos.getX() + 0.4 + 0.2 * rand.nextDouble(), pos.getY() + 1.6, pos.getZ() + 0.4 + rand.nextDouble() * 0.2, 0.0, 0.0, 0.0);
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = -0.012;
			this.particleMaxAge = 100;
			this.particleGravity = 0.0f;
			this.canCollide = false;
			this.setRBGColorF(0.8f, 0.4f, 0.0f);
			this.setAlphaF((float) Math.sin(Math.PI * this.particleAge / this.particleMaxAge));
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.setAlphaF((float) Math.sin(Math.PI * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class IsolatorParticle extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public IsolatorParticle(World world, BlockPos pos) {
			super(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			this.axisX = this.posX;
			this.axisZ = this.posZ;
			this.angle = 0.7 + (int) (this.world.getTotalWorldTime() % 2L) * Math.PI;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = 0.012;
			this.particleMaxAge = 100;
			this.particleGravity = 0.0f;
			this.canCollide = false;
			this.setRBGColorF(0.8f, 0.0f, 1.0f);
			onUpdate();
			onUpdate();
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.angle += 0.06;
			this.setPosition(this.axisX + 0.26 * Math.sin(this.angle), this.posY, this.axisZ + 0.26 * Math.cos(this.angle));
			this.setAlphaF((float) Math.cos(Math.PI / 2f * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}
}
