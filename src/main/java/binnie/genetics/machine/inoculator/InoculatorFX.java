package binnie.genetics.machine.inoculator;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import net.minecraft.client.particle.Particle;
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

import java.util.Random;

public class InoculatorFX extends MachineComponent implements IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
	private final EntityItem dummyEntityItem;

	public InoculatorFX(final IMachine machine) {
		super(machine);
		this.dummyEntityItem = new EntityItem(machine.getWorld());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDisplayTick(World world, BlockPos pos, Random rand) {
		final int tick = (int) (world.getTotalWorldTime() % 3L);
		if (tick == 0 && this.getUtil().getProcess().isInProgress()) {
			BinnieCore.getBinnieProxy().getMinecraftInstance().effectRenderer.addEffect(new Particle(world, pos.getX() + 0.5, pos.getY() + 0.92, pos.getZ() + 0.5, 0.0, 0.0, 0.0) {
				double axisX = this.posX;
				double axisZ = this.posZ;
				double angle = (int) (this.world.getTotalWorldTime() % 4L) * 0.5 * 3.1415;

				{
					this.axisX = 0.0;
					this.axisZ = 0.0;
					this.angle = 0.0;
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
					this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
					if (this.particleAge > 40) {
						this.setRBGColorF(this.particleRed + (1.0f - this.particleRed) / 10.0f, this.particleGreen + (0.0f - this.particleGreen) / 10.0f, this.particleBlue + (0.0f - this.particleBlue) / 10.0f);
					}
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
		final ItemStack stack = this.getUtil().getStack(9);
		this.dummyEntityItem.world = this.getMachine().getWorld();
		this.dummyEntityItem.setEntityItemStack(stack);
		final EntityItem dummyEntityItem = this.dummyEntityItem;
		dummyEntityItem.setAgeToCreativeDespawnTime(); //			++dummyEntityItem.age;
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
		GlStateManager.translate(0.0f, -0.25f, 0.0f);
		BinnieCore.getBinnieProxy().getMinecraftInstance().getRenderItem().renderItem(this.dummyEntityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED); //, 0.0, 0.0, 0.0, 0.0f, 0.0f);
		GlStateManager.popMatrix();
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
}
