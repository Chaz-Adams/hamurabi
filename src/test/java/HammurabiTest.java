import org.junit.Assert;
import org.junit.Test;

public class HammurabiTest {

//    @Test
//    public void testConstructor(){
//        //Given
//        Integer givenPeople = 100;
//        Integer givenGrain = 2800;
//        Integer givenLand = 1000;
//        Integer givenLandValue = 19;
//
//        Hammurabi hi = new Hammurabi();
//
//        Assert.assertEquals(givenPeople,hi.getPeople());
//        Assert.assertEquals(givenGrain,hi.getGrain());
//        Assert.assertEquals(givenLand,hi.getLand());
//        Assert.assertEquals(givenLandValue,hi.getLandValue());
//    }
/*
    // We can utilize System.in to create a custom InputStream and simulate user input during testing.
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    // Example test case utilizing inputStream
    @Test
    public void givenName_whenReadFromInput_thenReturnCorrectResult() {
        provideInput("Baeldung");
        String input = Application.readName();
        assertEquals(NAME.concat("Baeldung"), input);
    }

    @Test
    public void testaskHowManyAcresToBuy(){
        Integer givenBushels = 2000;
        Integer givenPrice = 19;
        Hammurabi hi = new Hammurabi();

        Integer expected = givenBushels / givenPrice;
        Integer actual = hi.askHowManyAcresToBuy(givenPrice, givenBushels);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testaskHowManyAcresToSell(){
        Integer acresOwned = 100;
        Integer acresSell = 50;
        Hammurabi hi = new Hammurabi();
        Integer expected = acresOwned - acresSell;
        Integer actual = hi.askHowManyAcresToSell(acresOwned);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testaskHowMuchGrainToFeedPeople(){
        Integer expectedGrain = 100;
        Hammurabi hi = new Hammurabi();
        Integer actualGrain = hi.askHowMuchGrainToFeedPeople(expectedGrain);

        Assert.assertEquals(expectedGrain, actualGrain);
    }

    @Test
    public void testaskHowManyAcresToPlant(){
        Integer acresToPlant = 500;
        Hammurabi hi = new Hammurabi();

        Integer expected = acresToPlant;
        Integer actual = hi.askHowManyAcresToPlant(acresToPlant);
        Assert.assertEquals(expected, actual);
    }
*/

    Hammurabi hammurabi = new Hammurabi();

    @Test
    public void testplagueDeaths(){
            int population = 100;
            boolean actual = hammurabi.plagueDeaths(population) <= 50;
            Assert.assertTrue(actual);
    }

    @Test
    public void teststarvationDeaths(){
            int population = 100;
            int grainNeeded = population * 20;
            int bushelsFedToPeople = 2000;
            double starved = (((double)grainNeeded - bushelsFedToPeople)
                    / grainNeeded) * 100;
            int expected = (int) starved;
            int actual = hammurabi.starvationDeaths(population,bushelsFedToPeople);
            Assert.assertEquals(expected,actual);
    }

    @Test
    public void testuprising(){
        int population = 100;
        int howManyPeopleStarved = 100;
        boolean actual = hammurabi.uprising(population,howManyPeopleStarved);
        Assert.assertTrue(actual);
    }

    @Test
    public void testimmigrants(){

    }

    @Test
    public void testharvest(){

    }

    @Test
    public void testgrainEatenByRats(){

    }

    @Test
    public void testnewCostOfLand(){

    }


//
//    Hammurabi ham;
//
//    boolean about(double expected, double actual) {
//        return actual > 0.90 * expected && actual < 1.10 * expected;
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        ham = new Hammurabi();
//    }
//
//    @Test
//    public final void testPlagueDeaths1() {
//        int number_of_plagues = 0;
//        for (int i = 0; i < 10000; i++) {
//            int deaths = ham.plagueDeaths(100);
//            if (deaths > 0) {
//                number_of_plagues += 1;
//            }
//        }
//        int percentPlagues = number_of_plagues / 100;
//        assertTrue("Number of plagues is about " + percentPlagues + ", not about 15%.",
//                   about(1500, number_of_plagues));
//    }
//
//    @Test
//    public final void testPlagueDeaths2() {
//        int deaths = 0;
//        for (int i = 0; i < 10000; i++) {
//            deaths = ham.plagueDeaths(100);
//            if (deaths > 0) break;
//        }
//        assertEquals("In a plague, " + deaths + "% of your people die, not 50%.",
//                     50, deaths);
//    }
//
//    @Test
//    public final void testStarvationDeaths() {
//        int deaths = ham.starvationDeaths(100, 1639);
//        assertEquals("Wrong number of starvations deaths.", 19, deaths);
//        deaths = ham.starvationDeaths(100, 2500);
//        if (deaths < 0) {
//            fail("You starved a negative number of people!");
//        }
//    }
//
//    @Test
//    public final void testUprising() {
//        assertTrue("Should have had an uprising!", ham.uprising(1000, 451));
//        assertFalse("Should not have had an uprising!", ham.uprising(1000, 449));
//    }
//
//    @Test
//    public final void testImmigrants() {
//        int imm = ham.immigrants(10, 1200, 500);
//        assertEquals("Wrong number of immigrants.", 25, imm);
//    }
//
//    @Test
//    public final void testHarvest() {
//        int[] yield = new int[7];
//        for (int i = 0; i < 1000; i++) {
//            int harvest = ham.harvest(1);
//            assertTrue("Illegal harvest per acre: " + harvest, harvest > 0 && harvest <= 6);
//            yield[harvest] += 1;
//        }
//        for (int j = 1; j <= 6; j++) {
//            assertTrue("You never have a yield of " + j + " bushels per acre.", yield[j] > 0);
//        }
//    }
//
//    @Test
//    public final void testGrainEatenByRats1() {
//        int infestations = 0;
//        for (int i = 0; i < 1000; i++) {
//            int eaten = ham.grainEatenByRats(100);
//            if (eaten > 0) {
//                infestations += 1;
//            }
//        }
//        int percentInfestations = infestations / 100;
//        assertTrue("Number of rat infestations is about " + percentInfestations +
//                   ", not about 40%.", about(400, infestations));
//    }
//
//    @Test
//    public final void testGrainEatenByRats2() {
//        int percent = 0;
//        int[] counts = new int[31];
//        for (int i = 0; i < 10000; i++) {
//            percent = ham.grainEatenByRats(100);
//            if (percent == 0) continue;
//            counts[percent] += 1;
//            assertTrue("Rats ate " + percent + "% of your grain, not 10% to 30%.",
//                       percent >= 10 && percent <= 30);
//        }
//        for (int j = 11; j < 30; j++) {
//            assertTrue("Rats never ate " + j + "% of your grain.", counts[j] > 0);
//        }
//    }
//
//    @Test
//    public final void testNewCostOfLand() {
//        int[] cost = new int[24];
//        for (int i = 0; i < 1000; i++) {
//            int price = ham.newCostOfLand();
//            assertTrue("Illegal cost of land: " + price, price >= 17 && price <= 23);
//            cost[price] += 1;
//        }
//        for (int j = 17; j <= 23; j++) {
//            assertTrue("You never have a land cost of " + j + " bushels per acre.", cost[j] > 0);
//        }
//    }
}

