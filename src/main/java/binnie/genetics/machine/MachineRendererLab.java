package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererLab {
    public static MachineRendererLab instance = new MachineRendererLab();

    private EntityItem dummyEntityItem;
    private EntityItem[] itemSides;
    private RenderItem customRenderItem;
    private long lastTick;
    private ModelMachine model;

    public MachineRendererLab() {
        dummyEntityItem = new EntityItem(null);
        itemSides =
                new EntityItem[] {new EntityItem(null), new EntityItem(null), new EntityItem(null), new EntityItem(null)
                };
        model = new ModelMachine();
        (customRenderItem = new RenderItem() {
                    @Override
                    public boolean shouldBob() {
                        return false;
                    }

                    @Override
                    public boolean shouldSpreadItems() {
                        return false;
                    }
                })
                .setRenderManager(RenderManager.instance);
    }

    public void renderMachine(
            Machine machine, int colour, BinnieResource texture, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        int ix = machine.getTileEntity().xCoord;
        int iz = machine.getTileEntity().zCoord;

        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        BinnieCore.proxy.bindTexture(texture);
        GL11.glPushMatrix();
        model.render((float) x, (float) y, (float) z, 0.0625f, 0.0625f, 0.0625f);
        GL11.glPopMatrix();
        World world = machine.getWorld();
        ComponentGUIHolder holder = Machine.getInterface(ComponentGUIHolder.class, machine);

        // TODO remove label
        Label_0591:
        {
            if (world != null && holder != null && holder.getStack() != null) {
                BinnieCore.proxy.getMinecraftInstance();
                if (Minecraft.isFancyGraphicsEnabled()) {
                    ItemStack stack = holder.getStack();
                    dummyEntityItem.worldObj = world;
                    dummyEntityItem.setEntityItemStack(stack);
                    if (world.getTotalWorldTime() != lastTick) {
                        lastTick = world.getTotalWorldTime();
                        dummyEntityItem.onUpdate();
                    }
                    dummyEntityItem.age = 0;
                    dummyEntityItem.hoverStart = 0.0f;
                    GL11.glPushMatrix();
                    EntityPlayer player = BinnieCore.proxy.getPlayer();
                    double dx = ix + 0.5 - player.lastTickPosX;
                    double dz = iz + 0.5 - player.lastTickPosZ;
                    double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.0f, -0.55f);
                    GL11.glRotatef(90.0f + (float) (-t), 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                    GL11.glScalef(1.2f, 1.2f, 1.2f);
                    GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.1f, 0.1f);
                    customRenderItem.doRender(dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                    GL11.glPopMatrix();
                    int rot = 0;

                    for (EntityItem item : itemSides) {
                        GL11.glPushMatrix();
                        item.worldObj = world;
                        item.setEntityItemStack(stack);
                        item.age = 0;
                        item.hoverStart = 0.0f;
                        GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
                        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                        GL11.glTranslated(0.0, -1.13, 0.4);
                        GL11.glScalef(0.8f, 0.8f, 0.8f);
                        customRenderItem.doRender(item, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                        rot += 90;
                        GL11.glPopMatrix();
                    }
                    break Label_0591;
                }
            }

            dummyEntityItem.setEntityItemStack(null);
            for (EntityItem item2 : itemSides) {
                item2.setEntityItemStack(null);
            }
        }
        GL11.glPopMatrix();
    }
}
