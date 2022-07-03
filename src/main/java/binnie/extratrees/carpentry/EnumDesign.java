package binnie.extratrees.carpentry;

import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.ILayout;

public enum EnumDesign implements IDesign {
    Blank("blank"),
    Octagon("octagon"),
    Diamond("diamond"),
    Ringed("ringed"),
    Squared("squared"),
    Multiply("multiply"),
    Halved("halved"),
    Striped("striped"),
    ThinStriped("thinStriped"),
    Chequered("fullChequered"),
    Tiled("fullTiled"),
    ChequeredB("chequered"),
    TiledB("tiled"),
    VeryThinCorner("veryThinCornered"),
    ThinCorner("thinCornered"),
    Corner("cornered"),
    ThickCorner("thickCornered"),
    Edged("edged"),
    ThinEdged("thinEdged"),
    ThinBarred("thinBarred"),
    Barred("barred"),
    ThickBarred("thickBarred"),
    Diagonal("diagonal"),
    ThickDiagonal("thickDiagonal"),
    ThinSaltire("thinSaltire"),
    Saltire("saltire"),
    ThickSaltire("thickSaltire"),
    ThinCrossed("thinCrossed"),
    Crossed("crossed"),
    ThickCrossed("thickCrossed"),
    ThinTSection("thinTSection"),
    TSection("tSection"),
    ThickTSection("thickTSection"),
    ThinBarredCorner("thinBarredCorner"),
    BarredCorner("barredCorner"),
    ThickBarredCorner("thickBarredCorner"),
    ThinStripedCorner("thinStripedCorner"),
    StripedCorner("stripedCorner"),
    Emblem1("emblem1"),
    Emblem2("emblem2"),
    Emblem3("emblem3"),
    Emblem4("emblem4"),
    Emblem5("emblem5"),
    LetterA("letterA"),
    LetterB("letterB"),
    LetterC("letterC"),
    LetterD("letterD"),
    LetterE("letterE"),
    LetterF("letterF"),
    LetterG("letterG"),
    LetterH("letterH"),
    LetterI("letterI"),
    LetterJ("letterJ"),
    LetterK("letterK"),
    LetterL("letterL"),
    LetterM("letterM"),
    LetterN("letterN"),
    LetterO("letterO"),
    LetterP("letterP"),
    LetterQ("letterQ"),
    LetterR("letterR"),
    LetterS("letterS"),
    LetterT("letterT"),
    LetterU("letterU"),
    LetterV("letterV"),
    LetterW("letterW"),
    LetterX("letterX"),
    LetterY("letterY"),
    LetterZ("letterZ"),
    ThinCurvedCrossed("thinCurvedCrossed"),
    ThinCurvedBarredCorner("thinCurvedBarredCorner"),
    CurvedBarredCorner("curvedBarredCorner"),
    ThinCurvedCorner("thinCurvedCorner"),
    CurvedCorner("curvedCorner"),
    ThinCurvedTSection("thinCurvedTSection"),
    CurvedTSection("curvedTSection"),
    BarredEnd("barredEnd"),
    DiagonalCorner("diagonalCorner"),
    DiagonalTSection("diagonalTSection"),
    DiagonalCurvedCorner("diagonalCurvedCorner"),
    DiagonalCurvedTSection("diagonalCurvedTSection"),
    OrnateBarred("ornateBarred"),
    SplitBarred("splitBarred"),
    SplitBarredCorner("splitBarredCorner"),
    SplitBarredTSection("splitBarredTSection"),
    SplitCrossed("splitCrossed"),
    SplitBarredEnd("splitBarredEnd"),
    OrnateThinBarred("ornateThinBarred"),
    Circle("circle"),
    Plus("plus"),
    Creeper("creeper"),
    OrnateStripedCorner("ornateStripedCorner"),
    Test("testingBlock"),
    DiagonalHalved("diagonalHalved"),
    Diagonal1Edged("corneredDiagonal"),
    Diagonal2Edged("oppositeCorneredDiagonal"),
    ThickDiagonal1Edged("thickCorneredDiagonal"),
    ThinBarredEnd("thinBarredEnd"),
    ThickBarredEnd("thickBarredEnd"),
    OverlappedBarred("overlappedBarred"),
    OverlappedSplitBarred("overlappedSplitBarred");

    static {
        EnumDesignCategory.Design.addDesign(EnumDesign.Blank);
        EnumDesignCategory.Design.addDesign(EnumDesign.Octagon);
        EnumDesignCategory.Design.addDesign(EnumDesign.Diamond);
        EnumDesignCategory.Design.addDesign(EnumDesign.Ringed);
        EnumDesignCategory.Design.addDesign(EnumDesign.Squared);
        EnumDesignCategory.Design.addDesign(EnumDesign.Multiply);
        EnumDesignCategory.Design.addDesign(EnumDesign.Plus);
        EnumDesignCategory.Design.addDesign(EnumDesign.Circle);
        EnumDesignCategory.Design.addDesign(EnumDesign.Emblem1);
        EnumDesignCategory.Design.addDesign(EnumDesign.Emblem2);
        EnumDesignCategory.Design.addDesign(EnumDesign.Emblem3);
        EnumDesignCategory.Design.addDesign(EnumDesign.Emblem4);
        EnumDesignCategory.Design.addDesign(EnumDesign.Emblem5);
        EnumDesignCategory.Design.addDesign(EnumDesign.Creeper);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.Chequered);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.ChequeredB);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.Tiled);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.TiledB);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.Striped);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.ThinStriped);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.ThinStripedCorner);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.StripedCorner);
        EnumDesignCategory.Stripes.addDesign(EnumDesign.OrnateStripedCorner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.Halved);
        EnumDesignCategory.Edges.addDesign(EnumDesign.Corner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.ThickCorner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.Edged);
        EnumDesignCategory.Edges.addDesign(EnumDesign.ThinCorner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.ThinEdged);
        EnumDesignCategory.Edges.addDesign(EnumDesign.VeryThinCorner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.ThinCurvedCorner);
        EnumDesignCategory.Edges.addDesign(EnumDesign.CurvedCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinBarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinTSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinCrossed);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinBarredEnd);
        EnumDesignCategory.Barred.addDesign(EnumDesign.OrnateThinBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.Barred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.BarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.TSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.Crossed);
        EnumDesignCategory.Barred.addDesign(EnumDesign.BarredEnd);
        EnumDesignCategory.Barred.addDesign(EnumDesign.OverlappedBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.OrnateBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThickBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThickBarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThickTSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThickCrossed);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThickBarredEnd);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinCurvedBarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.CurvedBarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinCurvedTSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.CurvedTSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.ThinCurvedCrossed);
        EnumDesignCategory.Barred.addDesign(EnumDesign.SplitBarred);
        EnumDesignCategory.Barred.addDesign(EnumDesign.SplitBarredCorner);
        EnumDesignCategory.Barred.addDesign(EnumDesign.SplitBarredTSection);
        EnumDesignCategory.Barred.addDesign(EnumDesign.SplitCrossed);
        EnumDesignCategory.Barred.addDesign(EnumDesign.SplitBarredEnd);
        EnumDesignCategory.Barred.addDesign(EnumDesign.OverlappedSplitBarred);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.ThinSaltire);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.Diagonal);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.DiagonalCorner);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.DiagonalTSection);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.DiagonalCurvedCorner);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.DiagonalCurvedTSection);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.Saltire);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.ThickDiagonal);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.ThickSaltire);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.DiagonalHalved);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.Diagonal1Edged);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.Diagonal2Edged);
        EnumDesignCategory.Diagonal.addDesign(EnumDesign.ThickDiagonal1Edged);
        EnumDesign.Octagon.setAllPatterns(EnumPattern.Octagon.layout());
        EnumDesign.Diamond.setAllPatterns(EnumPattern.Diamond.layout());
        EnumDesign.Ringed.setAllPatterns(EnumPattern.Ringed.layout());
        EnumDesign.Squared.setAllPatterns(EnumPattern.Squared.layout());
        EnumDesign.Multiply.setAllPatterns(EnumPattern.Multiply.layout());
        EnumDesign.ThinStriped.setupStriped(EnumPattern.ThinStriped.layout());
        EnumDesign.Striped.setupStriped(EnumPattern.Striped.layout());
        EnumDesign.Halved.setupStriped(EnumPattern.Halved.layout());
        EnumDesign.Chequered.setChequered(EnumPattern.Chequered.layout());
        EnumDesign.Tiled.setChequered(EnumPattern.Tiled.layout());
        EnumDesign.ChequeredB.setStripedChequered(EnumPattern.Chequered.layout(), EnumPattern.Halved.layout());
        EnumDesign.TiledB.setStripedChequered(EnumPattern.Tiled.layout(), EnumPattern.Striped.layout());
        EnumDesign.VeryThinCorner.setCornered(EnumPattern.VeryThinCorner.layout(), EnumPattern.ThinEdged.layout());
        EnumDesign.ThinCorner.setCornered(EnumPattern.ThinCorner.layout(), EnumPattern.Edged.layout());
        EnumDesign.Corner.setCornered(EnumPattern.Corner.layout(), EnumPattern.Halved.layout());
        EnumDesign.ThickCorner.setCornered(
                EnumPattern.ThickCorner.layout(), EnumPattern.Edged.layout(true).flipHorizontal());
        EnumDesign.ThinCurvedCorner.setCornered(EnumPattern.ThinCurvedCorner.layout(), EnumPattern.Edged.layout());
        EnumDesign.CurvedCorner.setCornered(EnumPattern.CurvedCorner.layout(), EnumPattern.Halved.layout());
        EnumDesign.Edged.setEdged(EnumPattern.Edged.layout());
        EnumDesign.ThinEdged.setEdged(EnumPattern.ThinEdged.layout());
        EnumDesign.ThinBarred.setBarred(EnumPattern.ThinBarred.layout());
        EnumDesign.Barred.setBarred(EnumPattern.Barred.layout());
        EnumDesign.ThickBarred.setBarred(EnumPattern.ThickBarred.layout());
        EnumDesign.Diagonal.setDiagonal(EnumPattern.Diagonal.layout(), EnumPattern.Edged.layout());
        EnumDesign.ThickDiagonal.setDiagonal(EnumPattern.ThickDiagonal.layout(), EnumPattern.Halved.layout());
        EnumDesign.ThinSaltire.setSaltire(EnumPattern.ThinSaltire.layout(), EnumPattern.ThickBarred.layout());
        EnumDesign.Saltire.setSaltire(EnumPattern.Saltire.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThickSaltire.setSaltire(EnumPattern.ThickSaltire.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.ThinCrossed.setCross(EnumPattern.ThinCrossed.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.Crossed.setCross(EnumPattern.Crossed.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThickCrossed.setCross(EnumPattern.ThickCrossed.layout(), EnumPattern.ThickBarred.layout());
        EnumDesign.ThinCurvedCrossed.setCross(EnumPattern.ThinCurvedCrossed.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.ThinTSection.setTSection(EnumPattern.ThinTSection.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.TSection.setTSection(EnumPattern.TSection.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThickTSection.setTSection(EnumPattern.ThickTSection.layout(), EnumPattern.ThickBarred.layout());
        EnumDesign.ThinCurvedTSection.setTSection(
                EnumPattern.ThinCurvedTSection.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.CurvedTSection.setTSection(EnumPattern.CurvedTSection.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThinBarredCorner.setBarredCorner(
                EnumPattern.ThinBarredCorner.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.BarredCorner.setBarredCorner(EnumPattern.BarredCorner.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThickBarredCorner.setBarredCorner(
                EnumPattern.ThickBarredCorner.layout(), EnumPattern.ThickBarred.layout());
        EnumDesign.ThinCurvedBarredCorner.setBarredCorner(
                EnumPattern.ThinCurvedBarredCorner.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.CurvedBarredCorner.setBarredCorner(
                EnumPattern.BarredCurvedCorner.layout(), EnumPattern.Barred.layout());
        EnumDesign.ThinStripedCorner.setStripedCorner(
                EnumPattern.ThinStripedCorner.layout(), EnumPattern.ThinStriped.layout());
        EnumDesign.StripedCorner.setStripedCorner(EnumPattern.StripedCorner.layout(), EnumPattern.Striped.layout());
        EnumDesign.OrnateStripedCorner.setStripedCorner(
                EnumPattern.OrnateStripedCorner.layout(), EnumPattern.ThinStriped.layout());
        EnumDesign.Emblem1.setAllPatterns(EnumPattern.Emblem1.layout());
        EnumDesign.Emblem2.setAllPatterns(EnumPattern.Emblem2.layout());
        EnumDesign.Emblem3.setAllPatterns(EnumPattern.Emblem3.layout());
        EnumDesign.Emblem4.setAllPatterns(EnumPattern.Emblem4.layout());
        EnumDesign.Emblem5.setAllPatterns(EnumPattern.Emblem5.layout());
        EnumDesign.LetterA.setLetterPattern(EnumPattern.LetterA.layout());
        EnumDesign.LetterB.setLetterPattern(EnumPattern.LetterB.layout());
        EnumDesign.LetterF.setLetterPattern(EnumPattern.LetterF.layout());
        EnumDesign.LetterS.setLetterPattern(EnumPattern.LetterS.layout());
        EnumDesign.LetterT.setLetterPattern(EnumPattern.LetterT.layout());
        EnumDesign.BarredEnd.setBarredEndPattern(EnumPattern.BarredEnd.layout(), EnumPattern.Barred.layout());
        EnumDesign.DiagonalCorner.setDiagonalCorner(
                EnumPattern.DiagonalCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
        EnumDesign.DiagonalTSection.setDiagonalTSection(
                EnumPattern.DiagonalTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
        EnumDesign.DiagonalCurvedCorner.setDiagonalCorner(
                EnumPattern.DiagonalCurvedCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
        EnumDesign.DiagonalCurvedTSection.setDiagonalTSection(
                EnumPattern.DiagonalCurvedTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
        EnumDesign.OrnateBarred.setBarred(EnumPattern.OrnateBarred.layout());
        EnumDesign.OrnateThinBarred.setBarred(EnumPattern.OrnateThinBarred.layout());
        EnumDesign.SplitBarred.setBarred(EnumPattern.SplitBarred.layout());
        EnumDesign.SplitBarredCorner.setBarredCorner(
                EnumPattern.SplitBarredCorner.layout(), EnumPattern.SplitBarred.layout());
        EnumDesign.SplitBarredTSection.setTSection(
                EnumPattern.SplitBarredTSection.layout(), EnumPattern.SplitBarred.layout());
        EnumDesign.SplitCrossed.setCross(EnumPattern.SplitCrossed.layout(), EnumPattern.SplitBarred.layout());
        EnumDesign.SplitBarredEnd.setBarredEndPattern(
                EnumPattern.SplitBarredEnd.layout(), EnumPattern.SplitBarred.layout());
        EnumDesign.Circle.setAllPatterns(EnumPattern.Circle.layout());
        EnumDesign.Plus.setAllPatterns(EnumPattern.Plus.layout());
        EnumDesign.Creeper.setAllPatterns(EnumPattern.Blank.layout(true));
        EnumDesign.Creeper.setTopPattern(EnumPattern.Creeper.layout());
        EnumDesign.DiagonalHalved.setTopPattern(EnumPattern.DiagonalHalved.layout());
        EnumDesign.DiagonalHalved.setEdgePatterns(
                EnumPattern.Blank.layout(),
                EnumPattern.Blank.layout(true),
                EnumPattern.Blank.layout(true),
                EnumPattern.Blank.layout());
        EnumDesign.Diagonal1Edged.setTopPattern(EnumPattern.Diagonal1Edged.layout());
        EnumDesign.Diagonal1Edged.setEdgePatterns(
                EnumPattern.Edged.layout().flipHorizontal(),
                EnumPattern.Blank.layout(true),
                EnumPattern.Blank.layout(true),
                EnumPattern.Edged.layout());
        EnumDesign.Diagonal2Edged.setTopPattern(EnumPattern.Diagonal2Edged.layout());
        EnumDesign.Diagonal2Edged.setEdgePatterns(
                EnumPattern.Edged.layout(),
                EnumPattern.Edged.layout().flipHorizontal(),
                EnumPattern.Edged.layout(),
                EnumPattern.Edged.layout().flipHorizontal());
        EnumDesign.ThickDiagonal1Edged.setTopPattern(EnumPattern.ThickDiagonal1Edged.layout());
        EnumDesign.ThickDiagonal1Edged.setEdgePatterns(
                EnumPattern.Halved.layout().flipHorizontal(),
                EnumPattern.Blank.layout(true),
                EnumPattern.Blank.layout(true),
                EnumPattern.Halved.layout());
        EnumDesign.ThickBarredEnd.setBarredEndPattern(
                EnumPattern.ThickBarredEnd.layout(), EnumPattern.ThickBarred.layout());
        EnumDesign.ThinBarredEnd.setBarredEndPattern(
                EnumPattern.ThinBarredEnd.layout(), EnumPattern.ThinBarred.layout());
        EnumDesign.OverlappedSplitBarred.setAllPatterns(EnumPattern.SplitBarred.layout());
        EnumDesign.OverlappedSplitBarred.setTopPattern(EnumPattern.OverlappedSplitBarred.layout());
        EnumDesign.OverlappedBarred.setAllPatterns(EnumPattern.Barred.layout());
        EnumDesign.OverlappedBarred.setTopPattern(EnumPattern.OverlappedBarred.layout());
    }

    protected String name;
    protected ILayout topPattern;
    protected ILayout bottomPattern;
    protected ILayout northPattern;
    protected ILayout southPattern;
    protected ILayout eastPattern;
    protected ILayout westPattern;

    EnumDesign(String name) {
        this.name = name;
        topPattern = Layout.get(EnumPattern.Blank, false);
        bottomPattern = Layout.get(EnumPattern.Blank, false);
        northPattern = Layout.get(EnumPattern.Blank, false);
        eastPattern = Layout.get(EnumPattern.Blank, false);
        southPattern = Layout.get(EnumPattern.Blank, false);
        westPattern = Layout.get(EnumPattern.Blank, false);
    }

    void setEdgePatterns(ILayout north, ILayout east, ILayout south, ILayout west) {
        setNorthPattern(north);
        setEastPattern(east);
        setSouthPattern(south);
        setWestPattern(west);
    }

    void setEdgePatterns(ILayout face) {
        setEdgePatterns(face, face, face, face);
    }

    void setupStriped(ILayout vert) {
        setTopPattern(vert);
        setEdgePatterns(vert.invert(), EnumPattern.Blank.layout(true), vert, EnumPattern.Blank.layout());
    }

    void setChequered(ILayout cheq) {
        setAllPatterns(cheq);
        setNorthPattern(cheq.invert());
        setSouthPattern(cheq.invert());
        setBottomPattern(cheq.invert());
    }

    void setStripedChequered(ILayout cheq, ILayout stripe) {
        setAllPatterns(cheq);
        setNorthPattern(stripe.invert());
        setSouthPattern(stripe.invert());
        setEastPattern(stripe);
        setWestPattern(stripe);
    }

    void setCornered(ILayout corner, ILayout edge) {
        setTopPattern(corner);
        setNorthPattern(EnumPattern.Blank.layout());
        setEastPattern(edge.flipHorizontal());
        setSouthPattern(edge);
        setWestPattern(EnumPattern.Blank.layout());
    }

    void setEdged(ILayout edge) {
        setAllPatterns(edge);
        setWestPattern(EnumPattern.Blank.layout());
        setEastPattern(EnumPattern.Blank.layout(true));
        northPattern = northPattern.flipHorizontal();
    }

    void setBarred(ILayout bar) {
        setAllPatterns(bar);
        setEastPattern(EnumPattern.Blank.layout(true));
        setWestPattern(EnumPattern.Blank.layout(true));
    }

    void setDiagonal(ILayout diagonal, ILayout edged) {
        setAllPatterns(edged);
        setTopPattern(diagonal);
        northPattern = northPattern.flipHorizontal();
        southPattern = southPattern.flipHorizontal();
    }

    void setSaltire(ILayout saltire, ILayout bar) {
        setTopPattern(saltire);
        setEdgePatterns(bar.invert());
    }

    void setCross(ILayout saltire, ILayout bar) {
        setTopPattern(saltire);
        setEdgePatterns(bar);
    }

    void setTSection(ILayout tsection, ILayout bar) {
        setTopPattern(tsection);
        setEdgePatterns(bar);
        setWestPattern(EnumPattern.Blank.layout(true));
    }

    void setBarredCorner(ILayout corner, ILayout bar) {
        setTSection(corner, bar);
        setNorthPattern(EnumPattern.Blank.layout(true));
    }

    void setStripedCorner(ILayout corner, ILayout striped) {
        setCornered(corner, striped);
    }

    void setLetterPattern(ILayout letter) {
        setAllPatterns(EnumPattern.Blank.layout(true));
        setTopPattern(letter);
        setBottomPattern(EnumPattern.Blank.layout(true));
    }

    void setBarredEndPattern(ILayout end, ILayout bar) {
        setAllPatterns(EnumPattern.Blank.layout(true));
        setTopPattern(end);
        setWestPattern(bar);
    }

    void setDiagonalCorner(ILayout diagonal, ILayout bar, ILayout edged) {
        setAllPatterns(EnumPattern.Blank.layout(true));
        setTopPattern(diagonal);
        setWestPattern(bar.invert());
        setNorthPattern(edged.flipHorizontal());
        setSouthPattern(edged);
    }

    void setDiagonalTSection(ILayout diagonal, ILayout bar, ILayout edged) {
        setAllPatterns(EnumPattern.Blank.layout(true));
        setTopPattern(diagonal);
        setWestPattern(bar.invert());
        setNorthPattern(bar.invert());
        setEastPattern(edged.flipHorizontal());
        setSouthPattern(edged);
    }

    private void setAllPatterns(ILayout layout) {
        setTopPattern(layout);
        setBottomPattern(layout);
        setNorthPattern(layout);
        setEastPattern(layout);
        setSouthPattern(layout);
        setWestPattern(layout);
    }

    @Override
    public String getName() {
        return I18N.localise("botany.design." + name);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ILayout getTopPattern() {
        return topPattern;
    }

    public void setTopPattern(ILayout layout) {
        topPattern = layout;
        setBottomPattern(layout);
    }

    @Override
    public ILayout getBottomPattern() {
        return bottomPattern;
    }

    public void setBottomPattern(ILayout layout) {
        bottomPattern = layout;
    }

    @Override
    public ILayout getNorthPattern() {
        return northPattern;
    }

    public void setNorthPattern(ILayout layout) {
        northPattern = layout;
    }

    @Override
    public ILayout getSouthPattern() {
        return southPattern;
    }

    public void setSouthPattern(ILayout layout) {
        southPattern = layout;
    }

    @Override
    public ILayout getEastPattern() {
        return eastPattern;
    }

    public void setEastPattern(ILayout layout) {
        eastPattern = layout;
    }

    @Override
    public ILayout getWestPattern() {
        return westPattern;
    }

    public void setWestPattern(ILayout layout) {
        westPattern = layout;
    }
}
