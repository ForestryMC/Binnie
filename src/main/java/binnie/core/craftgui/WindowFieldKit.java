package binnie.core.craftgui;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlImage;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.resource.StyleSheet;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.PaddedTexture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.extrabees.gui.ExtraBeeGUITexture;
import binnie.genetics.gui.ControlChromosome;
import binnie.genetics.machine.analyser.Analyser;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WindowFieldKit extends Window {
    private float glassOffsetX;
    private float glassOffsetY;
    private float glassVX;
    private float glassVY;
    private Random glassRand;
    private Control GlassControl;
    private ControlChromosome chromo;
    private ControlText text;
    private float analyseProgress;
    private boolean isAnalysing;
    private Map<IChromosomeType, String> info;

    public WindowFieldKit(EntityPlayer player, IInventory inventory, Side side) {
        super(280.0f, 230.0f, player, inventory, side);
        glassOffsetX = 0.0f;
        glassOffsetY = 0.0f;
        glassVX = 0.0f;
        glassVY = 0.0f;
        glassRand = new Random();
        analyseProgress = 1.0f;
        isAnalysing = false;
        info = new HashMap<>();
    }

    @Override
    protected AbstractMod getMod() {
        return BinnieCore.instance;
    }

    @Override
    protected String getName() {
        return I18N.localise("binniecore.gui.fieldKit");
    }

    private void setupValidators() {
        getWindowInventory().setValidator(0, new SlotValidator(null) {
            @Override
            public boolean isValid(ItemStack object) {
                return AlleleManager.alleleRegistry.isIndividual(object)
                        || Binnie.Genetics.getConversion(object) != null;
            }

            @Override
            public String getTooltip() {
                return I18N.localise("binniecore.gui.tooltip.individual");
            }
        });

        getWindowInventory().setValidator(1, new SlotValidator(null) {
            @Override
            public boolean isValid(ItemStack object) {
                return object.getItem() == Items.paper;
            }

            @Override
            public String getTooltip() {
                return I18N.localise("binniecore.gui.tooltip.paper");
            }
        });
        getWindowInventory().disableAutoDispense(1);
    }

    @Override
    public void initialiseClient() {
        setTitle(getName());
        CraftGUI.render.stylesheet(new StyleSheetPunnett());
        getWindowInventory().createSlot(0);
        getWindowInventory().createSlot(1);
        setupValidators();
        new ControlPlayerInventory(this);
        IPoint handGlass = new IPoint(16.0f, 32.0f);
        GlassControl = new ControlImage(
                this, handGlass.x(), handGlass.y(), new StandardTexture(0, 160, 96, 96, ExtraBeeTexture.GUIPunnett));
        new ControlSlot(this, handGlass.x() + 54.0f, handGlass.y() + 26.0f).assign(InventoryType.Window, 0);
        new ControlSlot(this, 208.0f, 8.0f).assign(InventoryType.Window, 1);
        (text = new ControlText(this, new IPoint(232.0f, 13.0f), I18N.localise("binniecore.gui.tooltip.paper")))
                .setColor(0x222222);
        (text = new ControlText(this, new IArea(0.0f, 120.0f, w(), 24.0f), "", TextJustification.MIDDLE_CENTER))
                .setColor(0x222222);
        chromo = new ControlChromosome(this, 150.0f, 24.0f);

        addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        IChromosomeType type = (IChromosomeType) event.getValue();
                        if (type != null && info.containsKey(type)) {
                            String t = info.get(type);
                            text.setValue(t);
                        } else {
                            text.setValue("");
                        }
                    }
                }.setOrigin(EventHandler.Origin.DirectChild, chromo));
    }

    @Override
    public void initialiseServer() {
        ItemStack kit = getPlayer().getHeldItem();
        int sheets = 64 - kit.getItemDamage();
        if (sheets != 0) {
            getWindowInventory().setInventorySlotContents(1, new ItemStack(Items.paper, sheets));
        }
        setupValidators();
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        if (isAnalysing) {
            analyseProgress += 0.01f;
            if (analyseProgress >= 1.0f) {
                isAnalysing = false;
                analyseProgress = 1.0f;
                ItemStack stack = getWindowInventory().getStackInSlot(0);
                if (stack != null) {
                    sendClientAction("analyse", new NBTTagCompound());
                }
                refreshSpecies();
            }
        }

        glassVX += glassRand.nextFloat() - 0.5f - glassOffsetX * 0.2f;
        glassVY += glassRand.nextFloat() - 0.5f - glassOffsetY * 0.2f;
        glassOffsetX += glassVX;
        glassOffsetX *= 1.0f - analyseProgress;
        glassOffsetY += glassVY;
        glassOffsetY *= 1.0f - analyseProgress;
        GlassControl.setOffset(new IPoint(glassOffsetX, glassOffsetY));
    }

    private void refreshSpecies() {
        ItemStack item = getWindowInventory().getStackInSlot(0);
        if (item == null || !AlleleManager.alleleRegistry.isIndividual(item)) {
            return;
        }

        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(item);
        if (ind == null) {
            return;
        }

        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(item);
        chromo.setRoot(root);
        Random rand = new Random();
        info.clear();
        for (IChromosomeType type : root.getKaryotype()) {
            if (!Binnie.Genetics.isInvalidChromosome(type)) {
                IAllele allele = ind.getGenome().getActiveAllele(type);
                List<String> infos = new ArrayList<>();

                int i = 0;
                for (String pref = root.getUID() + ".fieldkit." + type.getName().toLowerCase() + ".";
                        I18N.canLocalise(pref + i);
                        ++i) {
                    infos.add(I18N.localise(pref + i));
                }

                String text = Binnie.Genetics.getSystem(root).getAlleleName(type, allele);
                if (!infos.isEmpty()) {
                    text = infos.get(rand.nextInt(infos.size()));
                }

                info.put(type, text);
                chromo.setRoot(root);
            }
        }
    }

    @Override
    public void onWindowInventoryChanged() {
        super.onWindowInventoryChanged();
        if (isServer()) {
            ItemStack kit = getPlayer().getHeldItem();
            if (kit == null || !kit.getItem().equals(BinnieCore.fieldKit)) {
                return;
            }

            int sheets = 64 - kit.getItemDamage();
            int size = (getWindowInventory().getStackInSlot(1) == null)
                    ? 0
                    : getWindowInventory().getStackInSlot(1).stackSize;
            if (sheets != size) {
                kit.setItemDamage(64 - size);
            }
            ((EntityPlayerMP) getPlayer()).updateHeldItem();
        }

        if (!isClient()) {
            return;
        }

        ItemStack item = getWindowInventory().getStackInSlot(0);
        text.setValue("");

        if (item != null && !Analyser.isAnalysed(item)) {
            if (getWindowInventory().getStackInSlot(1) == null) {
                text.setValue(I18N.localise("binniecore.gui.tooltip.noPaper"));
                isAnalysing = false;
                analyseProgress = 1.0f;
            } else {
                startAnalysing();
                chromo.setRoot(null);
            }
        } else if (item != null) {
            isAnalysing = false;
            analyseProgress = 1.0f;
            refreshSpecies();
        } else {
            isAnalysing = false;
            analyseProgress = 1.0f;
            chromo.setRoot(null);
        }
    }

    private void startAnalysing() {
        glassVX = 0.0f;
        glassVY = 0.0f;
        glassOffsetX = 0.0f;
        glassOffsetY = 0.0f;
        isAnalysing = true;
        analyseProgress = 0.0f;
    }

    @Override
    public boolean showHelpButton() {
        return true;
    }

    @Override
    public String showInfoButton() {
        return I18N.localise("binniecore.gui.fieldKit.info");
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        super.recieveGuiNBT(side, player, name, nbt);
        if (side != Side.SERVER || !name.equals("analyse")) {
            return;
        }

        ItemStack stack = getWindowInventory().getStackInSlot(0);
        if (stack == null) {
            return;
        }

        getWindowInventory().setInventorySlotContents(0, Analyser.analyse(stack));
        getWindowInventory().decrStackSize(1, 1);
    }

    static class StyleSheetPunnett extends StyleSheet {
        public StyleSheetPunnett() {
            textures.put(
                    CraftGUITexture.Window,
                    new PaddedTexture(0, 0, 160, 160, 0, ExtraBeeTexture.GUIPunnett, 32, 32, 32, 32));
            textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, ExtraBeeTexture.GUIPunnett));
            textures.put(
                    ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            textures.put(
                    ExtraBeeGUITexture.Chromosome2,
                    new StandardTexture(160, 52, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            textures.put(
                    CraftGUITexture.HelpButton, new StandardTexture(178, 0, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            textures.put(
                    CraftGUITexture.InfoButton, new StandardTexture(178, 16, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
        }
    }
}
