import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlterApplication {

    private static final String FILE_PATH = "src/ApplicationContents.txt";

    public List<String> getApplicationTitles() throws IOException {
        List<String> titles = new ArrayList<>();
        List<String> applications = readApplications();

        for (String application : applications) {
            String title = application.split("\n")[0]; // Extract title
            titles.add(title);
        }
        return titles;
    }

    public List<String> getApplicationDetails(int applicationIndex) throws IOException {
        List<String> applications = readApplications();

        if (applicationIndex < 0 || applicationIndex >= applications.size()) {
            throw new IndexOutOfBoundsException("Invalid application index.");
        }

        String[] details = applications.get(applicationIndex).split("\n");
        List<String> detailList = new ArrayList<>();
        for (String detail : details) {
            detailList.add(detail);
        }
        return detailList;
    }

    public void updateApplicationDetail(int applicationIndex, int detailIndex, String newValue) throws IOException {
        List<String> applications = readApplications();

        if (applicationIndex < 0 || applicationIndex >= applications.size()) {
            throw new IndexOutOfBoundsException("Invalid application index.");
        }

        String[] selectedApplication = applications.get(applicationIndex).split("\n");
        if (detailIndex < 1 || detailIndex > selectedApplication.length) {
            throw new IndexOutOfBoundsException("Invalid detail index.");
        }

        String detailLabel = selectedApplication[detailIndex].split(":")[0]; // Extract label
        selectedApplication[detailIndex] = detailLabel + ": " + newValue;

        applications.set(applicationIndex, String.join("\n", selectedApplication));
        writeApplications(applications);
    }

    private List<String> readApplications() throws IOException {
        List<String> applications = new ArrayList<>();
        StringBuilder application = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() && application.length() > 0) {
                    applications.add(application.toString().trim());
                    application.setLength(0);
                } else {
                    application.append(line).append("\n");
                }
            }
            if (application.length() > 0) {
                applications.add(application.toString().trim());
            }
        }

        return applications;
    }

    private void writeApplications(List<String> applications) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String application : applications) {
                writer.write(application);
                writer.write("\n\n");
            }
        }
    }
}