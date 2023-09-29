package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main extends Thread {
    int choice;
    boolean isValid;
    Scanner scan = new Scanner(System.in);
    File drinkFile = new File("drink.txt");
    File foodFile = new File("food.txt");
    File playerFile = new File("player.txt");
    Vector<Menu> foods = new Vector<>();
    Vector<Menu> drinks = new Vector<>();
    Vector<User> users = new Vector<>();
    Vector<Customer> customers = new Vector<>();
    String userName;
    String pass;
    String toFile;
    volatile int score = 0;
    volatile int life = 5;
    String name;
    int price;
    int idk;
    volatile int gameTime = 0;
    int custPatience;
    int customer;
    String food;
    String drink;
    volatile boolean isFoodServed = false;
    volatile boolean isDrinkServed = false;
    int totalPrice;
    String tempPass;
    int chanceIncreasePerSecondCustomer = 0;
    int chanceIncreasePerSecondCustomerPatience = 0;
    int chanceToDecreaseCustomerPatience = 0;
    int chanceToIncreaseCustomer = 0;
    volatile int seats = 5;
    volatile int expandCost = 28500;
    volatile int workerCost = 40000;
    volatile boolean alive = true;
    volatile boolean running = true;
    volatile boolean modifyOnlyOne;
    volatile boolean workerModifyOnlyOne;
    volatile int ads = 5;
    volatile int adsAmount = 0;
    volatile int adsCost = 37500;
    volatile int decoration = 5;
    volatile int decoAmount = 0;
    volatile int decoCost = 35000;
    Random rand = new Random();
    String tempMenu;
    Vector<String> tempMenus = new Vector<>();
    int randomDrink;
    int randomFood;
    String tempPatience;
    String tempFood;
    String tempDrink;
    int tempPrice;
    Vector<Worker> workers = new Vector<>();
    String tempCook;
    volatile String input = "";
    Thread one;
    Thread two;
    volatile boolean dontEndOne = false;
    volatile boolean dontEndTwo = false;
    boolean isThreadStarted = false;
    volatile boolean playedOnce = false;

    public Main() {

	menu();

    }

    public static void main(String[] args) {
	new Main();
    }

    private void menu() {
	do {
	    clearScreen();
	    isValid = true;
	    try {
		System.out.println("AXFORPATTY");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		System.out.print(">>");
		choice = Integer.valueOf(scan.nextLine());
	    } catch (Exception e) {
		System.out.println("Please input number");
		isValid = false;
	    }
	} while (choice > 3 || choice < 1 || !isValid);
	switch (choice) {
	case 1:

	    login();

	    break;
	case 2:
	    register();

	    break;
	case 3:
	    System.exit(3);
	    break;
	default:

	    break;
	}
    }

    private synchronized void clearScreen() {
	for (int i = 0; i < 100; i++) {
	    System.out.println();
	}
    }

    private void register() {
	users.clear();
	score = 0;
	try {
	    if (playerFile.createNewFile()) {

	    } else {
		Scanner scanFile = new Scanner(playerFile);
		while (scanFile.hasNextLine()) {
		    String temp = (scanFile.nextLine());
		    String arrTemp[] = temp.split("#");
		    String tempName = arrTemp[0];
		    String tempPass = arrTemp[1];
		    int tempScore = Integer.parseInt(arrTemp[2]);
		    users.add(new User(tempName, tempPass, tempScore));
		}
	    }
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	do {
	    isValid = true;
	    System.out.print("Input a unique username [5 to 20 chars]: ");
	    userName = scan.nextLine();
	    for (User user : users) {
		if (user.getUserName().equals(userName)) {
		    isValid = false;
		    System.out.println("Username has been taken, choose another username");
		}
	    }
	} while (userName.length() > 30 || userName.length() < 5 || !isValid);
	do {
	    System.out.print("Input password [8 to 20 chars]: ");
	    pass = scan.nextLine();
	} while (pass.length() > 20 || pass.length() < 8);
	toFile = userName + "#" + pass + "#" + score;
	try {
	    FileWriter fw = new FileWriter(playerFile, true);
	    fw.write(toFile + "\n");
	    fw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	System.out.println("Successfully registered");
	System.out.println("press enter to continue...");
	scan.nextLine();
	mainMenu();
    }

    private void login() {
	users.clear();
	isValid = true;
	try {
	    if (playerFile.createNewFile()) {

	    } else {
		Scanner scanFile = new Scanner(playerFile);
		while (scanFile.hasNextLine()) {
		    String temp = (scanFile.nextLine());
		    String arrTemp[] = temp.split("#");
		    String tempName = arrTemp[0];
		    String tempPass = arrTemp[1];
		    int tempScore = Integer.parseInt(arrTemp[2]);
		    users.add(new User(tempName, tempPass, tempScore));
		}
	    }
	} catch (NumberFormatException e1) {
	    e1.printStackTrace();
	} catch (FileNotFoundException e1) {
	    e1.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}

	try {
	    System.out.print("Input username: ");
	    userName = scan.nextLine();
	    System.out.print("Input password: ");
	    pass = scan.nextLine();
	    for (User user : users) {
		if (user.getUserName().equals(userName)) {
		    isValid = true;
		    tempPass = user.getPass();
		}
	    }
	    if (!isValid || !tempPass.equals(pass)) {
		throw new Exception();
	    }
	} catch (Exception e) {
	    System.out.println("Invalid Username and Password");
	    System.out.println("press enter to continue...");
	    scan.nextLine();
	    isValid = false;
	    users.clear();
	    menu();
	}

	System.out.println("Successfully logged in");
	System.out.println("press enter to continue...");
	scan.nextLine();
	mainMenu();
    }

    private void mainMenu() {

	    if (!playedOnce) {
		users.clear();
		clearScreen();
		try {
		    Scanner scanFile = new Scanner(playerFile);
		    while (scanFile.hasNextLine()) {
			String temp = (scanFile.nextLine());
			String arrTemp[] = temp.split("#");
			String tempName = arrTemp[0];
			String tempPass = arrTemp[1];
			int tempScore = Integer.parseInt(arrTemp[2]);
			users.add(new User(tempName, tempPass, tempScore));
		    }
		} catch (NumberFormatException e1) {
		    e1.printStackTrace();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
		for (User user : users) {
		    if (userName.equals(user.getUserName())) {
			score = user.getScore();
		    }
		}
		do {
		    choice = 0;
		    isValid = true;
		    try {
			System.out.println("AXFORPATTY");
			System.out.printf("%-15s | %-15s \n", userName, ("Score : " + score));
			System.out.println("1. Play game");
			System.out.println("2. View scoreboard");
			System.out.println("3. Exit");
			System.out.print(">>");
			choice = Integer.valueOf(scan.nextLine());
		    } catch (Exception e) {
			System.out.println("Please input number");
			isValid = false;
		    }
		} while (choice > 3 || choice < 1 || !isValid);
		switch (choice) {
		case 1:
		    play();
		    break;
		case 2:
		    scoreboard();
		    mainMenu();
		    break;
		case 3:
		    menu();
		    break;
		default:

		    break;
		}
	    }
	

    }

    private void scoreboard() {
	users.clear();
	try {
	    Scanner scanFile = new Scanner(playerFile);
	    while (scanFile.hasNextLine()) {
		String temp = (scanFile.nextLine());
		String arrTemp[] = temp.split("#");
		String tempName = arrTemp[0];
		String tempPass = arrTemp[1];
		int tempScore = Integer.parseInt(arrTemp[2]);
		users.add(new User(tempName, tempPass, tempScore));
	    }
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	sortDescendingScore(users);
	for (User user : users) {
	    System.out.printf(" %-15s | %-15s \n", user.getUserName(), ("Score: " + user.getScore()));
	}
	System.out.println("Press [Enter] to continue..");
	scan.nextLine();
    }

    private void play() {
	playedOnce = true;
	foods.clear();
	drinks.clear();
	try {
	    Scanner scanFileFood = new Scanner(foodFile);
	    while (scanFileFood.hasNextLine()) {
		String temp = scanFileFood.nextLine();
		String arrTemp[] = temp.split("#");
		name = arrTemp[0];
		idk = Integer.parseInt(arrTemp[1]);
		price = Integer.parseInt(arrTemp[2]);
		foods.add(new Menu(name, idk, price));
	    }
	    Scanner scanFileDrink = new Scanner(drinkFile);
	    while (scanFileDrink.hasNextLine()) {
		String temp = scanFileDrink.nextLine();
		String arrTemp[] = temp.split("#");
		name = arrTemp[0];
		idk = Integer.parseInt(arrTemp[1]);
		price = Integer.parseInt(arrTemp[2]);
		drinks.add(new Menu(name, idk, price));
	    }
	} catch (NumberFormatException e1) {
	    e1.printStackTrace();
	} catch (FileNotFoundException e1) {
	    e1.printStackTrace();
	}

	startThreadOne();
	startThreadTwo();
	if (isThreadStarted) {
	    dontEndOne();
	    dontEndTwo();
	    resetGame();
	    input = "";
	}
	isThreadStarted = true;

    }

    public synchronized void incrementDecrement() {
	gameTime++;
	chanceToDecreaseCustomerPatience = rand.nextInt(100);
	chanceToIncreaseCustomer = rand.nextInt(100);
	chanceIncreasePerSecondCustomer += 10;
	chanceIncreasePerSecondCustomerPatience += 10;
	randomDrink = rand.nextInt(drinks.size() - 1);
	randomFood = rand.nextInt(foods.size() - 1);
	totalPrice = foods.get(randomFood).getPrice() + drinks.get(randomDrink).getPrice();
	isFoodServed = false;
	isDrinkServed = false;

	if (chanceToIncreaseCustomer < chanceIncreasePerSecondCustomer + (ads * adsAmount)
		&& customers.size() < seats) {
	    chanceIncreasePerSecondCustomer = 0;
	    customers.add(new Customer(foods.get(randomFood).getName(), isFoodServed, drinks.get(randomDrink).getName(),
		    isDrinkServed, totalPrice));

	}

	if (!workers.isEmpty()) {
	    for (Worker worker : workers) {
		worker.setWorkDuration(worker.getWorkDuration() - 1);
		if (worker.getWorkDuration() <= 0) {
		    worker.setWorkDuration(10);
		    if (!customers.isEmpty()) {
			int temp = workers.size();
			int index = 0;
			while (temp > 0) {
			    workerModifyOnlyOne = true;
			    if (index < customers.size() && customers.get(index).isFoodServed() == false
				    && workerModifyOnlyOne) {
				customers.get(index).setFoodServed(isFoodServed = true);
				workerModifyOnlyOne = false;
			    } else if (index < customers.size() && customers.get(index).isDrinkServed() == false
				    && customers.get(index).isFoodServed() == true && workerModifyOnlyOne) {
				customers.get(index).setDrinkServed(isDrinkServed = true);
				index++;
			    }

			    temp--;
			}
		    }
		}
	    }
	}
	if (!customers.isEmpty() && chanceToDecreaseCustomerPatience < chanceIncreasePerSecondCustomerPatience
		- (decoration * decoAmount)) {
	    for (Customer customer : customers) {
		customer.setCustPatience(customer.getCustPatience() - 1);
	    }
	    chanceIncreasePerSecondCustomerPatience = 0;
	}

    }

    public synchronized void shutdownRunner1() {
	running = false;
	dontEndOne = false;
	playedOnce = false;
    }

    public synchronized void resetGame() {
	customers.clear();
	life = 5;
	gameTime = 0;
	adsAmount = 0;
	decoAmount = 0;
	workers.clear();
	seats = 5;
	chanceIncreasePerSecondCustomer = 0;
	chanceIncreasePerSecondCustomerPatience = 0;
    }

    public synchronized void resetTime() {
	gameTime = 0;
    }

    public synchronized void shutdownRunner2() {
	alive = false;
	dontEndTwo = false;
	playedOnce = false;
    }

    public synchronized void dontEndOne() {
	dontEndOne = true;
    }

    public synchronized void dontEndTwo() {
	dontEndTwo = true;
    }

    public void sortDescendingScore(Vector<User> users) {
	for (int i = 0; i < users.size(); i++) {
	    for (int j = 0; j < users.size() - 1; j++) {
		if (users.get(j).getScore() < users.get(j + 1).getScore()) {
		    User temp = users.get(j);
		    users.set(j, users.get(j + 1));
		    users.set(j + 1, temp);
		}
	    }
	}
    }

    public void startThreadOne() {
	if (one == null) {
	    one = new Thread(new Runnable() {
		@Override
		public void run() {
		    synchronized (this) {
			gameTime = 0;
			do {

			    incrementDecrement();
			    clearScreen();
			    if (life <= 0 || input.equalsIgnoreCase("Exit")) {
				shutdownRunner1();
			    } else if (gameTime >= 60) {
				shutdownRunner1();
			    }
			    System.out.printf("Timer : %d | Life : %d | Score : %d\n", gameTime, life, score);
			    System.out.println();
			    if (workers.size() > 0) {
				System.out.printf(" %-6s | %-10s\n", "Worker", "Cooking");
				for (int i = 0; i < workers.size(); i++) {
				    tempCook = "";
				    if (!customers.isEmpty()) {
					for (int j = 0; j < workers.get(i).getWorkDuration(); j++) {
					    tempCook += "#";
					}
				    }
				    System.out.printf(" %-6d |[%-10s]\n", i + 1, tempCook);
				}
			    }
			    System.out.println();
			    System.out.printf(" %-3s | %-10s | %-25s | %-25s | %-10s \n", "NO", "Patience", "Food",
				    "Drink", "Price");
			    for (int i = 0; i < seats; i++) {
				if (i < customers.size()) {
				    if (customers.get(i).getCustPatience() <= 0) {
					customers.remove(i);
					life -= 1;
				    }
				    tempPatience = "";
				    if (!customers.isEmpty()) {
					for (int j = 0; j < customers.get(i).getCustPatience(); j++) {
					    tempPatience += "#";
					}
					if (customers.get(i).isFoodServed() == false) {
					    tempFood = customers.get(i).getFood() + " [ ]";
					} else {
					    tempFood = customers.get(i).getFood() + " [v]";
					}
					if (customers.get(i).isDrinkServed() == false) {
					    tempDrink = customers.get(i).getDrink() + " [ ]";
					} else {
					    tempDrink = customers.get(i).getDrink() + " [v]";
					}
					tempPrice = customers.get(i).getTotalPrice()
						* customers.get(i).getCustPatience();
					System.out.printf(" %-3d |[%-10s]| %-25s | %-25s | %-10d \n", (i + 1),
						tempPatience, tempFood, tempDrink, tempPrice);
				    }
				    if (customers.get(i).isFoodServed() == true
					    && customers.get(i).isDrinkServed() == true) {
					score += customers.get(i).getTotalPrice() * customers.get(i).getCustPatience();
					customers.remove(i);
				    }

				} else {
				    System.out.printf(" %-5d\n", (i + 1));
				}
			    }
			    System.out.println("Type the order to serve (case sensitive)");
			    System.out.println("Type exit to stop playing");
			    if (life <= 0 || input.equalsIgnoreCase("Exit")) {
				System.out.println("YOU LOST");
				System.out.println("Press [Enter] to continue..");
			    }
			    if (gameTime >= 60) {
				System.out.println("LEVEL CLEARED");
				System.out.println("Type exit to stop playing or continue to continue...");
			    }

			    while (!running) {
				if (dontEndOne) {
				    running = true;
				}
			    }
			    try {
				Thread.sleep(1000);

			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }

			} while (running);

		    }

		}
	    });
	    one.start();
	}
    }

    public void startThreadTwo() {
	if (two == null) {
	    two = new Thread(new Runnable() {
		@Override
		public void run() {
		    synchronized (this) {
			do {
			    if (life <= 0) {
				try {
				    Thread.sleep(1000);
				} catch (InterruptedException e1) {
				    e1.printStackTrace();
				}
				users.clear();
				try {
				    Scanner scanFile = new Scanner(playerFile);
				    while (scanFile.hasNextLine()) {
					String temp = (scanFile.nextLine());
					String arrTemp[] = temp.split("#");
					String tempName = arrTemp[0];
					String tempPass = arrTemp[1];
					int tempScore = Integer.parseInt(arrTemp[2]);
					if (tempName.equals(userName)) {
					    users.add(new User(tempName, tempPass, score));
					} else {
					    users.add(new User(tempName, tempPass, tempScore));
					}

				    }

				} catch (NumberFormatException e) {
				    e.printStackTrace();
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				}
				try {
				    FileWriter fw = new FileWriter(playerFile);
				    for (User user : users) {
					fw.write(user.getUserName() + "#" + user.getPass() + "#" + user.getScore()
						+ "\n");
				    }
				    fw.close();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				shutdownRunner2();
				mainMenu();
			    }
			    modifyOnlyOne = true;
			    Scanner sc = new Scanner(System.in);
			    input = sc.nextLine();
			    System.out.println(">>");
			    if (input.equalsIgnoreCase("Exit") && gameTime < 60) {
				try {
				    Thread.sleep(1000);
				} catch (InterruptedException e1) {
				    e1.printStackTrace();
				}
				users.clear();
				try {
				    Scanner scanFile = new Scanner(playerFile);
				    while (scanFile.hasNextLine()) {
					String temp = (scanFile.nextLine());
					String arrTemp[] = temp.split("#");
					String tempName = arrTemp[0];
					String tempPass = arrTemp[1];
					int tempScore = Integer.parseInt(arrTemp[2]);
					if (tempName.equals(userName)) {
					    users.add(new User(tempName, tempPass, score));
					} else {
					    users.add(new User(tempName, tempPass, tempScore));
					}

				    }

				} catch (NumberFormatException e) {
				    e.printStackTrace();
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				}
				try {
				    FileWriter fw = new FileWriter(playerFile);
				    for (User user : users) {
					fw.write(user.getUserName() + "#" + user.getPass() + "#" + user.getScore()
						+ "\n");
				    }
				    fw.close();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				scan.nextLine();
				shutdownRunner2();
				mainMenu();

			    } else if (gameTime >= 60) {
				if (input.equalsIgnoreCase("Exit")) {
				    try {
					Thread.sleep(1000);
				    } catch (InterruptedException e1) {
					e1.printStackTrace();
				    }
				    users.clear();
				    try {
					Scanner scanFile = new Scanner(playerFile);
					while (scanFile.hasNextLine()) {
					    String temp = (scanFile.nextLine());
					    String arrTemp[] = temp.split("#");
					    String tempName = arrTemp[0];
					    String tempPass = arrTemp[1];
					    int tempScore = Integer.parseInt(arrTemp[2]);
					    if (tempName.equals(userName)) {
						users.add(new User(tempName, tempPass, score));
					    } else {
						users.add(new User(tempName, tempPass, tempScore));
					    }

					}

				    } catch (NumberFormatException e) {
					e.printStackTrace();
				    } catch (FileNotFoundException e) {
					e.printStackTrace();
				    }
				    try {
					FileWriter fw = new FileWriter(playerFile);
					for (User user : users) {
					    fw.write(user.getUserName() + "#" + user.getPass() + "#" + user.getScore()
						    + "\n");
					}
					fw.close();
				    } catch (IOException e) {
					e.printStackTrace();
				    }
				    System.out.println("YOU LOST");
				    System.out.println("Press [Enter] to continue..");
				    scan.nextLine();
				    shutdownRunner2();
				    mainMenu();
				} else if (input.equalsIgnoreCase("Continue")) {
				    choice = 0;
				    while (choice != 5) {
					try {
					    clearScreen();
					    System.out.printf("%-15s | %-15s \n", userName, ("Score : " + score));
					    System.out.println();
					    System.out.printf("1. %-15s - %-10d \n", "Expand restaurant",
						    (seats * expandCost));
					    if (decoAmount > 0 && decoAmount < 20) {
						System.out.printf("2. %-15s - %-10d \n", "Buy decorations",
							((decoAmount + 1) * decoCost));
					    } else if (decoAmount <= 0) {
						System.out.printf("2. %-15s - %-10d \n", "Buy decorations", decoCost);
					    } else {
						System.out.printf("2. Limit reached\n");
					    }
					    if (adsAmount > 0 && adsAmount < 20) {
						System.out.printf("3. %-15s - %-10d \n", "Purchase more ads",
							((adsAmount + 1) * adsCost));
					    } else if (adsAmount <= 0) {
						System.out.printf("3. %-15s - %-10d \n", "Purchase more ads", adsCost);
					    } else {
						System.out.printf("3. Limit reached\n");
					    }

					    if (!workers.isEmpty()) {
						System.out.printf("4. %-15s - %-10d \n", "Hire worker",
							((workers.size() + 1) * workerCost));
					    } else {
						System.out.printf("4. %-15s - %-10d \n", "Hire worker", workerCost);
					    }
					    System.out.println("5. Exit");
					    System.out.print(">>");
					    choice = Integer.valueOf(scan.nextLine());
					} catch (NumberFormatException e) {
					    System.out.println("Please input number 1 - 5");
					}
					switch (choice) {
					case 1:
					    if (score - seats * expandCost >= 0) {
						score -= seats * expandCost;
						seats += 1;
						System.out.println("Restaurant expanded...");
						System.out.println("Press [Enter] to continue...");
						scan.nextLine();
					    } else {
						System.out.println("Insufficient funds...");
						System.out.println("Press [Enter] to continue...");
						scan.nextLine();
					    }
					    break;
					case 2:
					    if (decoAmount < 20) {
						if (decoAmount > 0) {
						    if (score - (decoAmount + 1) * decoCost >= 0) {
							score -= (decoAmount + 1) * decoCost;
							decoAmount += 1;
							System.out.println("Restaurant decorated...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    } else {
							System.out.println("Insufficient funds...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    }
						} else {
						    if (score - decoCost >= 0) {
							score -= decoCost;
							decoAmount += 1;
							System.out.println("Restaurant decorated...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    } else {
							System.out.println("Insufficient funds...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    }
						}
					    } else {
						System.out.println("Limit reached");
						System.out.println("Press [Enter] to continue...");
						scan.nextLine();
					    }
					    break;
					case 3:
					    if (adsAmount < 20) {
						if (adsAmount > 0) {
						    if (score - (adsAmount + 1) * adsCost >= 0) {
							score -= (adsAmount + 1) * adsCost;
							adsAmount += 1;
							System.out.println("Restaurant advertised...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    } else {
							System.out.println("Insufficient funds...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    }
						} else {
						    if (score - adsCost >= 0) {
							score -= adsCost;
							adsAmount += 1;
							System.out.println("Restaurant advertised...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    } else {
							System.out.println("Insufficient funds...");
							System.out.println("Press [Enter] to continue...");
							scan.nextLine();
						    }
						}
					    } else {
						System.out.println("Limit reached");
						System.out.println("Press [Enter] to continue...");
						scan.nextLine();
					    }
					    break;
					case 4:
					    if (!workers.isEmpty()) {
						if (score - (workers.size() + 1) * workerCost >= 0) {
						    score -= (workers.size() + 1) * workerCost;
						    workers.add(new Worker());
						    System.out.println("Worker hired...");
						    System.out.println("Press [Enter] to continue...");
						    scan.nextLine();
						} else {
						    System.out.println("Insufficient funds...");
						    System.out.println("Press [Enter] to continue...");
						    scan.nextLine();
						}
					    } else {
						if (score - workerCost >= 0) {
						    score -= workerCost;
						    workers.add(new Worker());
						    System.out.println("Worker hired...");
						    System.out.println("Press [Enter] to continue...");
						    scan.nextLine();
						} else {
						    System.out.println("Insufficient funds...");
						    System.out.println("Press [Enter] to continue...");
						    scan.nextLine();
						}
					    }
					    break;
					case 5:
					    dontEndOne();
					    resetTime();
					    input = "";
					    break;

					default:
					    break;
					}
				    }

				}
			    }
			    for (int i = 0; i < customers.size(); i++) {
				if (customers.get(i).getFood().equals(input)
					&& customers.get(i).isFoodServed() == false) {
				    if (modifyOnlyOne) {
					customers.get(i).setFoodServed(isFoodServed = true);
					modifyOnlyOne = false;
				    }
				}
				if (customers.get(i).getDrink().equals(input)
					&& customers.get(i).isDrinkServed() == false) {
				    if (modifyOnlyOne) {
					customers.get(i).setDrinkServed(isDrinkServed = true);
					modifyOnlyOne = false;
				    }
				}
			    }
			    while (!alive) {
				if (dontEndTwo) {
				    alive = true;
				}
			    }
			} while (alive);

		    }

		}
	    });
	    two.start();
	}
    }

}
