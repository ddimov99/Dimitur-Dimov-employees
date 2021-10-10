import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

public class EmployeesCollection {
 
    List<Employee> employees;
    Map<EmployeePair, Long> pairsWorkedTime = new HashMap<>();
 
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
        EmployeePair longestWorkingPair = null;
        Integer currProj = null;
        Integer nextProj = null;
        for (int i = 0; i < employees.size() - 1; ++i) {
            Employee currEmpl = employees.get(i);
            currProj = currEmpl.getProjectID();
            for (int j = i + 1;; ++j) {
                if (j == employees.size()) {
                    break;
                } // 63072000000
                Employee nextEmpl = employees.get(j);
                nextProj = nextEmpl.getProjectID();
                if (nextProj == currProj) {
                    long timeWorkingTogether = currEmpl.getTimeWorkedWith(nextEmpl);
                    EmployeePair currPair = new EmployeePair(currEmpl, nextEmpl);
                    if (!pairsWorkedTime.containsKey(currPair)) {
                        pairsWorkedTime.put(currPair, timeWorkingTogether);
                    } else {
                        pairsWorkedTime.put(currPair, pairsWorkedTime.get(currPair) + timeWorkingTogether);
                    }
                } else {
                    break;
                }
            }
        }
        long maxTime = 0;
        EmployeePair resultPair = null;
 
        for (EmployeePair pair : pairsWorkedTime.keySet()) {
            long currentPairTime = pairsWorkedTime.get(pair);
            if (currentPairTime > maxTime) {
                resultPair = pair;
                maxTime = currentPairTime;
            }
        }
 
        return resultPair;
    }
}
