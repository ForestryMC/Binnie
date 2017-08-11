package binnie.core.gui.genesis;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import binnie.core.api.genetics.IBreedingSystem;
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

import binnie.core.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.controls.listbox.ControlTextOption;
import binnie.core.gui.controls.tab.ControlTab;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlTabIcon;
import binnie.core.gui.window.Panel;
import binnie.core.util.I18N;
import binnie.core.gui.ControlGenesisOption;

public class WindowGenesis extends Window {
	private static final String ACTION_GENESIS = "genesis";

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
		this.setTitle(I18N.localise("binniecore.gui.genesis.title"));
		final ControlTabBar<IBreedingSystem> tabSystems = new ControlTabBar<IBreedingSystem>(this, 8, 28, 23, 100, Position.LEFT, Binnie.GENETICS.getActiveSystems()) {
			@Override
			public ControlTab<IBreedingSystem> createTab(final int x, final int y, final int w, final int h, final IBreedingSystem value) {
				return new ControlTabIcon<IBreedingSystem>(this, x, y, w, h, value) {
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
		new Panel(this, one.outset(1), MinecraftGUI.PanelType.BLACK);
		new Panel(this, two.outset(1), MinecraftGUI.PanelType.BLACK);
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
		tabSystems.addEventHandler(EventValueChanged.class, EventHandler.Origin.SELF, tabSystems, event -> {
			Object value = event.getValue();
			if (!(value instanceof BreedingSystem)) {
				return;
			}
			IBreedingSystem breedingSystem = (IBreedingSystem) value;
			root = breedingSystem.getSpeciesRoot();
			template = root.getDefaultTemplate();
			refreshTemplate(null);
		});
		this.geneList.addEventHandler(EventValueChanged.class, EventHandler.Origin.SELF, this.geneList, event -> {
			Object value = event.getValue();
			if (!(value instanceof Gene)) {
				return;
			}
			Gene gene = (Gene) value;
			Map<IChromosomeType, List<IAllele>> map = Binnie.GENETICS.getChromosomeMap(root);
			List<Gene> options = new ArrayList<>();
			IChromosomeType chromosomeType = gene.getChromosome();
			List<IAllele> alleles = map.get(chromosomeType);
			for (IAllele allele : alleles) {
				options.add(new Gene(allele, chromosomeType, root));
			}
			geneOptions.setOptions(options);
		});
		this.geneOptions.addEventHandler(EventValueChanged.class, EventHandler.Origin.SELF, this.geneOptions, event -> {
			Object value = event.getValue();
			if (!(value instanceof Gene)) {
				return;
			}
			Gene gene = (Gene) value;
			IChromosomeType chromosomeType = gene.getChromosome();
			ISpeciesRoot speciesRoot = gene.getSpeciesRoot();
			IAllele allele = gene.getAllele();
			if (chromosomeType == speciesRoot.getSpeciesChromosomeType()) {
				template = speciesRoot.getTemplate(allele.getUID());
			}else {
				template[chromosomeType.ordinal()] = allele;
			}
			refreshTemplate(chromosomeType);
		});
		this.panelPickup = new Panel(this, 16, 140, 60, 42, MinecraftGUI.PanelType.BLACK);
		this.refreshTemplate(null);
	}

	private void refreshTemplate(@Nullable IChromosomeType selection) {
		List<Gene> genes = new ArrayList<>();
		IChromosomeType[] chromosomeTypes = Binnie.GENETICS.getChromosomeMap(this.root).keySet().toArray(new IChromosomeType[0]);
		for (IChromosomeType type : chromosomeTypes) {
			IAllele allele = this.template[type.ordinal()];
			if (allele == null) {
				throw new NullPointerException("Allele missing for Chromosome " + type.getName());
			}
			genes.add(new Gene(allele, type, this.root));
		}
		geneList.setOptions(genes);
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
		IBreedingSystem system = Binnie.GENETICS.getSystem(this.root);
		for (ISpeciesType type : system.getActiveTypes()) {
			IIndividual ind = this.root.templateAsIndividual(this.template);
			ind.analyze();
			ItemStack stack = this.root.getMemberStack(ind, type);
			ControlItemDisplay display = new ControlItemDisplay(this.panelPickup, 4 + i % 3 * 18, 4 + i / 3 * 18);
			display.setItemStack(stack);
			display.setTooltip();
			display.addEventHandler(EventMouse.Down.class, EventHandler.Origin.SELF, display, event -> {
				NBTTagCompound nbt = new NBTTagCompound();
				stack.writeToNBT(nbt);
				Window.get(event.getOrigin()).sendClientAction(ACTION_GENESIS, nbt);
			});
			++i;
		}
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals(ACTION_GENESIS)) {
			ItemStack stack = new ItemStack(nbt);
			InventoryPlayer inventoryPlayer = player.inventory;
			ItemStack playerStack = inventoryPlayer.getItemStack();
			if (stack.isEmpty()) {
				return;
			}
			if (playerStack.isEmpty()) {
				inventoryPlayer.setItemStack(stack);
			} else if (playerStack.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(playerStack, stack)) {
				final int fit = stack.getMaxStackSize() - (stack.getCount() + playerStack.getCount());
				if (fit >= 0) {
					ItemStack itemStack = stack;
					itemStack.grow(playerStack.getCount());
					inventoryPlayer.setItemStack(itemStack);
				}
			}
			player.openContainer.detectAndSendChanges();
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).updateHeldItem();
			}
		}
	}
}
