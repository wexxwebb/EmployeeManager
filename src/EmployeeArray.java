import java.util.ArrayList;

public class EmployeeArray {

    private ArrayList<Employee> list;
    private int salarySum;

    public EmployeeArray() {
        this.list = new ArrayList<>();
        this.salarySum = 0;
    }

    public ArrayList<Employee> getList() {
        return list;
    }

    public int getSalarySum() {
        return salarySum;
    }

    public void setSalarySum(int salarySum) {
        this.salarySum = salarySum;
    }

}
