package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTrowel extends Item {
    protected Item.ToolMaterial theToolMaterial;
    String locName;

    public ItemTrowel(Item.ToolMaterial toolMaterial, String material) {
        theToolMaterial = toolMaterial;
        maxStackSize = 1;
        setMaxDamage(toolMaterial.getMaxUses());
        setCreativeTab(CreativeTabBotany.instance);
        setUnlocalizedName("trowel" + material);
        locName = "trowel" + material;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Botany.proxy.getIcon(register, "tools/" + locName);
    }

    @Override
    public boolean onItemUse(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float blockX,
            float blockY,
            float blockZ) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }
        Block block = world.getBlock(x, y, z);
        if (side == 0
                || (!world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)
                        && world.getBlock(x, y + 1, z) != Botany.flower)
                || (block != Blocks.grass && block != Blocks.dirt)) {
            return false;
        }

        Block block2 = Botany.soil;
        world.playSoundEffect(
                x + 0.5f,
                y + 0.5f,
                z + 0.5f,
                block2.stepSound.getStepResourcePath(),
                (block2.stepSound.getVolume() + 1.0f) / 2.0f,
                block2.stepSound.getPitch() * 0.8f);
        if (world.isRemote) {
            return true;
        }

        EnumMoisture moisture = Gardening.getNaturalMoisture(world, x, y, z);
        EnumAcidity acidity = Gardening.getNaturalPH(world, x, y, z);
        Gardening.createSoil(world, x, y, z, EnumSoilType.SOIL, moisture, acidity);
        stack.damageItem(1, player);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
