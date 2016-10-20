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
import binnie.craftgui.resource.StyleSheet;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.resource.minecraft.PaddedTexture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.extrabees.gui.punnett.ExtraBeeGUITexture;
import binnie.genetics.gui.ControlChromosome;
import binnie.genetics.machine.Analyser;
import forestry.api.genetics.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

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
    private ItemStack prev;

    public WindowFieldKit(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(280.0f, 230.0f, player, inventory, side);
        this.glassOffsetX = 0.0f;
        this.glassOffsetY = 0.0f;
        this.glassVX = 0.0f;
        this.glassVY = 0.0f;
        this.glassRand = new Random();
        this.analyseProgress = 1.0f;
        this.isAnalysing = false;
        this.info = new HashMap<IChromosomeType, String>();
        this.prev = null;
    }

    @Override
    protected AbstractMod getMod() {
        return BinnieCore.instance;
    }

    @Override
    protected String getName() {
        return "Field Kit";
    }

    private void setupValidators() {
        this.getWindowInventory().setValidator(0, new SlotValidator(null) {
            @Override
            public boolean isValid(final ItemStack object) {
                return AlleleManager.alleleRegistry.isIndividual(object) || Binnie.Genetics.getConversion(object) != null;
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
        CraftGUI.Render.stylesheet(new StyleSheetPunnett());
        this.getWindowInventory().createSlot(0);
        this.getWindowInventory().createSlot(1);
        this.setupValidators();
        new ControlPlayerInventory(this);
        final IPoint handGlass = new IPoint(16.0f, 32.0f);
        this.GlassControl = new ControlImage(this, handGlass.x(), handGlass.y(), new StandardTexture(0, 160, 96, 96, ExtraBeeTexture.GUIPunnett));
        new ControlSlot(this, handGlass.x() + 54.0f, handGlass.y() + 26.0f).assign(InventoryType.Window, 0);
        new ControlSlot(this, 208.0f, 8.0f).assign(InventoryType.Window, 1);
        (this.text = new ControlText(this, new IPoint(232.0f, 13.0f), "Paper")).setColour(2236962);
        (this.text = new ControlText(this, new IArea(0.0f, 120.0f, this.w(), 24.0f), "", TextJustification.MiddleCenter)).setColour(2236962);
        this.chromo = new ControlChromosome(this, 150.0f, 24.0f);
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
            if (this.analyseProgress >= 1.0f) {
                this.isAnalysing = false;
                this.analyseProgress = 1.0f;
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
        this.glassOffsetX *= 1.0f - this.analyseProgress;
        this.glassOffsetY += this.glassVY;
        this.glassOffsetY *= 1.0f - this.analyseProgress;
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
            if (!Binnie.Genetics.isInvalidChromosome(type)) {
                final IAllele allele = ind.getGenome().getActiveAllele(type);
                final List<String> infos = new ArrayList<String>();
                int i = 0;
                for (String pref = root.getUID() + ".fieldkit." + type.getName().toLowerCase() + "."; Binnie.Language.canLocalise(pref + i); ++i) {
                    infos.add(Binnie.Language.localise(pref + i));
                }
                String text = Binnie.Genetics.getSystem(root).getAlleleName(type, allele);
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
                    this.analyseProgress = 1.0f;
                } else {
                    this.startAnalysing();
                    this.chromo.setRoot(null);
                    if (this.damageKit()) {
                        return;
                    }
                }
            } else if (item != null) {
                this.isAnalysing = false;
                this.analyseProgress = 1.0f;
                this.refreshSpecies();
                if (this.damageKit()) {
                    return;
                }
            } else {
                this.isAnalysing = false;
                this.analyseProgress = 1.0f;
                this.chromo.setRoot(null);
            }
        }
    }

    private boolean damageKit() {
        return false;
    }

    private void startAnalysing() {
        this.glassVX = 0.0f;
        this.glassVY = 0.0f;
        this.glassOffsetX = 0.0f;
        this.glassOffsetY = 0.0f;
        this.isAnalysing = true;
        this.analyseProgress = 0.0f;
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

    static class StyleSheetPunnett extends StyleSheet {
        public StyleSheetPunnett() {
            this.textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, ExtraBeeTexture.GUIPunnett, 32, 32, 32, 32));
            this.textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, ExtraBeeTexture.GUIPunnett));
            this.textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            this.textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            this.textures.put(CraftGUITexture.HelpButton, new StandardTexture(178, 0, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
            this.textures.put(CraftGUITexture.InfoButton, new StandardTexture(178, 16, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
        }
    }
}
