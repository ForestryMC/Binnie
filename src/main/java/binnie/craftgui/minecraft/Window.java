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
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.ITooltipHelp;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.TopLevelWidget;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.minecraft.control.ControlHelp;
import binnie.craftgui.minecraft.control.ControlInfo;
import binnie.craftgui.minecraft.control.ControlPowerSystem;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlUser;
import binnie.craftgui.minecraft.control.EnumHighlighting;
import binnie.craftgui.resource.StyleSheetManager;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Deque;

public abstract class Window extends TopLevelWidget implements INetwork.ReceiveGuiNBT {
	@Nullable // client side only
	private GuiCraftGUI gui;
	private ContainerCraftGUI container;
	private WindowInventory windowInventory;
	@Nullable
	private ControlText title;
	protected int titleButtonLeft;
	protected int titleButtonRight;
	@Nullable
	private StandardTexture bgText1;
	@Nullable
	private StandardTexture bgText2;
	private boolean hasBeenInitialised;
	private EntityPlayer player;
	@Nullable
	private IInventory entityInventory;
	private Side side;

	public Window(final int width, final int height, final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		this.titleButtonLeft = 8;
		this.titleButtonRight = 8;
		this.bgText1 = null;
		this.bgText2 = null;
		this.hasBeenInitialised = false;
		this.side = Side.CLIENT;
		this.side = side;
		this.player = player;
		this.entityInventory = inventory;
		this.container = new ContainerCraftGUI(this);
		this.windowInventory = new WindowInventory(this);
		if (side == Side.SERVER) {
			return;
		}

		this.setSize(new Point(width, height));
		this.gui = new GuiCraftGUI(this);
		for (final EnumHighlighting h : EnumHighlighting.values()) {
			ControlSlot.highlighting.put(h, new ArrayList<>());
		}
		CraftGUI.render.setStyleSheet(StyleSheetManager.getDefault());
		this.titleButtonLeft = -14;
		if (this.showHelpButton()) {
			new ControlHelp(this, this.titleButtonLeft += 22, 8);
		}
		String showInfoButton = this.showInfoButton();
		if (showInfoButton != null) {
			new ControlInfo(this, this.titleButtonLeft += 22, 8, showInfoButton);
		}
		this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				if (Window.this.isClient()) {
					Window.this.getGui().resize(Window.this.getSize());
					if (Window.this.title != null) {
						Window.this.title.setSize(new Point(Window.this.width(), Window.this.title.height()));
					}
				}
			}
		});
	}

	public void getTooltip(final Tooltip tooltip) {
		final Deque<IWidget> queue = this.calculateMousedOverWidgets();
		while (!queue.isEmpty()) {
			final IWidget widget = queue.removeFirst();
			if (widget.isEnabled() && widget.isVisible() && widget.calculateIsMouseOver()) {
				if (widget instanceof ITooltip) {
					((ITooltip) widget).getTooltip(tooltip);
					if (tooltip.exists()) {
						return;
					}
				}
				if (widget.hasAttribute(Attribute.BlockTooltip)) {
					return;
				}
			}
		}
	}

	public void getHelpTooltip(final MinecraftTooltip tooltip) {
		final Deque<IWidget> queue = this.calculateMousedOverWidgets();
		while (!queue.isEmpty()) {
			final IWidget widget = queue.removeFirst();
			if (widget.isEnabled() && widget.isVisible() && widget.calculateIsMouseOver()) {
				if (widget instanceof ITooltipHelp) {
					((ITooltipHelp) widget).getHelpTooltip(tooltip);
					if (tooltip.exists()) {
						return;
					}
				}
				if (widget.hasAttribute(Attribute.BlockTooltip)) {
					return;
				}
			}
		}
	}

	protected abstract AbstractMod getMod();

	protected abstract String getBackgroundTextureName();

	public IBinnieTexture getBackgroundTextureFile(final int i) {
		return new IBinnieTexture() {
			@Override
			@SideOnly(Side.CLIENT)
			public BinnieResource getTexture() {
				return Binnie.RESOURCE.getPNG(getMod(), ResourceType.GUI, getBackgroundTextureName() + ((i == 1) ? "" : i));
			}
		};
	}

	public boolean showHelpButton() {
		return Machine.getInterface(IInventoryMachine.class, this.getInventory()) != null;
	}

	@Nullable
	public String showInfoButton() {
		IMachineInformation machineInformation = Machine.getInterface(IMachineInformation.class, this.getInventory());
		if (machineInformation != null) {
			return machineInformation.getInformation();
		}
		return null;
	}

	public void setTitle(final String title) {
		(this.title = new ControlTextCentered(this, 12, title)).setColour(4210752);
	}

	@SideOnly(Side.CLIENT)
	public final GuiCraftGUI getGui() {
		Preconditions.checkState(this.gui != null, "tried to get gui on server-side, should not be possible");
		return this.gui;
	}

	public final ContainerCraftGUI getContainer() {
		return this.container;
	}

	public final WindowInventory getWindowInventory() {
		return this.windowInventory;
	}

	@SideOnly(Side.CLIENT)
	public final void initGui() {
		if (this.hasBeenInitialised) {
			return;
		}
		this.bgText1 = new StandardTexture(0, 0, 256, 256, this.getBackgroundTextureFile(1));
		if (this.getSize().x() > 256) {
			this.bgText2 = new StandardTexture(0, 0, 256, 256, this.getBackgroundTextureFile(2));
		}
		if (!BinnieCore.getBinnieProxy().checkTexture(this.bgText1.getTexture())) {
			this.bgText1 = null;
			this.bgText2 = null;
		}
		this.initialiseClient();
		this.hasBeenInitialised = true;
	}

	@SideOnly(Side.CLIENT)
	public abstract void initialiseClient();

	public void initialiseServer() {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.setColour(16777215);
		if (this.getBackground1() != null) {
			CraftGUI.render.texture(this.getBackground1(), Point.ZERO);
		}
		if (this.getBackground2() != null) {
			CraftGUI.render.texture(this.getBackground2(), new Point(256, 0));
		}
		RenderUtil.setColour(this.getColour());
		CraftGUI.render.texture(CraftGUITexture.Window, this.getArea());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
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
		return this.player.inventory.getItemStack();
	}

	@Nullable
	public IInventory getInventory() {
		return this.entityInventory;
	}

	public void onClose() {
	}

	public void setHeldItemStack(final ItemStack stack) {
		this.player.inventory.setItemStack(stack);
	}

	public boolean isServer() {
		return !this.isClient();
	}

	public boolean isClient() {
		return this.side == Side.CLIENT;
	}

	public World getWorld() {
		return this.getPlayer().world;
	}

	public void onInventoryUpdate() {
	}

	public void sendClientAction(final String name, final NBTTagCompound action) {
		action.setString("type", name);
		final MessageCraftGUI packet = new MessageCraftGUI(action);
		BinnieCore.getBinnieProxy().sendToServer(packet);
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {

	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("username")) {
			final int w = this.width();
			final int titleButtonRight = this.titleButtonRight + 16;
			this.titleButtonRight = titleButtonRight;
			final ControlUser controlUser = new ControlUser(this, w - titleButtonRight, 8, nbt.getString("username"));
			this.titleButtonRight += 6;
		}
		if (name.equals("power-system")) {
			final int w2 = this.width();
			final int titleButtonRight2 = this.titleButtonRight + 16;
			this.titleButtonRight = titleButtonRight2;
			final ControlPowerSystem controlPowerSystem = new ControlPowerSystem(this, w2 - titleButtonRight2, 8, PowerSystem.get(nbt.getByte("system")));
			this.titleButtonRight += 6;
		}
	}

	public void onWindowInventoryChanged() {
	}

	@Nullable
	public Texture getBackground1() {
		return this.bgText1;
	}

	@Nullable
	public Texture getBackground2() {
		return this.bgText2;
	}

	public static <T extends Window> T get(final IWidget widget) {
		return (T) widget.getTopParent();
	}
}
