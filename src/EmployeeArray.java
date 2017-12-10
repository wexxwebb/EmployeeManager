import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class EmployeeArray implements Externalizable {

    static final long serialVersionUID = 0;

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(list);
        int result = 0;
        for (Employee emp : list) {
            result += emp.getSalary();
        }
        out.writeObject(new Integer(result));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        list = (ArrayList<Employee>) in.readObject();
        salarySum = (Integer) in.readObject();
    }
}