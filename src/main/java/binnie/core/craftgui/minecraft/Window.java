package binnie.core.craftgui.minecraft;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.ITooltipHelp;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.TopLevelWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlHelp;
import binnie.core.craftgui.minecraft.control.ControlInfo;
import binnie.core.craftgui.minecraft.control.ControlPowerSystem;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlUser;
import binnie.core.craftgui.minecraft.control.EnumHighlighting;
import binnie.core.craftgui.renderer.Renderer;
import binnie.core.craftgui.resource.StyleSheetManager;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.PowerSystem;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Deque;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class Window extends TopLevelWidget implements INetwork.RecieveGuiNBT {
    protected float titleButtonLeft;
    protected float titleButtonRight;
    private GuiCraftGUI gui;
    private ContainerCraftGUI container;
    private WindowInventory windowInventory;
    private ControlText title;
    private StandardTexture bgText1;
    private StandardTexture bgText2;
    private boolean hasBeenInitialised;
    private EntityPlayer player;
    private IInventory entityInventory;
    private Side side;

    public Window(float width, float height, EntityPlayer player, IInventory inventory, Side side) {
        this.side = side;
        titleButtonLeft = 8.0f;
        titleButtonRight = 8.0f;
        bgText1 = null;
        bgText2 = null;
        hasBeenInitialised = false;
        setInventories(player, inventory);
        container = new ContainerCraftGUI(this);
        windowInventory = new WindowInventory(this);
        if (side == Side.SERVER) {
            return;
        }

        setSize(new IPoint(width, height));
        gui = new GuiCraftGUI(this);
        for (EnumHighlighting h : EnumHighlighting.values()) {
            ControlSlot.highlighting.put(h, new ArrayList<>());
        }

        CraftGUI.render = new Renderer(gui);
        CraftGUI.render.stylesheet(StyleSheetManager.getDefault());
        titleButtonLeft = -14.0f;

        if (showHelpButton()) {
            new ControlHelp(this, titleButtonLeft += 22.0f, 8.0f);
        }
        if (showInfoButton() != null) {
            new ControlInfo(this, titleButtonLeft += 22.0f, 8.0f, showInfoButton());
        }

        addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            @Override
            public void onEvent(EventWidget.ChangeSize event) {
                if (isClient() && getGui() != null) {
                    getGui().resize(getSize());
                    if (title != null) {
                        title.setSize(new IPoint(w(), title.h()));
                    }
                }
            }
        });
    }

    public static <T extends Window> T get(IWidget widget) {
        return (T) widget.getSuperParent();
    }

    public void getTooltip(Tooltip tooltip) {
        Deque<IWidget> queue = calculateMousedOverWidgets();
        while (!queue.isEmpty()) {
            IWidget widget = queue.removeFirst();
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
                if (widget.hasAttribute(WidgetAttribute.BLOCK_TOOLTIP)) {
                    return;
                }
            }
        }
    }

    public void getHelpTooltip(MinecraftTooltip tooltip) {
        Deque<IWidget> queue = calculateMousedOverWidgets();
        while (!queue.isEmpty()) {
            IWidget widget = queue.removeFirst();
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
                if (widget.hasAttribute(WidgetAttribute.BLOCK_TOOLTIP)) {
                    return;
                }
            }
        }
    }

    protected abstract AbstractMod getMod();

    protected abstract String getName();

    public BinnieResource getBackgroundTextureFile(int i) {
        return Binnie.Resource.getPNG(getMod(), ResourceType.GUI, getName() + ((i == 1) ? "" : i));
    }

    public boolean showHelpButton() {
        return Machine.getInterface(IInventoryMachine.class, getInventory()) != null;
    }

    public String showInfoButton() {
        if (Machine.getInterface(IMachineInformation.class, getInventory()) != null) {
            return Machine.getInterface(IMachineInformation.class, getInventory())
                    .getInformation();
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = new ControlTextCentered(this, 12.0f, title);
        this.title.setColor(0x404040);
    }

    @SideOnly(Side.CLIENT)
    public GuiCraftGUI getGui() {
        return gui;
    }

    public ContainerCraftGUI getContainer() {
        return container;
    }

    public WindowInventory getWindowInventory() {
        return windowInventory;
    }

    public void initGui() {
        if (hasBeenInitialised) {
            return;
        }

        bgText1 = new StandardTexture(0, 0, 256, 256, getBackgroundTextureFile(1));
        if (getSize().x() > 256.0f) {
            bgText2 = new StandardTexture(0, 0, 256, 256, getBackgroundTextureFile(2));
        }

        if (!BinnieCore.proxy.checkTexture(bgText1.getTexture())) {
            bgText1 = null;
            bgText2 = null;
        }
        initialiseClient();
        hasBeenInitialised = true;
    }

    public abstract void initialiseClient();

    public void initialiseServer() {
        // ignored
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.color(0xffffff);
        if (getBackground1() != null) {
            CraftGUI.render.texture(getBackground1(), IPoint.ZERO);
        }
        if (getBackground2() != null) {
            CraftGUI.render.texture(getBackground2(), new IPoint(256.0f, 0.0f));
        }
        CraftGUI.render.color(getColor());
        CraftGUI.render.texture(CraftGUITexture.Window, getArea());
    }

    @Override
    public void onUpdateClient() {
        ControlSlot.highlighting.get(EnumHighlighting.HELP).clear();
        ControlSlot.shiftClickActive = false;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public GameProfile getUsername() {
        return getPlayer().getGameProfile();
    }

    public ItemStack getHeldItemStack() {
        if (player != null) {
            return player.inventory.getItemStack();
        }
        return null;
    }

    public void setHeldItemStack(ItemStack stack) {
        if (player != null) {
            player.inventory.setItemStack(stack);
        }
    }

    public IInventory getInventory() {
        return entityInventory;
    }

    public void setInventories(EntityPlayer player2, IInventory inventory) {
        player = player2;
        entityInventory = inventory;
    }

    public void onClose() {}

    public boolean isServer() {
        return !isClient();
    }

    public boolean isClient() {
        return side == Side.CLIENT;
    }

    public World getWorld() {
        if (getPlayer() != null) {
            return getPlayer().worldObj;
        }
        return BinnieCore.proxy.getWorld();
    }

    public void sendClientAction(String name, NBTTagCompound action) {
        action.setString("type", name);
        MessageCraftGUI packet = new MessageCraftGUI(action);
        BinnieCore.proxy.sendToServer(packet);
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (side == Side.CLIENT && name.equals("username")) {
            float w = w();
            float titleButtonRight = this.titleButtonRight + 16.0f;
            this.titleButtonRight = titleButtonRight;
            new ControlUser(this, w - titleButtonRight, 8.0f, nbt.getString("username"));
            this.titleButtonRight += 6.0f;
        }
        if (side == Side.CLIENT && name.equals("power-system")) {
            float w2 = w();
            float titleButtonRight2 = titleButtonRight + 16.0f;
            titleButtonRight = titleButtonRight2;
            new ControlPowerSystem(this, w2 - titleButtonRight2, 8.0f, PowerSystem.get(nbt.getByte("system")));
            titleButtonRight += 6.0f;
        }
    }

    public void onWindowInventoryChanged() {
        // ignored
    }

    public Texture getBackground1() {
        return bgText1;
    }

    public Texture getBackground2() {
        return bgText2;
    }
}
