package binnie.genetics.gui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.FakeWorld;
import binnie.genetics.item.ModuleItem;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAlleleBoolean;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;

public class AnalystPageWood extends AnalystPageProduce {
	public AnalystPageWood(IWidget parent, IArea area, ITree ind) {
		super(parent, area);
		setColor(6697728);
		ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Wood").setColor(getColor());
		y += 12;
		if (((IAlleleBoolean) ind.getGenome().getActiveAllele(EnumTreeChromosome.FIREPROOF)).getValue()) {
			new ControlIconDisplay(this, (w() - 16.0f) / 2.0f, y, ModuleItem.iconNoFire.getIcon()).addTooltip("Fireproof");
		} else {
			new ControlIconDisplay(this, (w() - 16.0f) / 2.0f, y, ModuleItem.iconFire.getIcon()).addTooltip("Flammable");
		}
		y += 30;
		Collection<ItemStack> products = new UniqueItemStackSet();

		genome.getPrimary().getGenerator().setLogBlock(genome, FakeWorld.instance, 0, 0, 0, ForgeDirection.UP);
		ItemStack stackWood = FakeWorld.instance.getWooLog();
		if (stackWood != null) {
			products.add(stackWood);
		}
		// for (ItemStack stack :
		// ind.getGenome().getFruitProvider().getProducts()) {
		// products.add(stack);
		// }
		if (products.size() > 0) {
			new ControlTextCentered(this, y, "Logs").setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : products) {
				ControlItemDisplay d = new ControlItemDisplay(this, (w() - w) / 2.0f + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
			}
			y += 26;
		}
		Collection<ItemStack> allProducts = new UniqueItemStackSet();
		for (ItemStack stack2 : products) {
			allProducts.add(stack2);
		}
		Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = getRefined("Refined Products", y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0) {
			new ControlTextCentered(this, y, "This tree has no \nfruits or nuts").setColor(getColor());
			y += 28;
		}
		setSize(new IPoint(w(), y + 8));
	}

	@Override
	public String getTitle() {
		return "Wood";
	}
}
