package binnie.botany.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.core.config.Config;
import forestry.core.items.IColoredItem;

import binnie.botany.CreativeTabBotany;
import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.blocks.BlockFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.Flower;
import binnie.botany.genetics.FlowerDefinition;
import binnie.botany.modules.ModuleFlowers;
import binnie.core.util.I18N;

public class ItemFlowerGE extends Item implements IColoredItem, IItemModelRegister {
	private final EnumFlowerStage type;
	private final String tag;

	public ItemFlowerGE(String name, EnumFlowerStage type, String tag) {
		this.type = type;
		this.tag = tag;
		setCreativeTab(CreativeTabBotany.INSTANCE);
		setUnlocalizedName(name);
		setRegistryName(name);
		hasSubtypes = true;
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
	public boolean hasEffect(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return false;
		}
		IIndividual individual = getIndividual(itemstack);
		return individual != null && individual.hasEffect();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		IFlower individual = (IFlower) getIndividual(itemStack);
		if (individual == null) {
			list.add(TextFormatting.DARK_RED + I18N.localise("item.botany.flower.destroy"));
			return;
		}

		IFlowerGenome genome = individual.getGenome();
		//Colors
		String primaryColor = genome.getPrimaryColor().getColorName();
		String secondaryColor = genome.getSecondaryColor().getColorName();
		String stemColor = genome.getStemColor().getColorName();
		String colorInfo;
		if (!primaryColor.equals(secondaryColor)) {
			colorInfo = I18N.localise("item.botany.grammar.flower.secondary", primaryColor, secondaryColor, stemColor);
		} else {
			colorInfo = I18N.localise("item.botany.grammar.flower", primaryColor, stemColor);
		}
		list.add(TextFormatting.YELLOW + colorInfo);

		if (individual.isAnalyzed()) {
			if (GuiScreen.isShiftKeyDown()) {
				individual.addTooltip(list);
			} else {
				list.add(TextFormatting.ITALIC + "<" + I18N.localise("for.gui.tooltip.tmi") + '>');
			}
		} else {
			list.add('<' + I18N.localise("for.gui.unknown") + '>');
		}
	}

	@Nullable
	@Override
	public String getCreatorModId(ItemStack itemStack) {
		IIndividual individual = getIndividual(itemStack);
		if (individual != null) {
			IAlleleSpecies species = individual.getGenome().getPrimary();
			return species.getModID();
		}
		return super.getCreatorModId(itemStack);
	}

	@Nullable
	protected IIndividual getIndividual(ItemStack itemstack) {
		NBTTagCompound tagCompound = itemstack.getTagCompound();
		if (tagCompound == null) {
			return null;
		}
		return new Flower(tagCompound);
	}

	private IAlleleFlowerSpecies getPrimarySpecies(ItemStack itemstack) {
		IFlower flower = BotanyCore.getFlowerRoot().getMember(itemstack);
		if (flower == null) {
			return (IAlleleFlowerSpecies) BotanyCore.getFlowerRoot().getDefaultTemplate()[EnumFlowerChromosome.SPECIES.ordinal()];
		}
		return flower.getGenome().getPrimary();
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return I18N.localise("item.botany.flower.corrupted.name");
		}
		IIndividual individual = getIndividual(itemstack);
		if (individual != null) {
			return individual.getDisplayName() + (!tag.isEmpty() ? ' ' + I18N.localise("item.botany." + tag + ".name") : "");
		}
		return I18N.localise("item.botany.flower.corrupted.name");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			addCreativeItems(items, true);
		}
	}

	public void addCreativeItems(NonNullList<ItemStack> itemList, boolean hideSecrets) {
		for (IIndividual individual : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
			if (hideSecrets && individual.isSecret() && !Config.isDebug) {
				continue;
			}
			itemList.add(BotanyCore.getFlowerRoot().getMemberStack(individual.copy(), type));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
		if (flower == null) {
			return 0xffffff;
		}
		if (tintIndex == 0) {
			return flower.getGenome().getStemColor().getColor(flower.isWilted());
		}
		return (tintIndex == 1)
				? flower.getGenome().getPrimaryColor().getColor(flower.isWilted())
				: flower.getGenome().getSecondaryColor().getColor(flower.isWilted());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (IFlowerType flowerType : (List<IFlowerType>) BlockFlower.FLOWER.getAllowedValues()) {
			flowerType.registerModels(item, manager, type);
		}
		manager.registerItemModel(item, new BotanyMeshDefinition());
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (type == EnumFlowerStage.POLLEN) {
			return pollinateFlower(world, pos, player, stack);
		}
		return placeFlower(player, stack, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	private EnumActionResult pollinateFlower(World world, BlockPos pos, EntityPlayer player, ItemStack stack){
		IFlower flower = BotanyAPI.flowerRoot.getMember(stack);
		TileEntity target = world.getTileEntity(pos);
		if (!(target instanceof IPollinatable)) {
			return EnumActionResult.PASS;
		}
		IPollinatable pollinatable = (IPollinatable) target;
		if (!pollinatable.canMateWith(flower)) {
			return EnumActionResult.FAIL;
		}
		pollinatable.mateWith(flower);
		if (!player.capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

	private EnumActionResult placeFlower(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		IBlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();

		if (!block.isReplaceable(world, pos)) {
			pos = pos.offset(facing);
		}

		if (stack.getCount() != 0 && player.canPlayerEdit(pos, facing, stack) && world.mayPlace(ModuleFlowers.flower, pos, false, facing, null)) {
			int i = getMetadata(stack.getMetadata());
			IBlockState stateForPlacement = ModuleFlowers.flower.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, i, player, hand);

			if (placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, stateForPlacement)) {
				SoundType soundType = block.getSoundType(stateForPlacement, world, pos, player);
				world.playSound(player, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
				stack.shrink(1);
			}

			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == ModuleFlowers.flower) {
			ItemBlock.setTileEntityNBT(world, player, pos, stack);
			ModuleFlowers.flower.onBlockPlacedBy(world, pos, state, player, stack);
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	private class BotanyMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
			if(flower == null){
				flower = FlowerDefinition.Poppy.getIndividual();
			}
			IAlleleFlowerSpecies flowerSpecies = flower.getGenome().getPrimary();
			return flowerSpecies.getFlowerModel(type, flower.hasFlowered());
		}
	}
}
