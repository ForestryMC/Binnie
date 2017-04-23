// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui;

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
import binnie.core.craftgui.geometry.IArea;
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
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WindowGenesis extends Window
{
	private ISpeciesRoot root;
	private IAllele[] template;
	private ControlListBox<Gene> geneList;
	private ControlListBox<Gene> geneOptions;
	private Panel panelPickup;

	public WindowGenesis(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(342.0f, 228.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return BinnieCore.instance;
	}

	@Override
	protected String getName() {
		return "Genesis";
	}

	@Override
	public void initialiseClient() {
		new ControlPlayerInventory(this);
		setTitle("Genesis");
		final ControlTabBar<BreedingSystem> tabSystems = new ControlTabBar<BreedingSystem>(this, 8.0f, 28.0f, 23.0f, 100.0f, Position.Left) {
			@Override
			public ControlTab<BreedingSystem> createTab(final float x, final float y, final float w, final float h, final BreedingSystem value) {
				return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
					@Override
					public ItemStack getItemStack() {
						final int type = value.getDefaultType();
						final IIndividual ind = value.getDefaultIndividual();
						return value.getSpeciesRoot().getMemberStack(ind, type);
					}

					@Override
					public String getName() {
						return value.getName();
					}

					@Override
					public int getOutlineColour() {
						return value.getColour();
					}

					@Override
					public boolean hasOutline() {
						return true;
					}
				};
			}
		};
		tabSystems.setValues(Binnie.Genetics.getActiveSystems());
		root = Binnie.Genetics.getActiveSystems().iterator().next().getSpeciesRoot();
		template = root.getDefaultTemplate();
		final IArea one = new IArea(32.0f, 28.0f, 170.0f, 100.0f);
		final IArea two = new IArea(214.0f, 28.0f, 100.0f, 100.0f);
		new Panel(this, one.outset(1), MinecraftGUI.PanelType.Black);
		new Panel(this, two.outset(1), MinecraftGUI.PanelType.Black);
		geneList = new ControlListBox<Gene>(this, one.x(), one.y(), one.w(), one.h(), 10.0f) {
			@Override
			public IWidget createOption(final Gene value, final int y) {
				return new ControlGenesisOption(getContent(), value, y);
			}
		};
		geneOptions = new ControlListBox<Gene>(this, two.x(), two.y(), two.w(), two.h(), 10.0f) {
			@Override
			public IWidget createOption(final Gene value, final int y) {
				return new ControlTextOption<Gene>(getContent(), value, y);
			}
		};
		tabSystems.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				root = ((BreedingSystem) event.getValue()).getSpeciesRoot();
				template = root.getDefaultTemplate();
				refreshTemplate(null);
			}
		}.setOrigin(EventHandler.Origin.Self, tabSystems));
		geneList.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				final Map<IChromosomeType, List<IAllele>> map = Binnie.Genetics.getChromosomeMap(root);
				final List<Gene> opts = new ArrayList<Gene>();
				final IChromosomeType chromo = event.value != null ? ((Gene) event.value).getChromosome() : null;
				if (chromo != null)// fix NPE
					for (final IAllele allele : map.get(chromo)) {
						opts.add(new Gene(allele, chromo, root));
					}
				geneOptions.setOptions(opts);
			}
		}.setOrigin(EventHandler.Origin.Self, geneList));
		geneOptions.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.value == null) {
					return;
				}
				final IChromosomeType chromo = ((Gene) event.value).getChromosome();
				template[chromo.ordinal()] = ((Gene) event.value).getAllele();
				if (chromo == ((Gene) event.value).getSpeciesRoot().getKaryotypeKey()) {
					template = ((Gene) event.value).getSpeciesRoot().getTemplate(((Gene) event.value).getAllele().getUID());
				}
				refreshTemplate(chromo);
			}
		}.setOrigin(EventHandler.Origin.Self, geneOptions));
		panelPickup = new Panel(this, 16.0f, 140.0f, 60.0f, 42.0f, MinecraftGUI.PanelType.Black);
		refreshTemplate(null);
	}

	private void refreshTemplate(final IChromosomeType selection) {
		final List<Gene> genes = new ArrayList<Gene>();
		final IChromosomeType[] arr$;
		final IChromosomeType[] chromos = arr$ = Binnie.Genetics.getChromosomeMap(root).keySet().toArray(new IChromosomeType[0]);
		for (final IChromosomeType type : arr$) {
			final IAllele allele = template[type.ordinal()];
			if (allele == null) {
				throw new NullPointerException("Allele missing for Chromosome " + type.getName());
			}
			genes.add(new Gene(allele, type, root));
		}
		final Map<IChromosomeType, List<IAllele>> map = Binnie.Genetics.getChromosomeMap(root);
		geneList.setOptions(genes);
		if (selection != null) {
			geneList.setValue(new Gene(template[selection.ordinal()], selection, root));
		}
		else {
			geneOptions.setOptions(new ArrayList<Gene>());
		}
		refreshPickup();
	}

	private void refreshPickup() {
		panelPickup.deleteAllChildren();
		int i = 0;
		for (final int type : Binnie.Genetics.getSystem(root).getActiveTypes()) {
			final IIndividual ind = root.templateAsIndividual(template);
			ind.analyze();
			final ItemStack stack = root.getMemberStack(ind, type);
			final ControlItemDisplay display = new ControlItemDisplay(panelPickup, 4 + i % 3 * 18, 4 + i / 3 * 18);
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
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.recieveGuiNBT(side, player, name, nbt);
		if (side == Side.SERVER && name.equals("genesis")) {
			final ItemStack stack = ItemStack.loadItemStackFromNBT(nbt);
			final InventoryPlayer playerInv = player.inventory;
			if (stack == null) {
				return;
			}
			if (playerInv.getItemStack() == null) {
				playerInv.setItemStack(stack);
			}
			else if (playerInv.getItemStack().isItemEqual(stack) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), stack)) {
				final int fit = stack.getMaxStackSize() - (stack.stackSize + playerInv.getItemStack().stackSize);
				if (fit >= 0) {
					final ItemStack itemStack;
					final ItemStack rec = itemStack = stack;
					itemStack.stackSize += playerInv.getItemStack().stackSize;
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
