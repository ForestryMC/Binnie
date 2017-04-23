// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui.database;

import java.util.List;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import java.util.Map;
import java.util.ArrayList;
import binnie.core.BinnieCore;
import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import forestry.api.apiculture.IAlleleBeeSpecies;
import binnie.core.craftgui.controls.listbox.ControlListBox;

public class ControlProductsBox extends ControlListBox<ControlProductsBox.Product>
{
	private int index;
	private Type type;
	IAlleleBeeSpecies species;

	@Override
	public IWidget createOption(final Product value, final int y) {
		return new ControlProductsItem(getContent(), value, y);
	}

	public ControlProductsBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12.0f);
		this.species = null;
		this.type = type;
	}

	public void setSpecies(final IAlleleBeeSpecies species) {
		if (species != this.species && (this.species = species) != null) {
			final IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
			if (template == null) {
				return;
			}
			final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
			final float speed = genome.getSpeed();
			final float modeSpeed = Binnie.Genetics.getBeeRoot().getBeekeepingMode(BinnieCore.proxy.getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
			final List<Product> strings = new ArrayList<Product>();
			if (this.type == Type.Products) {
				for (final Map.Entry<ItemStack, Float> entry : species.getProductChances().entrySet()) {
					strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
				}
			}
			else {
				for (final Map.Entry<ItemStack, Float> entry : species.getSpecialtyChances().entrySet()) {
					strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
				}
			}
			this.setOptions(strings);
		}
	}

	enum Type
	{
		Products,
		Specialties;
	}

	class Product
	{
		ItemStack item;
		float chance;

		public Product(final ItemStack item, final float chance) {
			this.item = item;
			this.chance = chance;
		}
	}
}
