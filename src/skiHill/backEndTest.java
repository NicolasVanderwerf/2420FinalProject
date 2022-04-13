package skiHill;

public class backEndTest {
    private static int start;
    private static int end;
    private static String[] pointsOfInterest = {"Crescent","Payday","Kiing Con"};

    public static void testOutPut(int i){
        start = i;
        System.out.println("Back End Recieved: " + start);
        System.out.println("Lift: " + pointsOfInterest[start]);

    }
    
}
