package binnie.genetics.gui.analyst;

import binnie.core.api.genetics.IBreedingSystem;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.util.I18N;
import binnie.genetics.item.ModuleItems;

public class ControlFurtherMutation extends ControlMutation {
	private final IAlleleSpecies resolute;

	public ControlFurtherMutation(IWidget parent, int x, int y, IMutation mutation, float specificChance, IBreedingSystem system, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies, IAlleleSpecies resolute) {
		super(parent, x, y, mutation, specificChance, system, firstSpecies, secondSpecies);
		this.resolute = resolute;

		addTooltip(resolute.getAlleleName());
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
		RenderUtil.drawItem(Point.ZERO, system.getDefaultMember(secondSpecies.getUID()));
		RenderUtil.drawItem(new Point(28, 0), system.getDefaultMember(resolute.getUID()));
		GlStateManager.disableBlend();
		RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColor());
		if (specificChance != mutation.getBaseChance()) {
			RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColor());
			drawSprite(ModuleItems.iconArrow0);
			RenderUtil.setColour(getMutationColour(specificChance).getColor());
			drawSprite(ModuleItems.iconArrow1);
		} else {
			RenderUtil.setColour(getMutationColour(mutation.getBaseChance()).getColor());
			drawSprite(ModuleItems.iconArrow);
		}
	}
}
