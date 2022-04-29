package cz.cuni.mff.java.hw.jdomlinks;

public class SectionTuple {
    public String Link;
    public String InSection;
    public String LinkText;
    public Integer SectionID;

    public SectionTuple(String link, String inSection, String linkText, Integer sectionID) {
        Link = link;
        InSection = inSection;
        LinkText = linkText;
        SectionID = sectionID;
    }
}
