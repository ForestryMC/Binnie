package binnie.botany.flower;

import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.Flower;
import forestry.api.genetics.IIndividual;
import forestry.core.config.Config;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemBotany extends Item {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getSpriteNumber() {
//		return 0;
//	}

    public ItemBotany(final String name) {
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName(name);
        setRegistryName(name);
        this.hasSubtypes = true;
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
//
//	@Override
//	public void addInformation(final ItemStack itemstack, final EntityPlayer player, final List list, final boolean flag) {
//		if (!itemstack.hasTagCompound()) {
//			return;
//		}
//		final IFlower individual = (IFlower) this.getIndividual(itemstack);
//		if (individual == null) {
//			list.add("This item is bugged. Destroy it!");
//			return;
//		}
//		list.add("§e" + individual.getGenome().getPrimaryColor().getName() + ((individual.getGenome().getPrimaryColor() == individual.getGenome().getSecondaryColor()) ? "" : (" and " + individual.getGenome().getSecondaryColor().getName())) + ", " + individual.getGenome().getStemColor().getName() + " Stem");
//		if (individual.isAnalyzed()) {
//			if (BinnieCore.proxy.isShiftDown()) {
//				individual.addTooltip(list);
//			}
//			else {
//				list.add("§o<Hold shift for details>");
//			}
//		}
//		else {
//			list.add("<Unknown>");
//		}
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister par1IconRegister) {
//	}

    protected IIndividual getIndividual(final ItemStack itemstack) {
        return new Flower(itemstack.getTagCompound());
    }

    private IAlleleFlowerSpecies getPrimarySpecies(final ItemStack itemstack) {
        final IFlower tree = BotanyCore.speciesRoot.getMember(itemstack);
        if (tree == null) {
            return (IAlleleFlowerSpecies) BotanyCore.speciesRoot.getDefaultTemplate()[EnumFlowerChromosome.SPECIES.ordinal()];
        }
        return tree.getGenome().getPrimary();
    }

    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        if (!itemstack.hasTagCompound()) {
            return "Unknown";
        }
        final IIndividual individual = this.getIndividual(itemstack);
        return (individual != null && individual.getGenome() != null) ? (individual.getDisplayName() + this.getTag()) : "Corrupted Flower";
    }

    @Override
    public void getSubItems(final Item item, final CreativeTabs par2CreativeTabs, final List itemList) {
        this.addCreativeItems(itemList, true);
    }

    public void addCreativeItems(final List itemList, final boolean hideSecrets) {
        for (final IIndividual individual : BotanyCore.speciesRoot.getIndividualTemplates()) {
            if (hideSecrets && individual.isSecret() && !Config.isDebug) {
                continue;
            }
            itemList.add(BotanyCore.speciesRoot.getMemberStack(individual.copy(), this.getStage()));
        }
    }

//	@Override
//	public int getColorFromItemStack(final ItemStack itemstack, final int renderPass) {
//		final IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
//		if (flower == null || flower.getGenome() == null) {
//			return 16777215;
//		}
//		return (renderPass == 0) ? flower.getGenome().getStemColor().getColor(flower.isWilted()) : ((renderPass == 1) ? flower.getGenome().getPrimaryColor().getColor(flower.isWilted()) : flower.getGenome().getSecondaryColor().getColor(flower.isWilted()));
//	}

//	@Override
//	public boolean requiresMultipleRenderPasses() {
//		return true;
//	}
//
//	@Override
//	public int getRenderPasses(final int metadata) {
//		return 3;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final ItemStack itemstack, final int renderPass) {
//		final IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
//		if (flower == null || flower.getGenome() == null || flower.getGenome().getPrimary() == null) {
//			return EnumFlowerType.Allium.getBlank();
//		}
//		final IFlowerType type = flower.getGenome().getPrimary().getType();
//		if (renderPass == 0) {
//			return type.getStem(this.getStage(), flower.hasFlowered(), type.getSections() - 1);
//		}
//		if (renderPass == 1) {
//			return type.getPetalIcon(this.getStage(), flower.hasFlowered(), type.getSections() - 1);
//		}
//		return type.getVariantIcon(this.getStage(), flower.hasFlowered(), type.getSections() - 1);
//	}

//	@Override
//	public boolean onItemUse(final ItemStack itemstack, final EntityPlayer player, final World world, int x, int y, int z, int side, final float px, final float py, final float pz) {
//		if (this.getStage() == EnumFlowerStage.POLLEN) {
//			final IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(itemstack);
//			final TileEntity target = world.getTileEntity(x, y, z);
//			if (!(target instanceof IPollinatable)) {
//				return false;
//			}
//			final IPollinatable pollinatable = (IPollinatable) target;
//			if (!pollinatable.canMateWith(flower)) {
//				return false;
//			}
//			pollinatable.mateWith(flower);
//			if (!player.capabilities.isCreativeMode) {
//				--itemstack.stackSize;
//			}
//			return true;
//		}
//		else {
//			final Block blockFlower = Botany.flower;
//			final Block blockAlreadyThere = world.getBlock(x, y, z);
//			if (blockAlreadyThere == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 0x7) < 1) {
//				side = 1;
//			}
//			else if (blockAlreadyThere != Blocks.vine && blockAlreadyThere != Blocks.tallgrass && blockAlreadyThere != Blocks.deadbush && !blockAlreadyThere.isReplaceable(world, x, y, z)) {
//				if (side == 0) {
//					--y;
//				}
//				if (side == 1) {
//					++y;
//				}
//				if (side == 2) {
//					--z;
//				}
//				if (side == 3) {
//					++z;
//				}
//				if (side == 4) {
//					--x;
//				}
//				if (side == 5) {
//					++x;
//				}
//			}
//			if (itemstack.stackSize == 0) {
//				return false;
//			}
//			if (!player.canPlayerEdit(x, y, z, side, itemstack)) {
//				return false;
//			}
//			if (y == 255 && blockFlower.getMaterial().isSolid()) {
//				return false;
//			}
//			if (world.canPlaceEntityOnSide(blockFlower, x, y, z, false, side, player, itemstack)) {
//				final int i1 = this.getMetadata(itemstack.getItemDamage());
//				final int j1 = blockFlower.onBlockPlaced(world, x, y, z, side, px, py, pz, i1);
//				if (this.placeBlockAt(itemstack, player, world, x, y, z, side, px, py, pz, j1)) {
//					world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, blockFlower.stepSound.func_150496_b(), (blockFlower.stepSound.getVolume() + 1.0f) / 2.0f, blockFlower.stepSound.getPitch() * 0.8f);
//					--itemstack.stackSize;
//				}
//				return true;
//			}
//			return false;
//		}
//	}

//	@SideOnly(Side.CLIENT)
//	public boolean func_150936_a(final World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, final EntityPlayer p_150936_6_, final ItemStack p_150936_7_) {
//		final Block field_150939_a = Botany.flower;
//		final Block block = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
//		if (block == Blocks.snow_layer) {
//			p_150936_5_ = 1;
//		}
//		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_)) {
//			if (p_150936_5_ == 0) {
//				--p_150936_3_;
//			}
//			if (p_150936_5_ == 1) {
//				++p_150936_3_;
//			}
//			if (p_150936_5_ == 2) {
//				--p_150936_4_;
//			}
//			if (p_150936_5_ == 3) {
//				++p_150936_4_;
//			}
//			if (p_150936_5_ == 4) {
//				--p_150936_2_;
//			}
//			if (p_150936_5_ == 5) {
//				++p_150936_2_;
//			}
//		}
//		return p_150936_1_.canPlaceEntityOnSide(field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, (Entity) null, p_150936_7_);
//	}
//
//	public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int metadata) {
//		final Block field_150939_a = Botany.flower;
//		if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
//			return false;
//		}
//		if (world.getBlock(x, y, z) == field_150939_a) {
//			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
//			field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
//		}
//		return true;
//	}

    public abstract EnumFlowerStage getStage();

    public abstract String getTag();
}
