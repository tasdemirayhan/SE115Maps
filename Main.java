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
        /*
         * if (args.length < 1) {
         * System.out.println("Lütfen bir dosya adı belirtin!");
         * System.exit(1);
         * }
         */

        Scanner reader = null;
        int count = 0;
        int numOfCity = 0;
        int numOfRoutes = 0;
        CountryMap map = null;
        City initialCity = null;
        City finalCity = null;
        // String fileName =args[0];

        try {
            reader = new Scanner(Paths.get("map1.txt"));
            while (reader.hasNextLine()) {
                String[] datas = reader.nextLine().split(" ");

                if (count == 0) {
                    numOfCity = Integer.parseInt(datas[0]);
                    map = new CountryMap(numOfCity);
                }

                else if (count == 1 && map != null) {
                    if (datas.length == numOfCity) {
                        for (int i = 0; i < datas.length; i++) {
                            map.addCity(new City(datas[i]));
                        }
                    }
                    else{
                        System.out.println("This file cannot read because number of city values are different...");
                        System.exit(0);
                    }
                } else if (count == 2) {
                    numOfRoutes = Integer.parseInt(datas[0]);
                } else if (count >= 3 && count < 3 + numOfRoutes) {
                    String city1 = datas[0];
                    String city2 = datas[1];
                    int time = Integer.parseInt(datas[2]);
                    map.addRoute(city1, city2, time);
                } else if (count == 3 + numOfRoutes) {
                    initialCity = map.findCityByLabel(datas[0]);
                    finalCity = map.findCityByLabel(datas[1]);
                }

                count++;
            }

            WayFinder wf = new WayFinder(map);
            if (initialCity != null && finalCity != null) {
                System.out.println(wf.findFastestRoute(initialCity, finalCity));

            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
