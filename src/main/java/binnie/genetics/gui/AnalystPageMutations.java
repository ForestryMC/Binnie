package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.EnumColor;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.apiculture.genetics.BeeDefinition;
import java.util.Collection;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageMutations extends ControlAnalystPage {
    public AnalystPageMutations(IWidget parent, IArea area, IIndividual ind, boolean isMaster) {
        super(parent, area);
        setColor(0x333300);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 18;
        BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
        IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
        Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
        Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
        if (ind instanceof IBee) {
            ItemStack hive = null;
            IAlleleSpecies primary = ind.getGenome().getPrimary();
            if (primary == ExtraBeeDefinition.WATER) {
                hive = new ItemStack(ExtraBees.hive, 1, 0);
            } else if (primary == ExtraBeeDefinition.ROCK) {
                hive = new ItemStack(ExtraBees.hive, 1, 1);
            } else if (primary == ExtraBeeDefinition.BASALT) {
                hive = new ItemStack(ExtraBees.hive, 1, 2);
            } else if (primary == BeeDefinition.FOREST.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 1);
            } else if (primary == BeeDefinition.MEADOWS.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 2);
            } else if (primary == BeeDefinition.MODEST.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 3);
            } else if (primary == BeeDefinition.TROPICAL.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 4);
            } else if (primary == BeeDefinition.ENDED.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 5);
            } else if (primary == BeeDefinition.WINTRY.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 6);
            } else if (primary == BeeDefinition.MARSHY.getGenome().getPrimary()) {
                hive = new ItemStack(Mods.forestry.block("beehives"), 1, 7);
            } else if (primary == BeeDefinition.STEADFAST.getGenome().getPrimary()) {
                hive = new ItemStack(Blocks.chest);
            }

            if (ind.getGenome().getPrimary()
                    == BeeDefinition.VALIANT.getGenome().getPrimary()) {
                new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.mutations.habitat"))
                        .setColor(getColor());

                y += 10;
                new ControlTextCentered(
                                this,
                                y,
                                EnumChatFormatting.ITALIC + I18N.localise("genetics.gui.analyst.mutations.habitat.any"))
                        .setColor(getColor());
                y += 22;
            } else if (ind.getGenome().getPrimary()
                    == BeeDefinition.MONASTIC.getGenome().getPrimary()) {
                new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.mutations.habitat"))
                        .setColor(getColor());

                y += 10;
                new ControlTextCentered(
                                this,
                                y,
                                EnumChatFormatting.ITALIC
                                        + I18N.localise("genetics.gui.analyst.mutations.habitat.villagers"))
                        .setColor(getColor());
                y += 22;
            } else if (hive != null) {
                new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.mutations.habitat"))
                        .setColor(getColor());

                y += 10;
                ControlItemDisplay display = new ControlItemDisplay(this, (w() - 16.0f) / 2.0f, y);
                if (ind.getGenome().getPrimary()
                        == BeeDefinition.STEADFAST.getGenome().getPrimary()) {
                    display.addTooltip(I18N.localise("genetics.gui.analyst.mutations.habitat.chest"));
                } else {
                    display.setTooltip();
                }

                display.setItemStack(hive);
                y += 24;
            }
        }

        float ox = (w() - 88.0f - 8.0f) / 2.0f;
        float dx = 0.0f;

        if (!resultant.isEmpty()) {
            if (resultant.size() == 1) {
                ox = (w() - 44.0f) / 2.0f;
            }
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.mutations.resultantMutations"))
                    .setColor(getColor());

            y += 10;
            for (IMutation mutation : resultant) {
                float specificChance = getSpecificChance(mutation, system);
                if (!isMaster && !isKnown(system, mutation)) {
                    new ControlUnknownMutation(this, ox + dx, y, 44.0f, 16.0f);
                } else {
                    new Control(this, ox + dx, y, 44.0f, 16.0f) {
                        @Override
                        public void initialise() {
                            IAlleleSpecies species0 = mutation.getAllele0();
                            IAlleleSpecies species2 = mutation.getAllele1();
                            String comb = species0.getName() + " + " + species2.getName();
                            addTooltip(comb);
                            String chance =
                                    getMutationColor(mutation.getBaseChance()).getCode()
                                            + I18N.localise("genetics.gui.analyst.mutations.chance", (int)
                                                    mutation.getBaseChance());
                            if (specificChance != mutation.getBaseChance()) {
                                chance += getMutationColor(specificChance).getCode() + " "
                                        + I18N.localise("genetics.gui.analyst.mutations.chance", (int) specificChance);
                            }

                            addTooltip(chance);
                            for (String s : mutation.getSpecialConditions()) {
                                addTooltip(s);
                            }
                        }

                        @Override
                        public void onRenderBackground() {
                            CraftGUI.render.item(
                                    new IPoint(0.0f, 0.0f),
                                    system.getDefaultMember(
                                            mutation.getAllele0().getUID()));
                            CraftGUI.render.item(
                                    new IPoint(28.0f, 0.0f),
                                    system.getDefaultMember(
                                            mutation.getAllele1().getUID()));
                            if (specificChance != mutation.getBaseChance()) {
                                CraftGUI.render.color(getMutationColor(mutation.getBaseChance())
                                        .getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd0.getIcon());
                                CraftGUI.render.color(
                                        getMutationColor(specificChance).getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd1.getIcon());
                            } else {
                                CraftGUI.render.color(getMutationColor(mutation.getBaseChance())
                                        .getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconAdd.getIcon());
                            }
                        }
                    };
                }
                dx = 52.0f - dx;
                if (dx == 0.0f || resultant.size() == 1) {
                    y += 18;
                }
            }

            if (dx != 0.0f && resultant.size() != 1) {
                y += 18;
            }
            y += 10;
        }

        ox = (w() - 88.0f - 8.0f) / 2.0f;
        dx = 0.0f;
        if (!further.isEmpty()) {
            if (further.size() == 1) {
                ox = (w() - 44.0f) / 2.0f;
            }
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.mutations.furtherMutations"))
                    .setColor(getColor());

            y += 10;
            for (IMutation mutation : further) {
                IAllele speciesComb = mutation.getPartner(speciesCurrent);
                float specificChance2 = getSpecificChance(mutation, system);
                if (!isMaster && !isKnown(system, mutation)) {
                    new ControlUnknownMutation(this, ox + dx, y, 44.0f, 16.0f);
                } else {
                    new Control(this, ox + dx, y, 44.0f, 16.0f) {
                        @Override
                        public void initialise() {
                            IAlleleSpecies species0 = (IAlleleSpecies) speciesComb;
                            IAlleleSpecies species2 = (IAlleleSpecies) mutation.getTemplate()[0];
                            addTooltip(species2.getName());
                            String comb = speciesCurrent.getName() + " + " + species0.getName();
                            addTooltip(comb);
                            String chance =
                                    getMutationColor(mutation.getBaseChance()).getCode()
                                            + I18N.localise("genetics.gui.analyst.mutations.chance", (int)
                                                    mutation.getBaseChance());
                            if (specificChance2 != mutation.getBaseChance()) {
                                chance = chance
                                        + getMutationColor(specificChance2).getCode() + " "
                                        + I18N.localise("genetics.gui.analyst.mutations.chance", (int) specificChance2);
                            }
                            addTooltip(chance);
                            for (String s : mutation.getSpecialConditions()) {
                                addTooltip(s);
                            }
                        }

                        @Override
                        public void onRenderBackground() {
                            CraftGUI.render.item(new IPoint(0.0f, 0.0f), system.getDefaultMember(speciesComb.getUID()));
                            CraftGUI.render.item(
                                    new IPoint(28.0f, 0.0f),
                                    system.getDefaultMember(mutation.getTemplate()[0].getUID()));
                            CraftGUI.render.color(
                                    getMutationColor(mutation.getBaseChance()).getColor());
                            if (specificChance2 != mutation.getBaseChance()) {
                                CraftGUI.render.color(getMutationColor(mutation.getBaseChance())
                                        .getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow0.getIcon());
                                CraftGUI.render.color(
                                        getMutationColor(specificChance2).getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow1.getIcon());
                            } else {
                                CraftGUI.render.color(getMutationColor(mutation.getBaseChance())
                                        .getColor());
                                CraftGUI.render.iconItem(new IPoint(14.0f, 0.0f), ModuleItem.iconArrow.getIcon());
                            }
                        }
                    };
                }

                dx = 52.0f - dx;
                if (dx == 0.0f || further.size() == 1) {
                    y += 18;
                }
            }

            if (dx != 0.0f && further.size() != 1) {
                y += 18;
            }
        }
        y += 8;
        setSize(new IPoint(w(), y));
    }

    private boolean isKnown(BreedingSystem system, IMutation mutation) {
        return system.getDiscoveredMutations(
                        getWindow().getWorld(), getWindow().getPlayer().getGameProfile())
                .contains(mutation);
    }

    private float getSpecificChance(IMutation mutation, BreedingSystem system) {
        return system.getChance(mutation, getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.mutations");
    }

    protected EnumColor getMutationColor(float percent) {
        if (percent >= 20.0f) {
            return EnumColor.DarkGreen;
        }
        if (percent >= 15.0f) {
            return EnumColor.Green;
        }
        if (percent >= 10.0f) {
            return EnumColor.Yellow;
        }
        if (percent >= 5.0f) {
            return EnumColor.Gold;
        }
        if (percent > 0.0f) {
            return EnumColor.Red;
        }
        return EnumColor.DarkRed;
    }

    static class ControlUnknownMutation extends Control {
        public ControlUnknownMutation(IWidget parent, float x, float y, float w, float h) {
            super(parent, x, y, w, h);
            addAttribute(WidgetAttribute.MOUSE_OVER);
            addTooltip(I18N.localise("genetics.gui.analyst.mutations.unknownMutation"));
        }

        @Override
        public void onRenderBackground() {
            CraftGUI.render.text(
                    getArea(),
                    TextJustification.MIDDLE_CENTER,
                    I18N.localise("genetics.gui.analyst.mutations.unknown"),
                    0xaaaaaa);
        }
    }
}
