import java.util.Objects;

public record EmployeePair(Employee e1, Employee e2) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmployeePair employeePair)) return false;
        return this.e1.equals(((EmployeePair) obj).e1) && this.e2.equals(((EmployeePair) obj).e2)
                || this.e1.equals(((EmployeePair) obj).e2)  && this.e2.equals(((EmployeePair) obj).e1);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }
}
