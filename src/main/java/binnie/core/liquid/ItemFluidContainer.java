package binnie.core.liquid;

import binnie.Binnie;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class ItemFluidContainer extends ItemFood {
    private FluidContainer container;
    public static int LiquidExtraBee = 64;
    public static int LiquidExtraTree = 128;
    public static int LiquidJuice = 256;
    public static int LiquidAlcohol = 384;
    public static int LiquidSpirit = 512;
    public static int LiquidLiqueuer = 640;
    public static int LiquidGenetics = 768;

    private static Map<Integer, String> idToFluid = new HashMap<>();
    private static Map<String, Integer> fluidToID = new HashMap<>();

    public static void registerFluid(IFluidType fluid, int id) {
        ItemFluidContainer.idToFluid.put(id, fluid.getIdentifier().toLowerCase());
        ItemFluidContainer.fluidToID.put(fluid.getIdentifier().toLowerCase(), id);
    }

    public ItemFluidContainer(FluidContainer container) {
        super(0, false);
        this.container = container;
        container.item = this;
        maxStackSize = container.getMaxStackSize();
        setHasSubtypes(true);
        setUnlocalizedName("container" + container.name());
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    private FluidStack getLiquid(ItemStack stack) {
        String liquid = ItemFluidContainer.idToFluid.get(stack.getItemDamage());
        return (liquid == null) ? null : Binnie.Liquid.getLiquidStack(liquid, 1000);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        container.updateIcons(register);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack == null) {
            return "???";
        }

        FluidStack fluid = getLiquid(stack);
        if (fluid == null) {
            return I18N.localise("binniecore.item.fluidContainer.missed");
        }
        return container.getName(fluid.getFluid().getName());
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
        for (IFluidType liquid : Binnie.Liquid.fluids.values()) {
            if (!liquid.canPlaceIn(container) || !liquid.showInCreative(container)) {
                continue;
            }
            itemList.add(getContainer(liquid));
        }
    }

    public ItemStack getContainer(IFluidType liquid) {
        int id = ItemFluidContainer.fluidToID.get(liquid.getIdentifier().toLowerCase());
        return new ItemStack(this, 1, id);
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int j) {
        if (j > 0) {
            return container.getBottleIcon();
        }
        return container.getContentsIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int pass) {
        FluidStack fluid = getLiquid(item);
        if (fluid == null) {
            return 0xffffff;
        }
        if (pass == 0 && fluid.getFluid() instanceof BinnieFluid) {
            return ((BinnieFluid) fluid.getFluid()).fluidType.getContainerColor();
        }
        return super.getColorFromItemStack(item, pass);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        player.getFoodStats().func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        onFoodEaten(stack, world, player);
        return container.getEmpty();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public int func_150905_g(ItemStack stack) {
        return 0;
    }

    @Override
    public float func_150906_h(ItemStack stack) {
        return 0.0f;
    }
}
