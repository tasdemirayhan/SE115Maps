import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            //System.out.println("Enter the name of your file with \".txt\" extenion...");
            //String userFile = sc.nextLine();
            List<String> datas = Files.readAllLines(Paths.get("map1.txt"));
            //şehir sayısını okur ve haritayı oluşturur.
            String[] data1 = datas.get(0).split(" ");
            int numOfCity = Integer.parseInt(data1[0]);
            if(numOfCity<0 || data1.length>1){
                errorMessage();
            }
            CountryMap map = new CountryMap(numOfCity);
            
            //şehirleri oluşturur ve şehre ekler.
            String[] labels = datas.get(1).split(" ");
            if(numOfCity!=labels.length){
                errorMessage();
            }
            for(int i=0;i<labels.length;i++){
                map.addCity(new City(labels[i]));
            }
            //rota sayısını okur.
            String[] rotaSayısı = datas.get(2).split(" ");
            int numOfRoutes =Integer.parseInt(rotaSayısı[0]);
            if(numOfRoutes<0 || rotaSayısı.length>1){
                errorMessage();
            }
            //rotaları okur ve haritaya rota ekler
            for(int i=0;i<numOfRoutes;i++){
                String[] route = datas.get(i+3).split(" ");
                String city1 = route[0];
                String city2 = route[1];
                int time = Integer.parseInt(route[2]);
                map.addRoute(city1, city2, time);
            }
            
            
            String[] lastData =datas.get(datas.size()-1).split(" ");
            City initialCity = map.findCityByLabel(lastData[0]);
            City finalCity = map.findCityByLabel(lastData[1]);
           
            //map.printAdjacency();
            int[] s = map.createShortestPath(3);
            System.out.println(Arrays.toString(s));
            
            
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
   public static void errorMessage(){
        System.out.println("ERROR. THERE IS A PROBLEM ON FILE. CANNOT READ...");
        System.exit(0);
   }
}
