package cz.cuni.mff.java.hw.jdomlinks;

public class SectionTuple {
    public String ID;
    public String InSection;
    public String LinkText;
    public Integer SectionCounter;

    public SectionTuple(String id, String inSection, String linkText, Integer sectionCounter) {
        ID = id;
        InSection = inSection;
        LinkText = linkText;
        SectionCounter = sectionCounter;
    }
}
