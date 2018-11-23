package binnie.genetics.machine.craftgui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextEdit;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlTabIcon;
import binnie.core.gui.window.Panel;
import binnie.core.gui.window.WindowMachine;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;

public class WindowGeneBank extends WindowMachine {
	//public static IIcon[] iconsDNA;
	private final boolean master;
	private ControlGeneScroll genes;

	public WindowGeneBank(EntityPlayer player, IInventory inventory, Side side) {
		this(player, inventory, side, false);
	}

	public WindowGeneBank(EntityPlayer player, IInventory inventory, Side side, boolean master) {
		super(320, 224, player, inventory, side);
		this.master = master;
	}

	@Override
	public void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals("gene-select")) {
			Gene gene = new Gene(nbt.getCompoundTag("gene"));
			ItemStack held = this.getHeldItemStack();
			ItemStack converted = Engineering.addGene(held, gene);
			this.getPlayer().inventory.setItemStack(converted);
			this.getPlayer().inventory.markDirty();
			if (this.getPlayer() instanceof EntityPlayerMP) {
				((EntityPlayerMP) this.getPlayer()).sendContainerToPlayer(player.inventoryContainer);
			}
		}
	}

	@Override
	public void initialiseServer() {
		GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
		if (tracker != null) {
			tracker.synchToPlayer(this.getPlayer());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		this.addEventHandler(EventValueChanged.class, event -> {
			if (event.getValue() instanceof BreedingSystem) {
				WindowGeneBank.this.genes.setValue((IBreedingSystem) event.getValue());
			}
		});
		int x = 16;
		int y = 32;
		new ControlPlayerInventory(this, x, y);
		x += 124;
		int boxX = x;
		int geneBoxWidth = 120;
		new Panel(this, boxX + 24, 32, geneBoxWidth, 120, MinecraftGUI.PanelType.BLACK);
		new Panel(this, boxX + 24 + geneBoxWidth, 32, 14, 120, MinecraftGUI.PanelType.GRAY);
		ControlScrollableContent<ControlGeneScroll> scroll = new ControlScrollableContent<>(this, boxX + 24 + 2, 34, geneBoxWidth + 10, 116, 12);
		ControlTextEdit edit = new ControlTextEdit(this, boxX + 27 + geneBoxWidth - 70, 18, 80, 12);
		this.addEventHandler(EventTextEdit.class, EventHandlerOrigin.SELF, edit, event -> {
			String value = event.getValue();
			if (value == null) {
				value = "";
			}
			WindowGeneBank.this.genes.setFilter(value);
		});
		this.genes = new ControlGeneScroll(scroll, 1, 1, geneBoxWidth, 116);
		scroll.setScrollableContent(this.genes);
		this.genes.setGenes(Binnie.GENETICS.getFirstActiveSystem());
		ControlTabBar<IBreedingSystem> tabBar = new GeneBankTabBar(this, boxX);
		tabBar.setValue(Binnie.GENETICS.getFirstActiveSystem());
		boxX -= 8;
		ControlTabBar<String> infoTabs = new ControlTabBar<>(this, boxX + 8, 160, 16, 50, Alignment.LEFT, Arrays.asList("Info", "Stats", "Ranking"));
		Panel panelProject = new Panel(this, boxX + 24, 160, geneBoxWidth + 20, 50, MinecraftGUI.PanelType.BLACK);
		int totalGenes = 0;
		int seqGenes = 0;
		for (IBreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
			Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(system.getSpeciesRoot());
			for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
				totalGenes += entry.getValue().size();
				for (IAllele allele : entry.getValue()) {
					Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
					if (tracker.isSequenced(gene)) {
						++seqGenes;
					}
				}
			}
		}
		new ControlText(panelProject, new Point(4, 4), "§nFull Genome Project");
		new ControlText(panelProject, new Point(4, 18), "§oSequenced §r" + seqGenes + '/' + totalGenes + " §oGenes");
	}

	@Override
	public String getTitle() {
		return "Gene Bank";
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "GeneBank";
	}

	public boolean isMaster() {
		return master;
	}

	// TODO: why is this unused?
	public static class ChromosomeType {
		IChromosomeType chromosome;
		IBreedingSystem system;

		public ChromosomeType(IChromosomeType chromosome, IBreedingSystem system) {
			this.chromosome = chromosome;
			this.system = system;
		}

		@Override
		public String toString() {
			return this.system.getChromosomeName(this.chromosome);
		}
	}

	private static class GeneBankTabBar extends ControlTabBar<IBreedingSystem> {

		public GeneBankTabBar(WindowGeneBank windowGeneBank, int boxX) {
			super(windowGeneBank, boxX, 32, 24, 120, Alignment.LEFT, Binnie.GENETICS.getActiveSystems(), (x, y, w, h, value) -> new GeneBankTab(windowGeneBank, x, y, w, h, value));
		}

		private static class GeneBankTab extends ControlTabIcon<IBreedingSystem> {
			private final WindowGeneBank windowGeneBank;

			public GeneBankTab(WindowGeneBank windowGeneBank, int x, int y, int w, int h, IBreedingSystem value) {
				super(x, y, w, h, value);
				this.windowGeneBank = windowGeneBank;
			}

			@Override
			public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
				tooltip.add(this.getValue().toString());
				int totalGenes = 0;
				int seqGenes = 0;
				GeneTracker tracker = GeneTracker.getTracker(windowGeneBank.getWorld(), windowGeneBank.getUsername());
				Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(this.getValue().getSpeciesRoot());
				for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
					totalGenes += entry.getValue().size();
					for (IAllele allele : entry.getValue()) {
						Gene gene = new Gene(allele, entry.getKey(), this.getValue().getSpeciesRoot());
						if (tracker.isSequenced(gene)) {
							++seqGenes;
						}
					}
				}
				tooltip.add(seqGenes + '/' + totalGenes + " Genes");
			}
		}
	}
}
