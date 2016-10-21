package binnie.genetics.machine;

import binnie.Binnie;
import binnie.botany.gardening.Gardening;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.*;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class Acclimatiser {
    public static final int[] slotReserve;
    public static final int slotTarget = 4;
    public static final int[] slotAcclimatiser;
    public static final int[] slotDone;
    private static List<ToleranceSystem> toleranceSystems;
    static Map<ItemStack, Float> temperatureItems;
    static Map<ItemStack, Float> humidityItems;

    private static ToleranceSystem getToleranceSystem(final ItemStack stack, final ItemStack acclim) {
        final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
        if (root == null) {
            return null;
        }
        for (final ToleranceSystem system : Acclimatiser.toleranceSystems) {
            if (root.getUID() == system.uid && system.type.hasEffect(acclim)) {
                return system;
            }
        }
        return null;
    }

    public static void addTolerance(final String uid, final IChromosomeType chromosome, final ToleranceType type) {
        Acclimatiser.toleranceSystems.add(new ToleranceSystem(uid, chromosome, type));
    }

    public static float getTemperatureEffect(final ItemStack item) {
        for (final ItemStack stack : Acclimatiser.temperatureItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return Acclimatiser.temperatureItems.get(stack);
            }
        }
        return 0.0f;
    }

    public static float getHumidityEffect(final ItemStack item) {
        for (final ItemStack stack : Acclimatiser.humidityItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return Acclimatiser.humidityItems.get(stack);
            }
        }
        return 0.0f;
    }

    public static void addTemperatureItem(final ItemStack itemstack, final float amount) {
        if (itemstack == null) {
            return;
        }
        Acclimatiser.temperatureItems.put(itemstack, amount);
    }

    public static void addHumidityItem(final ItemStack itemstack, final float amount) {
        if (itemstack == null) {
            return;
        }
        Acclimatiser.humidityItems.put(itemstack, amount);
    }

    public static void setupRecipes() {
        if (BinnieCore.isApicultureActive()) {
            addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
            addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
        }
        if (BinnieCore.isLepidopteryActive()) {
            addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
            addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
        }
        if (BinnieCore.isBotanyActive()) {
//TODO
//			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
//			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
//			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.PH_TOLERANCE, ToleranceType.PH);
        }
        addTemperatureItem(new ItemStack(Items.BLAZE_POWDER), 0.5f);
        addTemperatureItem(new ItemStack(Items.BLAZE_ROD), 0.75f);
        addTemperatureItem(new ItemStack(Items.LAVA_BUCKET), 0.75f);
        addTemperatureItem(new ItemStack(Items.SNOWBALL), -0.15f);
        addTemperatureItem(new ItemStack(Blocks.ICE), -0.75f);
        addHumidityItem(new ItemStack(Items.WATER_BUCKET), 0.75f);
        addHumidityItem(new ItemStack(Blocks.SAND), -0.15f);
        addTemperatureItem(Mods.Forestry.stack("canLava"), 0.75f);
        addTemperatureItem(Mods.Forestry.stack("refractoryLava"), 0.75f);
        addHumidityItem(Mods.Forestry.stack("canWater"), 0.75f);
        addHumidityItem(Mods.Forestry.stack("refractoryWater"), 0.75f);
        addHumidityItem(Mods.Forestry.stack("waxCapsuleWater"), 0.75f);
    }

    public static boolean canAcclimatise(final ItemStack stack, final List<ItemStack> acclimatisers) {
        if (stack == null || acclimatisers.isEmpty()) {
            return true;
        }
        for (final ItemStack acclim : acclimatisers) {
            if (canAcclimatise(stack, acclim)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canAcclimatise(final ItemStack stack, final ItemStack acclim) {
        final ToleranceSystem system = getToleranceSystem(stack, acclim);
        return system != null && system.canAlter(stack, acclim);
    }

    public static ItemStack acclimatise(final ItemStack stack, final ItemStack acc) {
        final ToleranceSystem system = getToleranceSystem(stack, acc);
        return system.alter(stack, acc);
    }

    public static Tolerance alterTolerance(final Tolerance tol, final float effect) {
        final int[] is = tol.getBounds();
        int[] range = new int[2];
        if (effect < 0.0f) {
            range = new int[]{is[0] - 1, is[1]};
        } else {
            range = new int[]{is[0], is[1] + 1};
        }
        if (range[0] < -5) {
            range[0] = -5;
        }
        if (range[1] > 5) {
            range[1] = 5;
        }
        final EnumTolerance[] up = {EnumTolerance.NONE, EnumTolerance.UP_1, EnumTolerance.UP_2, EnumTolerance.UP_3, EnumTolerance.UP_4, EnumTolerance.UP_5};
        final EnumTolerance[] down = {EnumTolerance.NONE, EnumTolerance.DOWN_1, EnumTolerance.DOWN_2, EnumTolerance.DOWN_3, EnumTolerance.DOWN_4, EnumTolerance.DOWN_5};
        final EnumTolerance[] both = {EnumTolerance.NONE, EnumTolerance.BOTH_1, EnumTolerance.BOTH_2, EnumTolerance.BOTH_3, EnumTolerance.BOTH_4, EnumTolerance.BOTH_5};
        if (range[0] == 0) {
            return Tolerance.get(up[range[1]]);
        }
        if (range[1] == 0) {
            return Tolerance.get(down[-range[0]]);
        }
        final int avg = (int) ((-range[0] + range[1]) / 2.0f + 0.6f);
        return Tolerance.get(both[avg]);
    }

    static {
        slotReserve = new int[]{0, 1, 2, 3};
        slotAcclimatiser = new int[]{5, 6, 7};
        slotDone = new int[]{8, 9, 10, 11};
        Acclimatiser.toleranceSystems = new ArrayList<>();
        Acclimatiser.temperatureItems = new HashMap<>();
        Acclimatiser.humidityItems = new HashMap<>();
    }

    public static class PackageAcclimatiser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageAcclimatiser() {
            super("acclimatiser", GeneticsTexture.Acclimatiser, 9857609, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Acclimatiser);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(4, "process");
            inventory.getSlot(4).setValidator(new SlotValidator.Individual());
            inventory.getSlot(4).setReadOnly();
            inventory.getSlot(4).forbidExtraction();
            for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotReserve, "input")) {
                slot.forbidExtraction();
                slot.setValidator(new SlotValidator.Individual());
            }
            for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotDone, "output")) {
                slot.setReadOnly();
                slot.setValidator(new SlotValidator.Individual());
            }
            for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotAcclimatiser, "acclimatiser")) {
                slot.setValidator(new ValidatorAcclimatiserItem());
            }
            final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Acclimatiser.slotReserve, 4, 1);
            transfer.addStorage(4, Acclimatiser.slotDone, new ComponentInventoryTransfer.Condition() {
                @Override
                public boolean fufilled(final ItemStack stack) {
                    return !Acclimatiser.canAcclimatise(stack, machine.getMachineUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser));
                }
            });
            new ComponentPowerReceptor(machine, 5000);
            new ComponentAcclimatiserLogic(machine);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentAcclimatiserLogic extends ComponentProcessIndefinate {
        public ComponentAcclimatiserLogic(final IMachine machine) {
            super(machine, 2.0f);
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().getStack(4) == null) {
                return new ErrorState.NoItem("No Individual to Acclimatise", 4);
            }
            if (this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser).isEmpty()) {
                return new ErrorState.NoItem("No Acclimatising Items", Acclimatiser.slotAcclimatiser);
            }
            return super.canWork();
        }

        @Override
        public ErrorState canProgress() {
            if (!Acclimatiser.canAcclimatise(this.getUtil().getStack(4), this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser))) {
                return new ErrorState.InvalidItem("Cannot Acclimatise this individual with these items", 4);
            }
            return super.canProgress();
        }

        @Override
        protected boolean inProgress() {
            return this.canWork() == null;
        }

        @Override
        protected void onTickTask() {
            super.onTickTask();
            if (this.getUtil().getRandom().nextInt(100) == 0) {
                this.attemptAcclimatisation();
            }
        }

        protected void attemptAcclimatisation() {
            final List<ItemStack> acclms = new ArrayList<>();
            for (final ItemStack s : this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser)) {
                if (Acclimatiser.canAcclimatise(this.getUtil().getStack(4), s)) {
                    acclms.add(s);
                }
            }
            final ItemStack acc = acclms.get(this.getUtil().getRandom().nextInt(acclms.size()));
            final ItemStack acclimed = Acclimatiser.acclimatise(this.getUtil().getStack(4), acc);
            if (acclimed != null) {
                this.getUtil().setStack(4, acclimed);
                boolean removed = false;
                for (final int i : Acclimatiser.slotAcclimatiser) {
                    if (!removed && this.getUtil().getStack(i) != null && this.getUtil().getStack(i).isItemEqual(acc)) {
                        this.getUtil().decreaseStack(i, 1);
                        removed = true;
                    }
                }
            }
        }
    }

    public static class ValidatorAcclimatiserItem extends SlotValidator {
        public ValidatorAcclimatiserItem() {
            super(null);
        }

        @Override
        public boolean isValid(final ItemStack stack) {
            for (final ToleranceType type : ToleranceType.values()) {
                if (type.hasEffect(stack)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getTooltip() {
            return "Acclimatising Items";
        }
    }

    public enum ToleranceType {
        Temperature,
        Humidity,
        PH;

        public float getEffect(final ItemStack stack) {
            switch (this) {
                case Temperature: {
                    return Acclimatiser.getTemperatureEffect(stack);
                }
                case Humidity: {
                    return Acclimatiser.getHumidityEffect(stack);
                }
                case PH: {
                    if (Gardening.isAcidFertiliser(stack)) {
                        return -0.5f * Gardening.getFertiliserStrength(stack);
                    }
                    if (Gardening.isAlkalineFertiliser(stack)) {
                        return 0.5f * Gardening.getFertiliserStrength(stack);
                    }
                    break;
                }
            }
            return 0.0f;
        }

        public boolean hasEffect(final ItemStack stack) {
            return this.getEffect(stack) != 0.0f;
        }
    }

    private static class ToleranceSystem {
        String uid;
        IChromosomeType chromosomeOrdinal;
        ToleranceType type;

        private ToleranceSystem(final String uid, final IChromosomeType chromosomeOrdinal, final ToleranceType type) {
            this.uid = uid;
            this.chromosomeOrdinal = chromosomeOrdinal;
            this.type = type;
        }

        public boolean canAlter(final ItemStack stack, final ItemStack acclim) {
            final IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
            final IGenome genome = member.getGenome();
            final IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeOrdinal);
            final Tolerance tol = Tolerance.get(tolAllele.getValue());
            final float effect = this.type.getEffect(acclim);
            return (effect > 0.0f && tol.getBounds()[1] < 5) || (effect < 0.0f && tol.getBounds()[0] > -5);
        }

        public ItemStack alter(final ItemStack stack, final ItemStack acc) {
            final Random rand = new Random();
            final float effect = this.type.getEffect(acc);
            if (rand.nextFloat() > Math.abs(effect)) {
                return stack;
            }
            final IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
            final IGenome genome = member.getGenome();
            final IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeOrdinal);
            final Tolerance tol = Tolerance.get(tolAllele.getValue());
            final Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
            if (rand.nextFloat() > 1.0f / (-newTol.getBounds()[0] + newTol.getBounds()[1])) {
                return stack;
            }
            final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
            Inoculator.setGene(new Gene(newTol.getAllele(), this.chromosomeOrdinal, root), stack, rand.nextInt(2));
            return stack;
        }
    }
}
