// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.genetics.effect;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.BlockWeb;

public class BlockEctoplasm extends BlockWeb
{
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
		this.blockIcon = ExtraBees.proxy.getIcon(register, "ectoplasm");
	}

	public BlockEctoplasm() {
		this.setLightOpacity(1);
		this.setHardness(0.5f);
	}

	@Override
	public int quantityDropped(final Random rand) {
		return (rand.nextInt(5) == 0) ? 1 : 0;
	}

	@Override
	public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
		return Items.slime_ball;
	}

	@Override
	public String getUnlocalizedName() {
		return "extrabees.block.ectoplasm";
	}
}
