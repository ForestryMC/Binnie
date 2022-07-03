package binnie.genetics.machine.inoculator;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class InoculatorComponentFX extends MachineComponent
        implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
    private EntityItem dummyEntityItem;

    public InoculatorComponentFX(IMachine machine) {
        super(machine);
        dummyEntityItem = new EntityItem(null);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
        // ignored
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDisplayTick(World world, int x, int y, int z, Random rand) {
        int tick = (int) (world.getTotalWorldTime() % 3L);
        if (tick == 0 && getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy
                    .getMinecraftInstance()
                    .effectRenderer
                    .addEffect(new EntityFX(world, x + 0.5, y + 0.92, z + 0.5, 0.0, 0.0, 0.0) {
                        double axisX = posX;
                        double axisZ = posZ;
                        double angle = (int) (worldObj.getTotalWorldTime() % 4L) * 0.5 * 3.1415;

                        {
                            axisX = 0.0;
                            axisZ = 0.0;
                            angle = 0.0;
                            motionX = 0.0;
                            motionZ = 0.0;
                            motionY = 0.007 + rand.nextDouble() * 0.002;
                            particleMaxAge = 240;
                            particleGravity = 0.0f;
                            noClip = true;
                            setRBGColorF(0.8f, 0.0f, 1.0f);
                        }

                        @Override
                        public void onUpdate() {
                            super.onUpdate();
                            double speed = 5.0E-4;
                            if (particleAge > 60) {
                                speed += (particleAge - 60) / 4000.0f;
                            }
                            angle += speed;
                            double dist = 0.27;
                            setPosition(axisX + dist * Math.sin(angle), posY, axisZ + dist * Math.cos(angle));
                            setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
                            if (particleAge > 40) {
                                setRBGColorF(
                                        particleRed + (1.0f - particleRed) / 10.0f,
                                        particleGreen + (0.0f - particleGreen) / 10.0f,
                                        particleBlue + (0.0f - particleBlue) / 10.0f);
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
    public void renderInWorld(RenderItem renderItem, double x, double y, double z) {
        if (!getUtil().getProcess().isInProgress()) {
            return;
        }

        ItemStack stack = getUtil().getStack(Inoculator.SLOT_TARGET);
        dummyEntityItem.worldObj = getMachine().getWorld();
        dummyEntityItem.setEntityItemStack(stack);
        EntityItem dummyEntityItem = this.dummyEntityItem;
        dummyEntityItem.age++;
        this.dummyEntityItem.hoverStart = 0.0f;
        if (stack == null) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, -0.25f, 0.0f);
        renderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void syncToNBT(NBTTagCompound nbt) {
        NBTTagCompound item = new NBTTagCompound();
        ItemStack stack = getUtil().getStack(Inoculator.SLOT_TARGET);
        if (stack != null) {
            stack.writeToNBT(item);
            nbt.setTag("item", item);
        }
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("item")) {
            getUtil().setStack(Inoculator.SLOT_TARGET, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
        } else {
            getUtil().setStack(Inoculator.SLOT_TARGET, null);
        }
    }

    @Override
    public void onInventoryUpdate() {
        if (!getUtil().isServer()) {
            return;
        }
        getUtil().refreshBlock();
    }
}
