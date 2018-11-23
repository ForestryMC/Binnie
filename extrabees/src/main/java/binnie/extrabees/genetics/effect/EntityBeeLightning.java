package binnie.extrabees.genetics.effect;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBeeLightning extends EntityLightningBolt {

	private int lightningState;
	private int boltLivingTime;

	public EntityBeeLightning(World world) {
		super(world, 0, 0, 0, true);
	}

	public EntityBeeLightning(World world, double x, double y, double z) {
		super(world, x, y, z, false);
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
				int i = MathHelper.floor(this.posX);
				int j = MathHelper.floor(this.posY);
				int k = MathHelper.floor(this.posZ);
				BlockPos pos = new BlockPos(i, j, k);
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
				double d0 = 3.0;
				List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0 + d0, this.posZ + d0));
				for (Entity entity : list) {
					entity.onStruckByLightning(this);
				}
			}
		}
	}
}
