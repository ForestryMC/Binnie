package binnie.genetics.gui.analyst;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.genetics.Genetics;
import binnie.genetics.api.analyst.AnalystConstants;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;

public class ControlResultantMutation extends ControlMutation {

	public ControlResultantMutation(IWidget parent, int x, int y, IMutation mutation, float specificChance, IBreedingSystem system, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies) {
		super(parent, x, y, mutation, specificChance, system, firstSpecies, secondSpecies);

		String comb = firstSpecies.getAlleleName() + " + " + secondSpecies.getAlleleName();
		addTooltip(comb);
		String chance = getMutationColour(mutation.getBaseChance()).getCode() + (int) mutation.getBaseChance() + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".chance");
		if (specificChance != mutation.getBaseChance()) {
			chance = chance + getMutationColour(specificChance).getCode() + " (" + (int) specificChance + "% " + I18N.localise(AnalystConstants.MUTATIONS_KEY + ".currently") + ")";
		}
		addTooltip(chance);
		for (String condition : mutation.getSpecialConditions()) {
			addTooltip(condition);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		GlStateManager.enableBlend();
		RenderUtil.drawItem(Point.ZERO, system.getDefaultMember(firstSpecies.getUID()));
		RenderUtil.drawItem(new Point(28, 0), system.getDefaultMember(secondSpecies.getUID()));
		GlStateManager.disableBlend();
		if (specificChance != mutation.getBaseChance()) {
			RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColor());
			drawSprite(Genetics.getIcons().getIconAdd0());
			RenderUtil.setColour(getMutationColour(specificChance).getColor());
			drawSprite(Genetics.getIcons().getIconAdd1());
		} else {
			RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColor());
			drawSprite(Genetics.getIcons().getIconAdd());
		}
	}
}
