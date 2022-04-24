package skiHill;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NicolasVanderWerf & HaydenBlackmer
 */
public class fileData {
    @Getter @Setter private static String mapLocation;
    @Getter @Setter private static String liftNameLocation;
    @Getter @Setter private static String graphLocation;
    @Getter @Setter private static String vertexPointsLocation;
    @Getter @Setter private static Double[] mapSize; // { X , Y }
}
