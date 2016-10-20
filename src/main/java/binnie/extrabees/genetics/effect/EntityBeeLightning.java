// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.genetics.effect;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.effect.EntityLightningBolt;

public class EntityBeeLightning extends EntityLightningBolt
{
	int lightningState;
	int boltLivingTime;

	public EntityBeeLightning(final World par1World, final double par2, final double par4, final double par6) {
		super(par1World, par2, par4, par6);
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
			}
			else if (this.lightningState < -this.rand.nextInt(10)) {
				--this.boltLivingTime;
				this.lightningState = 1;
				this.boltVertex = this.rand.nextLong();
				if (!this.worldObj.isRemote && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10)) {
					final int i = MathHelper.floor_double(this.posX);
					final int j = MathHelper.floor_double(this.posY);
					final int k = MathHelper.floor_double(this.posZ);
					if (this.worldObj.getBlock(i, j, k) == null && Blocks.fire.canPlaceBlockAt(this.worldObj, i, j, k)) {
						this.worldObj.setBlock(i, j, k, Blocks.fire);
					}
				}
			}
		}
		if (this.lightningState >= 0) {
			if (this.worldObj.isRemote) {
				this.worldObj.lastLightningBolt = 2;
			}
			else {
				final double d0 = 3.0;
				final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0 + d0, this.posZ + d0));
				for (int l = 0; l < list.size(); ++l) {
					final Entity entity = (Entity) list.get(l);
					entity.onStruckByLightning(this);
				}
			}
		}
	}
}
