package binnie.genetics.machine.polymeriser;

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

public class PolymeriserFX extends MachineComponent implements IRender.DisplayTick {
	public PolymeriserFX(final IMachine machine) {
		super(machine);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		final int tick = (int) (world.getTotalWorldTime() % 8L);
		if ((tick == 0 || tick == 3) && this.getUtil().getProcess().isInProgress()) {
			PolymeriserParticle polymeriserParticle = new PolymeriserParticle(world, pos);
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(polymeriserParticle);
		}
	}

	@SideOnly(Side.CLIENT)
	private static class PolymeriserParticle extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public PolymeriserParticle(World world, BlockPos pos) {
			super(world, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			this.axisX = this.posX;
			this.axisZ = this.posZ;
			this.angle = 0.7 + (int) (this.world.getTotalWorldTime() % 2L) * 3.1415;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = -0.006;
			this.particleMaxAge = 140;
			this.particleGravity = 0.0f;
			this.canCollide = false;
			this.setRBGColorF(0.8f, 0.0f, 1.0f);
			onUpdate();
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.angle += 0.1;
			this.motionY = -0.006;
			double dist = 0.2;
			if (this.particleAge > 60) {
				if (this.particleAge > 120) {
					dist = 0.1;
				} else {
					dist = 0.2 - 0.1 * ((this.particleAge - 60.0f) / 60.0f);
				}
			}
			this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
			if (this.particleAge <= 40) {
				this.setAlphaF(this.particleAge / 40.0f);
			}
			if (this.particleAge > 80) {
				this.setRBGColorF(this.particleRed + (0.0f - this.particleRed) / 10.0f, this.particleGreen + (1.0f - this.particleGreen) / 10.0f, this.particleBlue + (1.0f - this.particleBlue) / 10.0f);
			}
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}
}
