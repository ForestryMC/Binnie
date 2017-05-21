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


# Reinforced Casing
NEI.overrideName(<Genetics:misc:0>,"Укреплённый корпус");
<Genetics:misc:0>.addTooltip(format.darkGreen("Reinforced Casing"));

# DNA Dye
NEI.overrideName(<Genetics:misc:1>,"Краситель для ДНК");
<Genetics:misc:1>.addTooltip(format.darkGreen("DNA Dye"));

# Fluorescent Dye
NEI.overrideName(<Genetics:misc:2>,"Флюорофор");
<Genetics:misc:2>.addTooltip(format.darkGreen("Fluorescent Dye"));

# Enzyme
NEI.overrideName(<Genetics:misc:3>,"Фермент");
<Genetics:misc:3>.addTooltip(format.darkGreen("Enzyme"));

# Growth Medium
NEI.overrideName(<Genetics:misc:4>,"Питательная среда");
<Genetics:misc:4>.addTooltip(format.darkGreen("Growth Medium"));

# Blank Sequence
NEI.overrideName(<Genetics:misc:5>,"Пустая последовательность");
<Genetics:misc:5>.addTooltip(format.darkGreen("Blank Sequence"));

# Empty Serum Vial
NEI.overrideName(<Genetics:misc:6>,"Колба с пустой сывороткой");
<Genetics:misc:6>.addTooltip(format.darkGreen("Empty Serum Vial"));

# Empty Serum Array
NEI.overrideName(<Genetics:misc:7>,"Массив с пустой сывороткой");
<Genetics:misc:7>.addTooltip(format.darkGreen("Empty Serum Array"));

# Glass Cylinder
NEI.overrideName(<Genetics:misc:8>,"Пустой баллон");
<Genetics:misc:8>.addTooltip(format.darkGreen("Glass Cylinder"));

# Integrated Circuit Board
NEI.overrideName(<Genetics:misc:9>,"Интегральная печатная плата");
<Genetics:misc:9>.addTooltip(format.darkGreen("Integrated Circuit Board"));

# Integrated CPU
NEI.overrideName(<Genetics:misc:10>,"Интегральный процессор");
<Genetics:misc:10>.addTooltip(format.darkGreen("Integrated CPU"));

# Integrated Casing
NEI.overrideName(<Genetics:misc:11>,"Интегральный корпус");
<Genetics:misc:11>.addTooltip(format.darkGreen("Integrated Casing"));

# DNA Sequence
<Genetics:sequence:*>.addTooltip(format.lightPurple("Последовательность ДНК"));

# Serum
<Genetics:serum:*>.addTooltip(format.lightPurple("Сыворотка"));

# Serum Array
<Genetics:serumArray:*>.addTooltip(format.lightPurple("Массив с сывороткой"));

# Scented Gear
NEI.overrideName(<ExtraBees:misc:0>,"Благоухающая шестерня");
<ExtraBees:misc:0>.addTooltip(format.darkGreen("Scented Gear"));

# Soil Meter
NEI.overrideName(<Botany:soilMeter:*>,"Почвомер");
<Botany:soilMeter:*>.addTooltip(format.darkGreen("Soil Meter"));




# BEEHIVES

# Binnie bee hives
NEI.overrideName(<ExtraBees:hive:0>,"Водный улей");
<ExtraBees:hive:0>.addTooltip(format.darkGreen("Water Hive"));
NEI.overrideName(<ExtraBees:hive:1>,"Каменный улей");
<ExtraBees:hive:1>.addTooltip(format.darkGreen("Rock Hive"));
NEI.overrideName(<ExtraBees:hive:2>,"Адский улей");
<ExtraBees:hive:2>.addTooltip(format.darkGreen("Nether Hive"));


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


# Clay Dust
NEI.overrideName(<ExtraBees:misc:26>,"Глиняная пыль");
<ExtraBees:misc:26>.addTooltip(format.darkGreen("Clay Dust"));




# FRAGMENTS

# Diamond Fragment
NEI.overrideName(<ExtraBees:misc:1>,"Алмазный осколок");
<ExtraBees:misc:1>.addTooltip(format.darkGreen("Diamond Fragment"));

# Emerald Fragment
NEI.overrideName(<ExtraBees:misc:2>,"Изумрудный осколок");
<ExtraBees:misc:2>.addTooltip(format.darkGreen("Emerald Fragment"));

# Ruby Fragment
NEI.overrideName(<ExtraBees:misc:3>,"Рубиновый осколок");
<ExtraBees:misc:3>.addTooltip(format.darkGreen("Ruby Fragment"));

# Sapphire Fragment
NEI.overrideName(<ExtraBees:misc:4>,"Сапфировый осколок");
<ExtraBees:misc:4>.addTooltip(format.darkGreen("Sapphire Fragment"));

# Lapis Fragment
NEI.overrideName(<ExtraBees:misc:5>,"Лазуритовый осколок");
<ExtraBees:misc:5>.addTooltip(format.darkGreen("Lapis Fragment"));




# DYE

# White Dye
NEI.overrideName(<ExtraBees:misc:23>,"Белый краситель");
<ExtraBees:misc:23>.addTooltip(format.darkGreen("White Dye"));

# Black Dye
NEI.overrideName(<ExtraBees:misc:24>,"Чёрный краситель");
<ExtraBees:misc:24>.addTooltip(format.darkGreen("Black Dye"));

# Green Dye
NEI.overrideName(<ExtraBees:misc:22>,"Зелёный краситель");
<ExtraBees:misc:22>.addTooltip(format.darkGreen("Green Dye"));

# Blue Dye
NEI.overrideName(<ExtraBees:misc:21>,"Синий краситель");
<ExtraBees:misc:21>.addTooltip(format.darkGreen("Blue Dye"));

# Yellow Dye
NEI.overrideName(<ExtraBees:misc:20>,"Жёлтый краситель");
<ExtraBees:misc:20>.addTooltip(format.darkGreen("Yellow Dye"));

# Red Dye
NEI.overrideName(<ExtraBees:misc:19>,"Красный краситель");
<ExtraBees:misc:19>.addTooltip(format.darkGreen("Red Dye"));

# Brown Dye
NEI.overrideName(<ExtraBees:misc:25>,"Коричневый краситель");
<ExtraBees:misc:25>.addTooltip(format.darkGreen("Brown Dye"));

# GRAINS

# Iron Grains
NEI.overrideName(<ExtraBees:misc:6>,"Железные зёрна");
<ExtraBees:misc:6>.addTooltip(format.darkGreen("Iron Grains"));

# Gold Grains
NEI.overrideName(<ExtraBees:misc:7>,"Золотые зёрна");
<ExtraBees:misc:7>.addTooltip(format.darkGreen("Gold Grains"));

# Silver Grains
NEI.overrideName(<ExtraBees:misc:8>,"Серебряные зёрна");
<ExtraBees:misc:8>.addTooltip(format.darkGreen("Silver Grains"));

# Platinum Grains
NEI.overrideName(<ExtraBees:misc:9>,"Платиновые зёрна");
<ExtraBees:misc:9>.addTooltip(format.darkGreen("Platinum Grains"));

# Nickel Grains
NEI.overrideName(<ExtraBees:misc:12>,"Никелевые зёрна");
<ExtraBees:misc:12>.addTooltip(format.darkGreen("Nickel Grains"));

# Zinc Grains
NEI.overrideName(<ExtraBees:misc:14>,"Цинковые зёрна");
<ExtraBees:misc:14>.addTooltip(format.darkGreen("Zinc Grains"));

# Titanium Grains
NEI.overrideName(<ExtraBees:misc:15>,"Титановые зёрна");
<ExtraBees:misc:15>.addTooltip(format.darkGreen("Titanium Grains"));

# Coal Grains
NEI.overrideName(<ExtraBees:misc:18>,"Угольные зёрна");
<ExtraBees:misc:18>.addTooltip(format.darkGreen("Coal Grains"));

# Radioactive Fragments
NEI.overrideName(<ExtraBees:misc:17>,"Радиоактивные зёрна");
<ExtraBees:misc:17>.addTooltip(format.darkGreen("Radioactive Fragments"));

# Lead Grains
NEI.overrideName(<ExtraBees:misc:13>,"Свинцовые зёрна");
<ExtraBees:misc:13>.addTooltip(format.darkGreen("Lead Grains"));

# Tin Grains
NEI.overrideName(<ExtraBees:misc:11>,"Оловянные зёрна");
<ExtraBees:misc:11>.addTooltip(format.darkGreen("Tin Grains"));

# Copper Grains
NEI.overrideName(<ExtraBees:misc:10>,"Медные зёрна");
<ExtraBees:misc:10>.addTooltip(format.darkGreen("Copper Grains"));

# Blutonium Grains
NEI.overrideName(<ExtraBees:misc:28>,"Блутониевые зёрна");
<ExtraBees:misc:28>.addTooltip(format.darkGreen("Blutonium Grains"));

# Yellorium Grains
NEI.overrideName(<ExtraBees:misc:27>,"Йеллориумовые зёрна");
<ExtraBees:misc:27>.addTooltip(format.darkGreen("Yellorium Grains"));



# Insulated Tubes

# Copper Insulated Tube - Clay
NEI.overrideName(<Botany:insulatedTube:0>,"Медная электронная лампа (глина)");

# Copper Insulated Tube - Cobblectone
NEI.overrideName(<Botany:insulatedTube:128>,"Медная электронная лампа (булыжник)");

# Copper Insulated Tube - Sand
NEI.overrideName(<Botany:insulatedTube:256>,"Медная электронная лампа (песок)");

# Copper Insulated Tube - Hardened Clay
NEI.overrideName(<Botany:insulatedTube:384>,"Медная электронная лампа (Затвердевшая глина)");

# Copper Insulated Tube - Smooth Stone
NEI.overrideName(<Botany:insulatedTube:512>,"Медная электронная лампа (гладкий камень)");

# Copper Insulated Tube - Sandstone
NEI.overrideName(<Botany:insulatedTube:640>,"Медная электронная лампа (песчаник)");

# Tin Insulated Tube - Clay
NEI.overrideName(<Botany:insulatedTube:1>,"Оловянная электронная лампа (глина)");

# Tin Insulated Tube - Cobblestone
NEI.overrideName(<Botany:insulatedTube:129>,"Оловянная электронная лампа (булыжник)");

# Tin Insulated Tube - Sand
NEI.overrideName(<Botany:insulatedTube:257>,"Оловянная электронная лампа (песок)");

# Tin Insulated Tube - Hardened Clay
NEI.overrideName(<Botany:insulatedTube:385>,"Оловянная электронная лампа (затвердевшая глина)");

# Tin Insulated Tube - Smooth Stone
NEI.overrideName(<Botany:insulatedTube:513>,"Оловянная электронная лампа (гладкий камень)");

# Tin Insulated Tube - Sandstone
NEI.overrideName(<Botany:insulatedTube:641>,"Оловянная электронная лампа (песчаник)");

# Bronze Insulated Tube - Clay
NEI.overrideName(<Botany:insulatedTube:2>,"Бронзовая электронная лампа (глина)");

# Bronze Insulated Tube - Cobblestone
NEI.overrideName(<Botany:insulatedTube:130>,"Бронзовая электронная лампа (булыжник)");

# Bronze Insulated Tube - Sand
NEI.overrideName(<Botany:insulatedTube:258>,"Бронзовая электронная лампа (песок)");

# Bronze Insulated Tube - Hardened Clay
NEI.overrideName(<Botany:insulatedTube:386>,"Бронзовая электронная лампа (затвердевшая глина)");

# Bronze Insulated Tube - Smooth Stone
NEI.overrideName(<Botany:insulatedTube:514>,"Бронзовая электронная лампа (гладкий камень)");

# Bronze Insulated Tube - Sandstone
NEI.overrideName(<Botany:insulatedTube:642>,"Бронзовая электронная лампа (песчаник)");

# Iron Insulated Tube - Clay
NEI.overrideName(<Botany:insulatedTube:3>,"Железная электронная лампа (глина)");

# Iron Insulated Tube - Cobblestone
NEI.overrideName(<Botany:insulatedTube:131>,"Железная электронная лампа (булыжник)");

# Iron Insulated Tube - Sand
NEI.overrideName(<Botany:insulatedTube:259>,"Железная электронная лампа (песок)");

# Iron Insulated Tube - Hardened Clay
NEI.overrideName(<Botany:insulatedTube:387>,"Железная электронная лампа (затвердевшая глина)");

# Iron Insulated Tube - Smooth Stone
NEI.overrideName(<Botany:insulatedTube:515>,"Железная электронная лампа (гладкий камень)");

# Iron Insulated Tube - Sandstone
NEI.overrideName(<Botany:insulatedTube:643>,"Железная электронная лампа (песчаник)");




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
