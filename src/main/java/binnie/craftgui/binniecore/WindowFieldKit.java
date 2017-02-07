package binnie.craftgui.binniecore;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlImage;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.resource.StyleSheetPunnett;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.gui.ControlChromosome;
import binnie.genetics.machine.analyser.Analyser;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WindowFieldKit extends Window {
	private int glassOffsetX;
	private int glassOffsetY;
	private int glassVX;
	private int glassVY;
	private Random glassRand;
	private Control GlassControl;
	private ControlChromosome chromo;
	private ControlText text;
	private float analyseProgress;
	private boolean isAnalysing;
	private Map<IChromosomeType, String> info;
	@Nullable
	private ItemStack prev;

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
		this.prev = null;
	}

	@Override
	protected AbstractMod getMod() {
		return BinnieCore.getInstance();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Field Kit";
	}

	private void setupValidators() {
		this.getWindowInventory().setValidator(0, new SlotValidator(null) {
			@Override
			public boolean isValid(final ItemStack object) {
				return AlleleManager.alleleRegistry.isIndividual(object) || Binnie.GENETICS.getConversion(object) != null;
			}

			@Override
			public String getTooltip() {
				return "Individual";
			}
		});
		this.getWindowInventory().setValidator(1, new SlotValidator(null) {
			@Override
			public boolean isValid(final ItemStack object) {
				return object.getItem() == Items.PAPER;
			}

			@Override
			public String getTooltip() {
				return "Paper";
			}
		});
		this.getWindowInventory().disableAutoDispense(1);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Field Kit");
		CraftGUI.render.setStyleSheet(new StyleSheetPunnett());
		this.getWindowInventory().createSlot(0);
		this.getWindowInventory().createSlot(1);
		this.setupValidators();
		new ControlPlayerInventory(this);
		final IPoint handGlass = new IPoint(16, 32);
		this.GlassControl = new ControlImage(this, handGlass.x(), handGlass.y(), new StandardTexture(0, 160, 96, 96, ExtraBeeTexture.GUIPunnett));
		new ControlSlot.Builder(this, handGlass.x() + 54, handGlass.y() + 26).assign(InventoryType.Window, 0);
		new ControlSlot.Builder(this, 208, 8).assign(InventoryType.Window, 1);
		(this.text = new ControlText(this, new IPoint(232, 13), "Paper")).setColour(2236962);
		(this.text = new ControlText(this, new IArea(0, 120, this.w(), 24), "", TextJustification.MiddleCenter)).setColour(2236962);
		this.chromo = new ControlChromosome(this, 150, 24);
		this.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				final IChromosomeType type = (IChromosomeType) event.getValue();
				if (type != null && WindowFieldKit.this.info.containsKey(type)) {
					final String t = WindowFieldKit.this.info.get(type);
					WindowFieldKit.this.text.setValue(t);
				} else {
					WindowFieldKit.this.text.setValue("");
				}
			}
		}.setOrigin(EventHandler.Origin.DirectChild, this.chromo));
	}

	@Override
	public void initialiseServer() {
		final ItemStack kit = this.getPlayer().getHeldItemMainhand();
		final int sheets = 64 - kit.getItemDamage();
		if (sheets != 0) {
			this.getWindowInventory().setInventorySlotContents(1, new ItemStack(Items.PAPER, sheets));
		}
		this.setupValidators();
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.isAnalysing) {
			this.analyseProgress += 0.01f;
			if (this.analyseProgress >= 1) {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				final ItemStack stack = this.getWindowInventory().getStackInSlot(0);
				if (stack != null) {
					this.sendClientAction("analyse", new NBTTagCompound());
				}
				this.refreshSpecies();
			}
		}
		this.glassVX += this.glassRand.nextFloat() - 0.5f - this.glassOffsetX * 0.2f;
		this.glassVY += this.glassRand.nextFloat() - 0.5f - this.glassOffsetY * 0.2f;
		this.glassOffsetX += this.glassVX;
		this.glassOffsetX *= 1 - this.analyseProgress;
		this.glassOffsetY += this.glassVY;
		this.glassOffsetY *= 1 - this.analyseProgress;
		this.GlassControl.setOffset(new IPoint(this.glassOffsetX, this.glassOffsetY));
	}

	private void refreshSpecies() {
		final ItemStack item = this.getWindowInventory().getStackInSlot(0);
		if (item == null || !AlleleManager.alleleRegistry.isIndividual(item)) {
			return;
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(item);
		if (ind == null) {
			return;
		}
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(item);
		this.chromo.setRoot(root);
		final Random rand = new Random();
		this.info.clear();
		for (final IChromosomeType type : root.getKaryotype()) {
			if (!Binnie.GENETICS.isInvalidChromosome(type)) {
				final IAllele allele = ind.getGenome().getActiveAllele(type);
				final List<String> infos = new ArrayList<>();
				int i = 0;
				for (String pref = root.getUID() + ".fieldkit." + type.getName().toLowerCase() + "."; Binnie.LANGUAGE.canLocalise(pref + i); ++i) {
					infos.add(Binnie.LANGUAGE.localise(pref + i));
				}
				String text = Binnie.GENETICS.getSystem(root).getAlleleName(type, allele);
				if (!infos.isEmpty()) {
					text = infos.get(rand.nextInt(infos.size()));
				}
				this.info.put(type, text);
				this.chromo.setRoot(root);
			}
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		if (this.isServer()) {
			final ItemStack kit = this.getPlayer().getHeldItemMainhand();
			final int sheets = 64 - kit.getItemDamage();
			final int size = (this.getWindowInventory().getStackInSlot(1) == null) ? 0 : this.getWindowInventory().getStackInSlot(1).stackSize;
			if (sheets != size) {
				kit.setItemDamage(64 - size);
			}
			((EntityPlayerMP) this.getPlayer()).updateHeldItem();
		}
		if (this.isClient()) {
			final ItemStack item = this.getWindowInventory().getStackInSlot(0);
			this.prev = item;
			this.text.setValue("");
			if (item != null && !Analyser.isAnalysed(item)) {
				if (this.getWindowInventory().getStackInSlot(1) == null) {
					this.text.setValue("No Paper!");
					this.isAnalysing = false;
					this.analyseProgress = 1;
				} else {
					this.startAnalysing();
					this.chromo.setRoot(null);
					if (this.damageKit()) {
						return;
					}
				}
			} else if (item != null) {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				this.refreshSpecies();
				if (this.damageKit()) {
					return;
				}
			} else {
				this.isAnalysing = false;
				this.analyseProgress = 1;
				this.chromo.setRoot(null);
			}
		}
	}

	private boolean damageKit() {
		return false;
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
		return "The Field Kit analyses bees, trees, flowers and butterflies. All that is required is a piece of paper to jot notes";
	}

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound action) {
		super.recieveGuiNBT(side, player, name, action);
		if (side == Side.SERVER && name.equals("analyse")) {
			this.getWindowInventory().setInventorySlotContents(0, Analyser.analyse(this.getWindowInventory().getStackInSlot(0)));
			this.getWindowInventory().decrStackSize(1, 1);
		}
	}

}
