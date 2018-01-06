package binnie.genetics.machine.analyser;

import java.util.Random;

import binnie.core.util.EntityItemRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
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
	private final EntityItemRenderer entityItemRenderer;

	public AnalyserFX(final IMachine machine) {
		super(machine);
		this.entityItemRenderer = new EntityItemRenderer();
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
		final ItemStack itemstack = this.getUtil().getStack(6);
		World world = this.getMachine().getWorld();
		this.entityItemRenderer.renderInWorld(itemstack, world,  x + 0.5f, y + 0.8f, z + 0.5f);
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
		public AnalyserParticle(World world, BlockPos pos, Random rand) {
			super(world, pos.getX() + 0.5, pos.getY() + 1.3 + rand.nextDouble() * 0.2, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
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
