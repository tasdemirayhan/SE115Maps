import java.util.ArrayList;
import java.util.Arrays;

public class WayFinder {
    private CountryMap countryMap;

    public WayFinder(CountryMap countryMap) {
        this.countryMap = countryMap;
    }

    public CountryMap getCountryMap() {
        return countryMap;
    }

    public void setCountryMap(CountryMap countryMap) {
        this.countryMap = countryMap;
    }
    // ziyaret edilmemiş en küçük indexi döndürür
    public int findMinIndex(int[] dist, boolean[] visited) {
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
    public City[] createShortestPath(City start, City end){
        int[] distance = new int[countryMap.getAdjacency().length]; // sum of all steps distances
        boolean[] isVisited = new boolean[countryMap.getAdjacency().length];
        int[] previousCities = new int[countryMap.getAdjacency().length];
        countryMap.fullSingle(distance);
        Arrays.fill(previousCities, -1);
        int startIndex = countryMap.getCityIndex(start);
        distance[startIndex]=0;
        for(int i=0;i<distance.length-1;i++){
            int min  = findMinIndex(distance, isVisited);
            
            // j indexli data ziyaret edilmemişse, min/j arasında bir kenar varsa
            // ve min üzerinden j'ye olan mesafe daha kısa ise distance[j] yi güncelle
            //distance[j], her tur distance[min] le tanımlanır
            for(int j=0;j<distance.length;j++){
                if(!isVisited[j] && countryMap.getAdjacency()[min][j]!=0 && distance[min]!=Integer.MAX_VALUE && distance[min] + countryMap.getAdjacency()[min][j]<distance[j]){
                    distance[j] = distance[min] + countryMap.getAdjacency()[min][j];
                }
            }
            isVisited[min]=true;
            for (int j = 0; j < countryMap.getAdjacency().length; j++) {
                if (countryMap.getAdjacency()[min][j] != Integer.MAX_VALUE) {
                    int newDist = distance[min] + countryMap.getAdjacency()[min][j];
                    if (newDist < distance[j]) {
                        distance[j] = newDist;
                        previousCities[j] = min;
                    }
                }
            }
        }
        City[] path = new City[countryMap.getAdjacency().length];
        int endIndex = countryMap.getCityIndex(end);
        for (int i = endIndex; i != -1; i = previousCities[i]) {
            add(countryMap.getCities()[i], path);
    }
        if (path.length == 1 && !path[0].equals(start)) {
            
        }
        return path;
    
    }
    public void add(City city, City[] array) {
        City[] newArray = new City[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = city;
        array = newArray;
    }

}
