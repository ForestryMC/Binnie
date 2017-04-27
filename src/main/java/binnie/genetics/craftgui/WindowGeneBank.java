// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

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
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WindowGeneBank extends WindowMachine
{
	public static IIcon[] iconsDNA;
	public boolean isNei;
	ControlGeneScroll genes;

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.recieveGuiNBT(side, player, name, nbt);
		if (side == Side.SERVER && name.equals("gene-select")) {
			final Gene gene = new Gene(nbt.getCompoundTag("gene"));
			if (gene != null && !gene.isCorrupted()) {
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

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowGeneBank(player, inventory, side, false);
	}

	public WindowGeneBank(final EntityPlayer player, final IInventory inventory, final Side side, final boolean isNEI) {
		super(320, 224, player, inventory, side);
		this.isNei = isNEI;
	}

	@Override
	public void initialiseServer() {
		final GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
		if (tracker != null) {
			tracker.synchToPlayer(this.getPlayer());
		}
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Gene Bank");
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
		new Panel(this, boxX + 24, 32.0f, geneBoxWidth, 120.0f, MinecraftGUI.PanelType.Black);
		new Panel(this, boxX + 24 + geneBoxWidth, 32.0f, 14.0f, 120.0f, MinecraftGUI.PanelType.Gray);
		final ControlScrollableContent scroll = new ControlScrollableContent(this, boxX + 24 + 2, 34.0f, geneBoxWidth + 10, 116.0f, 12.0f);
		final ControlTextEdit edit = new ControlTextEdit(this, boxX + 27 + geneBoxWidth - 70, 18.0f, 80.0f, 12.0f);
		this.addEventHandler(new EventTextEdit.Handler() {
			@Override
			public void onEvent(final EventTextEdit event) {
				WindowGeneBank.this.genes.setFilter(event.getValue());
			}
		}.setOrigin(EventHandler.Origin.Self, edit));
		scroll.setScrollableContent(this.genes = new ControlGeneScroll(scroll, 1.0f, 1.0f, geneBoxWidth, 116.0f));
		this.genes.setGenes(Binnie.Genetics.beeBreedingSystem);
		final ControlTabBar<BreedingSystem> tabBar = new ControlTabBar<BreedingSystem>(this, boxX, 32.0f, 24.0f, 120.0f, Position.Left) {
			@Override
			public ControlTab<BreedingSystem> createTab(final float x, final float y, final float w, final float h, final BreedingSystem value) {
				return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
					@Override
					public void getTooltip(final Tooltip tooltip) {
						tooltip.add(this.getValue().toString());
						int totalGenes = 0;
						int seqGenes = 0;
						final GeneTracker tracker = GeneTracker.getTracker(WindowGeneBank.this.getWorld(), WindowGeneBank.this.getUsername());
						final Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(this.getValue().getSpeciesRoot());
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
		tabBar.setValues(Binnie.Genetics.getActiveSystems());
		tabBar.setValue(Binnie.Genetics.beeBreedingSystem);
		boxX -= 8;
		final ControlTabBar<String> infoTabs = new ControlTabBar<String>(this, boxX + 8, 160.0f, 16.0f, 50.0f, Position.Left);
		infoTabs.setValues(Arrays.asList("Stats", "Ranking"));
		infoTabs.setValue("Info");
		final Panel panelProject = new Panel(this, boxX + 24, 160.0f, geneBoxWidth + 20, 50.0f, MinecraftGUI.PanelType.Black);
		int totalGenes = 0;
		int seqGenes = 0;
		for (final BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
			final GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
			final Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
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
		new ControlText(panelProject, new IPoint(4.0f, 4.0f), EnumChatFormatting.UNDERLINE + "Full Genome Project");
		new ControlText(panelProject, new IPoint(4.0f, 18.0f), EnumChatFormatting.ITALIC.toString() + EnumChatFormatting.YELLOW + "quenced " + EnumChatFormatting.RESET + seqGenes + "/" + totalGenes + " " + EnumChatFormatting.ITALIC + "Genes");
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
	protected String getName() {
		return "GeneBank";
	}

	public static class ChromosomeType
	{
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
