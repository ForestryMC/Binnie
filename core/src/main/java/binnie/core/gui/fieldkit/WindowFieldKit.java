package binnie.core.gui.fieldkit;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import binnie.core.api.gui.events.EventHandlerOrigin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.InventoryType;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.WindowInventory;
import binnie.core.gui.minecraft.control.ControlImage;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.resource.stylesheet.StyleSheetManager;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;
import binnie.core.util.I18N;

public class WindowFieldKit extends Window {
	public static final int INDIVIDUAL_SLOT = 0;
	public static final int PAPER_SLOT = 1;

	private float glassOffsetX;
	private float glassOffsetY;
	private float glassVX;
	private float glassVY;
	private final Random glassRand;
	private Control GlassControl;
	private ControlChromosome chromo;
	private ControlText text;
	private float analyseProgress;
	private boolean isAnalysing;
	private final Map<IChromosomeType, String> info;

	public WindowFieldKit(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		super(280, 230, player, inventory, side);
		this.glassOffsetX = 0;
		this.glassOffsetY = 0;
		this.glassVX = 0;
		this.glassVY = 0;
		this.glassRand = new Random();
		this.analyseProgress = 1;
		this.isAnalysing = false;
		this.info = new HashMap<>();
	}

	@Override
	protected String getModId() {
		return BinnieCore.getInstance().getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Field Kit";
	}

	private void setupValidators() {
		WindowInventory inventory = this.getWindowInventory();
		inventory.setValidator(INDIVIDUAL_SLOT, new SlotValidatorIndividual(null));
		inventory.setValidator(PAPER_SLOT, new SlotValidatorPaper(null));
		inventory.disableAutoDispense(PAPER_SLOT);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle(I18N.localise("binniecore.gui.fieldkit.title"));
		CraftGUI.RENDER.setStyleSheet(StyleSheetManager.getSheet(StyleSheetManager.PUNNETT_SHEET));
		WindowInventory inventory = this.getWindowInventory();
		inventory.createSlot(INDIVIDUAL_SLOT);
		inventory.createSlot(PAPER_SLOT);
		this.setupValidators();
		new ControlPlayerInventory(this);
		final Point handGlass = new Point(16, 32);
		this.GlassControl = new ControlImage(this, handGlass.xPos(), handGlass.yPos(), new StandardTexture(0, 160, 96, 96, BinnieCoreTexture.GUI_PUNNETT));
		new ControlSlot.Builder(this, handGlass.xPos() + 54, handGlass.yPos() + 26).assign(InventoryType.WINDOW, 0);
		new ControlSlot.Builder(this, 208, 8).assign(InventoryType.WINDOW, 1);
		(this.text = new ControlText(this, new Point(232, 13), I18N.localise("binniecore.gui.fieldkit.paper"))).setColor(2236962);
		(this.text = new ControlText(this, new Area(0, 120, this.getWidth(), 24), "", TextJustification.MIDDLE_CENTER)).setColor(2236962);
		this.chromo = new ControlChromosome(this, 150, 24);
		this.addEventHandler(EventValueChanged.class, EventHandlerOrigin.DIRECT_CHILD, this.chromo, event -> {
			final IChromosomeType type = (IChromosomeType) event.getValue();
			if (type != null && WindowFieldKit.this.info.containsKey(type)) {
				final String t = WindowFieldKit.this.info.get(type);
				WindowFieldKit.this.text.setValue(t);
			} else {
				WindowFieldKit.this.text.setValue("");
			}
		});
	}

	@Override
	public void initialiseServer() {
		//create slots
		WindowInventory inventory = this.getWindowInventory();
		final ItemStack kit = this.getPlayer().getHeldItemMainhand();
		final int sheets = 64 - kit.getItemDamage();
		inventory.createSlot(INDIVIDUAL_SLOT);
		inventory.createSlot(PAPER_SLOT);
		if (sheets != 0) {
			inventory.setInventorySlotContents(PAPER_SLOT, new ItemStack(Items.PAPER, sheets));
		}
		this.setupValidators();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.isAnalysing) {
			this.analyseProgress += 0.01f;
			if (this.analyseProgress >= 1) {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				final ItemStack stack = this.getWindowInventory().getStackInSlot(INDIVIDUAL_SLOT);
				if (!stack.isEmpty()) {
					this.sendClientAction("analyse", new NBTTagCompound());
				}
				this.refreshSpecies();
			}
		}
		this.glassVX += this.glassRand.nextFloat() - 2.5f - this.glassOffsetX * 1f;
		this.glassVY += this.glassRand.nextFloat() - 2.5f - this.glassOffsetY * 1f;
		this.glassOffsetX += this.glassVX;
		this.glassOffsetX *= 1 - this.analyseProgress;
		this.glassOffsetY += this.glassVY;
		this.glassOffsetY *= 1 - this.analyseProgress;
		this.GlassControl.setOffset(new Point((int)this.glassOffsetX, (int)this.glassOffsetY));
	}

	private void refreshSpecies() {
		final ItemStack item = this.getWindowInventory().getStackInSlot(INDIVIDUAL_SLOT);
		if (item.isEmpty() || !AlleleManager.alleleRegistry.isIndividual(item)) {
			return;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(item);
		if (ind == null) {
			return;
		}
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(item);
		if (root == null) {
			return;
		}
		IBreedingSystem system = Binnie.GENETICS.getSystem(root);
		this.chromo.setSystem(system);
		final Random rand = new Random();
		this.info.clear();
		for (final IChromosomeType type : root.getKaryotype()) {
			if (!Binnie.GENETICS.isInvalidChromosome(type)) {
				final IAllele allele = ind.getGenome().getActiveAllele(type);
				final List<String> infos = new ArrayList<>();
				int i = 0;
				for (String pref = root.getUID() + ".fieldkit." + type.getName().toLowerCase() + "."; I18N.canLocalise(pref + i); ++i) {
					infos.add(I18N.localise(pref + i));
				}
				String text = system.getAlleleName(type, allele);
				if (!infos.isEmpty()) {
					text = infos.get(rand.nextInt(infos.size()));
				}
				this.info.put(type, text);
				this.chromo.setSystem(system);
			}
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		WindowInventory inventory = getWindowInventory();
		if (this.isServer()) {
			final ItemStack kit = this.getPlayer().getHeldItemMainhand();
			if (kit.isEmpty() || !kit.getItem().equals(BinnieCore.getFieldKit())) {
				return;
			}
			ItemStack paper = inventory.getStackInSlot(PAPER_SLOT);
			final int sheets = 64 - kit.getItemDamage();
			final int size = (paper.isEmpty()) ? 0 : paper.getCount();
			if (sheets != size) {
				kit.setItemDamage(64 - size);
			}
			((EntityPlayerMP) this.getPlayer()).updateHeldItem();
		}
		if (this.isClient()) {
			final ItemStack item = inventory.getStackInSlot(INDIVIDUAL_SLOT);
			this.text.setValue("");
			if (!item.isEmpty() && !ManagerGenetics.isAnalysed(item)) {
				if (inventory.getStackInSlot(PAPER_SLOT).isEmpty()) {
					this.text.setValue(I18N.localise("binniecore.gui.fieldkit.paper.no"));
					this.isAnalysing = false;
					this.analyseProgress = 1;
				} else {
					this.startAnalysing();
					this.chromo.setSystem(null);
				}
			} else if (!item.isEmpty()) {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				this.refreshSpecies();
			} else {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				this.chromo.setSystem(null);
			}
		}
	}

	private void startAnalysing() {
		this.glassVX = 0;
		this.glassVY = 0;
		this.glassOffsetX = 0;
		this.glassOffsetY = 0;
		this.isAnalysing = true;
		this.analyseProgress = 0;
	}

	@Override
	public boolean showHelpButton() {
		return true;
	}

	@Override
	public String showInfoButton() {
		return I18N.localise("binniecore.gui.fieldkit.info");
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals("analyse")) {
			WindowInventory inventory = getWindowInventory();
			ItemStack individualStack = inventory.getStackInSlot(INDIVIDUAL_SLOT);
			inventory.setInventorySlotContents(INDIVIDUAL_SLOT, ManagerGenetics.analyse(individualStack, this.getWorld(), this.getUsername()));
			inventory.decrStackSize(PAPER_SLOT, 1);
		}
	}
}
