import java.util.ArrayList;

public class CountryMap {
    private ArrayList<City> cities;
    private int[][] adjacency;
    private int numberOfCity;
    public CountryMap(int numberOfCity) {
        this.numberOfCity = numberOfCity;
        cities= new ArrayList<City>();
        adjacency = new int[numberOfCity][numberOfCity];
        full(adjacency);
    }
    public ArrayList<City> getCities() {
        return cities;
    }
    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
    public int[][] getAdjacency() {
        return adjacency;
    }
    public void setAdjacency(int[][] adjacency) {
        this.adjacency = adjacency;
    }
    public int getNumberOfCity() {
        return numberOfCity;
    }
    public void setNumberOfCity(int numberOfCity) {
        this.numberOfCity = numberOfCity;
    }
    public void full(int[][] arr){
        for (int i=0;i<arr.length;i++) {
            for(int j=0;j<arr[i].length;j++){
                if(i==j){
                    arr[i][j] = 0;
                }
                else{
                    arr[i][j]=Integer.MAX_VALUE;
                }
            }
        }
    }
    public void addCity(City city){
        cities.add(city);
    }
    public int getCityIndex(City city){
        for (int i=0;i<cities.size();i++) {
            if(city.getLabel().equals(cities.get(i).getLabel())){
                return i;
            }
        }
        return -1;
    }
    public City findCityByLabel(String label) {
        for (City city : cities) {
            if (city.getLabel().equals(label)) {
                return city;
            }
        }
        return null;
    }
    public void addRoute(String label1, String label2 , int time){
        City city1 = findCityByLabel(label1);
        City city2 = findCityByLabel(label2);
        if (city1 != null && city2 != null) {
            adjacency[cities.indexOf(city1)][cities.indexOf(city2)]= time;
            adjacency[cities.indexOf(city1)][cities.indexOf(city2)]= time;
        }
    }
    public void printAdjacency(){
        for (int i=0;i<adjacency.length;i++) {
            for(int j=0;j<adjacency[i].length;j++){
                System.out.println(adjacency[i][j] + ",");
            }
            System.out.println();
        }
    }

}

