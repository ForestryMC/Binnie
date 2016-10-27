package binnie.botany.gardening;

import binnie.Constants;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrowel extends Item implements IItemModelRegister {
	protected final ToolMaterial theToolMaterial;
	protected final String locName;

	public ItemTrowel(ToolMaterial p_i45343_1_, final String material) {
		this.theToolMaterial = p_i45343_1_;
		this.maxStackSize = 1;
		this.setMaxDamage(p_i45343_1_.getMaxUses());
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName("trowel" + material);
		this.locName = "trowel" + material;
		setRegistryName("trowel" + material);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new ItemTrowelMeshDefinition());
		ModelBakery.registerItemVariants(item, new ResourceLocation(Constants.BOTANY_MOD_ID + ":tools/" + locName));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		}
		Block block = world.getBlockState(pos).getBlock();
		if (facing == EnumFacing.DOWN || (!world.isAirBlock(pos.up()) && world.getBlockState(pos.up()).getBlock() != Botany.flower) || (block != Blocks.GRASS && block != Blocks.DIRT)) {
			return EnumActionResult.PASS;
		}
		Block block2 = Botany.soil;
		world.playSound(player, hitX + 0.5f, hitY + 0.5f, hitZ + 0.5f, block2.getSoundType().getStepSound(), SoundCategory.NEUTRAL, (block2.getSoundType().getVolume() + 1.0f) / 2.0f, block2.getSoundType().getPitch() * 0.8f);
		if (world.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		EnumMoisture moisture = Gardening.getNaturalMoisture(world, pos);
		EnumAcidity acidity = Gardening.getNaturalPH(world, pos);
		Gardening.plantSoil(world, pos, EnumSoilType.SOIL, moisture, acidity);
		stack.damageItem(1, player);
		return EnumActionResult.SUCCESS;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	public String getToolMaterialName() {
		return this.theToolMaterial.toString();
	}

	@SideOnly(Side.CLIENT)
	private class ItemTrowelMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":tools/" + locName, "inventory");
		}
	}
}
