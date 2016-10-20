// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import forestry.api.apiculture.IHiveDrop;
import java.util.Collections;
import java.util.ArrayList;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import forestry.api.core.Tabs;
import binnie.extrabees.ExtraBees;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class BlockExtraBeeHive extends Block
{
	IIcon[][] icons;

	public BlockExtraBeeHive() {
		super(ExtraBees.materialBeehive);
		this.setLightLevel(0.2f);
		this.setHardness(1.0f);
		this.setTickRandomly(true);
		this.setBlockName("hive");
		this.setCreativeTab(Tabs.tabApiculture);
	}

	public String getUnlocalizedName(final ItemStack par1ItemStack) {
		return "extrabees.block.hive." + par1ItemStack.getItemDamage();
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (int i = 0; i < 4; ++i) {
			itemList.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public IIcon getIcon(final int side, final int metadata) {
		if (metadata >= EnumHiveType.values().length) {
			return null;
		}
		if (side < 2) {
			return this.icons[metadata][1];
		}
		return this.icons[metadata][0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
		this.icons = new IIcon[EnumHiveType.values().length][2];
		for (final EnumHiveType hive : EnumHiveType.values()) {
			this.icons[hive.ordinal()][0] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".0");
			this.icons[hive.ordinal()][1] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".1");
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		final List<IHiveDrop> dropList = EnumHiveType.values()[metadata].drops;
		Collections.shuffle(dropList);
		int tries = 0;
		for (boolean hasPrincess = false; tries <= 10 && !hasPrincess; hasPrincess = true) {
			++tries;
			for (final IHiveDrop drop : dropList) {
				if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
					ret.add(drop.getPrincess(world, x, y, z, fortune));
					break;
				}
			}
		}
		for (final IHiveDrop drop : dropList) {
			if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
				ret.addAll(drop.getDrones(world, x, y, z, fortune));
				break;
			}
		}
		for (final IHiveDrop drop : dropList) {
			if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
				ret.addAll(drop.getAdditional(world, x, y, z, fortune));
				break;
			}
		}
		return ret;
	}
}
