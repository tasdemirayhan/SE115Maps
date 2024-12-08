public class City {
    private String label;
    public City(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return "City " + label ;
    }
}
