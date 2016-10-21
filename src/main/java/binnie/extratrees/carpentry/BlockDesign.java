package binnie.extratrees.carpentry;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.TileUtil;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IToolHammer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock {
    IDesignSystem designSystem;
    public static final EnumFacing[] RENDER_DIRECTIONS;

    @SubscribeEvent
    public void onClick(final PlayerInteractEvent.RightClickBlock event) {
        final World world = event.getWorld();
        final EntityPlayer player = event.getEntityPlayer();
        final BlockPos pos = event.getPos();
        if (!(world.getBlockState(pos).getBlock() instanceof BlockDesign)) {
            return;
        }
        final BlockDesign blockC = (BlockDesign) world.getBlockState(pos).getBlock();
        final ItemStack item = player.getHeldItemMainhand();
        if (item == null) {
            return;
        }
        if (!(item.getItem() instanceof IToolHammer)) {
            return;
        }
        if (!((IToolHammer) item.getItem()).isActive(item)) {
            return;
        }
        final DesignBlock block = blockC.getCarpentryBlock(world, pos);
        final TileEntityMetadata tile = TileUtil.getTile(world, pos, TileEntityMetadata.class);
        if (tile != null) {
            //block.rotate(event.getFace(), item, player, world, pos);
            final int meta = block.getBlockMetadata(blockC.getDesignSystem());
            tile.setTileMetadata(meta, true);
        }
    }

    public BlockDesign(final IDesignSystem system, final Material material) {
        super(material);
        this.designSystem = system;
    }

    public abstract ItemStack getCreativeStack(final IDesign p0);

    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
        for (final IDesign design : CarpentryManager.carpentryInterface.getSortedDesigns()) {
            itemList.add(this.getCreativeStack(design));
        }
    }

    public IDesignSystem getDesignSystem() {
        return this.designSystem;
    }

//	@Override
//	public int getRenderType() {
//		return BinnieCore.multipassRenderID;
//	}

    @Override
    public String getBlockName(final ItemStack stack) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return this.getBlockName(block);
    }

    public abstract String getBlockName(final DesignBlock p0);

    public DesignBlock getCarpentryBlock(final IBlockAccess world, final BlockPos pos) {
        return ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, pos));
    }

    @Override
    public int colorMultiplier(int p0) {
//		final DesignBlock block = this.getCarpentryBlock(world, pos);
//		return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
        return 0;
    }


//	@Override
//	public int colorMultiplier(final int meta) {
//		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), meta);
//		return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
//	}

//	@Override
//	public IIcon getIcon(final int side, final int damage) {
//		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
//		final IIcon icon = (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryIcon(this.getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]) : block.getPrimaryIcon(this.getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]);
//		return icon;
//	}

    @Override
    public void getBlockTooltip(final ItemStack stack, final List par3List) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
            par3List.add(block.getPrimaryMaterial().getName() + " and " + block.getSecondaryMaterial().getName());
        } else {
            par3List.add(block.getPrimaryMaterial().getName());
        }
    }

    public int primaryColor(final int damage) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
        return block.getPrimaryColour();
    }

    public int secondaryColor(final int damage) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
        return block.getSecondaryColour();
    }

    public ItemStack getItemStack(final int plank1, final int plank2, final int design) {
        return TileEntityMetadata.getItemStack(this, getMetadata(plank1, plank2, design));
    }

    public static int getMetadata(final int plank1, final int plank2, final int design) {
        return plank1 + (plank2 << 9) + (design << 18);
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		for (final IDesignSystem system : DesignerManager.instance.getDesignSystems()) {
//			system.registerIcons(register);
//		}
//	}

    @Override
    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), tileMeta);
        return block.getItemMetadata(this.getDesignSystem());
    }

    @Override
    public int getNumberOfPasses() {
        return 2;
    }

    static {
        RENDER_DIRECTIONS = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH};
    }
}
