import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    private Integer population = 100, bushelsOfGrainInStorage = 2800, acresOfLandOwned = 1000, costInBushelsPerAcre = 19,
                    currentYear = 1, yeild = 3, diedFromPlague = 0, diedFromStarvation = 0, newPeople = 0,
                    newBushels = 0, newCost = 0, bushelsEatenByRats = 0, grainUsed = 0;

    private Double plagueProbability = 0.15, deathRate = 0.5, ratInfestationProbability = .40,
                    ratEatRate = 0.10 + rand.nextDouble() * 0.20;

                /*   **************************************************************************  */

    private Boolean boughtAcres = false;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    public Hammurabi() {
    }// Default Constructor

    void playGame() {
        boolean exitValue = true;
        startingMessage();
        originalState();
        System.out.println();
        do {
            acresOfLandOwned += askHowManyAcresToBuy(costInBushelsPerAcre, bushelsOfGrainInStorage);
            if(!boughtAcres){
                acresOfLandOwned -= askHowManyAcresToSell(acresOfLandOwned);
            }
            diedFromStarvation = starvationDeaths(population, askHowMuchGrainToFeedPeople(bushelsOfGrainInStorage));
            newBushels = harvest(askHowManyAcresToPlant(acresOfLandOwned, population, bushelsOfGrainInStorage)) ;
            diedFromPlague = plagueDeaths(population);
            if(uprising(population,diedFromStarvation) == true){
                System.out.println("\n\n\tTo many people starved!\n\n\tAN UPRISING HAS BEGUN!!\n");
                gameOver();
                System.exit(0);
            }
            newPeople = immigrants(population,acresOfLandOwned, bushelsOfGrainInStorage);
            bushelsEatenByRats = grainEatenByRats(population);
            if(allDied(population,diedFromPlague,diedFromStarvation)){
                System.exit(0);
            }
            updateStatus(population, diedFromPlague, diedFromStarvation, newBushels, newPeople, bushelsEatenByRats);

            yearlySummary(acresOfLandOwned);
            if(currentYear < 10){
                currentYear++;
            }else{
                System.out.println("\n\n10 years passed. Game over!   \n  umm.. congratulations! \n\n\t\t¯\\_(ツ)_/¯ ");
                exitValue = gameOver();
            }
        } while (exitValue);
    }

                /*   **************************************************************************  */

    public void yearlySummary(int acresOfLand){
        System.out.printf("\nO great Hammurabi!\n" +
                "You are in year %s of your ten year rule.\n" +
                "In the previous year %s people starved to death.\n" +
                "In the previous year %s people entered the kingdom.\n" +
                "The population is now %s.\n" +
                "We harvested %s bushels at %s bushels per acre.\n" +
                "You currently have %s bushels in storage\n" +
                "The city owns %s acres of land.\n" +
                "Land is currently worth %s bushels per acre.",currentYear,diedFromStarvation,newPeople,
                population, newBushels, yeild, bushelsOfGrainInStorage,
                acresOfLand,costInBushelsPerAcre);
    }

    public void updateStatus(int population, int diedFromPlague, int diedFromStarvation,
                             int newBushels, int newPeople, int bushelsEatenByRats){
        this.population = (population - (diedFromPlague + diedFromStarvation)) + newPeople;
        bushelsOfGrainInStorage -= bushelsEatenByRats + grainUsed;
        bushelsOfGrainInStorage += newBushels;
        newCost = newCostOfLand();
        costInBushelsPerAcre = newCost;

    }

    public boolean allDied(int population,int diedFromPlague,int diedFromStarvation){
        if(diedFromPlague + diedFromStarvation > population){
            gameOver();
            return true;
        }
            return false;
    }

    public void originalState(){
        System.out.println("O great Hammurabi!\n" +
                "You are in year 1 of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested 3000 bushels at 3 bushels per acre.\n" +
                "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n" +
                "The city owns 1000 acres of land.\n" +
                "Land is currently worth 19 bushels per acre.");
    }

    public boolean gameOver(){
        System.out.println("So life didn't work out for you!\n \t\tGAMEOVER PLAYA!");
        return true;
    }

    public Integer askHowManyAcresToBuy(int landValueBushelsPerAcre, int grain) {
        this.boughtAcres = false;
        boolean exitValue = true;
        int userWantsToBuy = 0;
        do {
            System.out.println("\nHow many acres do you want to buy?");
            try{
                userWantsToBuy = scanner.nextInt();
                if(userWantsToBuy <= 0){
                    break;
                }
                else if((grain / landValueBushelsPerAcre) > userWantsToBuy){
                    this.boughtAcres = true;
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
        if(!boughtAcres) {
            do {
                System.out.println("How many acres of land do you want to sell?");
                try {
                    userWantsToSell = scanner.nextInt();
                    if (acresOwned > userWantsToSell) {
                        return userWantsToSell;
                    } else {
                        System.out.println("You can't sell more than you have.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Hmm! That won't work, try again!");
                    scanner.nextLine();
                    exitValue = false;
                }
            } while (!exitValue);
        }
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
                    grainUsed = userGrainToFeedPeople;
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
                    if(this.population * 20 < bushelsOfGrainInStorage){
                        if(population * 10 > userAcresToPlant){
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

    public int plagueDeaths(int population){
        int plagueDeaths = 0;

        if(rand.nextDouble() <= plagueProbability){
            plagueDeaths = (int)(population * deathRate);
        }
              return plagueDeaths;
    }
    // Each year, there is a 15% chance of a horrible plague. When this happens, half
    // your people die. Return the number of plague deaths (possibly zero).

    public int starvationDeaths(int population, int bushelsFedToPeople){
        int howMuchEachPersonEats = 20;
        int grainNeeded = population * howMuchEachPersonEats;
        double starved = (((double)grainNeeded - bushelsFedToPeople) / grainNeeded) * 100;
        return (int)starved;
    }
    // Each person needs 20 bushels of grain to survive. If you feed them more than this,
    // they are happy, but the grain is still gone. You don't get any benefit from having
    // happy subjects. Return the number of deaths from starvation (possibly zero).

    public boolean uprising(int population, int howManyPeopleStarved){
        Double uprisingThreshold = 0.45;
        Double currentStatus = (double)howManyPeopleStarved / population;
        return currentStatus >= uprisingThreshold;
    }
    // Return true if more than 45% of the people starve. (This will cause you to be immediately
    // thrown out of office, ending the game.)

    public int immigrants(int population, int acresOwned, int grainInStorage){
        int howManyImmigrantsAreJoining = (20 * acresOwned + grainInStorage) / (100 * population) +1;
        return howManyImmigrantsAreJoining;
    }
    // Nobody will come to the city if people are starving (so don't call this method).
    // If everyone is well-fed, compute how many people come to the city as: (20 * _number
    // of acres you have_ + _amount of grain you have in storage_) / (100 * _population_) + 1.

    public int harvest(int acres){
        int yeild = rand.nextInt(6)+1;
        this.yeild = yeild;
        int bushelsHarvested = acres * yeild;
        return bushelsHarvested;
    }
    // Choose a random integer between 1 and 6, inclusive. Each acre that was planted with
    // seed will yield this many bushels of grain. (Example: if you planted 50 acres, and
    // your number is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.

    public int grainEatenByRats(int bushels){
        int grainEaten = 0;
        if(rand.nextDouble() <= ratInfestationProbability){
            grainEaten = (int)(bushels * ratEatRate);
        }
        return grainEaten;
    }
    // There is a 40% chance that you will have a rat infestation. When this happens, rats will
    // eat somewhere between 10% and 30% of your grain. Return the amount of grain eaten by rats
    // (possibly zero).

    public int newCostOfLand(){
        int newCost = rand.nextInt(7)+17;
        return newCost;
    }
    //The price of land is random, and ranges from 17 to 23 bushels per acre. Return the new price
    // for the next set of decisions the player has to make. (The player will need this
    // information in order to buy or sell land.)

}