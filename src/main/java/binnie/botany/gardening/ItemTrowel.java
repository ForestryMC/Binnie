package binnie.botany.gardening;

import binnie.Constants;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import com.google.common.collect.Multimap;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
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
		this.setUnlocalizedName("trowel_" + material);
		this.locName = "trowel_" + material;
		setRegistryName("trowel_" + material);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new ItemTrowelMeshDefinition());
		ModelBakery.registerItemVariants(item, new ResourceLocation(Constants.BOTANY_MOD_ID + ":tools/" + locName));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		}
		Block block = worldIn.getBlockState(pos).getBlock();
		if (facing == EnumFacing.DOWN || (!worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos.up()).getBlock() != Botany.flower) || (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.GRASS_PATH)) {
			return EnumActionResult.PASS;
		}
		worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		EnumMoisture moisture = Gardening.getNaturalMoisture(worldIn, pos);
		EnumAcidity acidity = Gardening.getNaturalPH(worldIn, pos);
		Gardening.plantSoil(worldIn, pos, EnumSoilType.SOIL, moisture, acidity);
		stack.damageItem(1, player);
		return EnumActionResult.SUCCESS;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", theToolMaterial.getDamageVsEntity() + 0.5, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -1.5, 0));
		}

		return multimap;
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
