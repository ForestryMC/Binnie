package binnie.genetics.machine.sequencer;

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
import binnie.core.resource.BinnieSprite;

public class SequencerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
	public SequencerFX(final IMachine machine) {
		super(machine);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onRandomDisplayTick(World world, BlockPos pos, Random rand) {
		if (!this.getUtil().getProcess().isInProgress()) {
			return;
		}
		Particle particle = new SequencerParticleRandomTick(world, pos, rand);
		BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		final int ticks = (int) (world.getTotalWorldTime() % 16L);
		if (ticks == 0 && this.getUtil().getProcess().isInProgress()) {
			Particle particle = new SequencerParticle(world, pos);
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(particle);
		}
	}

	@SideOnly(Side.CLIENT)
	private static class SequencerParticleRandomTick extends Particle {
		private double axisX;
		private double axisZ;
		private double angle;

		public SequencerParticleRandomTick(World world, BlockPos pos, Random rand) {
			super(world, pos.getX() + 0.5, pos.getY() + 1.2 + rand.nextDouble() * 0.2, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			this.axisX = this.posX;
			this.axisZ = this.posZ;
			this.angle = this.rand.nextDouble() * 2.0 * 3.1415;
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = 0.0;
			this.particleMaxAge = 200;
			this.particleGravity = 0.0f;
			this.canCollide = true;
			this.setRBGColorF(0.6f + this.rand.nextFloat() * 0.2f, 1.0f, 0.8f * this.rand.nextFloat() * 0.2f);
			this.onUpdate();
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.angle += 0.03;
			this.setPosition(this.axisX + 0.4 * Math.sin(this.angle), this.posY, this.axisZ + 0.4 * Math.cos(this.angle));
			this.motionY = 0.0;
			this.setAlphaF((float) Math.sin(3.14 * this.particleAge / this.particleMaxAge));
		}

		@Override
		public int getFXLayer() {
			return 0;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class SequencerParticle extends Particle {
		private static final BinnieSprite[] SPRITES;

		static {
			SPRITES = new BinnieSprite[]{Sequencer.fxSeqA, Sequencer.fxSeqG, Sequencer.fxSeqC, Sequencer.fxSeqT};
		}

		public SequencerParticle(World world, BlockPos pos) {
			super(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
			this.motionX = 0.0;
			this.motionZ = 0.0;
			this.motionY = 0.012;
			this.particleMaxAge = 50;
			this.particleGravity = 0.0f;
			this.canCollide = true;
			this.particleScale = 2.0f;
			setParticleTexture(SPRITES[this.rand.nextInt(4)].getSprite());
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.motionY = 0.012;
			if (this.particleAge > 40) {
				this.setAlphaF((50 - this.particleAge) / 10.0f);
			}
		}

		@Override
		public int getFXLayer() {
			return 1;
		}
	}
}
