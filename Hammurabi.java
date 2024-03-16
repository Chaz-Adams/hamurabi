package hammurabi;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    private Integer population = 100,
                    bushelsOfGrain = 2800,
                    acresOfLand = 1000,
                    landValueBushelsPerAcre = 19,
                    currentYear = 1;
//                    totalDeaths = 0,
//                    percentDied = 0,
//                    immigrants = 0,
//                    currentYearDeaths = 0,
//                    harvest = 0,
//                    yeild = 0,
//                    stores,
//                    acres = harvest / yeild,
//                    eaten = harvest - stores,
//                    landPrice,
//                    fullPeople,
//                    temp;




    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    public Hammurabi() {
    }// Default Constructor

    void playGame() {
        boolean exitValue = true;
        startingMessage();
        System.out.println();
        do {
            acresOfLand += askHowManyAcresToBuy(landValueBushelsPerAcre, bushelsOfGrain);
            acresOfLand -= askHowManyAcresToSell(acresOfLand);
            bushelsOfGrain -= askHowMuchGrainToFeedPeople(bushelsOfGrain);

            /*          Not sure what to do with this yet            */
            askHowManyAcresToPlant(acresOfLand, population, bushelsOfGrain);


            yearlySummary(currentYear, population, acresOfLand, landValueBushelsPerAcre);
            if(currentYear <= 10){
                currentYear++;
            }else{
                System.out.println("\n\n10 years passed. Game over!   \n  umm.. congratulations! \n\n\t\t¯\\_(ツ)_/¯ ");
                exitValue = false;
            }
        } while (exitValue);
    }


    /*   **************************************************************************  */


    public Integer askHowManyAcresToBuy(int landValueBushelsPerAcre, int grain) {
        boolean exitValue = true;
        int userWantsToBuy = 0;
        do {
            System.out.println("\nHow many acres do you want to buy?");
            try{
                userWantsToBuy = scanner.nextInt();
                if((grain / landValueBushelsPerAcre) > userWantsToBuy){
                    return userWantsToBuy;
                }else {
                    System.out.println("You must have enough grain to pay for your purchase.");
                }
            }catch(InputMismatchException e){
                System.out.println("Hmm! That won't work, try again!");
                scanner.nextLine();
                exitValue = false;
            }
        } while (!exitValue);
        System.out.println("Your mistake...purchasing zero!\n");
        return userWantsToBuy;
    }
    // Asks the player how many acres of land to buy, and returns that number. You must
    // have enough grain to pay for your purchase.

    public Integer askHowManyAcresToSell(int acresOwned) {
        boolean exitValue = true;
        int userWantsToSell = 0;
        do {
            System.out.println("How many acres of land do you want to sell?");
            try{
                userWantsToSell = scanner.nextInt();
                if(acresOwned > userWantsToSell){
                    return userWantsToSell;
                }else {
                    System.out.println("You can't sell more than you have.");
                }
            }catch(InputMismatchException e){
                System.out.println("Hmm! That won't work, try again!");
                scanner.nextLine();
                exitValue = false;
            }
        } while (!exitValue);
        return null;
    }
    // Asks the player how many acres of land to sell, and returns that number.
    // You can't sell more than you have. Do not ask this question if the player is
    // buying land; it doesn't make sense to do both in one turn.

    public Integer askHowMuchGrainToFeedPeople(int bushels) {
        boolean exitValue = true;
        int userGrainToFeedPeople = 0;
        do {
            System.out.println("How much bushels of grain do you want to use to feed people?");
            try{
                userGrainToFeedPeople = scanner.nextInt();
                if(bushels > userGrainToFeedPeople){
                    return userGrainToFeedPeople;
                }else {
                    System.out.println("You can't feed people more than you have.");
                }
            }catch(InputMismatchException e){
                System.out.println("Hmm! That won't work, try again!");
                scanner.nextLine();
                exitValue = false;
            }
        } while (!exitValue);
        return null;
    }
    // Ask the player how much grain to feed people, and returns that number. You can't
    // feed them more grain than you have. You can feed them more than they need to survive.

    public Integer askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        boolean exitValue = true;
        int userAcresToPlant = 0;
        do {
            System.out.println("How many acres do you want to plant?");
            try{
                userAcresToPlant = scanner.nextInt();
                if(acresOwned > userAcresToPlant){
                    if(population / 20 > bushelsOfGrain){
                        if(population / 10 > userAcresToPlant){
                            if (bushels / 2 > userAcresToPlant){
                                return userAcresToPlant;
                            }else{
                                System.out.println("Not enough bushels to farm.");
                            }
                        }else{
                            System.out.println("To much to farm.");
                        }
                    }else {
                        System.out.println("You don't have enough people");
                    }
                }else{
                    System.out.println("You don't own that much!");
                }
            }catch(InputMismatchException e){
                System.out.println("Hmm! That won't work, try again!");
                scanner.nextLine();
                exitValue = false;
            }
        } while (!exitValue);
        return userAcresToPlant;
    }
    // Ask the player how many acres to plant with grain, and returns that number. You must
    // have enough acres, enough grain, and enough people to do the planting. Any grain left
    // over goes into storage for next year.

    public void startingMessage(){
        System.out.println("\nCongratulations, you are the newest ruler of ancient Sumer, elected " +
                "for a ten year term of office. \n" +
                "Your duties are to dispense food, direct farming, \n" +
                "and buy and sell land as needed to support your people. Watch out for rat infestiations \n" +
                "and the plague! Grain is the general currency, measured in bushels. The following \n" +
                "will help you in your decisions:\n" +
                "\n" +
                "Each person needs at least 20 bushels of grain per year to survive\n" +
                "Each person can farm at most 10 acres of land\n" +
                "It takes 2 bushels of grain to farm an acre of land\n" +
                "The market price for land fluctuates yearly\n" +
                "Rule wisely and you will be showered with appreciation at the end of your term. Rule " +
                "poorly and you will be kicked out of office!\n\n");
    }

    public void yearlySummary(int currentYear,int population,int acresOfLand,
                              int landValueBushelsPerAcre){
        System.out.printf("\n0 great Hammurabi!\n" +
                "You are in year %s of your ten year rule.\n" +
                "In the previous year ??? people starved to death.\n" +
                "In the previous year ??? people entered the kingdom.\n" +
                "The population is now %s.\n" +
                "We harvested 3000 bushels at ??? bushels per acre.\n" +
                "The city owns %s acres of land.\n" +
                "Land is currently worth %s bushels per acre.",currentYear,population,acresOfLand,landValueBushelsPerAcre);
    }


    /*   **************************************************************************  */
    /*     **********************Figure Out These Later**************************  */

    public int plagueDeaths(int population){
              return 0;
    }
    // Each year, there is a 15% chance of a horrible plague. When this happens, half
    // your people die. Return the number of plague deaths (possibly zero).

    public int starvationDeaths(int population, int bushelsFedToPeople){
        return 0;
    }
    // Each person needs 20 bushels of grain to survive. If you feed them more than this,
    // they are happy, but the grain is still gone. You don't get any benefit from having
    // happy subjects. Return the number of deaths from starvation (possibly zero).

    public boolean uprising(int population, int howManyPeopleStarved){
        return false;
    }
    // Return true if more than 45% of the people starve. (This will cause you to be immediately
    // thrown out of office, ending the game.)

    public int immigrants(int population, int acresOwned, int grainInStorage){
        return 0;
    }
    // Nobody will come to the city if people are starving (so don't call this method).
    // If everyone is well-fed, compute how many people come to the city as: (20 * _number
    // of acres you have_ + _amount of grain you have in storage_) / (100 * _population_) + 1.

    public int harvest(int acres, int bushelsUsedAsSeed){
        return 0;
    }
    // Choose a random integer between 1 and 6, inclusive. Each acre that was planted with
    // seed will yield this many bushels of grain. (Example: if you planted 50 acres, and
    // your number is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.

    public int grainEatenByRats(int bushels){
        return 0;
    }
    // There is a 40% chance that you will have a rat infestation. When this happens, rats will
    // eat somewhere between 10% and 30% of your grain. Return the amount of grain eaten by rats
    // (possibly zero).

    public int newCostOfLand(){
        return 0;
    }
    //The price of land is random, and ranges from 17 to 23 bushels per acre. Return the new price
    // for the next set of decisions the player has to make. (The player will need this
    // information in order to buy or sell land.)

}