import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Employee {
    private final int empID;
    private final int projectID;
    private final Date dateFrom;
    private final Date dateTo;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public Employee(int empID, int projectID, String dateFrom, String dateTo) throws ParseException {
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = formatter.parse(dateFrom);
        this.dateTo = dateTo.equals("NULL") ? new Date() : formatter.parse(dateTo);
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjectID() {
        return projectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return empID == employee.empID && projectID == employee.projectID
                && dateFrom.equals(employee.dateFrom)
                && dateTo.equals(employee.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empID, projectID, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID +
                ", projectID=" + projectID +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    public long getTimeWorkedWith(Employee other){
        final long thisFrom = this.dateFrom.getTime();
        final long thisTo = this.dateTo.getTime();
        final long otherFrom = other.dateFrom.getTime();
        final long otherTo = other.dateTo.getTime();

        if (thisFrom >= otherFrom && thisTo >= otherTo && otherTo > thisFrom) {
            return otherTo - thisFrom;
        } else if (thisFrom <= otherFrom && thisTo <= otherTo && thisTo > otherFrom) {
            return thisTo - otherFrom;
        } else if (thisFrom < otherFrom && thisTo > otherTo) {
            return otherTo - otherFrom;
        } else if (thisFrom > otherFrom && thisTo < otherTo) {
            return thisTo - thisFrom;
        } else if (thisFrom == otherFrom && thisTo == otherTo) {
            return thisTo - thisFrom;
        }
        return 0;
    }
}