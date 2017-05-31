# This is a script for the translation of certain items mod Binnie, which are not delivered in the language file. 
# Translation into Russian.
# If possible translated or names or tooltips.
# The format of this script file MineTweaker - UTF8

# Necessary modifications:
# forestry_1.7.10
# binnie-mods-2.0
# NotEnoughItems-1.7.10, CodeChickenCore-1.7.10, CodeChickenLib,
# CraftTweaker-1.7.10

# How to use:
# Copy the necessary modifications in the folder .minecraft\mods
# Copy this file in the folder .minecraft\scripts


import mods.nei.NEI;


# DATABASES

# Genesis
NEI.overrideName(<BinnieCore:genesis>,"Генезис");
<BinnieCore:genesis>.addTooltip(format.darkGreen("Genesis"));
# Apiarist Database
NEI.overrideName(<ExtraBees:dictionary:0>,"База данных пчеловода");
<ExtraBees:dictionary:0>.addTooltip(format.darkGreen("Apiarist Database"));
# Master Apiarist Database
NEI.overrideName(<ExtraBees:dictionary:1>,"База данных пчеловода-мастера");
<ExtraBees:dictionary:1>.addTooltip(format.darkGreen("Master Apiarist Database"));
# Arborist Database
NEI.overrideName(<ExtraTrees:database:0>,"База данных лесовода");
<ExtraTrees:database:0>.addTooltip(format.darkGreen("Arborist Database"));
# Master Arborist Database
NEI.overrideName(<ExtraTrees:database:1>,"База данных лесовода-мастера");
<ExtraTrees:database:1>.addTooltip(format.darkGreen("Master Arborist Database"));
# Lepidopterist Database
NEI.overrideName(<ExtraTrees:databaseMoth:0>,"База данных бабочковеда");
<ExtraTrees:databaseMoth:0>.addTooltip(format.darkGreen("Lepidopterist Database"));
# Master Lepidopterist Database
NEI.overrideName(<ExtraTrees:databaseMoth:1>,"База данных бабочковеда-мастера");
<ExtraTrees:databaseMoth:1>.addTooltip(format.darkGreen("Master Lepidopterist Database"));
# Gene Database
NEI.overrideName(<Genetics:database:0>,"Генная база данных");
<Genetics:database:0>.addTooltip(format.darkGreen("Gene Database"));
# Master Gene Database
NEI.overrideName(<Genetics:database:1>,"Генная база данных мастера");
<Genetics:database:1>.addTooltip(format.darkGreen("Master Gene Database"));
# Registry
NEI.overrideName(<Genetics:registry:0>,"Картотека");
<Genetics:registry:0>.addTooltip(format.darkGreen("Registry"));
# Master Registry
NEI.overrideName(<Genetics:masterRegistry:0>,"Картотека мастера");
<Genetics:masterRegistry:0>.addTooltip(format.darkGreen("Master Registry"));
# Analyst
NEI.overrideName(<Genetics:analyst:0>,"Аналитик");
<Genetics:analyst:0>.addTooltip(format.darkGreen("Analyst"));



# Master Carpentry Hammer
NEI.overrideName(<ExtraTrees:durableHammer:0>,"Плотницкий молоток мастера");
<ExtraTrees:durableHammer:0>.addTooltip(format.darkGreen("Master Carpentry Hammer"));

# Carpentry Hammer
NEI.overrideName(<ExtraTrees:hammer:0>,"Плотницкий молоток");
<ExtraTrees:hammer:0>.addTooltip(format.darkGreen("Carpentry Hammer"));


# MACHINES KOMPONENTS GENETICS
# DNA Sequence
<Genetics:sequence:*>.addTooltip(format.lightPurple("Последовательность ДНК"));

# Serum
<Genetics:serum:*>.addTooltip(format.lightPurple("Сыворотка"));

# Serum Array
<Genetics:serumArray:*>.addTooltip(format.lightPurple("Массив с сывороткой"));

# Soil Meter
NEI.overrideName(<Botany:soilMeter:*>,"Почвомер");
<Botany:soilMeter:*>.addTooltip(format.darkGreen("Soil Meter"));


# EXTRA TREES MISC

# Sawdust
NEI.overrideName(<ExtraTrees:misc:1>,"Опилки");
<ExtraTrees:misc:1>.addTooltip(format.darkGreen("Sawdust"));
# Bark
NEI.overrideName(<ExtraTrees:misc:2>,"Кора");
<ExtraTrees:misc:2>.addTooltip(format.darkGreen("Bark"));
# Proven Gear
NEI.overrideName(<ExtraTrees:misc:3>,"Проверенная шестерня");
<ExtraTrees:misc:3>.addTooltip(format.darkGreen("Proven Gear"));
# Wood Polish
NEI.overrideName(<ExtraTrees:misc:4>,"Полироль");
<ExtraTrees:misc:4>.addTooltip(format.darkGreen("Wood Polish"));


# Hops
NEI.overrideName(<ExtraTrees:misc:5>,"Хмель");
<ExtraTrees:misc:5>.addTooltip(format.darkGreen("Hops"));

# Yeast
NEI.overrideName(<ExtraTrees:misc:6>,"Дрожжи");
<ExtraTrees:misc:6>.addTooltip(format.darkGreen("Yeast"));

# Lager Yeast
NEI.overrideName(<ExtraTrees:misc:7>,"Пивоваренные дрожжи");
<ExtraTrees:misc:7>.addTooltip(format.darkGreen("Lager Yeast"));

# Wheat Grain
NEI.overrideName(<ExtraTrees:misc:8>,"Зёрна пшеницы");
<ExtraTrees:misc:8>.addTooltip(format.darkGreen("Wheat Grain"));

# Barley Grain
NEI.overrideName(<ExtraTrees:misc:9>,"Зёрна ячменя");
<ExtraTrees:misc:9>.addTooltip(format.darkGreen("Barley Grain"));

# Rye Grain
NEI.overrideName(<ExtraTrees:misc:10>,"Зёрна ржи");
<ExtraTrees:misc:10>.addTooltip(format.darkGreen("Rye Grain"));

# Corn Grain
NEI.overrideName(<ExtraTrees:misc:11>,"Зёрна кукурузы");
<ExtraTrees:misc:11>.addTooltip(format.darkGreen("Corn Grain"));

# Roasted Grain
NEI.overrideName(<ExtraTrees:misc:12>,"Зёрна жареные");
<ExtraTrees:misc:12>.addTooltip(format.darkGreen("Roasted Grain"));

# Glass Fittings
NEI.overrideName(<ExtraTrees:misc:13>,"Стеклянные фиттинги (детали)");
<ExtraTrees:misc:13>.addTooltip(format.darkGreen("Glass Fittings"));


# FLUIDS

NEI.overrideName(<BinnieCore:containerCan:128>,"Банка живицы");
NEI.overrideName(<BinnieCore:containerCapsule:128>,"Капсула живицы");
NEI.overrideName(<BinnieCore:containerBucket:128>,"Ведро живицы");
NEI.overrideName(<BinnieCore:containerCylinder:128>,"Баллон живицы");

NEI.overrideName(<BinnieCore:containerCan:129>,"Банка смолы");
NEI.overrideName(<BinnieCore:containerCapsule:129>,"Капсула смолы");
NEI.overrideName(<BinnieCore:containerBucket:129>,"Ведро смолы");
NEI.overrideName(<BinnieCore:containerCylinder:129>,"Баллон смолы");

NEI.overrideName(<BinnieCore:containerCan:130>,"Банка латекса");
NEI.overrideName(<BinnieCore:containerCapsule:130>,"Капсула латекса");
NEI.overrideName(<BinnieCore:containerBucket:130>,"Ведро латекса");
NEI.overrideName(<BinnieCore:containerCylinder:130>,"Баллон латекса");

NEI.overrideName(<BinnieCore:containerCan:131>,"Банка скипидара");
NEI.overrideName(<BinnieCore:containerCapsule:131>,"Капсула скипидара");
NEI.overrideName(<BinnieCore:containerBucket:131>,"Ведро скипидара");
NEI.overrideName(<BinnieCore:containerCylinder:131>,"Баллон скипидара");

NEI.overrideName(<BinnieCore:containerCylinder:768>,"Баллон Питательной среды");
NEI.overrideName(<BinnieCore:containerCylinder:769>,"Баллон Бактерии");
NEI.overrideName(<BinnieCore:containerCylinder:770>,"Баллон Полимеризованной Бактерии");
NEI.overrideName(<BinnieCore:containerCylinder:771>,"Баллон Жидкой ДНК");
NEI.overrideName(<BinnieCore:containerCylinder:772>,"Баллон Бактерии Вектор");


# Gen.Machines energy cost tooltips
# Isolator
<Genetics:machine:0>.addShiftTooltip(format.lightPurple("40 RF/t"));
# Sequencer
<Genetics:machine:1>.addShiftTooltip(format.lightPurple("20 RF/t"));
# Polymeriser
<Genetics:machine:2>.addShiftTooltip(format.lightPurple("40 RF/t"));
# Inoculator
<Genetics:machine:3>.addShiftTooltip(format.lightPurple("50 RF/t"));
# Analyser
<Genetics:labMachine:1>.addShiftTooltip(format.lightPurple("30 RF/t"));
# Incubator
<Genetics:labMachine:2>.addShiftTooltip(format.lightPurple("2 RF/t"));
# Genepool
<Genetics:labMachine:3>.addShiftTooltip(format.lightPurple("20 RF/t"));
# Acclimatiser
<Genetics:labMachine:4>.addShiftTooltip(format.lightPurple("2 RF/t"));
# Splicer
<Genetics:advMachine:0>.addShiftTooltip(format.lightPurple("10000 RF/t"));
