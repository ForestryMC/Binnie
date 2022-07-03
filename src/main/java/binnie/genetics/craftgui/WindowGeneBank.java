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
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class WindowGeneBank extends WindowMachine {
    public boolean isNei;

    protected ControlGeneScroll genes;

    public WindowGeneBank(EntityPlayer player, IInventory inventory, Side side, boolean isNEI) {
        super(320, 224, player, inventory, side);
        isNei = isNEI;
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowGeneBank(player, inventory, side, false);
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        super.recieveGuiNBT(side, player, name, nbt);
        if (side != Side.SERVER || !name.equals("gene-select")) {
            return;
        }

        Gene gene = new Gene(nbt.getCompoundTag("gene"));
        if (gene.isCorrupted()) {
            return;
        }

        ItemStack held = getHeldItemStack();
        ItemStack converted = Engineering.addGene(held, gene);
        getPlayer().inventory.setItemStack(converted);
        getPlayer().inventory.markDirty();
        if (getPlayer() instanceof EntityPlayerMP) {
            ((EntityPlayerMP) getPlayer()).sendContainerToPlayer(player.inventoryContainer);
        }
    }

    @Override
    public void initialiseServer() {
        GeneTracker tracker = GeneTracker.getTracker(getWorld(), getUsername());
        if (tracker != null) {
            tracker.synchToPlayer(getPlayer());
        }
    }

    @Override
    public void initialiseClient() {
        setTitle(getTitle());
        addEventHandler(new EventValueChanged.Handler() {
            @Override
            public void onEvent(EventValueChanged event) {
                if (event.value instanceof BreedingSystem) {
                    genes.setValue((BreedingSystem) event.value);
                }
            }
        });

        int boxX;
        int x = 16;
        int y = 32;
        new ControlPlayerInventory(this, x, y);
        x += 124;
        boxX = x;
        int geneBoxWidth = 120;
        new Panel(this, boxX + 24, 32.0f, geneBoxWidth, 120.0f, MinecraftGUI.PanelType.Black);
        new Panel(this, boxX + 24 + geneBoxWidth, 32.0f, 14.0f, 120.0f, MinecraftGUI.PanelType.Gray);
        ControlScrollableContent scroll =
                new ControlScrollableContent(this, boxX + 24 + 2, 34.0f, geneBoxWidth + 10, 116.0f, 12.0f);
        ControlTextEdit edit = new ControlTextEdit(this, boxX + 27 + geneBoxWidth - 70, 18.0f, 80.0f, 12.0f);
        addEventHandler(
                new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(EventTextEdit event) {
                        genes.setFilter(event.getValue());
                    }
                }.setOrigin(EventHandler.Origin.Self, edit));
        scroll.setScrollableContent(genes = new ControlGeneScroll(scroll, 1.0f, 1.0f, geneBoxWidth, 116.0f));
        genes.setGenes(Binnie.Genetics.beeBreedingSystem);
        ControlTabBar<BreedingSystem> tabBar =
                new ControlTabBar<BreedingSystem>(this, boxX, 32.0f, 24.0f, 120.0f, Position.LEFT) {
                    @Override
                    public ControlTab<BreedingSystem> createTab(
                            float x, float y, float w, float h, BreedingSystem value) {
                        return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
                            @Override
                            public void getTooltip(Tooltip tooltip) {
                                tooltip.add(getValue().toString());
                                int totalGenes = 0;
                                int seqGenes = 0;
                                GeneTracker tracker = GeneTracker.getTracker(getWorld(), getUsername());
                                Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(
                                        getValue().getSpeciesRoot());
                                for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
                                    totalGenes += entry.getValue().size();
                                    for (IAllele allele : entry.getValue()) {
                                        Gene gene = new Gene(
                                                allele,
                                                entry.getKey(),
                                                getValue().getSpeciesRoot());
                                        if (tracker.isSequenced(gene)) {
                                            seqGenes++;
                                        }
                                    }
                                }
                                tooltip.add(I18N.localise(
                                        "genetics.gui.geneBank.sequencedGenes.short", seqGenes, totalGenes));
                            }
                        };
                    }
                };

        tabBar.setValues(Binnie.Genetics.getActiveSystems());
        tabBar.setValue(Binnie.Genetics.beeBreedingSystem);
        boxX -= 8;
        ControlTabBar<String> infoTabs = new ControlTabBar<>(this, boxX + 8, 160.0f, 16.0f, 50.0f, Position.LEFT);
        infoTabs.setValues(Arrays.asList(
                I18N.localise("genetics.gui.geneBank.stats"), I18N.localise("genetics.gui.geneBank.ranking")));
        infoTabs.setValue(I18N.localise("genetics.gui.geneBank.info"));
        Panel panelProject = new Panel(this, boxX + 24, 160.0f, geneBoxWidth + 20, 50.0f, MinecraftGUI.PanelType.Black);
        int totalGenes = 0;
        int seqGenes = 0;

        for (BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
            GeneTracker tracker = GeneTracker.getTracker(getWorld(), getUsername());
            Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
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

        new ControlText(
                panelProject,
                new IPoint(4.0f, 4.0f),
                EnumChatFormatting.UNDERLINE + I18N.localise("genetics.gui.geneBank.project"));
        new ControlText(
                panelProject,
                new IPoint(4.0f, 18.0f),
                EnumChatFormatting.ITALIC.toString()
                        + EnumChatFormatting.YELLOW
                        + I18N.localise("genetics.gui.geneBank.sequencedGenes", seqGenes, totalGenes));
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.geneBank");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "GeneBank";
    }
}
