
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


    public String findFastestRoute(City start, City end) {
        int length = countryMap.getLength(); // Şehir sayısı
        int startIndex = countryMap.getCityIndex(start); // Başlangıç şehri indeksi
        int endIndex = countryMap.getCityIndex(end); // Bitiş şehri indeksi

        int[] distance = new int[length]; // Mesafeleri saklar
        boolean[] visited = new boolean[length]; // Ziyaret edilen şehirler
        int[] previous = new int[length]; // Bir önceki şehirleri tutar

        // initialize arrays
        for (int i = 0; i < length; i++) {
            distance[i] = Integer.MAX_VALUE; // Default mesafeleri sonsuz tanımla
            visited[i] = false; // default ziyaretler false
            previous[i] = -1; // oluşmadığı için -1
        }
        distance[startIndex] = 0; // Başlangıç şehri mesafesi 0 olarak atanır
        // total distance bulur
        // Dijkstra --ziyaret edilmeyen en yakın şehri bul ve distance arrayini güncelle
        for (int i = 0; i < length; i++) {
            // Ziyaret edilmeyen şehirlerden en küçük mesafeye sahip olanı bul
            int minIdx = findMinDistance(distance, visited);
            if (minIdx == -1)
                break; // Eğer erişilemeyen bir şehir varsa çık

            visited[minIdx] = true; // Şehir ziyaret edildi

            // Komşuları güncelle
            for (int j = 0; j < length; j++) {
                int time = countryMap.getAdjacency()[minIdx][j]; // Şehirler arasındaki zaman
                if (!visited[j] && time != Integer.MAX_VALUE && distance[minIdx] + time < distance[j]) {
                    distance[j] = distance[minIdx] + time;
                    previous[j] = minIdx; // Bir önceki şehir indexini j olarak ata
                }
            }
        }

        // (if) yol yoksa feedback verir
        if (distance[endIndex] == Integer.MAX_VALUE) {
            return "Route does not exist..."; // Yol bulunamadı
        }
        //total yolu boyunca geçtiği şehirleri previous[] ile bulur
        // oluşan rotanın sonundan başına doğru previous arrayi ile start'a eşit olana
        // kadar dön
        // return string'ine ekle
        int index = endIndex;
        String str = "";
        str += countryMap.getCityByIndex(index).getLabel();
        int totalTime = distance[endIndex];
        while (previous[index] != startIndex) {
            str = countryMap.getCityByIndex(previous[index]).getLabel() + " ->" + str;
            index = previous[index];

        }
        str = countryMap.getCityByIndex(startIndex).getLabel() + " ->" + str;
        str = "Fastest Way: " + str + "\nTotal Time: " + totalTime;
        return str;
    }

    // Ziyaret edilmemiş şehirler arasında minimum mesafeyi bulan fonksiyon
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
}
