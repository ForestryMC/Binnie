package binnie.extratrees.carpentry;

import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock {
    public static ForgeDirection[] RENDER_DIRECTIONS = new ForgeDirection[] {
        ForgeDirection.DOWN,
        ForgeDirection.UP,
        ForgeDirection.EAST,
        ForgeDirection.WEST,
        ForgeDirection.NORTH,
        ForgeDirection.SOUTH
    };

    protected IDesignSystem designSystem;

    public BlockDesign(IDesignSystem system, Material material) {
        super(material);
        designSystem = system;
    }

    public static int getMetadata(int plank1, int plank2, int design) {
        return plank1 + (plank2 << 9) + (design << 18);
    }

    @SubscribeEvent
    public void onClick(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        World world = event.entityPlayer.worldObj;
        EntityPlayer player = event.entityPlayer;
        int x = event.x;
        int y = event.y;
        int z = event.z;
        if (!(world.getBlock(x, y, z) instanceof BlockDesign)) {
            return;
        }

        BlockDesign blockC = (BlockDesign) world.getBlock(x, y, z);
        ItemStack item = player.getHeldItem();
        if (item == null
                || !(item.getItem() instanceof IToolHammer)
                || !((IToolHammer) item.getItem()).isActive(item)) {
            return;
        }

        DesignBlock block = blockC.getCarpentryBlock(world, x, y, z);
        TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
        block.rotate(event.face, item, player, world, x, y, z);
        int meta = block.getBlockMetadata(blockC.getDesignSystem());
        tile.setTileMetadata(meta, true);
    }

    public abstract ItemStack getCreativeStack(IDesign design);

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (IDesign design : CarpentryManager.carpentryInterface.getSortedDesigns()) {
            list.add(getCreativeStack(design));
        }
    }

    public IDesignSystem getDesignSystem() {
        return designSystem;
    }

    @Override
    public int getRenderType() {
        return BinnieCore.multipassRenderID;
    }

    @Override
    public String getBlockName(ItemStack stack) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return getBlockName(block);
    }

    public abstract String getBlockName(DesignBlock design);

    public DesignBlock getCarpentryBlock(IBlockAccess world, int x, int y, int z) {
        return ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        DesignBlock block = getCarpentryBlock(world, x, y, z);
        return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
    }

    @Override
    public int colorMultiplier(int meta) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), meta);
        return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
    }

    @Override
    public IIcon getIcon(int side, int damage) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), damage);
        if (MultipassBlockRenderer.getLayer() > 0) {
            return block.getSecondaryIcon(getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]);
        }
        return block.getPrimaryIcon(getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]);
    }

    @Override
    public void addBlockTooltip(ItemStack stack, List tooltip) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
            tooltip.add(I18N.localise(
                    "extratrees.block.tooltip.twoMaterials",
                    block.getPrimaryMaterial().getName(),
                    block.getSecondaryMaterial().getName()));
        } else {
            tooltip.add(block.getPrimaryMaterial().getName());
        }
    }

    public ItemStack getItemStack(int plank1, int plank2, int design) {
        return TileEntityMetadata.getItemStack(this, getMetadata(plank1, plank2, design));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (IDesignSystem system : DesignerManager.instance.getDesignSystems()) {
            system.registerIcons(register);
        }
    }

    @Override
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(getDesignSystem(), tileMeta);
        return block.getItemMetadata(getDesignSystem());
    }

    @Override
    public int getNumberOfPasses() {
        return 2;
    }
}
