import java.io.*;
import java.util.ArrayList;

public class EmployeeManager {

    private File file;
    private EmployeeArray employees;

    private class CustomObjectOutputStream extends ObjectOutputStream {
        public CustomObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        @Override
        protected void writeStreamHeader() throws IOException {
            super.writeStreamHeader();
            reset();
        }
    }

    EmployeeManager(String filename) {
        file = new File(filename);
        employees = readEmployees();
    }

    EmployeeArray readEmployees() {
        EmployeeArray result = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            result = (EmployeeArray) ois.readObject();
            ois.close();
        } catch (EOFException e) {
            return new EmployeeArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    boolean deleteEmployee(String name) {
        for (int i = 0; i < employees.getList().size(); i++) {
            if (employees.getList().get(i).getName().equals(name)) {
                employees.getList().remove(i);
                storeEmployees();
                return true;
            }
        }
        return false;
    }

    public int getSalary() {
        return readEmployees().getSalarySum();
    }

    Employee getByName(String name) {
        for (Employee emp : readEmployees().getList()) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }

    ArrayList<Employee> getByJob(String job) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee emp : readEmployees().getList()) {
            if (emp.getJob().equals(job)) {
                result.add(emp);
            }
        }
        return result;
    }

    void storeEmployees() {
        try {
            CustomObjectOutputStream oos = new CustomObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(employees);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean saveEmployee(String name, int age, int salary, String job) {
        boolean result = true;
            employees.getList().add(new Employee(name, age, salary, job));
            storeEmployees();
        return result;
    }
}
