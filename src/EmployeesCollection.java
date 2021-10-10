import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

public class EmployeesCollection {

    List<Employee> employees;

    public EmployeesCollection(String fileName) {
        employees = new ArrayList<>();
        Path usersFilePath = Path.of(fileName);
        if (Files.exists(usersFilePath)) {
            try (BufferedReader br = Files.newBufferedReader(usersFilePath)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parameters = line.split(", ");
                    int employeeID = Integer.parseInt(parameters[0]);
                    int projectID = Integer.parseInt(parameters[1]);
                    String dateFrom = parameters[2];
                    String dateTo = parameters[3];

                    employees.add(new Employee(employeeID, projectID, dateFrom, dateTo));
                }
            } catch (IOException e) {
                throw new RuntimeException("There is a problem with the file", e);
            } catch (ParseException e) {
                throw new RuntimeException("Date format is wrong");
            }
        }
        employees.sort(Comparator.comparing(Employee::getProjectID));
    }

    public EmployeePair findLongestWorkingPair() {
        long currLongestTime = 0;
        EmployeePair longestWorkingPair = null;
        Integer currProj = null;
        Integer nextProj = null;
        for (int i = 0; i < employees.size() - 1; ++i) {
            Employee currEmpl = employees.get(i);
            currProj = currEmpl.getProjectID();
            for (int j = i + 1;; ++j) {
                if (j == employees.size()) {
                    break;
                }
                Employee nextEmpl = employees.get(j);
                nextProj = nextEmpl.getProjectID();
                if (nextProj == currProj) {
                    long timeWorkingTogether = currEmpl.getTimeWorkedWith(nextEmpl);
                    if (timeWorkingTogether > currLongestTime) {
                        longestWorkingPair = new EmployeePair(currEmpl, nextEmpl);
                        currLongestTime = timeWorkingTogether;
                    }
                } else {
                    break;
                }
            }
        }
        return longestWorkingPair;
    }
}