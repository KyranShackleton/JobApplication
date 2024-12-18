import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationCollection {

    private static final String FILE_PATH = "src/ApplicationContents.txt";

    public List<Map<String, String>> getAllApplications() throws IOException {
        List<Map<String, String>> applications = new ArrayList<>();
        StringBuilder application = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() && application.length() > 0) {
                    applications.add(parseApplication(application.toString().trim()));
                    application.setLength(0);
                } else {
                    application.append(line).append("\n");
                }
            }
            if (application.length() > 0) {
                applications.add(parseApplication(application.toString().trim()));
            }
        }

        return applications;
    }

    public List<Map<String, String>> filterByStatus(String status) throws IOException {
        return getAllApplications().stream()
                .filter(app -> app.get("8) Status").equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> filterByLocation(String location) throws IOException {
        return getAllApplications().stream()
                .filter(app -> app.get("3) Location").equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> sortBySalary(boolean ascending) throws IOException {
        Comparator<Map<String, String>> comparator = Comparator.comparingDouble(app ->
                Double.parseDouble(app.get("2) Salary").replaceAll("[^\\d.]", "")));

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return getAllApplications().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Map<String, List<Map<String, String>>> groupByJobDescription() throws IOException {
        return getAllApplications().stream()
                .collect(Collectors.groupingBy(app -> app.get("1) Job Description")));
    }

    private Map<String, String> parseApplication(String application) {
        Map<String, String> appDetails = new LinkedHashMap<>();
        String[] lines = application.split("\n");

        for (String line : lines) {
            String[] parts = line.split(": ", 2);
            if (parts.length == 2) {
                appDetails.put(parts[0], parts[1]);
            }
        }

        return appDetails;
    }
}