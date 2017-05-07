package binnie.botany.ceramic;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.ModuleCarpentry;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class BlockCeramicPatterned extends BlockDesign {
	public BlockCeramicPatterned() {
		super(CeramicDesignSystem.instance, Material.rock);
		setHardness(1.0f);
		setResistance(5.0f);
		setCreativeTab(CreativeTabBotany.instance);
		setBlockName("ceramicPattern");
	}

	@Override
	public ItemStack getCreativeStack(IDesign design) {
		return ModuleCarpentry.getItemStack(this, CeramicColor.get(EnumFlowerColor.White), CeramicColor.get(EnumFlowerColor.Black), design);
	}

	@Override
	public String getBlockName(DesignBlock design) {
		return Binnie.I18N.localise(Botany.instance, "ceramicBlock");
	}

	@Override
	public void addBlockTooltip(ItemStack stack, List tooltip) {
		super.addBlockTooltip(stack, tooltip);
		DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
		tooltip.add(EnumChatFormatting.GRAY + block.getDesign().getName());
	}
}
