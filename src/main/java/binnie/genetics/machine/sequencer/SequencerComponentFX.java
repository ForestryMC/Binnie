package binnie.genetics.machine.sequencer;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.resource.BinnieIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class SequencerComponentFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
    public SequencerComponentFX(IMachine machine) {
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
                .addEffect(new EntityFX(world, x + 0.5, y + 1.2 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
                    double axisX = posX;
                    double axisZ = posZ;
                    double angle = rand.nextDouble() * 2.0 * 3.1415;

                    {
                        axisX = 0.0;
                        axisZ = 0.0;
                        angle = 0.0;
                        motionX = 0.0;
                        motionZ = 0.0;
                        motionY = 0.0;
                        particleMaxAge = 200;
                        particleGravity = 0.0f;
                        noClip = true;
                        setRBGColorF(0.6f + rand.nextFloat() * 0.2f, 1.0f, 0.8f * rand.nextFloat() * 0.2f);
                    }

                    @Override
                    public void onUpdate() {
                        super.onUpdate();
                        angle += 0.03;
                        setPosition(axisX + 0.4 * Math.sin(angle), posY, axisZ + 0.4 * Math.cos(angle));
                        motionY = 0.0;
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
        int ticks = (int) (world.getTotalWorldTime() % 16L);
        if (ticks != 0 || !getUtil().getProcess().isInProgress()) {
            return;
        }

        BinnieCore.proxy
                .getMinecraftInstance()
                .effectRenderer
                .addEffect(new EntityFX(world, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0) {
                    {
                        motionX = 0.0;
                        motionZ = 0.0;
                        motionY = 0.012;
                        particleMaxAge = 50;
                        particleGravity = 0.0f;
                        noClip = true;
                        particleScale = 2.0f;

                        setParticleIcon((new BinnieIcon[] {
                                    Sequencer.fxSeqA, Sequencer.fxSeqG, Sequencer.fxSeqC, Sequencer.fxSeqT
                                })
                                [rand.nextInt(4)].getIcon());
                    }

                    @Override
                    public void onUpdate() {
                        super.onUpdate();
                        motionY = 0.012;
                        if (particleAge > 40) {
                            setAlphaF((50 - particleAge) / 10.0f);
                        }
                    }

                    @Override
                    public int getFXLayer() {
                        return 1;
                    }
                });
    }
}
