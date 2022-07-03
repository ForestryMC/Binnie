package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerType;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlower extends BlockContainer {
    public BlockFlower() {
        super(Material.plants);
        float f = 0.2f;
        setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 3.0f, 0.5f + f);
        setTickRandomly(true);
        setBlockName("flower");
        setStepSound(Block.soundTypeGrass);
    }

    @Override
    public String getUnlocalizedName() {
        return "botany.flower";
    }

    @Override
    public int getRenderType() {
        return RendererBotany.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (EnumFlowerType type : EnumFlowerType.values()) {
            type.registerIcons(register);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFlower();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        TileEntity flower = world.getTileEntity(x, y, z);
        if (!BinnieCore.proxy.isSimulating(world)) {
            if (flower != null && flower instanceof TileEntityFlower) {
                IFlower f = BotanyCore.getFlowerRoot().getMember(stack);
                ((TileEntityFlower) flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower) flower));
            }
            return;
        }

        TileEntity below = world.getTileEntity(x, y - 1, z);
        if (flower != null && flower instanceof TileEntityFlower) {
            if (below instanceof TileEntityFlower) {
                ((TileEntityFlower) flower).setSection(((TileEntityFlower) below).getSection());
            } else {
                GameProfile owner = (entity instanceof EntityPlayer) ? ((EntityPlayer) entity).getGameProfile() : null;
                ((TileEntityFlower) flower).create(stack, owner);
            }
        }
        Gardening.tryGrowSection(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityFlower)) {
            return super.getIcon(world, x, y, z, side);
        }

        TileEntityFlower f = (TileEntityFlower) tile;
        EnumFlowerStage stage = (f.getAge() == 0) ? EnumFlowerStage.SEED : EnumFlowerStage.FLOWER;
        IFlowerType flower = f.getType();
        int section = f.getRenderSection();
        boolean flowered = f.isFlowered();
        if (RendererBotany.pass == 0) {
            return flower.getStem(stage, flowered, section);
        }

        return (RendererBotany.pass == 1)
                ? flower.getPetalIcon(stage, flowered, section)
                : flower.getVariantIcon(stage, flowered, section);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            TileEntityFlower f = (TileEntityFlower) tile;
            if (RendererBotany.pass == 0) {
                return f.getStemColour();
            }
            return (RendererBotany.pass == 1) ? f.getPrimaryColor() : f.getSecondaryColor();
        }
        return 0xffffff;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || Gardening.isSoil(block);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        checkAndDropBlock(world, x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityFlower)) {
            return;
        }

        TileEntityFlower flower = (TileEntityFlower) tile;
        if (flower.getSection() == 0
                && flower.getFlower() != null
                && flower.getFlower().getAge() > 0
                && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1
                && world.getBlock(x, y + 1, z) != Botany.flower) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            ((TileEntityFlower) tile).randomUpdate(rand);
            checkAndDropBlock(world, x, y, z);
            return;
        }
        world.setBlockToAir(x, y, z);
    }

    protected void checkAndDropBlock(World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() > 0) {
            return world.getBlock(x, y - 1, z) == Botany.flower;
        }
        return canPlaceBlockOn(world.getBlock(x, y - 1, z));
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() == 0) {
            ItemStack flower = ((TileEntityFlower) tile).getItemStack();
            if (flower != null) {
                drops.add(flower);
            }
        }
        return drops;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        List<ItemStack> drops = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        boolean hasBeenBroken = world.setBlockToAir(x, y, z);
        if (hasBeenBroken
                && BinnieCore.proxy.isSimulating(world)
                && drops.size() > 0
                && (player == null || !player.capabilities.isCreativeMode)) {
            for (ItemStack drop : drops) {
                dropBlockAsItem(world, x, y, z, drop);
            }
        }
        return hasBeenBroken;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int meta, int side) {
        return Blocks.yellow_flower.getIcon(0, 0);
    }
}
