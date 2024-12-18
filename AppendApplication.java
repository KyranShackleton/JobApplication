import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AppendApplication {
    
    public void addApplication(String title, String jobDescription, String salary, String location,
                               String duration, String dateOfDuration, String testsToTake,
                               String testsTaken, String applicationStatus) {
        StringBuilder applicationEntry = new StringBuilder();

        applicationEntry.append("Title: ").append(title).append("\n");

        applicationEntry.append("1) Job Description: ").append(jobDescription).append("\n")
                .append("2) Salary: ").append(salary).append("\n")
                .append("3) Location: ").append(location).append("\n")
                .append("4) Duration: ").append(duration).append("\n")
                .append("5) Date of Duration: ").append(dateOfDuration).append("\n")
                .append("6) Number of Tests to Take: ").append(testsToTake).append("\n")
                .append("7) Number of Tests Taken: ").append(testsTaken).append("\n")
                .append("8) Status in Application Process: ").append(applicationStatus).append("\n\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/ApplicationContents.txt", true))) {
            writer.write(applicationEntry.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}