
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Lütfen bir dosya adı belirtin!");
            System.exit(1);
        }

        Scanner reader = null;
        Formatter format = null;
        int count = 0;
        int routeCount = 0;
        int numOfCity = 0;
        int numOfRoutes = 0;
        CountryMap map = null;
        City initialCity = null;
        City finalCity = null;
        String fileName = args[0];

        try {

            format = new Formatter("output.txt");
            boolean running = isValid(fileName, format);
            if (!running) {
                System.out.println("Invalid file, to see error check \"output.txt\"...");
                format.close();
                System.exit(0);

            }
            reader = new Scanner(Paths.get(fileName));
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
                } else if (count == 2) {
                    numOfRoutes = Integer.parseInt(datas[0]);
                } else if (count >= 3 && count < 3 + numOfRoutes) {
                    String city1 = datas[0];
                    String city2 = datas[1];
                    int time = Integer.parseInt(datas[2]);
                    map.addRoute(city1, city2, time);
                    routeCount++;
                } else if (count == 3 + numOfRoutes) {
                    initialCity = map.findCityByLabel(datas[0]);
                    finalCity = map.findCityByLabel(datas[1]);
                }

                count++;
            }

            WayFinder wf = new WayFinder(map);

            if (initialCity != null && finalCity != null) {
                System.out.println("File read is successful!");
                System.out.println("Continue on \"output.txt\" file");
                // System.out.println(format.format("%s", wf.findFastestRoute(initialCity,
                // finalCity)));
                format.format("%s", wf.findFastestRoute(initialCity, finalCity));
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }

            if (routeCount != numOfRoutes) {
                format.format("%s", "\"This file cannot read because number of route values are different...\"");
                System.out.println("---FileReadError---\n you can see the error on \"output.txt\"...");
                format.close();
                System.exit(0);
            }
            if (format != null) {
                format.close();
            }
        }
    }

    public static boolean isThere(String[] array, String label) {
        for (String item : array) {
            if (item.equals(label)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValid(String fileName, Formatter format) {
        int count = 0;
        int cityNum = -1;
        int routeNum = -1;
        int rotaCount = 0;
        String[] labels = null;
        Scanner reader = null;
        try {
            reader = new Scanner(Paths.get(fileName));
            format = new Formatter("output.txt");
            while (reader.hasNextLine()) {
                String[] datas = reader.nextLine().split(" ");
                if (count == 0) {
                    cityNum = Integer.parseInt(datas[0]);
                } else if (count == 1) {

                    if (datas.length != cityNum) {
                        format.format("%s", "\"This file cannot read because number of city values are different...\"");
                        format.close();
                        return false;
                    }
                    labels = Arrays.copyOf(datas, datas.length);
                } else if (count == 2) {
                    routeNum = Integer.parseInt(datas[0]);
                }

                else if (count > 2 && datas.length == 3 && labels != null) {
                    if (!(isThere(labels, datas[0]) && isThere(labels, datas[1]) && datas[2].matches("\\d+"))) {
                        format.format("%s", "\"This file cannot read because there are undefined labels...\"");
                        format.close();
                        return false;
                    }
                    rotaCount++;

                }
                count++;
            }
            if (rotaCount != routeNum) {
                format.format("%s", "\"This file cannot read because number of route values are different...\"");
                format.close();
                return false;
            }

        } catch (IOException e) {

            format.format("Error while reading the file: %s", e.getMessage());
            return false;
        } finally {
            if (format != null) {
                format.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return true;
    }
}
