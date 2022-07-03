package binnie.extrabees.genetics.effect;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockEctoplasm extends BlockWeb {
    public BlockEctoplasm() {
        setLightOpacity(1);
        setHardness(0.5f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = ExtraBees.proxy.getIcon(register, "ectoplasm");
    }

    @Override
    public int quantityDropped(Random rand) {
        return (rand.nextInt(5) == 0) ? 1 : 0;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Items.slime_ball;
    }

    @Override
    public String getUnlocalizedName() {
        return "extrabees.block.ectoplasm";
    }
}
