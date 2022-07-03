package binnie.extrabees.gui.database.product;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemStack;

public class ControlProductsBox extends ControlListBox<Product> {
    protected IAlleleBeeSpecies species;

    private ProductType type;

    public ControlProductsBox(IWidget parent, int x, int y, int width, int height, ProductType type) {
        super(parent, x, y, width, height, 12.0f);
        this.type = type;
        species = null;
    }

    @Override
    public IWidget createOption(Product value, int y) {
        return new ControlProductsItem(getContent(), value, y);
    }

    public void setSpecies(IAlleleBeeSpecies species) {
        if (species != this.species && (this.species = species) != null) {
            IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
            if (template == null) {
                return;
            }

            IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
            float speed = genome.getSpeed();
            float modeSpeed = Binnie.Genetics.getBeeRoot()
                    .getBeekeepingMode(BinnieCore.proxy.getWorld())
                    .getBeeModifier()
                    .getProductionModifier(genome, 1.0f);

            List<Product> strings = new ArrayList<>();
            Set<Map.Entry<ItemStack, Float>> chances;
            if (type == ProductType.PRODUCTS) {
                chances = species.getProductChances().entrySet();
            } else {
                chances = species.getSpecialtyChances().entrySet();
            }

            for (Map.Entry<ItemStack, Float> entry : chances) {
                strings.add(new Product(entry.getKey(), speed * modeSpeed * entry.getValue()));
            }
            setOptions(strings);
        }
    }
}
