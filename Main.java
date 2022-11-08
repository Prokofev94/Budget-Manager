package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static File file = new File("purchases.txt");
    static List<Purchase> listOfPurchases = new ArrayList<>();
    static double balance = 0;
    public static void main(String[] args) {
        while (true) {
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("7) Analyze (Sort)");
            System.out.println("0) Exit");
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1":
                    addIncome();
                    break;
                case "2":
                    addPurchase();
                    break;
                case "3":
                    showListPurchases();
                    break;
                case "4":
                    showBalance();
                    break;
                case "5":
                    save();
                    break;
                case "6":
                    load();
                    break;
                case "7":
                    sort();
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
            }
            System.out.println();
        }
    }

    static void addIncome() {
        System.out.println("Enter income:");
        balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!");
    }

    static void addPurchase() {
        while (true) {
            System.out.println("Choose the type of purchase");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) Back");
            String input = scanner.nextLine();
            if ("5".equals(input)) {
                return;
            }
            System.out.println();
            System.out.println("Enter purchase name:");
            String name = scanner.nextLine();
            System.out.println("Enter its price:");
            String price = scanner.nextLine();
            balance -= Double.parseDouble(price);
            String type = "";
            switch (input) {
                case "1":
                    type = "Food";
                    break;
                case "2":
                    type = "Clothes";
                    break;
                case "3":
                    type = "Entertainment";
                    break;
                case "4":
                    type = "Other";
                    break;
            }
            listOfPurchases.add(new Purchase(type, name, price));
            System.out.println("Purchase was added!");
            System.out.println();
        }
    }

    static void showListPurchases() {
        if (listOfPurchases.size() == 0) {
            System.out.println("The purchase list is empty");
        } else {
            while (true) {
                System.out.println("Choose the type of purchases");
                System.out.println("1) Food");
                System.out.println("2) Clothes");
                System.out.println("3) Entertainment");
                System.out.println("4) Other");
                System.out.println("5) All");
                System.out.println("6) Back");
                String input = scanner.nextLine();
                System.out.println();
                String type = "";
                switch (input) {
                    case "1":
                        System.out.println("Food:");
                        type = "Food";
                        break;
                    case "2":
                        System.out.println("Clothes:");
                        type = "Clothes";
                        break;
                    case "3":
                        System.out.println("Entertainment:");
                        type = "Entertainment";
                        break;
                    case "4":
                        System.out.println("Other:");
                        type = "Other";
                        break;
                    case "5":
                        System.out.println("All:");
                        break;
                    case "6":
                        System.out.println();
                        return;
                }
                int count = 0;
                for (Purchase purchase : listOfPurchases) {
                    if (purchase.getType().startsWith(type)) {
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.println("The purchase list is empty");
                } else {
                    double result = 0;
                    for (Purchase purchase : listOfPurchases) {
                        if (purchase.getType().startsWith(type)) {
                            System.out.println(purchase);
                            result += purchase.getPrice();
                        }
                    }
                    System.out.printf("Total sum: $%.2f\n\n", result);
                }
            }
        }
    }

    static void showBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
    }

    static void save() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.append(String.valueOf(balance)).append("\n");
            for (Purchase purchase : listOfPurchases) {
                writer.append(String.format("%s#%s", purchase.getType(), purchase)).append("\n");
            }
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    static void load() {
        try (Scanner sc = new Scanner(file)) {
            balance = Double.parseDouble(sc.nextLine());
            listOfPurchases = new ArrayList<>();
            while (sc.hasNext()) {
                listOfPurchases.add(new Purchase(sc.nextLine()));
            }
            System.out.println("Purchases were loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    static void sort() {
        while (true) {
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1":
                    if (listOfPurchases.size() == 0) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        System.out.println("All:");
                        List<Purchase> sortedList = new ArrayList<>(listOfPurchases);
                        Collections.sort(sortedList);
                        double total = 0;
                        for (Purchase purchase : sortedList) {
                            System.out.println(purchase);
                            total += purchase.getPrice();
                        }
                        System.out.printf("Total: $%.2f\n", total);
                    }
                    break;
                case "2":
                    double food = 0;
                    double entertainment = 0;
                    double clothes = 0;
                    double other = 0;
                    double total = 0;
                    for (Purchase purchase : listOfPurchases) {
                        switch (purchase.getType()) {
                            case "Food":
                                food += purchase.getPrice();
                                break;
                            case "Entertainment":
                                entertainment += purchase.getPrice();
                                break;
                            case "Clothes":
                                clothes += purchase.getPrice();
                                break;
                            case "Other":
                                other += purchase.getPrice();
                                break;
                        }
                        total += purchase.getPrice();
                    }
                    System.out.println("Types:");
                    System.out.printf("Food - $%.2f\n", food);
                    System.out.printf("Entertainment - $%.2f\n", entertainment);
                    System.out.printf("Clothes - $%.2f\n", clothes);
                    System.out.printf("Other - $%.2f\n", other);
                    System.out.printf("Total sum: $%.2f\n", total);
                    break;
                case "3":
                    System.out.println("Choose the type of purchase");
                    System.out.println("1) Food");
                    System.out.println("2) Clothes");
                    System.out.println("3) Entertainment");
                    System.out.println("4) Other");
                    input = scanner.nextLine();
                    System.out.println();
                    String type = "";
                    switch (input) {
                        case "1":
                            type = "Food";
                            break;
                        case "2":
                            type = "Clothes";
                            break;
                        case "3":
                            type = "Entertainment";
                            break;
                        case "4":
                            type = "Other";
                            break;
                    }
                    int count = 0;
                    for (Purchase purchase : listOfPurchases) {
                        if (type.equals(purchase.getType())) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        System.out.printf("%s:\n", type);
                        List<Purchase> typeList = new ArrayList<>();
                        for (Purchase purchase : listOfPurchases) {
                            if (type.equals(purchase.getType())) {
                                typeList.add(purchase);
                            }
                        }
                        Collections.sort(typeList);
                        total = 0;
                        for (Purchase purchase : typeList) {
                            System.out.println(purchase);
                            total += purchase.getPrice();
                        }
                        System.out.printf("Total sum: $%.2f\n", total);
                    }
                    break;
                case "4":
                    return;
            }
            System.out.println();
        }
    }
}

class Purchase implements Comparable<Purchase> {
    private final String type;
    private final String name;
    private final double price;

    public Purchase(String type, String name, String price) {
        this.type = type;
        this.name = name;
        this.price = Double.parseDouble(price);
    }

    public Purchase(String line) {
        this.type = line.split("#")[0];
        line = line.split("#")[1];
        this.name = line.substring(0, line.lastIndexOf(" $"));
        this.price = Double.parseDouble(line.substring(line.lastIndexOf('$') + 1));
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Purchase purchase) {
        double different = this.price - purchase.price;
        if (different > 0) {
            return -1;
        } else if (different < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }
}