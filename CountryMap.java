import java.util.ArrayList;
import java.util.Arrays;

public class CountryMap {
    private ArrayList<City> cities;
    private int[][] adjacency;
    private int numberOfCity;
    public CountryMap(int numberOfCity) {
        this.numberOfCity = numberOfCity;
        cities= new ArrayList<City>();
        adjacency = new int[numberOfCity][numberOfCity];
        fullDouble(adjacency);
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
    public void fullDouble(int[][] arr){
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
    public void fullSingle(int[] arr, int index){
        for (int i=0;i<arr.length;i++) {
            arr[i]=Integer.MAX_VALUE;
        }
        arr[index]=0;
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
                System.out.printf("%11d", adjacency[i][j]);
            }
            System.out.println();
        }
    }
     
    // ziyaret edilmemiş en küçük indexi döndürür
    private int findMinIndex(int[] dist, boolean[] visited) {
        int minVal = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minVal) {
                minVal= dist[i];
                minIndex = i;
            }
        }
        return minIndex; 
    }
    public int[] createShortestPath(int index){
        int[] distance = new int[adjacency.length]; // sum of all steps distances
        boolean[] isVisited = new boolean[adjacency.length];
        fullSingle(distance,index);
        
        for(int i=0;i<distance.length-1;i++){
            int min  = findMinIndex(distance, isVisited);
            isVisited[min]=true;
            // j indexli data ziyaret edilmemişse, min/j arasında bir kenar varsa
            // ve min üzerinden j'ye olan mesafe daha kısa ise distance[j] yi güncelle
            //distance[j], her tur distance[min] le tanımlanır
            for(int j=0;j<distance.length;j++){
                if(!isVisited[j] && adjacency[min][j]!=0 && distance[min]!=Integer.MAX_VALUE && distance[min] + adjacency[min][j]<distance[j]){
                    distance[j] = distance[min] + adjacency[min][j];
                }
            }
        }
        return distance;
    }
}

