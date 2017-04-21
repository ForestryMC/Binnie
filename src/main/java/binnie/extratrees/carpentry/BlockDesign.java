// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.carpentry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.BinnieCore;
import binnie.extratrees.api.CarpentryManager;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import binnie.extratrees.api.IDesign;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.world.IBlockAccess;
import binnie.extratrees.api.IToolHammer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.extratrees.api.IDesignSystem;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.BlockMetadata;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock
{
	IDesignSystem designSystem;
	public static final ForgeDirection[] RENDER_DIRECTIONS;

	@SubscribeEvent
	public void onClick(final PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		final World world = event.entityPlayer.worldObj;
		final EntityPlayer player = event.entityPlayer;
		final int x = event.x;
		final int y = event.y;
		final int z = event.z;
		if (!(world.getBlock(x, y, z) instanceof BlockDesign)) {
			return;
		}
		final BlockDesign blockC = (BlockDesign) world.getBlock(x, y, z);
		final ItemStack item = player.getHeldItem();
		if (item == null) {
			return;
		}
		if (!(item.getItem() instanceof IToolHammer)) {
			return;
		}
		if (!((IToolHammer) item.getItem()).isActive(item)) {
			return;
		}
		final DesignBlock block = blockC.getCarpentryBlock(world, x, y, z);
		final TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
		block.rotate(event.face, item, player, world, x, y, z);
		final int meta = block.getBlockMetadata(blockC.getDesignSystem());
		tile.setTileMetadata(meta, true);
	}

	public BlockDesign(final IDesignSystem system, final Material material) {
		super(material);
		this.designSystem = system;
	}

	public abstract ItemStack getCreativeStack(final IDesign p0);

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (final IDesign design : CarpentryManager.carpentryInterface.getSortedDesigns()) {
			itemList.add(this.getCreativeStack(design));
		}
	}

	public IDesignSystem getDesignSystem() {
		return this.designSystem;
	}

	@Override
	public int getRenderType() {
		return BinnieCore.multipassRenderID;
	}

	@Override
	public String getBlockName(final ItemStack itemStack) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
		return this.getBlockName(block);
	}

	public abstract String getBlockName(final DesignBlock p0);

	public DesignBlock getCarpentryBlock(final IBlockAccess world, final int x, final int y, final int z) {
		return ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(final IBlockAccess world, final int x, final int y, final int z) {
		final DesignBlock block = this.getCarpentryBlock(world, x, y, z);
		return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
	}

	@Override
	public int colorMultiplier(final int meta) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), meta);
		return (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryColour() : block.getPrimaryColour();
	}

	@Override
	public IIcon getIcon(final int side, final int damage) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
		final IIcon icon = (MultipassBlockRenderer.getLayer() > 0) ? block.getSecondaryIcon(this.getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]) : block.getPrimaryIcon(this.getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[side]);
		return icon;
	}

	@Override
	public void getBlockTooltip(final ItemStack itemStack, final List tooltip) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
		if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
			tooltip.add(block.getPrimaryMaterial().getName() + " and " + block.getSecondaryMaterial().getName());
		}
		else {
			tooltip.add(block.getPrimaryMaterial().getName());
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
		for (final IDesignSystem system : DesignerManager.instance.getDesignSystems()) {
			system.registerIcons(register);
		}
	}

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
		RENDER_DIRECTIONS = new ForgeDirection[] { ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH };
	}
}
