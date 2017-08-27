package binnie.botany.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.core.IModelManager;
import forestry.core.circuits.SolderManager;
import forestry.core.items.IColoredItem;
import forestry.core.utils.Translator;

import binnie.botany.CreativeTabBotany;
import binnie.core.item.ItemCore;
import binnie.core.util.I18N;

public class ItemInsulatedTube extends ItemCore implements IColoredItem {
	public ItemInsulatedTube() {
		super("insulated_tube");
		setUnlocalizedName("botany.insulated_tube");
		setCreativeTab(CreativeTabBotany.INSTANCE);
		setHasSubtypes(true);
	}

	private static Multimap<ICircuitLayout, ICircuit> getCircuits(ItemStack itemStack) {
		Multimap<ICircuitLayout, ICircuit> circuits = ArrayListMultimap.create();
		Collection<ICircuitLayout> allLayouts = ChipsetManager.circuitRegistry.getRegisteredLayouts().values();
		for (ICircuitLayout circuitLayout : allLayouts) {
			ICircuit circuit = SolderManager.getCircuit(circuitLayout, itemStack);
			if (circuit != null) {
				circuits.put(circuitLayout, circuit);
			}
		}
		return circuits;
	}

	public static String getInsulate(ItemStack stack) {
		return EnumTubeInsulate.get(stack.getItemDamage()).getDisplayName();
	}

	public static ItemStack getInsulateStack(ItemStack stack) {
		return EnumTubeInsulate.get(stack.getItemDamage()).getStack();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumTubeMaterial mat : EnumTubeMaterial.values()) {
				for (EnumTubeInsulate ins : EnumTubeInsulate.values()) {
					items.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumTubeMaterial mat : EnumTubeMaterial.values()) {
			for (EnumTubeInsulate ins : EnumTubeInsulate.values()) {
				manager.registerItemModel(item, mat.ordinal() + ins.ordinal() * 128);
			}
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (tintIndex == 0) {
			return 0xffffff;
		} else if (tintIndex == 1) {
			return EnumTubeMaterial.get(stack.getItemDamage()).getColor();
		}
		return EnumTubeInsulate.get(stack.getItemDamage()).getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(itemstack, worldIn, list, flagIn);
		Multimap<ICircuitLayout, ICircuit> circuits = getCircuits(itemstack);
		if (!circuits.isEmpty()) {
			if (GuiScreen.isShiftKeyDown()) {
				for (ICircuitLayout circuitLayout : circuits.keys()) {
					String circuitLayoutName = circuitLayout.getUsage();
					list.add(TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + circuitLayoutName);
					for (ICircuit circuit : circuits.get(circuitLayout)) {
						circuit.addTooltip(list);
					}
				}
			} else {
				list.add(TextFormatting.ITALIC + "<" + Translator.translateToLocal("for.gui.tooltip.tmi") + ">");
			}
		} else {
			list.add("<" + Translator.translateToLocal("for.gui.noeffect") + ">");
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int meta = itemStack.getMetadata();
		return I18N.localise(
				"item.botany.insulated_tube.name",
				EnumTubeMaterial.get(meta).getDisplayName(),
				EnumTubeInsulate.get(meta).getDisplayName()
		);
	}
}
