package binnie.extrabees.alveary;

import binnie.extrabees.utils.AlvearyMutationHandler;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicMutator extends AbstractAlvearyLogic {

	public AlvearyLogicMutator(){
		this.inv = new ItemStackHandler(1);
	}

	private final IItemHandlerModifiable inv;

	@Override
	public float getMutationModifier(@Nonnull IBeeGenome genome, @Nonnull IBeeGenome mate, float currentModifier) {
		ItemStack mutator = inv.getStackInSlot(0);
		float mult = AlvearyMutationHandler.getMutationMult(mutator);
		return Math.min(mult * currentModifier, 15f);
	}

	@Override
	public void onQueenDeath() {
		inv.extractItem(0, 1, false);
	}

}
