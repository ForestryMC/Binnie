package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class AnalystPageGenome extends ControlAnalystPage {
    boolean active;

    public AnalystPageGenome(IWidget parent, IArea area, boolean active, IIndividual ind) {
        super(parent, area);
        this.active = active;
        setColor(0x006699);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());
        y += 16;
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
        BreedingSystem system = Binnie.Genetics.getSystem(root);
        Control scaled = new Control(this, 0.0f, y, 0.0f, 0.0f) {
            @Override
            public void onRenderBackground() {
                GL11.glPushMatrix();
                GL11.glTranslatef(10.0f, -15.0f, 0.0f);
                GL11.glScalef(0.9f, 0.95f, 1.0f);
            }

            @Override
            public void onRenderForeground() {
                GL11.glPopMatrix();
            }
        };

        for (IChromosomeType chromo : system.getActiveKaryotype()) {
            IAllele allele = active
                    ? ind.getGenome().getActiveAllele(chromo)
                    : ind.getGenome().getInactiveAllele(chromo);
            String alleleName = system.getAlleleName(chromo, allele);
            float height = CraftGUI.render.textHeight(alleleName, w() / 2.0f - 2.0f);
            new ControlText(
                            scaled,
                            new IArea(0.0f, y + (height - 9.0f) / 2.0f, w() / 2.0f - 2.0f, 0.0f),
                            system.getChromosomeShortName(chromo) + " :",
                            TextJustification.TOP_RIGHT)
                    .setColor(getColor());
            new ControlText(
                            scaled,
                            new IArea(w() / 2.0f + 2.0f, y, w() / 2.0f - 2.0f, 0.0f),
                            alleleName,
                            TextJustification.TOP_LEFT)
                    .setColor(getColor());
            y += (int) (3.0f + height);
        }
        setSize(new IPoint(w(), y + 8));
    }

    @Override
    public String getTitle() {
        if (active) {
            return I18N.localise("genetics.gui.analyst.genome.active");
        }
        return I18N.localise("genetics.gui.analyst.genome.inactive");
    }
}
