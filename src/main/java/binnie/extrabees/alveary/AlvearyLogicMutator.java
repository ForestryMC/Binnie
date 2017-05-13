package binnie.extrabees.alveary;

import binnie.extrabees.client.gui2.AbstractAlvearyContainer;
import binnie.extrabees.client.gui2.ContainerMutator;
import binnie.extrabees.client.gui2.GuiContainerAlvearyPart;
import binnie.extrabees.client.gui2.GuiContainerMutator;
import binnie.extrabees.utils.AlvearyMutationHandler;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicMutator extends AbstractAlvearyLogic {

	public AlvearyLogicMutator(){
		this.inv = new ItemStackHandler(1);
	}

	private final IItemHandlerModifiable inv;

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

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

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerMutator(getContainer(player, data));
	}

	@Nullable
	@Override
	public AbstractAlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerMutator(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

}
