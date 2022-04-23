package skiHill;

import lombok.Getter;

public class fileData {
    @Getter private static String mapLocation = "src\\ParkCityMountain\\ParkCityMap.jpeg";
    @Getter private static String liftNameLocation = "src\\ParkCityMountain\\LiftNames.txt";
    @Getter private static String graphLocation = "src\\ParkCityMountain\\ParkCityMountainGraph";
    @Getter private static String vertexPointsLocation = "src\\ParkCityMountain\\POILocations.txt";
    @Getter private static Double[] mapSize = {7193.0,3861.0}; // { X , Y }
}
