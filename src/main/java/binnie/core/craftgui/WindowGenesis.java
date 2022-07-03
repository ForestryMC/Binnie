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
import binnie.core.util.I18N;
import binnie.genetics.gui.ControlGenesisOption;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WindowGenesis extends Window {
    private ISpeciesRoot root;
    private IAllele[] template;
    private ControlListBox<Gene> geneList;
    private ControlListBox<Gene> geneOptions;
    private Panel panelPickup;

    public WindowGenesis(EntityPlayer player, IInventory inventory, Side side) {
        super(342.0f, 228.0f, player, inventory, side);
    }

    @Override
    protected AbstractMod getMod() {
        return BinnieCore.instance;
    }

    @Override
    protected String getName() {
        return I18N.localise("binniecore.gui.genesis");
    }

    @Override
    public void initialiseClient() {
        new ControlPlayerInventory(this);
        setTitle(getName());

        ControlTabBar<BreedingSystem> tabSystems = new BreedingSystemControlTabBar();
        tabSystems.setValues(Binnie.Genetics.getActiveSystems());
        root = Binnie.Genetics.getActiveSystems().iterator().next().getSpeciesRoot();
        template = root.getDefaultTemplate();
        IArea one = new IArea(32.0f, 28.0f, 170.0f, 100.0f);
        IArea two = new IArea(214.0f, 28.0f, 100.0f, 100.0f);
        new Panel(this, one.outset(1), MinecraftGUI.PanelType.Black);
        new Panel(this, two.outset(1), MinecraftGUI.PanelType.Black);

        geneList = new ControlListBox<Gene>(this, one.x(), one.y(), one.w(), one.h(), 10.0f) {
            @Override
            public IWidget createOption(Gene value, int y) {
                return new ControlGenesisOption(getContent(), value, y);
            }
        };

        geneOptions = new ControlListBox<Gene>(this, two.x(), two.y(), two.w(), two.h(), 10.0f) {
            @Override
            public IWidget createOption(Gene value, int y) {
                return new ControlTextOption<>(getContent(), value, y);
            }
        };

        tabSystems.addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        root = ((BreedingSystem) event.getValue()).getSpeciesRoot();
                        template = root.getDefaultTemplate();
                        refreshTemplate(null);
                    }
                }.setOrigin(EventHandler.Origin.Self, tabSystems));

        geneList.addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        Map<IChromosomeType, List<IAllele>> map = Binnie.Genetics.getChromosomeMap(root);
                        List<Gene> opts = new ArrayList<>();
                        IChromosomeType chromo = event.value != null ? ((Gene) event.value).getChromosome() : null;
                        if (chromo != null) { // fix NPE
                            for (IAllele allele : map.get(chromo)) {
                                opts.add(new Gene(allele, chromo, root));
                            }
                        }
                        geneOptions.setOptions(opts);
                    }
                }.setOrigin(EventHandler.Origin.Self, geneList));

        geneOptions.addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        if (event.value == null) {
                            return;
                        }

                        IChromosomeType chromo = ((Gene) event.value).getChromosome();
                        template[chromo.ordinal()] = ((Gene) event.value).getAllele();
                        if (chromo == ((Gene) event.value).getSpeciesRoot().getKaryotypeKey()) {
                            template = ((Gene) event.value)
                                    .getSpeciesRoot()
                                    .getTemplate(
                                            ((Gene) event.value).getAllele().getUID());
                        }
                        refreshTemplate(chromo);
                    }
                }.setOrigin(EventHandler.Origin.Self, geneOptions));
        panelPickup = new Panel(this, 16.0f, 140.0f, 60.0f, 42.0f, MinecraftGUI.PanelType.Black);
        refreshTemplate(null);
    }

    private void refreshTemplate(IChromosomeType selection) {
        List<Gene> genes = new ArrayList<>();
        IChromosomeType[] chromos =
                Binnie.Genetics.getChromosomeMap(root).keySet().toArray(new IChromosomeType[0]);

        for (IChromosomeType type : chromos) {
            IAllele allele = template[type.ordinal()];
            if (allele == null) {
                throw new NullPointerException("Allele missing for Chromosome " + type.getName());
            }
            genes.add(new Gene(allele, type, root));
        }

        geneList.setOptions(genes);
        if (selection != null) {
            geneList.setValue(new Gene(template[selection.ordinal()], selection, root));
        } else {
            geneOptions.setOptions(new ArrayList<>());
        }
        refreshPickup();
    }

    private void refreshPickup() {
        panelPickup.deleteAllChildren();
        int i = 0;
        for (int type : Binnie.Genetics.getSystem(root).getActiveTypes()) {
            IIndividual ind = root.templateAsIndividual(template);
            ind.analyze();
            ItemStack stack = root.getMemberStack(ind, type);
            ControlItemDisplay display = new ControlItemDisplay(panelPickup, 4 + i % 3 * 18, 4 + i / 3 * 18);
            display.setItemStack(stack);
            display.setTooltip();
            display.addEventHandler(new MouseDownHandler(stack).setOrigin(EventHandler.Origin.Self, display));
            i++;
        }
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        super.recieveGuiNBT(side, player, name, nbt);
        if (side == Side.SERVER && name.equals("genesis")) {
            ItemStack stack = ItemStack.loadItemStackFromNBT(nbt);
            InventoryPlayer playerInv = player.inventory;
            if (stack == null) {
                return;
            }

            if (playerInv.getItemStack() == null) {
                playerInv.setItemStack(stack);
            } else if (playerInv.getItemStack().isItemEqual(stack)
                    && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), stack)) {
                int fit = stack.getMaxStackSize() - (stack.stackSize + playerInv.getItemStack().stackSize);
                if (fit >= 0) {
                    ItemStack itemStack;
                    ItemStack rec = itemStack = stack;
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

    private static class MouseDownHandler extends EventMouse.Down.Handler {
        private final ItemStack stack;

        public MouseDownHandler(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public void onEvent(EventMouse.Down event) {
            NBTTagCompound nbt = new NBTTagCompound();
            stack.writeToNBT(nbt);
            Window.get(event.getOrigin()).sendClientAction("genesis", nbt);
        }
    }

    private class BreedingSystemControlTabBar extends ControlTabBar<BreedingSystem> {
        public BreedingSystemControlTabBar() {
            super(WindowGenesis.this, 8.0f, 28.0f, 23.0f, 100.0f, Position.LEFT);
        }

        @Override
        public ControlTab<BreedingSystem> createTab(float x, float y, float w, float h, BreedingSystem value) {
            return new ControlTabIcon<BreedingSystem>(this, x, y, w, h, value) {
                @Override
                public ItemStack getItemStack() {
                    int type = value.getDefaultType();
                    IIndividual ind = value.getDefaultIndividual();
                    return value.getSpeciesRoot().getMemberStack(ind, type);
                }

                @Override
                public String getName() {
                    return value.getName();
                }

                @Override
                public int getOutlineColor() {
                    return value.getColor();
                }

                @Override
                public boolean hasOutline() {
                    return true;
                }
            };
        }
    }
}
