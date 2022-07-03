package binnie.genetics.machine.genepool;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class GenepoolComponentFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
    public GenepoolComponentFX(IMachine machine) {
        super(machine);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
        // ignored
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextFloat() >= 1.0f || !getUtil().getProcess().isInProgress()) {
            return;
        }

        BinnieCore.proxy
                .getMinecraftInstance()
                .effectRenderer
                .addEffect(
                        new EntityFX(
                                world,
                                x + 0.3 + rand.nextDouble() * 0.4,
                                y + 1,
                                z + 0.3 + rand.nextDouble() * 0.4,
                                0.0,
                                0.0,
                                0.0) {
                            double axisX = posX;
                            double axisZ = posZ;
                            double angle = rand.nextDouble() * 2.0 * 3.1415;

                            {
                                axisX = 0.0;
                                axisZ = 0.0;
                                angle = 0.0;
                                motionX = 0.0;
                                motionZ = 0.0;
                                motionY = rand.nextFloat() * 0.01;
                                particleMaxAge = 25;
                                particleGravity = 0.0f;
                                noClip = true;
                                setRBGColorF(
                                        0.4f + 0.6f * rand.nextFloat(),
                                        0.6f * rand.nextFloat(),
                                        0.6f + 0.4f * rand.nextFloat());
                            }

                            @Override
                            public void onUpdate() {
                                super.onUpdate();
                                setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
                            }

                            @Override
                            public int getFXLayer() {
                                return 0;
                            }
                        });
    }
}
