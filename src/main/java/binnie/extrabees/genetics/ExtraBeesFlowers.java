package binnie.extrabees.genetics;

import binnie.core.Mods;
import binnie.core.util.I18N;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFlower;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IFruitBearer;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public enum ExtraBeesFlowers implements IFlowerProvider, IAlleleFlowers, IChromosomeType {
    WATER,
    SUGAR,
    ROCK,
    BOOK,
    DEAD,
    REDSTONE,
    WOOD,
    LEAVES,
    SAPLING,
    FRUIT,
    MYSTICAL;

    protected boolean dominant;

    ExtraBeesFlowers() {
        dominant = true;
    }

    public static void doInit() {
        for (ExtraBeesFlowers effect : values()) {
            effect.register();
        }
    }

    @Override
    public String getUID() {
        return "extrabees.flower." + toString().toLowerCase();
    }

    @Override
    public boolean isDominant() {
        return dominant;
    }

    @Override
    public IFlowerProvider getProvider() {
        return this;
    }

    @Override
    public String getDescription() {
        return I18N.localise("extrabees.flowers." + name().toLowerCase() + ".name");
    }

    public void register() {
        // AlleleManager.alleleRegistry.registerAllele((IAllele)this);
        for (ItemStack stack : getItemStacks()) {
            FlowerManager.flowerRegistry.registerAcceptableFlower(Block.getBlockFromItem(stack.getItem()), getUID());
        }

        AlleleManager.alleleRegistry.registerAllele(this, this);
    }

    public ItemStack[] getItemStacks() {
        switch (this) {
            case WATER:
                return new ItemStack[] {new ItemStack(Blocks.waterlily)};

            case SUGAR:
                return new ItemStack[] {new ItemStack(Blocks.reeds)};

            case ROCK:
                return new ItemStack[] {new ItemStack(Blocks.cobblestone)};

            case BOOK:
                return new ItemStack[] {new ItemStack(Blocks.bookshelf)};

            case REDSTONE:
                return new ItemStack[] {new ItemStack(Blocks.redstone_torch)};

            case DEAD:
                return new ItemStack[] {new ItemStack(Blocks.deadbush)};

            case FRUIT:
                return new ItemStack[] {new ItemStack(Items.apple)};

            case LEAVES:
                return new ItemStack[] {new ItemStack(Blocks.leaves)};

            case SAPLING:
                return new ItemStack[] {new ItemStack(Blocks.sapling)};

            case WOOD:
                return new ItemStack[] {new ItemStack(Blocks.log)};
        }
        return new ItemStack[0];
    }

    @Override
    public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable) {
        EnumSet<EnumPlantType> types = pollinatable.getPlantType();
        return types.size() > 1 || !types.contains(EnumPlantType.Nether);
    }

    public boolean isAcceptedFlower(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == null) {
            return false;
        }

        switch (this) {
            case WATER:
                return block == Blocks.waterlily;

            case ROCK:
                return block.getMaterial() == Material.rock;

            case SUGAR:
                return block == Blocks.reeds;

            case BOOK:
                return block == Blocks.bookshelf;

            case REDSTONE:
                return block == Blocks.redstone_torch;

            case DEAD:
                return block == Blocks.deadbush;

            case WOOD:
                return block.isWood(world, x, y, z);

            case FRUIT:
                return world.getTileEntity(x, y, z) instanceof IFruitBearer;

            case LEAVES:
                return block.isLeaves(world, x, y, z);

            case SAPLING:
                return block.getClass().getName().toLowerCase().contains("sapling");

            case MYSTICAL:
                return block == Mods.botania.block("flower");
        }
        return false;
    }

    @Override
    public boolean growFlower(World world, IIndividual individual, int x, int y, int z) {
        switch (this) {
            case WATER:
                return world.isAirBlock(x, y, z)
                        && world.getBlock(x, y - 1, z) == Blocks.water
                        && world.setBlock(x, y, z, Blocks.waterlily, 0, 2);

            case SUGAR:
                return world.getBlock(x, y - 1, z) == Blocks.reeds
                        && world.isAirBlock(x, y, z)
                        && world.setBlock(x, y, z, Blocks.reeds, 0, 0);
        }
        return false;
    }

    @Override
    public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products) {
        if (this == ExtraBeesFlowers.MYSTICAL) {
            List<ItemStack> prods = new ArrayList<>();
            Collections.addAll(prods, products);

            for (int k = 0; k < 50; ++k) {
                int tX = 7;
                int tY = 7;
                int tZ = 3;
                int x2 = x - tX + world.rand.nextInt(1 + 2 * tX);
                int y2 = y - tY + world.rand.nextInt(1 + 2 * tY);
                int z2 = z - tZ + world.rand.nextInt(1 + 2 * tZ);

                Block block = world.getBlock(x2, y2, z2);
                if (block != null) {
                    if (block == Mods.botania.block("flower")) {
                        int meta = world.getBlockMetadata(x2, y2, z2);
                        Item item = Mods.botania.item("petal");
                        if (item != null) {
                            prods.add(new ItemStack(item, 1, meta));
                        }
                    }
                }
            }
            return prods.toArray(new ItemStack[0]);
        }
        return products;
    }

    @Override
    public String getName() {
        return getDescription();
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }

    @Override
    public String getFlowerType() {
        return getUID();
    }

    @Override
    public Set<IFlower> getFlowers() {
        return new HashSet<>();
    }

    @Override
    public Class<? extends IAllele> getAlleleClass() {
        return getClass();
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return AlleleManager.alleleRegistry.getSpeciesRoot(getUID());
    }
}
