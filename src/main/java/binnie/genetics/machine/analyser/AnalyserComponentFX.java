package binnie.genetics.machine.analyser;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class AnalyserComponentFX extends MachineComponent
        implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
    private EntityItem dummyEntityItem;

    public AnalyserComponentFX(IMachine machine) {
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
        if (rand.nextFloat() < 1.0f && getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy
                    .getMinecraftInstance()
                    .effectRenderer
                    .addEffect(new EntityFX(world, x + 0.5, y + 1.3 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
                        double axisX = posX;
                        double axisZ = posZ;
                        double angle = rand.nextDouble() * 2.0 * 3.1415;

                        {
                            axisX = 0.0;
                            axisZ = 0.0;
                            angle = 0.0;
                            motionX = 0.05 * (rand.nextDouble() - 0.5);
                            motionZ = 0.05 * (rand.nextDouble() - 0.5);
                            motionY = 0.0;
                            particleMaxAge = 25;
                            particleGravity = 0.05f;
                            noClip = true;
                            setRBGColorF(0.6f, 0.0f, 1.0f);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInWorld(RenderItem renderItem, double x, double y, double z) {
        if (!getUtil().getProcess().isInProgress()) {
            return;
        }

        ItemStack stack = getUtil().getStack(Analyser.SLOT_TARGET);
        dummyEntityItem.worldObj = getMachine().getWorld();
        dummyEntityItem.setEntityItemStack(stack);
        EntityItem dummyEntityItem = this.dummyEntityItem;
        dummyEntityItem.age++;
        this.dummyEntityItem.hoverStart = 0.0f;
        if (stack == null) {
            return;
        }

        EntityPlayer player = BinnieCore.proxy.getPlayer();
        double dx = x + 0.5 - player.lastTickPosX;
        double dz = z + 0.5 - player.lastTickPosZ;
        double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, -0.2f, 0.0f);
        renderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void syncToNBT(NBTTagCompound nbt) {
        NBTTagCompound item = new NBTTagCompound();
        ItemStack stack = getUtil().getStack(Analyser.SLOT_TARGET);
        if (stack == null) {
            return;
        }

        stack.writeToNBT(item);
        nbt.setTag("item", item);
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("item")) {
            getUtil().setStack(Analyser.SLOT_TARGET, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
        } else {
            getUtil().setStack(Analyser.SLOT_TARGET, null);
        }
    }

    @Override
    public void onInventoryUpdate() {
        if (!getUtil().isServer()) {
            return;
        }

        IMachine machine = getMachine();
        TileEntity tile = machine.getTileEntity();
        machine.getWorld().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
    }
}
