package binnie.extrabees.genetics.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityBeeLightning extends EntityLightningBolt {
	int lightningState;
	int boltLivingTime;

	@SuppressWarnings("unused")
	public EntityBeeLightning(final World world) {
		super(world, 0, 0, 0, true);
	}

	public EntityBeeLightning(final World par1World, final double par2, final double par4, final double par6) {
		super(par1World, par2, par4, par6, false);
		this.lightningState = 2;
		this.boltLivingTime = this.rand.nextInt(3) + 1;
	}

	@Override
	public void onUpdate() {
		this.onEntityUpdate();
		--this.lightningState;
		if (this.lightningState < 0) {
			if (this.boltLivingTime == 0) {
				this.setDead();
			} else if (this.lightningState < -this.rand.nextInt(10)) {
				--this.boltLivingTime;
				this.lightningState = 1;
				this.boltVertex = this.rand.nextLong();
				final int i = MathHelper.floor(this.posX);
				final int j = MathHelper.floor(this.posY);
				final int k = MathHelper.floor(this.posZ);
				final BlockPos pos = new BlockPos(i, j, k);
				if (!this.world.isRemote && this.world.isAreaLoaded(pos, 10)) {
					if (this.world.isAirBlock(pos) && Blocks.FIRE.canPlaceBlockAt(this.world, pos)) {
						this.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}
		if (this.lightningState >= 0) {
			if (this.world.isRemote) {
				this.world.setLastLightningBolt(2);
			} else {
				final double d0 = 3.0;
				final List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0 + d0, this.posZ + d0));
				for (Entity entity : list) {
					entity.onStruckByLightning(this);
				}
			}
		}
	}
}
