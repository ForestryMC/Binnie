package binnie.genetics.machine.isolator;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class IsolatorComponentFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
    public IsolatorComponentFX(IMachine machine) {
        super(machine);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (!getUtil().getProcess().isInProgress()) {
            return;
        }

        BinnieCore.proxy
                .getMinecraftInstance()
                .effectRenderer
                .addEffect(
                        new EntityFX(
                                world,
                                x + 0.4 + 0.2 * rand.nextDouble(),
                                y + 1.6,
                                z + 0.4 + rand.nextDouble() * 0.2,
                                0.0,
                                0.0,
                                0.0) {
                            double angle = 0.0;

                            {
                                motionX = 0.0;
                                motionZ = 0.0;
                                motionY = -0.012;
                                particleMaxAge = 100;
                                particleGravity = 0.0f;
                                noClip = true;
                                setRBGColorF(0.8f, 0.4f, 0.0f);
                            }

                            @Override
                            public void onUpdate() {
                                super.onUpdate();
                                angle += 0.06;
                                setAlphaF((float) Math.sin(3.14 * particleAge / particleMaxAge));
                            }

                            @Override
                            public int getFXLayer() {
                                return 0;
                            }
                        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDisplayTick(World world, int x, int y, int z, Random rand) {
        int tick = (int) (world.getTotalWorldTime() % 6L);
        if ((tick != 0 && tick != 5) || !getUtil().getProcess().isInProgress()) {
            return;
        }

        BinnieCore.proxy
                .getMinecraftInstance()
                .effectRenderer
                .addEffect(new EntityFX(world, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0) {
                    double axisX = posX;
                    double axisZ = posZ;
                    double angle = 0.7 + (int) (worldObj.getTotalWorldTime() % 2L) * 3.1415;

                    {
                        axisX = 0.0;
                        axisZ = 0.0;
                        angle = 0.0;
                        motionX = 0.0;
                        motionZ = 0.0;
                        motionY = 0.012;
                        particleMaxAge = 100;
                        particleGravity = 0.0f;
                        noClip = true;
                        setRBGColorF(0.8f, 0.0f, 1.0f);
                    }

                    @Override
                    public void onUpdate() {
                        super.onUpdate();
                        angle += 0.06;
                        setPosition(axisX + 0.26 * Math.sin(angle), posY, axisZ + 0.26 * Math.cos(angle));
                        setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
                    }

                    @Override
                    public int getFXLayer() {
                        return 0;
                    }
                });
    }
}
