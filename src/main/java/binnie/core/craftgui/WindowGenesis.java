package binnie.core.craftgui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.controls.tab.ControlTab;
import binnie.core.craftgui.controls.tab.ControlTabBar;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.gui.ControlGenesisOption;

public class WindowGenesis extends Window {
	private ISpeciesRoot root;
	private IAllele[] template;
	private ControlListBox<Gene> geneList;
	private ControlListBox<Gene> geneOptions;
	private Panel panelPickup;

	public WindowGenesis(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		super(342, 228, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return BinnieCore.getInstance();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Genesis";
	}

	@Override
	public void initialiseClient() {
		new ControlPlayerInventory(this);
		this.setTitle("Genesis");
		final ControlTabBar<BreedingSystem> tabSystems = new ControlTabBar<BreedingSystem>(this, 8, 28, 23, 100, Position.LEFT, Binnie.GENETICS.getActiveSystems()) {
			@Override
			public ControlTab<BreedingSystem> createTab(final int x, final int y, final int w, final int h, final BreedingSystem value) {
				return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
					@Override
					public ItemStack getItemStack() {
						final ISpeciesType type = this.value.getDefaultType();
						final IIndividual ind = this.value.getDefaultIndividual();
						return this.value.getSpeciesRoot().getMemberStack(ind, type);
					}

					@Override
					public String getName() {
						return this.value.getName();
					}

					@Override
					public int getOutlineColour() {
						return this.value.getColour();
					}

					@Override
					public boolean hasOutline() {
						return true;
					}
				};
			}
		};
		this.root = Binnie.GENETICS.getActiveSystems().iterator().next().getSpeciesRoot();
		this.template = this.root.getDefaultTemplate();
		final Area one = new Area(32, 28, 170, 100);
		final Area two = new Area(214, 28, 100, 100);
		new Panel(this, one.outset(1), MinecraftGUI.PanelType.Black);
		new Panel(this, two.outset(1), MinecraftGUI.PanelType.Black);
		this.geneList = new ControlListBox<Gene>(this, one.xPos(), one.yPos(), one.width(), one.height(), 10) {
			@Override
			public IWidget createOption(final Gene value, final int y) {
				return new ControlGenesisOption(this.getContent(), value, y);
			}
		};
		this.geneOptions = new ControlListBox<Gene>(this, two.xPos(), two.yPos(), two.width(), two.height(), 10) {
			@Override
			public IWidget createOption(final Gene value, final int y) {
				return new ControlTextOption<>(this.getContent(), value, y);
			}
		};
		tabSystems.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				WindowGenesis.this.root = ((BreedingSystem) event.getValue()).getSpeciesRoot();
				WindowGenesis.this.template = WindowGenesis.this.root.getDefaultTemplate();
				WindowGenesis.this.refreshTemplate(null);
			}
		}.setOrigin(EventHandler.Origin.Self, tabSystems));
		this.geneList.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				final Map<IChromosomeType, List<IAllele>> map = Binnie.GENETICS.getChromosomeMap(WindowGenesis.this.root);
				final List<Gene> opts = new ArrayList<>();
				final IChromosomeType chromo = event.value != null ? ((Gene) event.value).getChromosome() : null;
				if (chromo != null)// fix NPE
					for (final IAllele allele : map.get(chromo)) {
						opts.add(new Gene(allele, chromo, WindowGenesis.this.root));
					}
				WindowGenesis.this.geneOptions.setOptions(opts);
			}
		}.setOrigin(EventHandler.Origin.Self, this.geneList));
		this.geneOptions.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.value == null) {
					return;
				}
				final IChromosomeType chromo = ((Gene) event.value).getChromosome();
				IAllele allele = ((Gene) event.value).getAllele();
				WindowGenesis.this.template[chromo.ordinal()] = allele;
				ISpeciesRoot speciesRoot = ((Gene) event.value).getSpeciesRoot();
				if (chromo == speciesRoot.getSpeciesChromosomeType()) {
					WindowGenesis.this.template = speciesRoot.getTemplate(allele.getUID());
				}
				WindowGenesis.this.refreshTemplate(chromo);
			}
		}.setOrigin(EventHandler.Origin.Self, this.geneOptions));
		this.panelPickup = new Panel(this, 16, 140, 60, 42, MinecraftGUI.PanelType.Black);
		this.refreshTemplate(null);
	}

	private void refreshTemplate(@Nullable final IChromosomeType selection) {
		final List<Gene> genes = new ArrayList<>();
		final IChromosomeType[] arr$;
		final IChromosomeType[] chromos = arr$ = Binnie.GENETICS.getChromosomeMap(this.root).keySet().toArray(new IChromosomeType[0]);
		for (final IChromosomeType type : arr$) {
			final IAllele allele = this.template[type.ordinal()];
			if (allele == null) {
				throw new NullPointerException("Allele missing for Chromosome " + type.getName());
			}
			genes.add(new Gene(allele, type, this.root));
		}
		final Map<IChromosomeType, List<IAllele>> map = Binnie.GENETICS.getChromosomeMap(this.root);
		this.geneList.setOptions(genes);
		if (selection != null) {
			this.geneList.setValue(new Gene(this.template[selection.ordinal()], selection, this.root));
		} else {
			this.geneOptions.setOptions(new ArrayList<>());
		}
		this.refreshPickup();
	}

	private void refreshPickup() {
		this.panelPickup.deleteAllChildren();
		int i = 0;
		for (final ISpeciesType type : Binnie.GENETICS.getSystem(this.root).getActiveTypes()) {
			final IIndividual ind = this.root.templateAsIndividual(this.template);
			ind.analyze();
			final ItemStack stack = this.root.getMemberStack(ind, type);
			final ControlItemDisplay display = new ControlItemDisplay(this.panelPickup, 4 + i % 3 * 18, 4 + i / 3 * 18);
			display.setItemStack(stack);
			display.setTooltip();
			display.addEventHandler(new EventMouse.Down.Handler() {
				@Override
				public void onEvent(final EventMouse.Down event) {
					final NBTTagCompound nbt = new NBTTagCompound();
					stack.writeToNBT(nbt);
					Window.get(event.getOrigin()).sendClientAction("genesis", nbt);
				}
			}.setOrigin(EventHandler.Origin.Self, display));
			++i;
		}
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals("genesis")) {
			final ItemStack stack = new ItemStack(nbt);
			final InventoryPlayer playerInv = player.inventory;
			if (stack.isEmpty()) {
				return;
			}
			if (playerInv.getItemStack().isEmpty()) {
				playerInv.setItemStack(stack);
			} else if (playerInv.getItemStack().isItemEqual(stack) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), stack)) {
				final int fit = stack.getMaxStackSize() - (stack.getCount() + playerInv.getItemStack().getCount());
				if (fit >= 0) {
					final ItemStack itemStack;
					final ItemStack rec = itemStack = stack;
					itemStack.grow(playerInv.getItemStack().getCount());
					playerInv.setItemStack(rec);
				}
			}
			player.openContainer.detectAndSendChanges();
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).updateHeldItem();
			}
		}
	}
}
