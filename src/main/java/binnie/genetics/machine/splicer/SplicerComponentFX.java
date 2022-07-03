package binnie.genetics.machine.splicer;

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

public class SplicerComponentFX extends MachineComponent
        implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
    private EntityItem dummyEntityItem;

    public SplicerComponentFX(IMachine machine) {
        super(machine);
        dummyEntityItem = new EntityItem(null);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {}

    @SideOnly(Side.CLIENT)
    @Override
    public void onDisplayTick(World world, int x, int y, int z, Random rand) {
        int tick = (int) (world.getTotalWorldTime() % 3L);
        if (tick != 0 || !getUtil().getProcess().isInProgress()) {
            return;
        }

        BinnieCore.proxy
                .getMinecraftInstance()
                .effectRenderer
                .addEffect(new EntityFX(world, x + 0.5, y + 1.5, z + 0.5, 0.0, 0.0, 0.0) {
                    double axisX = posX;
                    double axisZ = posZ;
                    double angle = (int) (worldObj.getTotalWorldTime() % 4L) * 0.5 * 3.1415;

                    {
                        axisX = 0.0;
                        axisZ = 0.0;
                        angle = 0.0;
                        motionX = 0.0;
                        motionZ = 0.0;
                        motionY = (rand.nextDouble() - 0.5) * 0.02;
                        particleMaxAge = 240;
                        particleGravity = 0.0f;
                        noClip = true;
                        setRBGColorF(0.3f + rand.nextFloat() * 0.5f, 0.3f + rand.nextFloat() * 0.5f, 0.0f);
                    }

                    @Override
                    public void onUpdate() {
                        super.onUpdate();
                        double speed = 0.04;
                        angle -= speed;
                        double dist = 0.25 + 0.2 * Math.sin(particleAge / 50.0f);
                        setPosition(axisX + dist * Math.sin(angle), posY, axisZ + dist * Math.cos(angle));
                        setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
                    }

                    @Override
                    public int getFXLayer() {
                        return 0;
                    }
                });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInWorld(RenderItem renderItem, double x, double y, double z) {
        if (!getUtil().getProcess().isInProgress()) {
            return;
        }

        ItemStack stack = getUtil().getStack(Splicer.SLOT_TARGET);
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
        ItemStack stack = getUtil().getStack(Splicer.SLOT_TARGET);
        if (stack != null) {
            stack.writeToNBT(item);
            nbt.setTag("item", item);
        }
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("item")) {
            getUtil().setStack(Splicer.SLOT_TARGET, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
        } else {
            getUtil().setStack(Splicer.SLOT_TARGET, null);
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
