package skiHill;

/**
 *Data Class containing all file locations needed for other classes.
 *Program is written dynamically so other ski resorts can be added here.
 *
 * @author NicolasVanderWerf & HaydenBlackmer
 */
public class fileData {
    
	private static final String mapLocation = "src\\ParkCityMountain\\ParkCityMap.jpeg";
	private static final String liftNameLocation = "src\\ParkCityMountain\\LiftNames.txt";
	private static final String graphLocation = "src\\ParkCityMountain\\ParkCityMountainGraph";
	private static final String vertexPointsLocation = "src\\ParkCityMountain\\POILocations.txt";
	private static final Double[] mapSize = new Double[] { 7193.0, 3861.0 }; // { X , Y }

	/**
	 * @return the mapLocation
	 */
	public static String getMapLocation() {
		return mapLocation;
	}

	/**
	 * @return the liftNameLocation
	 */
	public static String getLiftNameLocation() {
		return liftNameLocation;
	}

	/**
	 * @return the graphLocation
	 */
	public static String getGraphLocation() {
		return graphLocation;
	}

	/**
	 * @return the vertexPointsLocation
	 */
	public static String getVertexPointsLocation() {
		return vertexPointsLocation;
	}

	/**
	 * @return the mapSize
	 */
	public static Double[] getMapSize() {
		return mapSize;
	}
}
