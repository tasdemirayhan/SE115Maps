import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    /*public int findMinIndex(int[] dist, boolean[] visited) {
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
            isVisited[min]=true;
            // j indexli data ziyaret edilmemişse, min/j arasında bir kenar varsa
            // ve min üzerinden j'ye olan mesafe daha kısa ise distance[j] yi güncelle
            //distance[j], her tur distance[min] le tanımlanır
            for(int j=0;j<distance.length;j++){
                if(!isVisited[j] && countryMap.getAdjacency()[min][j]!=0 && distance[min]!=Integer.MAX_VALUE && distance[min] + countryMap.getAdjacency()[min][j]<distance[j]){
                    distance[j] = distance[min] + countryMap.getAdjacency()[min][j];
                }
            }
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
    }*/
    public String findFastestRoute(City start, City end) {
        int n = countryMap.getNumberOfCity(); // Şehir sayısı
        int startIndex = countryMap.getCityIndex(start); // Başlangıç şehri indeksi
        int endIndex = countryMap.getCityIndex(end);     // Bitiş şehri indeksi

        // 1. En kısa yol için diziler
        int[] dist = new int[n];          // Mesafeleri saklar
        boolean[] visited = new boolean[n]; // Ziyaret edilen şehirler
        int[] previous = new int[n];      // Bir önceki şehirleri tutar

        // 2. Dizileri başlat
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE; // İlk başta tüm mesafeler sonsuz kabul edilir
            visited[i] = false;          // Hiçbir şehir ziyaret edilmedi
            previous[i] = -1;            // Yol henüz oluşmadı
        }
        dist[startIndex] = 0; // Başlangıç şehri mesafesi 0 olarak atanır

        // 3. Dijkstra Algoritması
        for (int i = 0; i < n; i++) {
            // Ziyaret edilmeyen şehirlerden en küçük mesafeye sahip olanı bul
            int u = findMinDistance(dist, visited);
            if (u == -1) break; // Eğer erişilemeyen bir şehir varsa çık

            visited[u] = true; // Şehri ziyaret ettik

            // Komşuları güncelle
            for (int v = 0; v < n; v++) {
                int time = countryMap.getAdjacency()[u][v]; // Şehirler arasındaki zaman
                if (!visited[v] && time != Integer.MAX_VALUE && dist[u] + time < dist[v]) {
                    dist[v] = dist[u] + time;
                    previous[v] = u; // Bir önceki şehir olarak 'u' atanır
                }
            }
        }

        // 4. Rotayı oluştur (Eğer yol yoksa boş dizi döner)
        if (dist[endIndex] == Integer.MAX_VALUE) {
            return "Route does not exist..."; // Yol bulunamadı
        }
        
        int index=endIndex;
        String str="";
        str+=countryMap.getCityByIndex(index).getLabel();
        int totalTime = dist[endIndex];
        while(previous[index]!=startIndex){
            str=countryMap.getCityByIndex(previous[index]).getLabel() + " ->" +str;
            index=previous[index];
            
        }
        str=countryMap.getCityByIndex(startIndex).getLabel() + " ->" +str;
        str = "Fastest Way: " + str + "\nTotal Time: " + totalTime;
        return str;
    }
    //Fastest Way: A -> C -> E
    //Total Time: 50 min
    // Ziyaret edilmemiş şehirler arasında minimum mesafeyi bul
    public int findMinDistance(int[] dist, boolean[] visited) {
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minValue) {
                minValue = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Önceki diziyi kullanarak yolu yeniden oluştur
    /*public City[] reconstructPath(int[] previous, int start, int end) {
        int[] path = new int[previous.length]; // Yolu geçici olarak tutacak
        int count = 0; // Yol uzunluğu

        // Bitişten başlayarak başa doğru ilerle
        for (int at = end; at != -1; at = previous[at]) {
            path[count++] = at;
        }

        // Ters çevirerek yolu oluştur
        City[] result = new City[count];
        for (int i = 0; i < count; i++) {
            result[i] = countryMap.getCityByIndex(path[count - 1 - i]);
        }
        return result;
    }*/
}
