package binnie.genetics.machine.craftgui;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.controls.tab.ControlTab;
import binnie.core.craftgui.controls.tab.ControlTabBar;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WindowGeneBank extends WindowMachine {
	//	public static IIcon[] iconsDNA;
	public boolean isNei;
	ControlGeneScroll genes;

	public WindowGeneBank(final EntityPlayer player, final IInventory inventory, final Side side) {
		this(player, inventory, side, false);
	}

	public WindowGeneBank(final EntityPlayer player, final IInventory inventory, final Side side, final boolean isNEI) {
		super(320, 224, player, inventory, side);
		this.isNei = isNEI;
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals("gene-select")) {
			final Gene gene = new Gene(nbt.getCompoundTag("gene"));
			if (gene != null && !false) {
				final ItemStack held = this.getHeldItemStack();
				final ItemStack converted = Engineering.addGene(held, gene);
				this.getPlayer().inventory.setItemStack(converted);
				this.getPlayer().inventory.markDirty();
				if (this.getPlayer() instanceof EntityPlayerMP) {
					((EntityPlayerMP) this.getPlayer()).sendContainerToPlayer(player.inventoryContainer);
				}
			}
		}
	}

	@Override
	public void initialiseServer() {
		final GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
		if (tracker != null) {
			tracker.synchToPlayer(this.getPlayer());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		this.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.value instanceof BreedingSystem) {
					WindowGeneBank.this.genes.setValue((BreedingSystem) event.value);
				}
			}
		});
		int boxX = 100;
		int x = 16;
		final int y = 32;
		new ControlPlayerInventory(this, x, y);
		x += 124;
		boxX = x;
		final int geneBoxWidth = 120;
		new Panel(this, boxX + 24, 32, geneBoxWidth, 120, MinecraftGUI.PanelType.Black);
		new Panel(this, boxX + 24 + geneBoxWidth, 32, 14, 120, MinecraftGUI.PanelType.Gray);
		final ControlScrollableContent<ControlGeneScroll> scroll = new ControlScrollableContent<>(this, boxX + 24 + 2, 34, geneBoxWidth + 10, 116, 12);
		final ControlTextEdit edit = new ControlTextEdit(this, boxX + 27 + geneBoxWidth - 70, 18, 80, 12);
		this.addEventHandler(new EventTextEdit.Handler() {
			@Override
			public void onEvent(final EventTextEdit event) {
				String value = event.getValue();
				if (value == null) {
					value = "";
				}
				WindowGeneBank.this.genes.setFilter(value);
			}
		}.setOrigin(EventHandler.Origin.Self, edit));
		this.genes = new ControlGeneScroll(scroll, 1, 1, geneBoxWidth, 116);
		scroll.setScrollableContent(this.genes);
		this.genes.setGenes(Binnie.GENETICS.beeBreedingSystem);
		final ControlTabBar<BreedingSystem> tabBar = new ControlTabBar<BreedingSystem>(this, boxX, 32, 24, 120, Position.LEFT, Binnie.GENETICS.getActiveSystems()) {
			@Override
			public ControlTab<BreedingSystem> createTab(final int x, final int y, final int w, final int h, final BreedingSystem value) {
				return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
					@Override
					public void getTooltip(final Tooltip tooltip) {
						tooltip.add(this.getValue().toString());
						int totalGenes = 0;
						int seqGenes = 0;
						final GeneTracker tracker = GeneTracker.getTracker(WindowGeneBank.this.getWorld(), WindowGeneBank.this.getUsername());
						final Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(this.getValue().getSpeciesRoot());
						for (final Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
							totalGenes += entry.getValue().size();
							for (final IAllele allele : entry.getValue()) {
								final Gene gene = new Gene(allele, entry.getKey(), this.getValue().getSpeciesRoot());
								if (tracker.isSequenced(gene)) {
									++seqGenes;
								}
							}
						}
						tooltip.add("" + seqGenes + "/" + totalGenes + " Genes");
					}
				};
			}
		};
		tabBar.setValue(Binnie.GENETICS.beeBreedingSystem);
		boxX -= 8;
		final ControlTabBar<String> infoTabs = new ControlTabBar<>(this, boxX + 8, 160, 16, 50, Position.LEFT, Arrays.asList("Info", "Stats", "Ranking"));
		final Panel panelProject = new Panel(this, boxX + 24, 160, geneBoxWidth + 20, 50, MinecraftGUI.PanelType.Black);
		int totalGenes = 0;
		int seqGenes = 0;
		for (final BreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			final GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
			final Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(system.getSpeciesRoot());
			for (final Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
				totalGenes += entry.getValue().size();
				for (final IAllele allele : entry.getValue()) {
					final Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
					if (tracker.isSequenced(gene)) {
						++seqGenes;
					}
				}
			}
		}
		new ControlText(panelProject, new Point(4, 4), "§nFull Genome Project");
		new ControlText(panelProject, new Point(4, 18), "§oSequenced §r" + seqGenes + "/" + totalGenes + " §oGenes");
	}

	@Override
	public String getTitle() {
		return "Gene Bank";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "GeneBank";
	}

	public static class ChromosomeType {
		IChromosomeType chromosome;
		BreedingSystem system;

		public ChromosomeType(final IChromosomeType chromosome, final BreedingSystem system) {
			this.chromosome = chromosome;
			this.system = system;
		}

		@Override
		public String toString() {
			return this.system.getChromosomeName(this.chromosome);
		}
	}
}
