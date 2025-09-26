import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String[] usernames = {"Nichlas", "Mark", "Morten", "Mikkel", "", "", "", "", "", ""};
    static String[] passwords = {"Ramm", "Kordon", "Larsen", "Illemann", "", "", "", "", "", ""};
    static int MAX_ATTEMPTS = 3;
    static boolean entryGranted = false;

    public static void main(String[] args) {

        while (!entryGranted) {
            promptMenu();
        }
    }

    public static void promptMenu() {
        printToConsole("""
                ---------- Vælg handling ----------
                1... Forsøg indgang ved dørmand
                2... ADMIN: Rediger gæsteliste
                3... Tag hjem igen
                """);
        while (!input.hasNextInt()) {
            printToConsole("""
                    ---------- Vælg handling ----------
                    1... Forsøg indgang ved dørmand
                    2... ADMIN: Rediger gæsteliste
                    3... Tag hjem igen
                    """);
            printToConsole("FEJL - indtast et gyldigt tal.", false);
        }
        int userInput = input.nextInt();
        switch (userInput) {
            case 1:
                promptLogin();
                break;
            case 2:
                promptEdit();
                break;
            case 3:
                break;
        }
    }

    public static void promptLogin() {
        printToConsole("Dørmand: Hvad hedder du?");
        System.out.print("Jeg hedder ");
        input.nextLine();
        String userInput = input.nextLine();
        String user = getUser(userInput);
        if (user == null) {
            printToConsole("Dørmand: Du står ikke på listen.\nDørmand: Forlad venligst området");
            return;
        }

        printToConsole("\nDørmand: Tak for det, " + user + ".", false);
        boolean passwordIsCorrect = false;
        int attempts = 0;
        while ((!passwordIsCorrect) && (attempts < MAX_ATTEMPTS)) {
            printToConsole("\nDørmand: Hvad er dit kodeord?", false);
            System.out.print("Forsøg " + (attempts + 1) + ": ");
            userInput = input.nextLine();
            passwordIsCorrect = checkPassWord(userInput, user);
            attempts++;
        }

        if (!passwordIsCorrect) {
            printToConsole("Dørmand: Det var desværre dine 3 forsøg.\nDørmand: Forlad venligst området");
            waitForUser();
            return;
        }

        printToConsole("Dørmand: Det er korrekt.\nDørmand: Velkommen til, " + user + ". \nDørmand: Vi har glædet os til at se dig.");
        entryGranted = true;
    }

    private static boolean checkPassWord(String userInput, String user) {
        int index = IntStream.range(0, usernames.length)
                .filter(i -> usernames[i].equalsIgnoreCase(user))
                .findFirst()
                .orElse(-1);
        String password = passwords[index];
        return userInput.equals(password);
    }

    private static String getUser(String userInput) {
        return Arrays.stream(usernames)
                .filter(username -> username.equalsIgnoreCase(userInput))
                .findFirst()
                .orElse(null);
    }

    public static void printToConsole(String text, boolean clear) {
        if (clear) {
            clearConsole();
        }
        System.out.println(text);
    }

    public static void printToConsole(String text) {
        printToConsole(text, true);
    }

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void promptEdit() {
        printToConsole("""
                ---------- Vælg handling ----------
                    1... Tilføj bruger
                    2... Fjern bruger
                    3... Rediger bruger
                -----------------------------------
                   \s""");
        int userInput = input.nextInt();
        switch (userInput) {
            case 1:
                addUser();
                break;
            case 2:
                removeUser();
                break;
            case 3:
                editUser();
                break;
        }
    }

    public static void addUser() {
        int takenCounter = 0;
        String username;
        String password;
        for (int n = 0; n <= usernames.length; n++) {
            if (usernames[n].equalsIgnoreCase("")) {
                printToConsole("Indtast navn på gæsten du vil tilføje:");
                System.out.print("Jeg ønsker at tilføje: ");
                input.nextLine();
                username = input.nextLine();

                printToConsole("Angiv venligst adgangskode til " + username + ".");
                System.out.print(username + " adgangskode: ");
                password = input.nextLine();

                usernames[n] = username;
                passwords[n] = password;

                printToConsole("Du har nu tilføjet:\nBrugernavn: " + username + "\nAdgangskode: " + password);
                waitForUser();
                return;
            } else {
                takenCounter++;
            }
        }
        if (takenCounter == 10) {
            printToConsole("""
                    Der er desværre ikke flere ledige pladser på gæstelisten.
                    Fjern eller rediger en fra listen.
                    """);
        }
    }

    public static void removeUser() {
        printUserList();
        while (true) {
            printToConsole("\nIndtast venligst navnet på den bruger du ønsker at fjerne.");
            System.out.print("Jeg ønsker af fjerne: ");
            String userInput = input.nextLine();
            for (int n = 0; n <= usernames.length - 1; n++) {
                if (userInput.equalsIgnoreCase(usernames[n])) {
                    usernames[n] = "";
                    passwords[n] = "";
                    return;
                }
            }
            System.out.println("Navn ikke fundet. Prøv igen.");
        }
    }

    private static void editUser() {
        while (true) {
            clearConsole();
            printUserList();
            printToConsole("\nIndtast venligst navnet på den bruger du ønsker at redigere.", false);
            System.out.print("Jeg ønsker af redigere: ");
            input.nextLine();
            String userInput = input.nextLine();
            for (int n = 0; n <= usernames.length - 1; n++) {
                if (userInput.equalsIgnoreCase(usernames[n])) {
                    printToConsole("Du redigerer lige nu:\n" + "Navn: " + usernames[n] + "\n" + "Adgangskode: " + passwords[n]);
                    System.out.print("\nIndtast nye navn: ");
                    usernames[n] = input.nextLine();
                    System.out.print("\nIndtast nye adgangskode: ");
                    passwords[n] = input.nextLine();
                    return;
                }
            }
            System.out.println("Navn ikke fundet. Prøv igen.");
        }
    }

    public static void waitForUser() {
        System.out.println("\nTryk enter for at fortsætte...");
        input.nextLine();
    }

    public static void printUserList() {
        printToConsole("---------- GÆSTELISTE ----------");
        for (int n = 0; n <= usernames.length - 1; n++) {
            if (!usernames[n].isEmpty())
                System.out.println(n + 1 + ": " + usernames[n]);
        }
    }

}