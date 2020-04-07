package excel.model;

public class ChemicalElement {

    private int atomicNumber;
    private String symbol;
    private String name;
    private String discoveredBy;
    private int yearOfDiscovery;
    private String atomicWeight;
    private String density;
    private String meltingPoint;
    private String boilingPoint;

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(String discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    public int getYearOfDiscovery() {
        return yearOfDiscovery;
    }

    public void setYearOfDiscovery(int yearOfDiscovery) {
        this.yearOfDiscovery = yearOfDiscovery;
    }

    public String getAtomicWeight() {
        return atomicWeight;
    }

    public void setAtomicWeight(String atomicWeight) {
        this.atomicWeight = atomicWeight;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getMeltingPoint() {
        return meltingPoint;
    }

    public void setMeltingPoint(String meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public String getBoilingPoint() {
        return boilingPoint;
    }

    public void setBoilingPoint(String boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    @Override
    public String toString() {
        return "ChemicalElement{" +
                "atomicNumber=" + atomicNumber +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", discoveredBy='" + discoveredBy + '\'' +
                ", yearOfDiscovery=" + yearOfDiscovery +
                ", atomicWeight='" + atomicWeight + '\'' +
                ", density='" + density + '\'' +
                ", meltingPoint='" + meltingPoint + '\'' +
                ", boilingPoint='" + boilingPoint + '\'' +
                '}';
    }
}
