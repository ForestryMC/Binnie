package binnie.genetics.gui.database.bee;

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

	public ControlProductsBox(IWidget parent, int x, int y, int width, int height, Type type) {
		super(parent, x, y, width, height, 12);
		species = null;
		this.type = type;
	}

	@Override
	public IWidget createOption(Product value, int y) {
		return new ControlProductsItem(getContent(), value, y);
	}

	public void setSpecies(IAlleleBeeSpecies species) {
		if (species == this.species || (this.species = species) == null) {
			return;
		}

		IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}

		IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);
		float speed = genome.getSpeed();
		float modeSpeed = Binnie.GENETICS.getBeeRoot().getBeekeepingMode(BinnieCore.getBinnieProxy().getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
		List<Product> strings = new ArrayList<>();

		if (type == Type.PRODUCTS) {
			for (Map.Entry<ItemStack, Float> entry : species.getProductChances().entrySet()) {
				strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
			}
		} else {
			for (Map.Entry<ItemStack, Float> entry : species.getSpecialtyChances().entrySet()) {
				strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
			}
		}
		setOptions(strings);
	}

	enum Type {
		PRODUCTS,
		SPECIALTIES
	}

	class Product {
		ItemStack item;
		float chance;

		public Product(ItemStack item, float chance) {
			this.item = item;
			this.chance = chance;
		}
	}
}
