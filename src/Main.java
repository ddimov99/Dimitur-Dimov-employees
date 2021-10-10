public class Main {
    public static void main(String[] args) {
        EmployeesCollection ec = new EmployeesCollection("Employees.txt");
        System.out.println(ec.findLongestWorkingPair());
    }
}
