import java.util.*;
class Crime {
    private String type;
    private String description;
    private String location;
    private String dateTime;
    private String status; // Pending, Approved, Rejected

    public Crime(String type, String description, String location) {
        this.type = type;
        this.description = description;
        this.location = location;
        this.dateTime = new Date().toString();
        this.status = "Pending";
    }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void display() {
        System.out.println(" ");
        System.out.println("Crime Type     : " + type);
        System.out.println("Description    : " + description);
        System.out.println("Location       : " + location);
        System.out.println("Date/Time      : " + dateTime);
        System.out.println("Status         : " + status);
        if (status.equals("Approved")) {
            LawInfo law = LawDatabase.getLawInfo(type);
            if (law != null) {
                System.out.println("Law Section    : " + law.getSection());
                System.out.println("Punishment     : " + law.getPunishment());
            }
        }
        System.out.println(" ");
    }
}
class LawInfo {
    private String section;
    private String punishment;

    public LawInfo(String section, String punishment) {
        this.section = section;
        this.punishment = punishment;
    }
    public String getSection() { return section; }
    public String getPunishment() { return punishment; }
}
class LawDatabase {
    private static final Map<String, LawInfo> lawMap = new HashMap<>();
    static {
        lawMap.put("Theft", new LawInfo("Section 378", "Up to 3 years jail or fine"));
        lawMap.put("Robbery", new LawInfo("Section 392", "Up to 10 years jail"));
        lawMap.put("Murder", new LawInfo("Section 302", "Death penalty or life imprisonment"));
        lawMap.put("Assault", new LawInfo("Section 351", "Up to 2 years jail or fine"));
        lawMap.put("Burglary", new LawInfo("Section 457", "Up to 7 years jail"));
    }

    public static LawInfo getLawInfo(String crimeType) {
        return lawMap.get(crimeType);
    }
}
abstract class User {
    protected String username;

    public User(String username) {
        this.username = username;
    }

    public abstract void menu(ArrayList<Crime> crimes, Scanner sc);
}
class GeneralUser extends User {

    public GeneralUser(String username) {
        super(username);
    }
    @Override
    public void menu(ArrayList<Crime> crimes, Scanner sc) {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Submit Crime Report");
            System.out.println("2. View Approved Crimes");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    submitCrime(crimes, sc);
                    break;
                case 2:
                    viewApprovedCrimes(crimes);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    private void submitCrime(ArrayList<Crime> crimes, Scanner sc) {
        System.out.println("\n--- Submit Crime Report ---");
        System.out.print("Enter crime type (Theft/Robbery/Murder/Assault/Burglary): ");
        String type = sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();
        System.out.print("Enter location: ");
        String loc = sc.nextLine();

        Crime crime = new Crime(type, desc, loc);
        crimes.add(crime);
        System.out.println("Crime submitted successfully. Status: Pending verification.");
    }
    private void viewApprovedCrimes(ArrayList<Crime> crimes) {
        System.out.println("\n--- Approved Crimes ---");
        boolean found = false;
        for (Crime c : crimes) {
            if (c.getStatus().equals("Approved")) {
                c.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No approved crimes available yet.");
        }
    }
}
class Admin extends User {
    public Admin(String username) {
        super(username);
    }
    @Override
    public void menu(ArrayList<Crime> crimes, Scanner sc) {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View Pending Crimes");
            System.out.println("2. Verify Crimes");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    viewPendingCrimes(crimes);
                    break;
                case 2:
                    verifyCrimes(crimes, sc);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    private void viewPendingCrimes(ArrayList<Crime> crimes) {
        System.out.println("\n--- Pending Crimes ---");
        boolean found = false;
        for (Crime c : crimes) {
            if (c.getStatus().equals("Pending")) {
                c.display();
                found = true;
            }
        }
        if (!found) System.out.println("No pending crimes.");
    }

    private void verifyCrimes(ArrayList<Crime> crimes, Scanner sc) {
        System.out.println("\n--- Verify Crimes ---");
        boolean found = false;
        for (int i = 0; i < crimes.size(); i++) {
            Crime c = crimes.get(i);
            if (c.getStatus().equals("Pending")) {
                found = true;
                System.out.println("\nCrime #" + (i + 1));
                c.display();
                System.out.print("Approve (A) / Reject (R): ");
                String option = sc.nextLine().toUpperCase();
                if (option.equals("A")) {
                    c.setStatus("Approved");
                    System.out.println("Crime approved successfully!");
                } else if (option.equals("R")) {
                    c.setStatus("Rejected");
                    System.out.println("Crime rejected.");
                } else {
                    System.out.println("Invalid option, skipped.");
                }
            }
        }
        if (!found) System.out.println("No crimes to verify.");
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Crime> crimes = new ArrayList<>();
        while (true) {
            System.out.println("\n===== Crime Reporting & Law Information System =====");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as General User");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter admin username: ");
                    String adminName = sc.nextLine();
                    Admin admin = new Admin(adminName);
                    admin.menu(crimes, sc);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String userName = sc.nextLine();
                    GeneralUser user = new GeneralUser(userName);
                    user.menu(crimes, sc);
                    break;
                case 3:
                    System.out.println("Exiting system... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

