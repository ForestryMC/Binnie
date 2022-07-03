package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IPattern;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum EnumPattern implements IPattern {
    Blank,
    Octagon,
    Diamond,
    Ringed,
    Squared,
    Multiply,
    Circle,
    Plus,
    Creeper,
    Creeper2,
    Creeper3,
    Creeper4,
    Halved,
    Halved2,
    Striped,
    Striped2,
    ThinStriped,
    ThinStriped2,
    Chequered,
    Tiled,
    VeryThinCorner,
    VeryThinCorner2,
    VeryThinCorner3,
    VeryThinCorner4,
    ThinCorner,
    ThinCorner2,
    ThinCorner3,
    ThinCorner4,
    Corner,
    Corner2,
    Corner3,
    Corner4,
    ThickCorner,
    ThickCorner2,
    ThickCorner3,
    ThickCorner4,
    ThinCurvedCorner,
    ThinCurvedCorner2,
    ThinCurvedCorner3,
    ThinCurvedCorner4,
    CurvedCorner,
    CurvedCorner2,
    CurvedCorner3,
    CurvedCorner4,
    Edged,
    Edged2,
    Edged3,
    Edged4,
    ThinEdged,
    ThinEdged2,
    ThinEdged3,
    ThinEdged4,
    ThinBarred,
    ThinBarred2,
    Barred,
    Barred2,
    ThickBarred,
    ThickBarred2,
    BarredEnd,
    BarredEnd2,
    BarredEnd3,
    BarredEnd4,
    OrnateBarred,
    OrnateBarred2,
    OrnateThinBarred,
    OrnateThinBarred2,
    SplitBarred,
    SplitBarred2,
    SplitBarredCorner,
    SplitBarredCorner2,
    SplitBarredCorner3,
    SplitBarredCorner4,
    SplitBarredTSection,
    SplitBarredTSection2,
    SplitBarredTSection3,
    SplitBarredTSection4,
    SplitCrossed,
    SplitBarredEnd,
    SplitBarredEnd2,
    SplitBarredEnd3,
    SplitBarredEnd4,
    Diagonal,
    Diagonal2,
    ThickDiagonal,
    ThickDiagonal2,
    DiagonalCorner,
    DiagonalCorner2,
    DiagonalCorner3,
    DiagonalCorner4,
    DiagonalTSection,
    DiagonalTSection2,
    DiagonalTSection3,
    DiagonalTSection4,
    DiagonalCurvedCorner,
    DiagonalCurvedCorner2,
    DiagonalCurvedCorner3,
    DiagonalCurvedCorner4,
    DiagonalCurvedTSection,
    DiagonalCurvedTSection2,
    DiagonalCurvedTSection3,
    DiagonalCurvedTSection4,
    DiagonalStriped,
    DiagonalStriped2,
    ThinDiagonalStriped,
    ThinDiagonalStriped2,
    ThinSaltire,
    Saltire,
    ThickSaltire,
    ThinCrossed,
    Crossed,
    ThickCrossed,
    ThinCurvedCrossed,
    ThinTSection,
    ThinTSection2,
    ThinTSection3,
    ThinTSection4,
    TSection,
    TSection2,
    TSection3,
    TSection4,
    ThickTSection,
    ThickTSection2,
    ThickTSection3,
    ThickTSection4,
    ThinCurvedTSection,
    ThinCurvedTSection2,
    ThinCurvedTSection3,
    ThinCurvedTSection4,
    CurvedTSection,
    CurvedTSection2,
    CurvedTSection3,
    CurvedTSection4,
    ThinBarredCorner,
    ThinBarredCorner2,
    ThinBarredCorner3,
    ThinBarredCorner4,
    BarredCorner,
    BarredCorner2,
    BarredCorner3,
    BarredCorner4,
    ThickBarredCorner,
    ThickBarredCorner2,
    ThickBarredCorner3,
    ThickBarredCorner4,
    ThinCurvedBarredCorner,
    ThinCurvedBarredCorner2,
    ThinCurvedBarredCorner3,
    ThinCurvedBarredCorner4,
    BarredCurvedCorner,
    BarredCurvedCorner2,
    BarredCurvedCorner3,
    BarredCurvedCorner4,
    ThinStripedCorner,
    ThinStripedCorner2,
    ThinStripedCorner3,
    ThinStripedCorner4,
    StripedCorner,
    StripedCorner2,
    StripedCorner3,
    StripedCorner4,
    OrnateStripedCorner,
    OrnateStripedCorner2,
    OrnateStripedCorner3,
    OrnateStripedCorner4,
    Emblem1,
    Emblem2,
    Emblem3,
    Emblem4,
    Emblem5,
    LetterA,
    LetterA2,
    LetterA3,
    LetterA4,
    LetterB,
    LetterB2,
    LetterB3,
    LetterB4,
    LetterF,
    LetterF2,
    LetterF3,
    LetterF4,
    LetterS,
    LetterS2,
    LetterS3,
    LetterS4,
    LetterT,
    LetterT2,
    LetterT3,
    LetterT4,
    DiagonalHalved,
    DiagonalHalved2,
    Diagonal1Edged,
    Diagonal1Edged2,
    Diagonal1Edged3,
    Diagonal1Edged4,
    Diagonal2Edged,
    Diagonal2Edged2,
    ThickDiagonal1Edged,
    ThickDiagonal1Edged2,
    ThickDiagonal1Edged3,
    ThickDiagonal1Edged4,
    ThinBarredEnd,
    ThinBarredEnd2,
    ThinBarredEnd3,
    ThinBarredEnd4,
    ThickBarredEnd,
    ThickBarredEnd2,
    ThickBarredEnd3,
    ThickBarredEnd4,
    OverlappedSplitBarred,
    OverlappedSplitBarred2,
    OverlappedBarred,
    OverlappedBarred2;

    static {
        setupStripedPattern(EnumPattern.ThinStriped, EnumPattern.ThinStriped2);
        setupStripedPattern(EnumPattern.Striped, EnumPattern.Striped2);
        setupStripedPattern(EnumPattern.Halved, EnumPattern.Halved2);
        setupChequeredPattern(EnumPattern.Chequered);
        setupChequeredPattern(EnumPattern.Tiled);
        setupCornerPattern(
                EnumPattern.VeryThinCorner,
                EnumPattern.VeryThinCorner2,
                EnumPattern.VeryThinCorner3,
                EnumPattern.VeryThinCorner4);
        setupCornerPattern(
                EnumPattern.ThinCorner, EnumPattern.ThinCorner2, EnumPattern.ThinCorner3, EnumPattern.ThinCorner4);
        setupCornerPattern(EnumPattern.Corner, EnumPattern.Corner2, EnumPattern.Corner3, EnumPattern.Corner4);
        setupCornerPattern(
                EnumPattern.ThickCorner, EnumPattern.ThickCorner2, EnumPattern.ThickCorner3, EnumPattern.ThickCorner4);
        setupCornerPattern(
                EnumPattern.ThinCurvedCorner,
                EnumPattern.ThinCurvedCorner2,
                EnumPattern.ThinCurvedCorner3,
                EnumPattern.ThinCurvedCorner4);
        setupCornerPattern(
                EnumPattern.CurvedCorner,
                EnumPattern.CurvedCorner2,
                EnumPattern.CurvedCorner3,
                EnumPattern.CurvedCorner4);
        setupBarredPattern(
                EnumPattern.BarredEnd, EnumPattern.BarredEnd2, EnumPattern.BarredEnd3, EnumPattern.BarredEnd4);
        setupEdgedPattern(
                EnumPattern.ThinEdged, EnumPattern.ThinEdged2, EnumPattern.ThinEdged3, EnumPattern.ThinEdged4);
        setupEdgedPattern(EnumPattern.Edged, EnumPattern.Edged2, EnumPattern.Edged3, EnumPattern.Edged4);
        setupBarredPattern(EnumPattern.ThinBarred, EnumPattern.ThinBarred2);
        setupBarredPattern(EnumPattern.Barred, EnumPattern.Barred2);
        setupBarredPattern(EnumPattern.ThickBarred, EnumPattern.ThickBarred2);
        setupDiagonalPattern(EnumPattern.Diagonal, EnumPattern.Diagonal2);
        setupDiagonalPattern(EnumPattern.ThickDiagonal, EnumPattern.ThickDiagonal2);
        setupTSectionPattern(
                EnumPattern.ThinTSection,
                EnumPattern.ThinTSection2,
                EnumPattern.ThinTSection3,
                EnumPattern.ThinTSection4);
        setupTSectionPattern(EnumPattern.TSection, EnumPattern.TSection2, EnumPattern.TSection3, EnumPattern.TSection4);
        setupTSectionPattern(
                EnumPattern.ThickTSection,
                EnumPattern.ThickTSection2,
                EnumPattern.ThickTSection3,
                EnumPattern.ThickTSection4);
        setupTSectionPattern(
                EnumPattern.ThinCurvedTSection,
                EnumPattern.ThinCurvedTSection2,
                EnumPattern.ThinCurvedTSection3,
                EnumPattern.ThinCurvedTSection4);
        setupTSectionPattern(
                EnumPattern.CurvedTSection,
                EnumPattern.CurvedTSection2,
                EnumPattern.CurvedTSection3,
                EnumPattern.CurvedTSection4);
        setupCornerPattern(
                EnumPattern.ThinBarredCorner,
                EnumPattern.ThinBarredCorner2,
                EnumPattern.ThinBarredCorner3,
                EnumPattern.ThinBarredCorner4);
        setupCornerPattern(
                EnumPattern.BarredCorner,
                EnumPattern.BarredCorner2,
                EnumPattern.BarredCorner3,
                EnumPattern.BarredCorner4);
        setupCornerPattern(
                EnumPattern.ThickBarredCorner,
                EnumPattern.ThickBarredCorner2,
                EnumPattern.ThickBarredCorner3,
                EnumPattern.ThickBarredCorner4);
        setupCornerPattern(
                EnumPattern.ThinCurvedBarredCorner,
                EnumPattern.ThinCurvedBarredCorner2,
                EnumPattern.ThinCurvedBarredCorner3,
                EnumPattern.ThinCurvedBarredCorner4);
        setupCornerPattern(
                EnumPattern.BarredCurvedCorner,
                EnumPattern.BarredCurvedCorner2,
                EnumPattern.BarredCurvedCorner3,
                EnumPattern.BarredCurvedCorner4);
        setupCornerPattern(
                EnumPattern.ThinStripedCorner,
                EnumPattern.ThinStripedCorner2,
                EnumPattern.ThinStripedCorner3,
                EnumPattern.ThinStripedCorner4);
        setupCornerPattern(
                EnumPattern.StripedCorner,
                EnumPattern.StripedCorner2,
                EnumPattern.StripedCorner3,
                EnumPattern.StripedCorner4);
        setupCornerPattern(
                EnumPattern.OrnateStripedCorner,
                EnumPattern.OrnateStripedCorner2,
                EnumPattern.OrnateStripedCorner3,
                EnumPattern.OrnateStripedCorner4);
        setupRotation(EnumPattern.LetterA, EnumPattern.LetterA2, EnumPattern.LetterA3, EnumPattern.LetterA4);
        setupRotation(EnumPattern.LetterB, EnumPattern.LetterB2, EnumPattern.LetterB3, EnumPattern.LetterB4);
        setupRotation(EnumPattern.LetterF, EnumPattern.LetterF2, EnumPattern.LetterF3, EnumPattern.LetterF4);
        setupRotation(EnumPattern.LetterS, EnumPattern.LetterS2, EnumPattern.LetterS3, EnumPattern.LetterS4);
        setupRotation(EnumPattern.LetterT, EnumPattern.LetterT2, EnumPattern.LetterT3, EnumPattern.LetterT4);
        setupEdgedPattern(
                EnumPattern.DiagonalCorner,
                EnumPattern.DiagonalCorner2,
                EnumPattern.DiagonalCorner3,
                EnumPattern.DiagonalCorner4);
        setupCornerPattern(
                EnumPattern.DiagonalTSection,
                EnumPattern.DiagonalTSection2,
                EnumPattern.DiagonalTSection3,
                EnumPattern.DiagonalTSection4);
        setupEdgedPattern(
                EnumPattern.DiagonalCurvedCorner,
                EnumPattern.DiagonalCurvedCorner2,
                EnumPattern.DiagonalCurvedCorner3,
                EnumPattern.DiagonalCurvedCorner4);
        setupCornerPattern(
                EnumPattern.DiagonalCurvedTSection,
                EnumPattern.DiagonalCurvedTSection2,
                EnumPattern.DiagonalCurvedTSection3,
                EnumPattern.DiagonalCurvedTSection4);
        setupBarredPattern(EnumPattern.OrnateBarred, EnumPattern.OrnateBarred2);
        setupBarredPattern(EnumPattern.OrnateThinBarred, EnumPattern.OrnateThinBarred2);
        setupBarredPattern(EnumPattern.SplitBarred, EnumPattern.SplitBarred2);
        setupCornerPattern(
                EnumPattern.SplitBarredCorner,
                EnumPattern.SplitBarredCorner2,
                EnumPattern.SplitBarredCorner3,
                EnumPattern.SplitBarredCorner4);
        setupTSectionPattern(
                EnumPattern.SplitBarredTSection,
                EnumPattern.SplitBarredTSection2,
                EnumPattern.SplitBarredTSection3,
                EnumPattern.SplitBarredTSection4);
        setupBarredPattern(
                EnumPattern.SplitBarredEnd,
                EnumPattern.SplitBarredEnd2,
                EnumPattern.SplitBarredEnd3,
                EnumPattern.SplitBarredEnd4);
        setupRotation(EnumPattern.Creeper, EnumPattern.Creeper2, EnumPattern.Creeper3, EnumPattern.Creeper4);
        setupDiagonalPattern(EnumPattern.DiagonalStriped, EnumPattern.DiagonalStriped2);
        setupDiagonalPattern(EnumPattern.ThinDiagonalStriped, EnumPattern.ThinDiagonalStriped2);
        setupCornerPattern(
                EnumPattern.Diagonal1Edged,
                EnumPattern.Diagonal1Edged2,
                EnumPattern.Diagonal1Edged3,
                EnumPattern.Diagonal1Edged4);
        setupCornerPattern(
                EnumPattern.ThickDiagonal1Edged,
                EnumPattern.ThickDiagonal1Edged2,
                EnumPattern.ThickDiagonal1Edged3,
                EnumPattern.ThickDiagonal1Edged4);
        setupInvert2Rot(EnumPattern.DiagonalHalved, EnumPattern.DiagonalHalved2);
        set2Rotation(EnumPattern.Diagonal2Edged, EnumPattern.Diagonal2Edged2);
        setupBarredPattern(
                EnumPattern.ThinBarredEnd,
                EnumPattern.ThinBarredEnd2,
                EnumPattern.ThinBarredEnd3,
                EnumPattern.ThinBarredEnd4);
        setupBarredPattern(
                EnumPattern.ThickBarredEnd,
                EnumPattern.ThickBarredEnd2,
                EnumPattern.ThickBarredEnd3,
                EnumPattern.ThickBarredEnd4);
        EnumPattern.OverlappedBarred.setLeftRotation(EnumPattern.OverlappedBarred2, false);
        EnumPattern.OverlappedBarred2.setLeftRotation(EnumPattern.OverlappedBarred, false);
        EnumPattern.OverlappedSplitBarred.setLeftRotation(EnumPattern.OverlappedSplitBarred2, false);
        EnumPattern.OverlappedSplitBarred2.setLeftRotation(EnumPattern.OverlappedSplitBarred, false);
    }

    protected ILayout leftRotation;
    protected ILayout horizontalFlip;

    EnumPattern() {
        leftRotation = Layout.get(this, false);
        horizontalFlip = Layout.get(this, false);
    }

    static void setupStripedPattern(EnumPattern vert, EnumPattern hori) {
        vert.setLeftRotation(hori, true);
        hori.setLeftRotation(vert, false);
        vert.setHorizontalFlip(vert, true);
    }

    static void setupChequeredPattern(EnumPattern cheq) {
        cheq.setLeftRotation(cheq, true);
        cheq.setHorizontalFlip(cheq, true);
    }

    static void setupCornerPattern(EnumPattern tl, EnumPattern tr, EnumPattern br, EnumPattern bl) {
        tl.setLeftRotation(bl, false);
        tr.setLeftRotation(tl, false);
        br.setLeftRotation(tr, false);
        bl.setLeftRotation(br, false);
        tl.setHorizontalFlip(tr, false);
        bl.setHorizontalFlip(br, false);
    }

    static void setupInvert2Rot(EnumPattern a, EnumPattern b) {
        a.setLeftRotation(b, true);
        b.setLeftRotation(a, false);
        a.setHorizontalFlip(b, false);
    }

    static void set2Rotation(EnumPattern a, EnumPattern b) {
        a.setLeftRotation(b, false);
        b.setLeftRotation(a, false);
        a.setHorizontalFlip(b, false);
    }

    static void setupBarredPattern(EnumPattern vert, EnumPattern hori) {
        vert.setLeftRotation(hori, false);
        hori.setLeftRotation(vert, false);
    }

    static void setupEdgedPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
        l.setLeftRotation(b, false);
        t.setLeftRotation(l, false);
        r.setLeftRotation(t, false);
        b.setLeftRotation(r, false);
        l.setHorizontalFlip(r, false);
    }

    static void setupDiagonalPattern(EnumPattern a, EnumPattern b) {
        a.setLeftRotation(b, false);
        b.setLeftRotation(a, false);
        a.setHorizontalFlip(b, false);
    }

    static void setupBarredPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
        l.setLeftRotation(b, false);
        t.setLeftRotation(l, false);
        r.setLeftRotation(t, false);
        b.setLeftRotation(r, false);
        l.setHorizontalFlip(r, false);
    }

    static void setupTSectionPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
        setupEdgedPattern(l, t, r, b);
    }

    private static void setupRotation(EnumPattern t, EnumPattern r, EnumPattern b, EnumPattern l) {
        setupEdgedPattern(l, t, r, b);
    }

    @Override
    public IIcon getPrimaryIcon(IDesignSystem system) {
        return system.getPrimaryIcon(this);
    }

    private void setHorizontalFlip(EnumPattern pattern, boolean inverted) {
        horizontalFlip = Layout.get(pattern, inverted);
        pattern.horizontalFlip = Layout.get(this, inverted);
    }

    @Override
    public IIcon getSecondaryIcon(IDesignSystem system) {
        return system.getSecondaryIcon(this);
    }

    @Override
    public ILayout getRotation() {
        return leftRotation;
    }

    @Override
    public ILayout getHorizontalFlip() {
        return horizontalFlip;
    }

    protected void setLeftRotation(EnumPattern pattern, boolean inverted) {
        leftRotation = Layout.get(pattern, inverted);
    }

    @Override
    public void registerIcons(IIconRegister register) {}

    public ILayout layout() {
        return layout(false);
    }

    public ILayout layout(boolean invert) {
        return Layout.get(this, invert);
    }
}
