package binnie.genetics.machine.polymeriser;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class PolymeriserComponentFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
    public PolymeriserComponentFX(IMachine machine) {
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
        int tick = (int) (world.getTotalWorldTime() % 8L);
        if ((tick == 0 || tick == 3) && getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy
                    .getMinecraftInstance()
                    .effectRenderer
                    .addEffect(new EntityFX(world, x + 0.5, y + 1.8, z + 0.5, 0.0, 0.0, 0.0) {
                        double axisX = posX;
                        double axisZ = posZ;
                        double angle = 0.7 + (int) (worldObj.getTotalWorldTime() % 2L) * 3.1415;

                        {
                            axisX = 0.0;
                            axisZ = 0.0;
                            angle = 0.0;
                            motionX = 0.0;
                            motionZ = 0.0;
                            motionY = -0.006;
                            particleMaxAge = 140;
                            particleGravity = 0.0f;
                            noClip = true;
                            setRBGColorF(0.8f, 0.0f, 1.0f);
                        }

                        @Override
                        public void onUpdate() {
                            super.onUpdate();
                            angle += 0.1;
                            motionY = -0.006;
                            double dist = 0.2;
                            if (particleAge > 60) {
                                if (particleAge > 120) {
                                    dist = 0.1;
                                } else {
                                    dist = 0.2 - 0.1 * ((particleAge - 60.0f) / 60.0f);
                                }
                            }

                            setPosition(axisX + dist * Math.sin(angle), posY, axisZ + dist * Math.cos(angle));
                            if (particleAge <= 40) {
                                setAlphaF(particleAge / 40.0f);
                            }
                            if (particleAge > 80) {
                                setRBGColorF(
                                        particleRed + (0.0f - particleRed) / 10.0f,
                                        particleGreen + (1.0f - particleGreen) / 10.0f,
                                        particleBlue + (1.0f - particleBlue) / 10.0f);
                            }
                        }

                        @Override
                        public int getFXLayer() {
                            return 0;
                        }
                    });
        }
    }
}
