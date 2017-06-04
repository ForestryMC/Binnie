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
