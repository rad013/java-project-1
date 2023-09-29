package main;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {
    Scanner scan = new Scanner(System.in);
    int choice;
    boolean isValid;
    Vector<User> users = new Vector<>();
    Vector<User> tempVec = new Vector<>();
    File encryptedFile = new File("SuperS3cr3tFile.dat");
    String userName;
    String pass;
    String toFile;
    int score;
    String tempPass;
    int bet;
    Random rand = new Random();
    Vector<Card> playerCards = new Vector<>();
    Vector<Card> dealerCards = new Vector<>();
    int dealerTotalVal = 0;
    int playerTotalVal = 0;
    main.Deck deck = new Deck();

    public Main() throws Exception {

	menu();

    }

    public void menu() throws Exception {
	do {
	    isValid = true;
	    try {

		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		System.out.print("Choose[1-3]>>");
		choice = Integer.valueOf(scan.nextLine());
	    } catch (NumberFormatException e) {
		isValid = false;
		System.out.println("please input number");
	    }
	} while (choice > 3 || choice < 1 || !isValid);
	switch (choice) {
	case 1:
	    Login();
	    menu();
	    break;
	case 2:
	    Register();
	    menu();
	    break;
	case 3:
	    System.exit(0);
	    break;

	default:

	    break;
	}
    }

    public void Register() throws Exception {

	Scanner scanFile = new Scanner(encryptedFile);
	while (scanFile.hasNextLine()) {
	    String temp = decrypt(scanFile.nextLine());
	    String arrTemp[] = temp.split("#");
	    userName = arrTemp[0];
	    pass = arrTemp[1];
	    score = Integer.parseInt(arrTemp[2]);
	    users.add(new User(userName, pass, score));
	}

	do {
	    isValid = true;
	    System.out.print("Input username: ");
	    userName = scan.nextLine();
	    for (User user : users) {
		if (user.getUserName().equals(userName)) {
		    isValid = false;
		    System.out.println("[!] Username already exist");
		}
	    }
	} while (userName.length() > 10 || userName.length() < 4 || !isValid);
	do {
	    System.out.print("Input password: ");
	    pass = scan.nextLine();
	} while (pass.length() > 16 || pass.length() < 8 || !isAlphaNumeric(pass));
	score = 100;
	toFile = userName + "#" + pass + "#" + score;
	users.clear();
	FileWriter fw = new FileWriter(encryptedFile, true);
	fw.write(encrypt(toFile) + "\n");
	fw.close();
	System.out.println("[*] Successfully registered an account");
	System.out.println("press enter to continue...");
	scan.nextLine();
    }

    public boolean isAlphaNumeric(String pass) {

	if (pass.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")) {
	    return true;
	}
	return false;
    }

    public void Login() throws Exception {
	isValid = true;
	Scanner scanFile = new Scanner(encryptedFile);
	while (scanFile.hasNextLine()) {
	    String temp = decrypt(scanFile.nextLine());
	    String arrTemp[] = temp.split("#");
	    userName = arrTemp[0];
	    pass = arrTemp[1];
	    score = Integer.parseInt(arrTemp[2]);
	    users.add(new User(userName, pass, score));
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
	    System.out.println("[!] Invalid Username and Password");
	    System.out.println("press enter to continue...");
	    scan.nextLine();
	    isValid = false;
	    users.clear();
	    menu();
	}

	System.out.println("[*] Successfully logged in");
	System.out.println("press enter to continue...");
	scan.nextLine();
	users.clear();
	mainMenu();

    }

    public void mainMenu() throws Exception {
	if (tempVec.isEmpty()) {
	    Scanner scanFile = new Scanner(encryptedFile);
	    while (scanFile.hasNextLine()) {
		String temp = decrypt(scanFile.nextLine());
		String arrTemp[] = temp.split("#");
		String tempName = arrTemp[0];
		String tempPass = arrTemp[1];
		int tempScore = Integer.parseInt(arrTemp[2]);
		if (userName.equals(tempName)) {
		    tempVec.add(new User(tempName, tempPass, tempScore));
		}

	    } 
	}
	choice = 0;
	do {
	    isValid = true;
	    try {
		System.out.println("Hello, " + tempVec.get(0).getUserName());
		System.out.println("point : " + tempVec.get(0).getScore());
		System.out.println("1. Play");
		System.out.println("2. Highscore");
		System.out.println("3. Save & Logout");
		System.out.print("Choose[1-3]>>");
		choice = Integer.valueOf(scan.nextLine());
	    } catch (NumberFormatException e) {
		isValid = false;
		System.out.println("please input number");
	    }
	} while (choice > 3 || choice < 1 || !isValid);
	switch (choice) {
	case 1:
	    if (tempVec.get(0).getScore() <= 0) {
		System.out.println("Error");
		System.out.println("Your account has reached 0 point\nand been banned by system");
		System.out.println("Press enter to continue...");
		scan.nextLine();
	    }else {
		bet();
	    }
	    mainMenu();
	    break;
	case 2:
	    highScore();
	    mainMenu();
	    break;
	case 3:
	    save();
	    menu();
	    break;

	default:

	    break;
	}
    }

    public void bet() throws Exception {

	do {
	    isValid = true;
	    try {
		System.out.printf("Input your bet [max %d]: ", tempVec.get(0).getScore());
		bet = Integer.valueOf(scan.nextLine());
		if (bet > tempVec.get(0).getScore() || bet < 1 ) {
		    throw new NumberFormatException();
		}
	    } catch (NumberFormatException e) {
		isValid = false;
		System.out.println("[!] Input must be between 1 and " + tempVec.get(0).getScore());
	    }
	} while (!isValid || bet < 1);

	do {
	    isValid = true;
	    playerCards.add(deck.getDeck(rand.nextInt(51) + 1));
	    dealerCards.add(deck.getDeck(rand.nextInt(51) + 1));
	    playerCards.add(deck.getDeck(rand.nextInt(51) + 1));
	    dealerCards.add(deck.getDeck(rand.nextInt(51) + 1));
	    if (dealerCards.get(0).equals(playerCards.get(0)) || dealerCards.get(1).equals(playerCards.get(1))
		    || dealerCards.get(0).equals(dealerCards.get(1)) || playerCards.get(0).equals(playerCards.get(1))) {
		playerCards.clear();
		dealerCards.clear();
		isValid = false;
	    }
	} while (!isValid);
	play();
    }

    public void play() throws Exception {
	playerTotalVal = 0;
	dealerTotalVal = 0;

	do {
	    isValid = true;
	    try {
		for (int i = 0; i < playerCards.size(); i++) {
		    playerTotalVal += playerCards.get(i).getRank().getValue();
		}
		for (int i = 0; i < dealerCards.size(); i++) {
		    dealerTotalVal += dealerCards.get(i).getRank().getValue();
		}
		System.out.println("Dealer Card : ");
		for (int i = 0; i < dealerCards.size(); i++) {
		    if (i < dealerCards.size() - 1) {
			System.out.print(dealerCards.get(i) + " | ");
		    } else {
			if (i == 1 && choice != 2 && dealerTotalVal < 21 && playerTotalVal < 21) {
			    System.out.print("??");
			} else {
			    System.out.print(dealerCards.get(i));
			}

		    }
		}
		System.out.println("\nPlayer Card : ");
		for (int i = 0; i < playerCards.size(); i++) {
		    if (i < playerCards.size() - 1) {
			System.out.print(playerCards.get(i) + " | ");
		    } else {
			System.out.print(playerCards.get(i));
		    }
		}

		System.out.println();

		if (playerTotalVal >= 21 || dealerTotalVal >= 21 || choice == 2) {
		    if (dealerTotalVal > 21) {
			System.out.println("[!] The dealer Busted, you won " + (bet * 2) + " point(s)");
			tempVec.get(0).setScore(tempVec.get(0).getScore() + bet * 2);
			System.out.println("press enter to continue...");
			scan.nextLine();
			playerCards.clear();
			dealerCards.clear();
			playerTotalVal = 0;
			dealerTotalVal = 0;
			mainMenu();
			break;
		    } else if (playerTotalVal > 21) {
			System.out.println("[!]" + userName + " Busted, you lost " + (bet) + " point(s)");
			tempVec.get(0).setScore(tempVec.get(0).getScore() - bet);
			System.out.println("press enter to continue...");
			scan.nextLine();
			playerCards.clear();
			dealerCards.clear();
			playerTotalVal = 0;
			dealerTotalVal = 0;
			mainMenu();
			break;
		    } else if ((playerTotalVal - 21) < (dealerTotalVal - 21) && playerTotalVal != 21) {
			System.out.println("[!]" + userName + " Busted, you lost " + (bet) + " point(s)");
			tempVec.get(0).setScore(tempVec.get(0).getScore() - bet);
			System.out.println("press enter to continue...");
			scan.nextLine();
			playerCards.clear();
			dealerCards.clear();
			playerTotalVal = 0;
			dealerTotalVal = 0;
			mainMenu();
			break;
		    } else if ((playerTotalVal - 21) > (dealerTotalVal - 21) && dealerTotalVal != 21) {
			System.out.println("[!] The dealer Busted, you won " + (bet * 2) + " point(s)");
			tempVec.get(0).setScore(tempVec.get(0).getScore() + bet * 2);
			System.out.println("press enter to continue...");
			scan.nextLine();
			playerCards.clear();
			dealerCards.clear();
			playerTotalVal = 0;
			dealerTotalVal = 0;
			mainMenu();
			break;

		    } else if (playerTotalVal == dealerTotalVal) {
			System.out.println("Draw");
			System.out.println("press enter to continue...");
			scan.nextLine();
			playerCards.clear();
			dealerCards.clear();
			playerTotalVal = 0;
			dealerTotalVal = 0;
			mainMenu();
			break;
		    }
		}
		System.out.println("Choose your move");
		System.out.println("1. Hit");
		System.out.println("2. Stand");
		System.out.print("Choose[1-2]>>");
		choice = Integer.valueOf(scan.nextLine());
	    } catch (NumberFormatException e) {
		isValid = false;
		System.out.println("please input number");
	    }
	} while (choice > 2 || choice < 1 || !isValid);

	switch (choice) {
	case 1:
	    do {
		int j = 0;
		isValid = true;
		playerCards.add(deck.getDeck(rand.nextInt(51) + 1));
		for (int i = 0; i < playerCards.size(); i++) {
		    if (j < dealerCards.size()) {
			if (playerCards.get(i).equals(dealerCards.get(j))) {
			    isValid = false;
			    j++;
			}
		    }

		    if (i > 0) {
			if (playerCards.get(i).equals(playerCards.get(i - 1))) {
			    isValid = false;
			}
		    }
		}

	    } while (!isValid);
	    play();

	    break;
	case 2:

	    if (dealerTotalVal < 17) {
		while (dealerTotalVal < 21) {
		    do {
			int j = 0;
			int i = 0;
			int count = 2;
			isValid = true;
			dealerCards.add(deck.getDeck(rand.nextInt(51) + 1));
			for (i = 0; i < dealerCards.size(); i++) {
			    if (j < playerCards.size()) {
				if (dealerCards.get(i).equals(playerCards.get(j))) {
				    isValid = false;
				    j++;
				}
			    }

			    if (i > 0) {
				if (dealerCards.get(i).equals(dealerCards.get(i - 1))) {
				    isValid = false;
				}
			    }
			}
			dealerTotalVal += dealerCards.get(count).getRank().getValue();
			count++;
		    } while (!isValid);
		}
	    }
	    play();
	    break;

	default:
	    break;
	}

    }

    public void highScore() throws Exception {
	Scanner scanFile = new Scanner(encryptedFile);
	while (scanFile.hasNextLine()) {
	    String temp = decrypt(scanFile.nextLine());
	    String arrTemp[] = temp.split("#");
	    String tempName = arrTemp[0];
	    String tempPass = arrTemp[1];
	    int tempScore = Integer.parseInt(arrTemp[2]);
	    if (userName.equals(tempName)) {
		users.add(new User(tempName, tempPass, tempVec.get(0).getScore()));
	    } else {
		users.add(new User(tempName, tempPass, tempScore));
	    }

	}
	mergeSort(0, users.size() - 1);
	System.out.println("|\tHIGHSCORE\t|");
	System.out.println("| Username  | Point     |");
	for (User user : users) {
	    System.out.println("| " + user.getUserName() + "  |" + '\t' + user.getScore() + "\t|");
	}
	users.clear();
	System.out.println("press enter to continue...");
	scan.nextLine();
    }

    public void mergeSort(int l, int r) {
	if (l < r) {
	    int m = l + (r - l) / 2;
	    mergeSort(l, m);
	    mergeSort(m + 1, r);
	    merge(l, m, r);
	}
    }

    public void merge(int left, int mid, int right) {
	int nL = mid - left + 1;
	int nR = right - mid;
	String[] arrL = new String[nL];
	String[] arrR = new String[nR];
	Integer[] arrL2 = new Integer[nL];
	Integer[] arrR2 = new Integer[nR];
	String[] arrL3 = new String[nL];
	String[] arrR3 = new String[nR];
	for (int i = 0; i < nL; i++) {
	    arrL[i] = users.get(left + i).getUserName();
	    arrL2[i] = users.get(left + i).getScore();
	    arrL3[i] = users.get(left + i).getPass();
	}
	for (int i = 0; i < nR; i++) {
	    arrR[i] = users.get(mid + 1 + i).getUserName();
	    arrR2[i] = users.get(mid + 1 + i).getScore();
	    arrR3[i] = users.get(mid + 1 + i).getPass();

	}
	int tempL = 0, tempR = 0;
	int currentIndex = left;
	while (tempL < nL && tempR < nR) {
	    if (arrL2[tempL] >= arrR2[tempR]) {
		users.get(currentIndex).setUserName(arrL[tempL]);
		users.get(currentIndex).setScore(arrL2[tempL]);
		users.get(currentIndex).setPass(arrL3[tempL]);
		tempL++;
	    } else {
		users.get(currentIndex).setUserName(arrR[tempR]);
		users.get(currentIndex).setScore(arrR2[tempR]);
		users.get(currentIndex).setPass(arrR3[tempR]);
		tempR++;
	    }
	    currentIndex++;
	}
	while (tempL < nL) {
	    users.get(currentIndex).setUserName(arrL[tempL]);
	    users.get(currentIndex).setScore(arrL2[tempL]);
	    users.get(currentIndex).setPass(arrL3[tempL]);
	    tempL++;
	    currentIndex++;
	}
	while (tempR < nR) {
	    users.get(currentIndex).setUserName(arrR[tempR]);
	    users.get(currentIndex).setScore(arrR2[tempR]);
	    users.get(currentIndex).setPass(arrR3[tempR]);
	    tempR++;
	    currentIndex++;
	}

    }

    public void save() throws Exception {
	Scanner scanFile = new Scanner(encryptedFile);
	while (scanFile.hasNextLine()) {
	    String temp = decrypt(scanFile.nextLine());
	    String arrTemp[] = temp.split("#");
	    String tempName = arrTemp[0];
	    String tempPass = arrTemp[1];
	    int tempScore = Integer.parseInt(arrTemp[2]);
	    if (userName.equals(tempName)) {
		users.add(new User(tempName, tempPass, tempVec.get(0).getScore()));
	    } else {
		users.add(new User(tempName, tempPass, tempScore));
	    }

	}

	FileWriter fw = new FileWriter(encryptedFile);
	for (User user : users) {
	    fw.write(encrypt(user.getUserName() + "#" + user.getPass() + "#" + user.getScore()) + "\n");
	}
	tempVec.clear();
	users.clear();
	fw.close();

    }

    public String decrypt(String encryptedString) throws Exception {
	byte[] decodeKey = Base64.getDecoder().decode("Us3rS3CR3TD4T4SS");
	Cipher cipher = Cipher.getInstance("AES");
	SecretKey key = new SecretKeySpec(Arrays.copyOf(decodeKey, 16), "AES");
	cipher.init(Cipher.DECRYPT_MODE, key);
	byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
	return new String(cipherText);
    }

    public String encrypt(String data) throws Exception {
	byte[] decodeKey = Base64.getDecoder().decode("Us3rS3CR3TD4T4SS");
	Cipher cipher = Cipher.getInstance("AES");
	SecretKey key = new SecretKeySpec(Arrays.copyOf(decodeKey, 16), "AES");
	cipher.init(Cipher.ENCRYPT_MODE, key);
	byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
	return Base64.getEncoder().encodeToString(cipherText);
    }

    public static void main(String[] args) throws Exception {
	new Main();
    }
}
