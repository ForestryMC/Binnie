package binnie.craftgui.minecraft;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.PowerSystem;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.Renderer;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.StyleSheetManager;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Deque;

public abstract class Window extends TopLevelWidget implements INetwork.RecieveGuiNBT {
    private GuiCraftGUI gui;
    private ContainerCraftGUI container;
    private WindowInventory windowInventory;
    private ControlText title;
    protected float titleButtonLeft;
    protected float titleButtonRight;
    private StandardTexture bgText1;
    private StandardTexture bgText2;
    private boolean hasBeenInitialised;
    private EntityPlayer player;
    private IInventory entityInventory;
    private Side side;

    public void getTooltip(final Tooltip tooltip) {
        final Deque<IWidget> queue = this.calculateMousedOverWidgets();
        while (!queue.isEmpty()) {
            final IWidget widget = queue.removeFirst();
            if (widget.isEnabled() && widget.isVisible()) {
                if (!widget.calculateIsMouseOver()) {
                    continue;
                }
                if (widget instanceof ITooltip) {
                    ((ITooltip) widget).getTooltip(tooltip);
                    if (tooltip.exists()) {
                        return;
                    }
                }
                if (widget.hasAttribute(Attribute.BlockTooltip)) {
                    return;
                }
                continue;
            }
        }
    }

    public void getHelpTooltip(final MinecraftTooltip tooltip) {
        final Deque<IWidget> queue = this.calculateMousedOverWidgets();
        while (!queue.isEmpty()) {
            final IWidget widget = queue.removeFirst();
            if (widget.isEnabled() && widget.isVisible()) {
                if (!widget.calculateIsMouseOver()) {
                    continue;
                }
                if (widget instanceof ITooltipHelp) {
                    ((ITooltipHelp) widget).getHelpTooltip(tooltip);
                    if (tooltip.exists()) {
                        return;
                    }
                }
                if (widget.hasAttribute(Attribute.BlockTooltip)) {
                    return;
                }
                continue;
            }
        }
    }

    protected abstract AbstractMod getMod();

    protected abstract String getName();

    public BinnieResource getBackgroundTextureFile(final int i) {
        return Binnie.Resource.getPNG(this.getMod(), ResourceType.GUI, this.getName() + ((i == 1) ? "" : i));
    }

    public boolean showHelpButton() {
        return Machine.getInterface(IInventoryMachine.class, this.getInventory()) != null;
    }

    public String showInfoButton() {
        if (Machine.getInterface(IMachineInformation.class, this.getInventory()) != null) {
            return Machine.getInterface(IMachineInformation.class, this.getInventory()).getInformation();
        }
        return null;
    }

    public Window(final float width, final float height, final EntityPlayer player, final IInventory inventory, final Side side) {
        this.titleButtonLeft = 8.0f;
        this.titleButtonRight = 8.0f;
        this.bgText1 = null;
        this.bgText2 = null;
        this.hasBeenInitialised = false;
        this.side = Side.CLIENT;
        this.side = side;
        this.setInventories(player, inventory);
        this.container = new ContainerCraftGUI(this);
        this.windowInventory = new WindowInventory(this);
        if (side == Side.SERVER) {
            return;
        }
        this.setSize(new IPoint(width, height));
        this.gui = new GuiCraftGUI(this);
        for (final EnumHighlighting h : EnumHighlighting.values()) {
            ControlSlot.highlighting.put(h, new ArrayList<>());
        }
        (CraftGUI.Render = new Renderer(this.gui)).stylesheet(StyleSheetManager.getDefault());
        this.titleButtonLeft = -14.0f;
        if (this.showHelpButton()) {
            new ControlHelp(this, this.titleButtonLeft += 22.0f, 8.0f);
        }
        if (this.showInfoButton() != null) {
            new ControlInfo(this, this.titleButtonLeft += 22.0f, 8.0f, this.showInfoButton());
        }
        this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(final EventWidget.ChangeSize event) {
                if (Window.this.isClient() && Window.this.getGui() != null) {
                    Window.this.getGui().resize(Window.this.getSize());
                    if (Window.this.title != null) {
                        Window.this.title.setSize(new IPoint(Window.this.w(), Window.this.title.h()));
                    }
                }
            }
        });
    }

    public void setTitle(final String title) {
        (this.title = new ControlTextCentered(this, 12.0f, title)).setColour(4210752);
    }

    @SideOnly(Side.CLIENT)
    public final GuiCraftGUI getGui() {
        return this.gui;
    }

    public final ContainerCraftGUI getContainer() {
        return this.container;
    }

    public final WindowInventory getWindowInventory() {
        return this.windowInventory;
    }

    public final void initGui() {
        if (this.hasBeenInitialised) {
            return;
        }
        this.bgText1 = new StandardTexture(0, 0, 256, 256, this.getBackgroundTextureFile(1));
        if (this.getSize().x() > 256.0f) {
            this.bgText2 = new StandardTexture(0, 0, 256, 256, this.getBackgroundTextureFile(2));
        }
        if (!BinnieCore.proxy.checkTexture(this.bgText1.getTexture())) {
            this.bgText1 = null;
            this.bgText2 = null;
        }
        this.initialiseClient();
        this.hasBeenInitialised = true;
    }

    public abstract void initialiseClient();

    public void initialiseServer() {
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.colour(16777215);
        if (this.getBackground1() != null) {
            CraftGUI.Render.texture(this.getBackground1(), IPoint.ZERO);
        }
        if (this.getBackground2() != null) {
            CraftGUI.Render.texture(this.getBackground2(), new IPoint(256.0f, 0.0f));
        }
        CraftGUI.Render.colour(this.getColour());
        CraftGUI.Render.texture(CraftGUITexture.Window, this.getArea());
    }

    @Override
    public void onUpdateClient() {
        ControlSlot.highlighting.get(EnumHighlighting.Help).clear();
        ControlSlot.shiftClickActive = false;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public GameProfile getUsername() {
        return this.getPlayer().getGameProfile();
    }

    public ItemStack getHeldItemStack() {
        if (this.player != null) {
            return this.player.inventory.getItemStack();
        }
        return null;
    }

    public IInventory getInventory() {
        return this.entityInventory;
    }

    public void setInventories(final EntityPlayer player2, final IInventory inventory) {
        this.player = player2;
        this.entityInventory = inventory;
    }

    public void onClose() {
    }

    public void setHeldItemStack(final ItemStack stack) {
        if (this.player != null) {
            this.player.inventory.setItemStack(stack);
        }
    }

    public boolean isServer() {
        return !this.isClient();
    }

    public boolean isClient() {
        return this.side == Side.CLIENT;
    }

    public World getWorld() {
        if (this.getPlayer() != null) {
            return this.getPlayer().worldObj;
        }
        return BinnieCore.proxy.getWorld();
    }

    public void onInventoryUpdate() {
    }

    public void sendClientAction(final String name, final NBTTagCompound action) {
        action.setString("type", name);
        final MessageCraftGUI packet = new MessageCraftGUI(action);
        BinnieCore.proxy.sendToServer(packet);
    }

    @Override
    public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound action) {
        if (side == Side.CLIENT && name.equals("username")) {
            final float w = this.w();
            final float titleButtonRight = this.titleButtonRight + 16.0f;
            this.titleButtonRight = titleButtonRight;
            final ControlUser controlUser = new ControlUser(this, w - titleButtonRight, 8.0f, action.getString("username"));
            this.titleButtonRight += 6.0f;
        }
        if (side == Side.CLIENT && name.equals("power-system")) {
            final float w2 = this.w();
            final float titleButtonRight2 = this.titleButtonRight + 16.0f;
            this.titleButtonRight = titleButtonRight2;
            final ControlPowerSystem controlPowerSystem = new ControlPowerSystem(this, w2 - titleButtonRight2, 8.0f, PowerSystem.get(action.getByte("system")));
            this.titleButtonRight += 6.0f;
        }
    }

    public void onWindowInventoryChanged() {
    }

    public Texture getBackground1() {
        return this.bgText1;
    }

    public Texture getBackground2() {
        return this.bgText2;
    }

    public static <T extends Window> T get(final IWidget widget) {
        return (T) widget.getSuperParent();
    }
}
