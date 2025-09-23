import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String[] usernames = {"Nichlas", "Mark", "Morten", "Mikkel"};
    static String[] passwords = {"Ramm", "Kordon", "Larsen", "Illemann"};
    static int MAX_ATTEMPTS = 3;

    public static void main(String[] args) {

        promptLogin();

    }

    public static void promptLogin() {
        printToConsole("Dørmand: Hvad hedder du?");
        System.out.print("Jeg hedder ");
        String userInput = input.nextLine();
        String user = getUser(userInput);
        if (user == null) {
            printToConsole("Dørmand: Du står ikke på listen.\nDørmand: Forlad venligst området");
            return;
        }

        printToConsole("Dørmand: Tak for det, " + user + ".", false);
        boolean passwordIsCorrect = false;
        int attempts = 0;
        while (!passwordIsCorrect && attempts < MAX_ATTEMPTS) {
            printToConsole("\nDørmand: Hvad er dit kodeord?", false);
            System.out.print("Forsøg " + (attempts + 1) + ": ");
            userInput = input.nextLine();
            passwordIsCorrect = checkPassWord(userInput, user);
            attempts++;
        }

        if (!passwordIsCorrect) {
            printToConsole("Dørmand: Det var desværre dine 3 forsøg.\nDørmand: Forlad venligst området");
            return;
        }

        printToConsole("Dørmand: Det er korrekt.\nDørmand: Velkommen til, " + user + ". \nDørmand: Vi har glædet os til at se dig.");
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
}