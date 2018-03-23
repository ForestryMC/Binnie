package binnie.extrabees.items;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.Tabs;

import binnie.core.Mods;
import binnie.core.util.I18N;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;

@Optional.InterfaceList({
		@Optional.Interface(modid = "ic2", iface = "ic2.api.item.IElectricItem"),
		@Optional.Interface(modid = "ic2", iface = "ic2.api.item.IItemHudInfo")
})
public class ItemHoneyCrystal extends Item implements IElectricItem, IItemHudInfo, IItemModelProvider {
	private static final int MAX_CHARGE = 8000;
	private static final int TRANSFER_LIMIT = 500;
	private static final int TIER = 1;

	public ItemHoneyCrystal() {
		this.setMaxDamage(27);
		this.setMaxStackSize(16);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_crystal");
		this.setRegistryName("honey_crystal");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(Mods.IC2.active()) {
			double charge = ElectricItem.manager.getCharge(stack);
			if (charge <= 0.0F) {
				return I18N.localise("extrabees.item.honeycrystal.empty");
			}
		}
		return I18N.localise("extrabees.item.honeycrystal");
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public boolean showDurabilityBar(ItemStack stack) {
		double charge = ElectricItem.manager.getCharge(stack);
		double maxCharge = ElectricItem.manager.getMaxCharge(stack);
		return charge != maxCharge;
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public double getDurabilityForDisplay(ItemStack stack) {
		double charge = ElectricItem.manager.getCharge(stack);
		double maxCharge = ElectricItem.manager.getMaxCharge(stack);
		return 1D - (charge / maxCharge);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item) {
		ModelLoader.registerItemVariants(item, new ModelResourceLocation("extrabees:honey_crystal_full", "inventory"), new ModelResourceLocation("extrabees:honey_crystal_empty", "inventory"), new ModelResourceLocation("extrabees:honey_crystal", "inventory"));
		ModelLoader.setCustomMeshDefinition(item, new HoneyCrystalMeshDefinition());
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote && stack.getCount() == 1) {
			if(ElectricItem.manager.getCharge(stack) > 0.0D) {
				boolean transferred = false;
				
				for(int i = 0; i < 9; ++i) {
					ItemStack target = player.inventory.mainInventory.get(i);
					if(target != null && target != stack && ElectricItem.manager.discharge(target, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true) <= 0.0D) {
						double transfer = ElectricItem.manager.discharge(stack, 2.0D * TRANSFER_LIMIT, Integer.MAX_VALUE, true, true, true);
						if(transfer > 0.0D) {
							transfer = ElectricItem.manager.charge(target, transfer, TIER, true, false);
							if(transfer > 0.0D) {
								ElectricItem.manager.discharge(stack, transfer, Integer.MAX_VALUE, true, true, false);
								transferred = true;
							}
						}
					}
				}
				
				if(transferred && !world.isRemote) {
					player.openContainer.detectAndSendChanges();
				}
			}
			
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		} else {
			return new ActionResult(EnumActionResult.PASS, stack);
		}
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public boolean canProvideEnergy(ItemStack stack) {
		return true;
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public double getMaxCharge(ItemStack stack) {
		return MAX_CHARGE;
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public int getTier(ItemStack stack) {
		return TIER;
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public double getTransferLimit(ItemStack stack) {
		return TRANSFER_LIMIT;
	}
	
	@Override
	@Optional.Method(modid = "ic2")
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
		List<String> info = new LinkedList();
		info.add(ElectricItem.manager.getToolTip(stack));
		return info;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (Mods.IC2.active() && this.isInCreativeTab(tab)) {
			items.add(getCharged(0.0D));
			items.add(getCharged(MAX_CHARGE));
		}
	}
	
	@Optional.Method(modid = "ic2")
	public ItemStack getCharged(double charge) {
		ItemStack ret = new ItemStack(this);
		ElectricItem.manager.charge(ret, charge, TIER, true, false);
		return ret;
	}
	
	private static class HoneyCrystalMeshDefinition implements ItemMeshDefinition{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			int damage = stack.getItemDamage();
			String name = "honey_crystal";
			if(damage == 0) {
				name = "honey_crystal_full";
			}else if(damage == 26){
				name = "honey_crystal_empty";
			}
			return new ModelResourceLocation("extrabees:" + name, "inventory");
		}
	}
}
