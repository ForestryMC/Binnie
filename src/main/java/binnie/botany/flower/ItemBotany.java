package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColor;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.core.BinnieCore;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.core.config.Config;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class ItemBotany extends Item {
    public ItemBotany(String name) {
        setCreativeTab(CreativeTabBotany.instance);
        setUnlocalizedName(name);
        hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        if (!stack.hasTagCompound()) {
            return false;
        }

        IIndividual individual = getIndividual(stack);
        return individual != null && individual.getGenome() != null && individual.hasEffect();
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List tooltip, boolean flag) {
        if (!itemstack.hasTagCompound()) {
            return;
        }
        IFlower individual = (IFlower) getIndividual(itemstack);
        if (individual == null) {
            tooltip.add(I18N.localise("botany.item.tooltip.bugged"));
            return;
        }

        IFlowerGenome genome = individual.getGenome();
        IFlowerColor primaryColor = genome.getPrimaryColor();
        IFlowerColor secondColor = genome.getSecondaryColor();
        IFlowerColor stemColor = genome.getStemColor();

        if (primaryColor == secondColor) {
            tooltip.add(EnumChatFormatting.YELLOW
                    + I18N.localise("botany.item.tooltip.flowerColor1", primaryColor.getName(), stemColor.getName()));
        } else {
            tooltip.add(EnumChatFormatting.YELLOW
                    + I18N.localise(
                            "botany.item.tooltip.flowerColor2",
                            primaryColor.getName(),
                            secondColor.getName(),
                            stemColor.getName()));
        }

        if (individual.isAnalyzed()) {
            if (BinnieCore.proxy.isShiftDown()) {
                individual.addTooltip(tooltip);
            } else {
                tooltip.add(EnumChatFormatting.GRAY + I18N.localise("botany.item.tooltip.holdMore"));
            }
        } else {
            tooltip.add(I18N.localise("botany.item.tooltip.unknownGenome"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {}

    protected IIndividual getIndividual(ItemStack itemstack) {
        return new Flower(itemstack.getTagCompound());
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        if (!itemstack.hasTagCompound()) {
            return I18N.localise("botany.item.tooltip.unknown");
        }

        IIndividual individual = getIndividual(itemstack);
        if (individual != null && individual.getGenome() != null) {
            String tag = getTag();
            if (tag == null || tag.isEmpty()) {
                return individual.getDisplayName();
            }
            return I18N.localise("botany.item.botany.name", individual.getDisplayName(), tag);
        }
        return I18N.localise("botany.item.flowerCorrupted.name");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        addCreativeItems(itemList, true);
    }

    public void addCreativeItems(List itemList, boolean hideSecrets) {
        for (IIndividual individual : BotanyCore.speciesRoot.getIndividualTemplates()) {
            if (hideSecrets && individual.isSecret() && !Config.isDebug) {
                continue;
            }
            itemList.add(BotanyCore.speciesRoot.getMemberStack(
                    individual.copy(), getStage().ordinal()));
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack itemstack, int renderPass) {
        IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
        if (flower == null || flower.getGenome() == null) {
            return 0xffffff;
        }

        if (renderPass == 0) {
            return flower.getGenome().getStemColor().getColor(flower.isWilted());
        }
        return (renderPass == 1)
                ? flower.getGenome().getPrimaryColor().getColor(flower.isWilted())
                : flower.getGenome().getSecondaryColor().getColor(flower.isWilted());
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemstack, int renderPass) {
        IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
        if (flower == null || flower.getGenome() == null || flower.getGenome().getPrimary() == null) {
            return EnumFlowerType.ALLIUM.getBlank();
        }

        IFlowerType type = flower.getGenome().getPrimary().getType();
        if (renderPass == 0) {
            return type.getStem(getStage(), flower.hasFlowered(), type.getSections() - 1);
        }
        if (renderPass == 1) {
            return type.getPetalIcon(getStage(), flower.hasFlowered(), type.getSections() - 1);
        }
        return type.getVariantIcon(getStage(), flower.hasFlowered(), type.getSections() - 1);
    }

    @Override
    public boolean onItemUse(
            ItemStack itemstack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float px,
            float py,
            float pz) {
        if (getStage() == EnumFlowerStage.POLLEN) {
            IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(itemstack);
            TileEntity target = world.getTileEntity(x, y, z);
            if (!(target instanceof IPollinatable)) {
                return false;
            }
            IPollinatable pollinatable = (IPollinatable) target;
            if (!pollinatable.canMateWith(flower)) {
                return false;
            }
            pollinatable.mateWith(flower);
            if (!player.capabilities.isCreativeMode) {
                itemstack.stackSize--;
            }
            return true;
        }

        Block blockFlower = Botany.flower;
        Block blockAlreadyThere = world.getBlock(x, y, z);
        if (blockAlreadyThere == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 0x7) < 1) {
            side = 1;
        } else if (blockAlreadyThere != Blocks.vine
                && blockAlreadyThere != Blocks.tallgrass
                && blockAlreadyThere != Blocks.deadbush
                && !blockAlreadyThere.isReplaceable(world, x, y, z)) {
            if (side == 0) {
                y--;
            } else if (side == 1) {
                y++;
            } else if (side == 2) {
                z--;
            } else if (side == 3) {
                z++;
            } else if (side == 4) {
                x--;
            } else if (side == 5) {
                x++;
            }
        }

        if (itemstack.stackSize == 0 || !player.canPlayerEdit(x, y, z, side, itemstack)) {
            return false;
        }
        if (y == 255 && blockFlower.getMaterial().isSolid()) {
            return false;
        }

        if (world.canPlaceEntityOnSide(blockFlower, x, y, z, false, side, player, itemstack)) {
            int i1 = getMetadata(itemstack.getItemDamage());
            int j1 = blockFlower.onBlockPlaced(world, x, y, z, side, px, py, pz, i1);
            if (placeBlockAt(itemstack, player, world, x, y, z, side, px, py, pz, j1)) {
                world.playSoundEffect(
                        x + 0.5f,
                        y + 0.5f,
                        z + 0.5f,
                        blockFlower.stepSound.func_150496_b(),
                        (blockFlower.stepSound.getVolume() + 1.0f) / 2.0f,
                        blockFlower.stepSound.getPitch() * 0.8f);
                itemstack.stackSize--;
            }
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
        Block field_150939_a = Botany.flower;
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.snow_layer) {
            side = 1;
        } else if (block != Blocks.vine
                && block != Blocks.tallgrass
                && block != Blocks.deadbush
                && !block.isReplaceable(world, x, y, z)) {
            if (side == 0) {
                y--;
            } else if (side == 1) {
                y++;
            } else if (side == 2) {
                z--;
            } else if (side == 3) {
                z++;
            } else if (side == 4) {
                x--;
            } else if (side == 5) {
                x++;
            }
        }
        return world.canPlaceEntityOnSide(field_150939_a, x, y, z, false, side, null, stack);
    }

    public boolean placeBlockAt(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float hitX,
            float hitY,
            float hitZ,
            int metadata) {
        Block block = Botany.flower;
        if (!world.setBlock(x, y, z, block, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == block) {
            block.onBlockPlacedBy(world, x, y, z, player, stack);
            block.onPostBlockPlaced(world, x, y, z, metadata);
        }
        return true;
    }

    public abstract EnumFlowerStage getStage();

    public abstract String getTag();
}
