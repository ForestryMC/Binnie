package binnie.genetics.gui.bee.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;

public class ControlProductsBox extends ControlListBox<ControlProductsBox.Product> {
	IAlleleBeeSpecies species;
	private int index;
	private Type type;

	public ControlProductsBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12);
		this.species = null;
		this.type = type;
	}

	@Override
	public IWidget createOption(final Product value, final int y) {
		return new ControlProductsItem(getContent(), value, y);
	}

	public void setSpecies(final IAlleleBeeSpecies species) {
		if (species != this.species && (this.species = species) != null) {
			final IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species.getUID());
			if (template == null) {
				return;
			}
			final IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);
			final float speed = genome.getSpeed();
			final float modeSpeed = Binnie.GENETICS.getBeeRoot().getBeekeepingMode(BinnieCore.getBinnieProxy().getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
			final List<Product> strings = new ArrayList<>();
			if (this.type == Type.Products) {
				for (final Map.Entry<ItemStack, Float> entry : species.getProductChances().entrySet()) {
					strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
				}
			} else {
				for (final Map.Entry<ItemStack, Float> entry : species.getSpecialtyChances().entrySet()) {
					strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
				}
			}
			this.setOptions(strings);
		}
	}

	enum Type {
		Products,
		Specialties
	}

	class Product {
		ItemStack item;
		float chance;

		public Product(final ItemStack item, final float chance) {
			this.item = item;
			this.chance = chance;
		}
	}
}
