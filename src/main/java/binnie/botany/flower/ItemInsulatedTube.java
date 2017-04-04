package binnie.botany.flower;

import binnie.botany.CreativeTabBotany;
import binnie.core.item.ItemCore;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.core.IModelManager;
import forestry.core.circuits.SolderManager;
import forestry.core.items.IColoredItem;
import forestry.core.utils.Translator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;

public class ItemInsulatedTube extends ItemCore implements IColoredItem {

	public ItemInsulatedTube() {
		super("insulated_tube");
		setUnlocalizedName("insulated_tube");
		setCreativeTab(CreativeTabBotany.instance);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (Material mat : Material.values()) {
			for (Insulate ins : Insulate.values()) {
				subItems.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (Material mat : Material.values()) {
			for (Insulate ins : Insulate.values()) {
				manager.registerItemModel(item, mat.ordinal() + ins.ordinal() * 128);
			}
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (tintIndex == 0) {
			return 16777215;
		} else if (tintIndex == 1) {
			return Material.get(stack.getItemDamage()).getColor();
		}
		return Insulate.get(stack.getItemDamage()).getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean flag) {
		super.addInformation(itemstack, player, list, flag);
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

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int meta = itemStack.getMetadata();
		return Material.get(meta).getName() + " " + Insulate.get(meta).getName() + " " + super.getItemStackDisplayName(itemStack);
	}

	public static String getInsulate(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getName();
	}

	public static ItemStack getInsulateStack(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getStack();
	}

	enum Material {
		Copper(14923662, "Copper"),
		Tin(14806772, "Tin"),
		Bronze(14533238, "Bronze"),
		Iron(14211288, "Iron");

		int color;
		String name;

		public int getColor() {
			return this.color;
		}

		public String getName() {
			return this.name;
		}

		Material(final int color, final String name) {
			this.color = color;
			this.name = name;
		}

		public static Material get(final int i) {
			return values()[i % values().length];
		}
	}

	enum Insulate {
		Clay(10595020, "Clay"),
		Cobble(8092539, "Cobblestone"),
		Sand(15723189, "Sand"),
		HardenedClay(9657411, "Hardened Clay"),
		Stone(7171437, "Smooth Stone"),
		Sandstone(12695945, "Sandstone");

		int color;
		String name;

		public int getColor() {
			return this.color;
		}

		public String getName() {
			return this.name;
		}

		Insulate(final int color, final String name) {
			this.color = color;
			this.name = name;
		}

		public static Insulate get(final int i) {
			return values()[i / 128 % values().length];
		}

		public ItemStack getStack() {
			switch (this) {
				case Clay: {
					return new ItemStack(Blocks.CLAY);
				}
				case Cobble: {
					return new ItemStack(Blocks.COBBLESTONE);
				}
				case HardenedClay: {
					return new ItemStack(Blocks.HARDENED_CLAY);
				}
				case Sand: {
					return new ItemStack(Blocks.SAND);
				}
				case Sandstone: {
					return new ItemStack(Blocks.SANDSTONE);
				}
				case Stone: {
					return new ItemStack(Blocks.STONE);
				}
				default: {
					throw new IllegalStateException("Unknown insulated tube type: " + this);
				}
			}
		}
	}
}
