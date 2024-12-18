import java.util.*;
import java.io.*;

public class ApplicationDatabase {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppendApplication appendApplication = new AppendApplication();
        AlterApplication alterApplication = new AlterApplication();
        DeleteApplication deleteApplication = new DeleteApplication();
        ApplicationCollection applicationCollection = new ApplicationCollection();

        while (true) {
            System.out.println("\n--- Application Database Menu ---");
            System.out.println("1. Add a new application");
            System.out.println("2. Alter an application");
            System.out.println("3. Delete an application");
            System.out.println("4. Query applications");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addApplication(appendApplication, scanner);
                    case 2 -> alterApplication(alterApplication, scanner);
                    case 3 -> deleteApplication(deleteApplication, scanner);
                    case 4 -> queryApplications(applicationCollection, scanner);
                    case 5 -> {
                        System.out.println("Exiting the Application Database. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }


    private static void addApplication(AppendApplication appendApplication, Scanner scanner) throws IOException {
        System.out.print("Enter the title of the application: ");
        String title = scanner.nextLine();

        String[] details = new String[7];
        System.out.print("1) Job description: ");
        details[0] = scanner.nextLine();
        System.out.print("2) Salary: ");
        details[1] = scanner.nextLine();
        System.out.print("3) Location: ");
        details[2] = scanner.nextLine();
        System.out.print("4) Duration: ");
        details[3] = scanner.nextLine();
        System.out.print("5) Date of duration (if applicable): ");
        details[4] = scanner.nextLine();
        System.out.print("6) Number of tests to take: ");
        details[5] = scanner.nextLine();
        System.out.print("7) Number of tests taken: ");
        details[6] = scanner.nextLine();
        System.out.print("8) Status in application process: ");
        String status = scanner.nextLine();

        appendApplication.addApplication(title, details[0], details[1], details[2], details[3], details[4], details[5], details[6], status);
        System.out.println("Application added successfully.");
    }

    private static void alterApplication(AlterApplication alterApplication, Scanner scanner) throws IOException {
        List<String> titles = alterApplication.getApplicationTitles();
        if (titles.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        System.out.println("\nAvailable Applications:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
        System.out.print("Enter the number of the application to alter: ");
        int applicationIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        List<String> details = alterApplication.getApplicationDetails(applicationIndex);
        System.out.println("\nApplication Details:");
        for (int i = 1; i <= details.size(); i++) {
            System.out.println(i + ". " + details.get(i - 1));
        }
        System.out.print("Enter the number of the detail to alter: ");
        int detailIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.print("Enter the new value: ");
        String newValue = scanner.nextLine();

        alterApplication.updateApplicationDetail(applicationIndex, detailIndex, newValue);
        System.out.println("Application updated successfully.");
    }

    private static void deleteApplication(DeleteApplication deleteApplication, Scanner scanner) throws IOException {
        List<String> titles = deleteApplication.getApplicationTitles();
        if (titles.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        System.out.println("\nAvailable Applications:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
        System.out.print("Enter the number of the application to delete: ");
        int applicationIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.print("Are you sure you want to delete this application? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            deleteApplication.deleteApplication(applicationIndex);
            System.out.println("Application deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void queryApplications(ApplicationCollection applicationCollection, Scanner scanner) throws IOException {
        System.out.println("\nQuery Options:");
        System.out.println("1. Show rejected applications");
        System.out.println("2. Show accepted applications");
        System.out.println("3. Show pending applications");
        System.out.println("4. Show applications in a specific location");
        System.out.println("5. Sort applications by highest salary");
        System.out.println("6. Sort applications by lowest salary");
        System.out.println("7. Group applications by job descriptions");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Map<String, String>> result;
        switch (choice) {
            case 1 -> result = applicationCollection.filterByStatus("rejected");
            case 2 -> result = applicationCollection.filterByStatus("accepted");
            case 3 -> result = applicationCollection.filterByStatus("pending");
            case 4 -> {
                System.out.print("Enter the location: ");
                String location = scanner.nextLine();
                result = applicationCollection.filterByLocation(location);
            }
            case 5 -> result = applicationCollection.sortBySalary(false);
            case 6 -> result = applicationCollection.sortBySalary(true);
            case 7 -> {
                Map<String, List<Map<String, String>>> grouped = applicationCollection.groupByJobDescription();
                grouped.forEach((job, apps) -> {
                    System.out.println("\nJob Description: " + job);
                    apps.forEach(app -> System.out.println(app));
                });
                return;
            }
            default -> {
                System.out.println("Invalid choice. Returning to menu.");
                return;
            }
        }

        System.out.println("\nQuery Results:");
        result.forEach(System.out::println);
    }
}