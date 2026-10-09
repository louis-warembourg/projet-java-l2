package towerdefense.util;


import java.util.*;


import towerdefense.*;
import towerdefense.tower.*;
import towerdefense.projectile.*;

/** a utility class for a menu of choices */
public class Menu {
    
    /**the list representing the menu of choices */
    private ArrayList<String> listChoose;
    /**the list of available towers to buy */
    private ArrayList<String> towersList;
    /**the prompt instance of the menu */
    private Ask ask = new Ask();
    /**the number of available towers */
    private int nbTower;
    /**the number of available upgrades */
    private int nbUpgrade;
    /**the player who will make choices in the menu */
    private Player p;
    /**the list of dead balloons */
    private ArrayList<Balloon> deadBalloons;
    /**the list of tower managers */
    private ArrayList<ManageTower> towerManagers;
    
    /**
     * builds a new menu
     * @param ask the prompt specific to the menu
     * @param p the playerwho will make choices in the menu
     */
    public Menu(Ask ask, Player p){
        this.p = p;
        this.ask = ask;
        this.nbTower = 0;
        this.nbUpgrade = 0;
        this.listChoose = new ArrayList<String>();
        this.listChoose.add("Start Round");
        this.listChoose.add("View");
        this.listChoose.add("Buy a Tower");
        this.towersList = new ArrayList<String>();
        this.towersList.add("buy DartMonkeytower");
        this.towersList.add("buy BombMonkeytower");
        this.towersList.add("buy EliteMonkeytower");
        this.towersList.add("buy FreezeTower");
        this.towersList.add("buy SlowTower");
        this.towersList.add("buy GorillaTower");
        this.towersList.add("buy NeedleMonkeytower");
        this.towersList.add("Back");
    }

    /**
     * provides the list of dead balloons
     * @return the list of dead balloons
     */
    public ArrayList<Balloon> getDeadBalloons(){
        return this.deadBalloons;
    }

    /**
     * adds a choice option in the menu
     * @param option the option to be added
     */
    public void addOption(String option){
        this.listChoose.add(option);
    }

    /**
     * removes a choice from the menu
     * @param option the option to be removed
     */
    public void removeOptions(String option){
        this.listChoose.remove(option);
    }

    /**
     * modifies the menu according to added or removed options 
     */
    public void updateMenu(){
        if(this.nbTower > 0 && !this.listChoose.contains("Sell a Tower") && !this.listChoose.contains("Buy an Upgrade")){
            addOption("Sell a Tower");
            addOption("Buy an Upgrade");
        }
        if(this.nbUpgrade > 0 && !this.listChoose.contains("Sell an Upgrade")){
            addOption("Sell an Upgrade");
        }
        if(this.nbUpgrade == 0 && this.listChoose.contains("Sell an Upgrade")){
            removeOptions("Sell an Upgrade");
        }
        if(this.nbTower == 0 && this.listChoose.contains("Sell a Tower")){
            removeOptions("Buy an Upgrade");
            removeOptions("Sell an Upgrade");
            removeOptions("Sell a Tower");
        }
    }

    /**
     * creates a principal menu when starting a game
     * @param g the game for which the principal menu is created
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     */
    public void mainMenu(Game g, Board b, ListChooser<String> lc){
        while(true){
            System.out.println("=============================================" + "\n");
            this.updateMenu();
            String choice = lc.choose("Choice ? ", this.listChoose);
            switch(choice){
                case("Start Round"):
                    return;
                case("View"):
                    b.printBoard();
                    break;
                case("Buy a Tower"):
                    this.buyTowerMenu(g, b, lc);
                    break;
                case("Sell a Tower"):
                    this.sellTowerMenu(g, b, lc);
                    break;
                case("Buy an Upgrade"):
                    this.buyUpgradeAllTowerMenu(g, b, lc);
                    break;
                case("Sell an Upgrade"):
                    this.sellUpgradeAllTowerMenu(g, b, lc);
                    break;
            }
            System.out.println("\n");
            System.out.println("=============================================" + "\n");
        }
    }

    /**
     * creates a menu allowing to choose between different towers to buy
     * @param g the game for which the buying tower menu is created 
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     */
    public void buyTowerMenu(Game g, Board b, ListChooser<String> lc){

        while(true){
            System.out.println("=============================================" + "\n");
            System.out.println("you have left :" + this.p.getResources());
            String choice = lc.choose("Choice ? ", this.towersList);
            if(choice.equals("Back")){
                return;
            }
            b.printBoard();
            int x;
            int y;
            if(lc instanceof RandomListChooser){
                Random random = new Random();	
                x = random.nextInt(b.getX()-1);
                y = random.nextInt(b.getY()-1);
                while(b.getCell(x, y).hasTower()){
                    x = random.nextInt(b.getX()-1);
                    y = random.nextInt(b.getY()-1);
                }
                if(g.getGameMode() == 0){
                    while(b.getPath().contains(b.getCell(x, y))){
                        x = random.nextInt(b.getX()-1);
                        y = random.nextInt(b.getY()-1);
                    }                
                }
            }
            else{
            x = this.ask.askInteger("Enter the x-coordinate of the tower : ", 0, b.getX()-1);
            y = this.ask.askInteger("Enter the y-coordinate of the tower : ", 0, b.getY()-1);
            while(b.getCell(x, y).hasTower()){
                System.out.println("A tower is already placed at this position ! try again." + "\n");
                x = this.ask.askInteger("Enter the x-coordinate of the tower : ", 0, b.getX()-1);
                y = this.ask.askInteger("Enter the y-coordinate of the tower : ", 0, b.getY()-1);
            }
            if(g.getGameMode() == 0){
                while(b.getPath().contains(b.getCell(x, y))){
                    b.printBoard();
                    x = this.ask.askInteger("Coordonate x not valid, enter the x-coordinate of the tower : ", 0, b.getX()-1);
                    y = this.ask.askInteger("Coordonate y not valid, enter the y-coordinate of the tower : ", 0, b.getY()-1);
                }                
            }
            }
            try{
                switch (choice) {
                    case("buy DartMonkeytower"):
                        Tower t = g.setTower(b, new DartMonkeyTower(x, y, new Dart()), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("buy BombMonkeytower"):
                        t = g.setTower(b, new BombMonkeyTower(x, y, new Bomb()), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("buy EliteMonkeytower"):
                        t = g.setTower(b, new EliteMonkeyTower(x, y, new VeryPointyDart()), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("buy FreezeTower"):
                        t = g.setTower(b, new FreezeTower(x, y), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("buy SlowTower"):
                        t = g.setTower(b, new SlowTower(x, y), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("buy GorillaTower"):
                        t = g.setTower(b, new GorillaTower(x, y, new PointyDart()), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                        case("buy NeedleMonkeytower"):
                        t = g.setTower(b, new NeedleMonkeyTower(x, y, new Needle()), x, y, this.p);
                        g.addManageTower(new ManageTower(t, b));
                        break;
                    case("Back"):
                        return;
                    default:
                        continue;
                }
                this.nbTower += 1;
                System.out.println("Purchase confirmed !");
                System.out.println("you have left :" + this.p.getResources() + "\n");
                System.out.println("=============================================" + "\n");
            }
            catch(Exception e){
                System.out.println("You don't have enough resources !");
            }
        }
    }

    /**
     * creates a menu allowing to choose between different towers to sell
     * @param g the game for which the selling tower menu is created 
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     */
    public void sellTowerMenu(Game g, Board b, ListChooser<String> lc){

        while(true){
            System.out.println("=============================================" + "\n");
            System.out.println("you have left :" + this.p.getResources());
            ArrayList<String> alreadyPlacedTowers = new ArrayList<String>();
            for(Tower t : g.getTowers()){
                alreadyPlacedTowers.add("Sell a " + t.print());
            }
            alreadyPlacedTowers.add("Back");
            String choice = lc.choose("Choice ?", alreadyPlacedTowers);

            if(choice.equals("Back")){
                return;
            }
            for(Tower t : g.getTowers()){
                if(("Sell a " + t.print()).equals(choice)){
                    g.removeTower(b, t, t.getX(), t.getY(), this.p);
                    this.nbTower -= 1;
                    System.out.println("Confirm sale !");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    g.removeManageTower(t);
                    break;
                }
            }
            System.out.println("=============================================" + "\n");
        }
    }

    /**
     * creates a menu allowing to choose between different tower to buy an upgrade.
     * @param g the game for which the buying upgrade menu is created 
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     */
    public void buyUpgradeAllTowerMenu(Game g, Board b, ListChooser<String> lc){
        System.out.println("=============================================" + "\n");
        System.out.println("you have left :" + this.p.getResources());
        ArrayList<String> alreadyPlacedTowers = new ArrayList<String>();
        for(Tower t : g.getTowers()){
            if(t.getAvailableUpgrades() != null && !t.getAvailableUpgrades().isEmpty()){
                alreadyPlacedTowers.add("Upgrade " + t.print());
            }
        }
        alreadyPlacedTowers.add("Back");
        String choice = lc.choose("Choice ?", alreadyPlacedTowers);
        if(choice.equals("Back")){
                return;
        }
        try{
            for(Tower t : g.getTowers()){
                if(("Upgrade " + t.print()).equals(choice)){
                    buyUpgradeMenu(g, b, t, lc);
                    break;
                }
            }
            System.out.println("=============================================" + "\n");
        }
        catch(Exception e){
            System.out.println("You don't have enough resources !");
        }
        
    }

    /**
     * creates a menu allowing to choose between different tower to sell an upgrade.
     * @param g the game for which the selling upgrade menu is created 
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     */
    public void sellUpgradeAllTowerMenu(Game g, Board b, ListChooser<String> lc){
        System.out.println("=============================================" + "\n");
        System.out.println("you have left :" + this.p.getResources());
        ArrayList<String> alreadyUpgradedTowers = new ArrayList<String>();
        for(Tower t : g.getUpgradedTowers()){
            alreadyUpgradedTowers.add("Sell Upgrade from " + t.print());
        }
        alreadyUpgradedTowers.add("Back");
        String choice = lc.choose("Choice ?", alreadyUpgradedTowers);
        if(choice.equals("Back")){
                return;
        }
        for(Tower t : g.getUpgradedTowers()){
            if(("Sell Upgrade from " +t.print()).equals(choice)){
                sellUpgradeMenu(g, b, t, lc);
                break;
            }
        }
         System.out.println("=============================================" + "\n");
    }


    /**
     * creates a menu allowing to choose from a variety of additional upgrades.
     * @param g the game for which the selling upgrade menu is created 
     * @param b the board of the game
     * @param lc the listChooser, decide whether it is a random or interactive choice
     * @param t the tower for which the buying upgrade menu is created
     * @exception IllegalArgumentException if upgrade unavailable or insufficient fund     
     */    
    public void buyUpgradeMenu(Game g, Board b, Tower t, ListChooser<String> lc) throws IllegalArgumentException{
        ArrayList<String> menu = new ArrayList<String>();
        for(Upgrade upgr : t.getAvailableUpgrades()){
            switch(upgr.getUpgradeType()){
                case POWER:
                    menu.add("buy Power upgrade ( + " + upgr.getNbIncrease() + " damage )");
                    break;
                case RANGE:
                    menu.add("buy Range upgrade ( + " + upgr.getPercentage() + " % of range )");
                    break;
                case SPEED:
                    menu.add("buy Speed upgrade ( + " + upgr.getPercentage() + " % of speed )");
                    break;
                case MODIFY_PROJECTILE:
                    menu.add("buy Modify projectile upgrade ( ExtraBomb )");
                    break;
                default:
            }
        }
        menu.add("Back");
        String choice = lc.choose("Choice ?", menu);
        if(choice.equals("Back")){
                return;
        }
        try{
            for(Upgrade upgr : t.getAvailableUpgrades()){
                if(choice.equals("buy Power upgrade ( + " + upgr.getNbIncrease() + " damage )") && upgr.getUpgradeType() == UpgradeType.POWER){
                    t.upgrade(upgr, this.p);
                    this.nbUpgrade += 1;
                    System.out.println("Confirm sale !");
                    System.out.println(t.toString() + " has the upgrade : POWER\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
                if(choice.equals("buy Range upgrade ( + " + upgr.getPercentage() + " % of range )") && upgr.getUpgradeType() == UpgradeType.RANGE){
                    t.upgrade(upgr, this.p);
                    this.nbUpgrade += 1;
                    System.out.println("Confirm sale !");
                    System.out.println(t.toString() + " has the upgrade : RANGE\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
                if(choice.equals("buy Speed upgrade ( + " + upgr.getPercentage() + " % of speed )") && upgr.getUpgradeType() == UpgradeType.SPEED){
                    t.upgrade(upgr, this.p);
                    this.nbUpgrade += 1;
                    System.out.println("Confirm sale !");
                    System.out.println(t.toString() + " has the upgrade : SPEED\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
                if(choice.equals("buy Modify projectile upgrade ( ExtraBomb )") && upgr.getUpgradeType() == UpgradeType.MODIFY_PROJECTILE){
                    t.upgrade(upgr, this.p);
                    this.nbUpgrade += 1;
                    System.out.println("Confirm sale !");
                    System.out.println(t.toString() + " has the upgrade : MODIFY PROJECTILE\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException();
        }

    }

    /**
     * creates a menu allowing to choose remove various upgrade options.
     * @param g the game for which the selling upgrade menu is created 
     * @param b the board of the game
     * @param t the tower for which the selling upgrade menu is created
     * @param lc the listChooser, decide whether it is a random or interactive choice 
     */    
    public void sellUpgradeMenu(Game g, Board b, Tower t, ListChooser<String> lc){
        ArrayList<String> menu = new ArrayList<String>();
         for(Upgrade upgr : t.getCurrentUpgrades()){
            switch(upgr.getUpgradeType()){
                case POWER:
                    menu.add("sell Power upgrade ( - " + upgr.getNbIncrease() + " damage )");
                    break;
                case RANGE:
                    menu.add("sell Range upgrade ( - " + upgr.getPercentage() + " % of range )");
                    break;
                case SPEED:
                    menu.add("sell Speed upgrade ( - " + upgr.getPercentage() + " % of speed )");
                    break;
                case MODIFY_PROJECTILE:
                    menu.add("sell Modify projectile upgrade ( ExtraBomb )");
                    break;
                default:
            }
        }
        menu.add("Back");
         String choice = lc.choose("Choice ?", menu);
        if(choice.equals("Back")){
                return;
        }
        for(Upgrade upgr : t.getCurrentUpgrades()){
                if(choice.equals("sell Power upgrade ( - " + upgr.getNbIncrease() + " damage )") && upgr.getUpgradeType() == UpgradeType.POWER){
                    t.sellUpgrade(upgr,this.p);
                    this.nbUpgrade -= 1;
                    System.out.println(t.toString() + " remove the upgrade : POWER\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");

                    return;
                }
                if(choice.equals("sell Range upgrade ( - " + upgr.getPercentage() + " % of range )") && upgr.getUpgradeType() == UpgradeType.RANGE){
                    t.sellUpgrade(upgr,this.p);
                    System.out.println(t.getRange());
                    this.nbUpgrade -= 1;
                    System.out.println(t.toString() + " remove the upgrade : RANGE\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
                if(choice.equals("sell Speed upgrade ( - " + upgr.getPercentage() + " % of speed )") && upgr.getUpgradeType() == UpgradeType.SPEED){
                    t.sellUpgrade(upgr,this.p);
                    this.nbUpgrade -= 1;
                    System.out.println(t.toString() + " remove the upgrade : SPEED\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
                if(choice.equals("sell Modify projectile upgrade ( ExtraBomb )") && upgr.getUpgradeType() == UpgradeType.MODIFY_PROJECTILE){
                    t.sellUpgrade(upgr,this.p);
                    this.nbUpgrade -= 1;
                    System.out.println(t.toString() + " remove the upgrade : MODIFY PROJECTILE\n");
                    System.out.println("you have left :" + this.p.getResources() + "\n");
                    return;
                }
            }
    }
}



