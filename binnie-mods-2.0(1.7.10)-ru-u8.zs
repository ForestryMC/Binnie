# This is a script for the translation of certain items mod Binnie, which are not delivered in the language file. 
# Translation into Russian.
# If possible translated or names or tooltips.
# The format of this script file MineTweaker - UTF8

# Necessary modifications:
# forestry_1.7.10
# binnie-mods-2.0.15-7,
# NotEnoughItems-1.7.10-1.0.5.110, CodeChickenCore-1.7.10-1.0.7.46, CodeChickenLib-1.7.10-1.1.3.138,
# CraftTweaker-1.7.10

# Optional modification:
# Waila-1.5.10

# How to use:
# Copy the necessary modifications in the folder .minecraft\mods
# Copy this file in the folder .minecraft\scripts

import mods.nei.NEI;

# DATABASES

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
