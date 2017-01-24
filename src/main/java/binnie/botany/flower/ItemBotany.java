package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.Flower;
import binnie.core.BinnieCore;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.core.config.Config;
import forestry.core.items.IColoredItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBotany extends Item implements IColoredItem, IItemModelRegister {

	private EnumFlowerStage type;
	private String tag;

	public ItemBotany(final String name, EnumFlowerStage type, String tag) {
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName(name);
		setRegistryName(name);
		this.hasSubtypes = true;
		this.type = type;
		this.tag = tag;
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
	public boolean hasEffect(final ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return false;
		}
		final IIndividual individual = this.getIndividual(itemstack);
		return individual != null && individual.getGenome() != null && individual.hasEffect();
	}

	@Override
	public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List list, final boolean flag) {
		if (!itemStack.hasTagCompound()) {
			return;
		}
		IFlower individual = (IFlower) this.getIndividual(itemStack);
		if (individual == null || individual.getGenome() == null) {
			list.add(TextFormatting.DARK_RED + Binnie.LANGUAGE.localise("botany.flower.destroy"));
			return;
		}
		IFlowerGenome genome = individual.getGenome();
		//Colors
		String primaryColor = genome.getPrimaryColor().getColourName();
		String secondaryColor = genome.getSecondaryColor().getColourName();
		String stemColor = genome.getStemColor().getColourName();
		String colorInfo;
		if (!primaryColor.equals(secondaryColor)) {
			colorInfo = Binnie.LANGUAGE.localise("botany.grammar.flower.secondary");
		} else {
			colorInfo = Binnie.LANGUAGE.localise("botany.grammar.flower");
		}
		list.add(TextFormatting.YELLOW + colorInfo.replaceAll("%PRIMARY", primaryColor).replaceAll("%SECONDARY", secondaryColor).replaceAll("%STEM", stemColor));

		if (individual.isAnalyzed()) {
			if (BinnieCore.proxy.isShiftDown()) {
				individual.addTooltip(list);
			} else {
				list.add(TextFormatting.ITALIC + "<" + Binnie.LANGUAGE.localise("for.gui.tooltip.tmi") + ">");
			}
		} else {
			list.add("<" + Binnie.LANGUAGE.localise("for.gui.unknown") + ">");
		}
	}

	protected IIndividual getIndividual(final ItemStack itemstack) {
		return new Flower(itemstack.getTagCompound());
	}

	private IAlleleFlowerSpecies getPrimarySpecies(final ItemStack itemstack) {
		final IFlower tree = BotanyCore.getFlowerRoot().getMember(itemstack);
		if (tree == null) {
			return (IAlleleFlowerSpecies) BotanyCore.getFlowerRoot().getDefaultTemplate()[EnumFlowerChromosome.SPECIES.ordinal()];
		}
		return tree.getGenome().getPrimary();
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return Binnie.LANGUAGE.localise("item.botany.flower.corrupted.name");
		}
		IIndividual individual = this.getIndividual(itemstack);
		if (individual != null && individual.getGenome() != null) {
			return individual.getDisplayName() + (!tag.isEmpty() ? " " + Binnie.LANGUAGE.localise("item.botany." + tag + ".name") : "");
		}
		return Binnie.LANGUAGE.localise("item.botany.flower.corrupted.name");
	}

	@Override
	public void getSubItems(final Item item, final CreativeTabs par2CreativeTabs, final List itemList) {
		this.addCreativeItems(itemList, true);
	}

	public void addCreativeItems(final List itemList, final boolean hideSecrets) {
		for (final IIndividual individual : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
			if (hideSecrets && individual.isSecret() && !Config.isDebug) {
				continue;
			}
			itemList.add(BotanyCore.getFlowerRoot().getMemberStack(individual.copy(), type));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
		if (flower == null || flower.getGenome() == null) {
			return 16777215;
		}
		return (tintIndex == 0) ? flower.getGenome().getStemColor().getColor(flower.isWilted()) : ((tintIndex == 1) ? flower.getGenome().getPrimaryColor().getColor(flower.isWilted()) : flower.getGenome().getSecondaryColor().getColor(flower.isWilted()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (IFlowerType flowerType : (List<IFlowerType>) BlockFlower.FLOWER.getAllowedValues()) {
			flowerType.registerModels(item, manager, type);
		}
		manager.registerItemModel(item, new BotanyMeshDefinition());
	}

	@SideOnly(Side.CLIENT)
	private class BotanyMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
			if (flower == null || flower.getGenome() == null || flower.getGenome().getPrimary() == null) {
				return null;
			}
			IAlleleFlowerSpecies flowerSpecies = flower.getGenome().getPrimary();
			IFlowerType flowerType = flowerSpecies.getType();
			return flowerSpecies.getFlowerModel(type, flower.hasFlowered());
		}

	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (type == EnumFlowerStage.POLLEN) {
			final IFlower flower = Binnie.GENETICS.getFlowerRoot().getMember(stack);
			final TileEntity target = world.getTileEntity(pos);
			if (!(target instanceof IPollinatable)) {
				return EnumActionResult.PASS;
			}
			final IPollinatable pollinatable = (IPollinatable) target;
			if (!pollinatable.canMateWith(flower)) {
				return EnumActionResult.FAIL;
			}
			pollinatable.mateWith(flower);
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}
			return EnumActionResult.SUCCESS;
		} else {
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (!block.isReplaceable(world, pos)) {
				pos = pos.offset(facing);
			}

			if (stack.stackSize != 0 && player.canPlayerEdit(pos, facing, stack) && world.canBlockBePlaced(Botany.flower, pos, false, facing, null, stack)) {
				int i = this.getMetadata(stack.getMetadata());
				IBlockState iblockstate1 = Botany.flower.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, i, player, stack);

				if (placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
					SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
					world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					--stack.stackSize;
				}

				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.FAIL;
			}
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == Botany.flower) {
			ItemBlock.setTileEntityNBT(world, player, pos, stack);
			Botany.flower.onBlockPlacedBy(world, pos, state, player, stack);
		}

		return true;
	}
}
