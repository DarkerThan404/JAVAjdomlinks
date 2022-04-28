package cz.cuni.mff.java.hw.jdomlinks;

public class SectionTuple {
    private final String SectionIn;
    private final String SectionOut;

    public SectionTuple(String sectionIn, String sectionOut) {
        SectionIn = sectionIn;
        SectionOut = sectionOut;
    }

    public String getSectionIn() {
        return SectionIn;
    }

    public String getSectionOut() {
        return SectionOut;
    }
}
