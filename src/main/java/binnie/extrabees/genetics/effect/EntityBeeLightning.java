package binnie.extrabees.genetics.effect;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBeeLightning extends EntityLightningBolt {
    int lightningState;
    int boltLivingTime;

    public EntityBeeLightning(World world, double x, double y, double z) {
        super(world, x, y, z);
        lightningState = 2;
        boltLivingTime = rand.nextInt(3) + 1;
    }

    @Override
    public void onUpdate() {
        onEntityUpdate();
        lightningState--;

        if (lightningState < 0) {
            if (boltLivingTime == 0) {
                setDead();
            } else if (lightningState < -rand.nextInt(10)) {
                boltLivingTime--;
                lightningState = 1;
                boltVertex = rand.nextLong();

                if (!worldObj.isRemote
                        && worldObj.doChunksNearChunkExist(
                                MathHelper.floor_double(posX),
                                MathHelper.floor_double(posY),
                                MathHelper.floor_double(posZ),
                                10)) {
                    int i = MathHelper.floor_double(posX);
                    int j = MathHelper.floor_double(posY);
                    int k = MathHelper.floor_double(posZ);
                    if (worldObj.getBlock(i, j, k) == null && Blocks.fire.canPlaceBlockAt(worldObj, i, j, k)) {
                        worldObj.setBlock(i, j, k, Blocks.fire);
                    }
                }
            }
        }

        if (lightningState >= 0) {
            if (worldObj.isRemote) {
                worldObj.lastLightningBolt = 2;
            } else {
                double offset = 3.0;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(
                        this,
                        AxisAlignedBB.getBoundingBox(
                                posX - offset,
                                posY - offset,
                                posZ - offset,
                                posX + offset,
                                posY + 6.0 + offset,
                                posZ + offset));
                for (Object aList : list) {
                    Entity entity = (Entity) aList;
                    entity.onStruckByLightning(this);
                }
            }
        }
    }
}
