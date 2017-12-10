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
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found");
        } catch (IOException e) {
            System.out.println("Can't read file " + file.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Can't read object");
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
        } catch (IOException e) {
            System.out.println("Can't write file " + file.getName());
        }
    }

    boolean saveEmployee(String name, int age, int salary, String job) {
        Employee new_Employee = new Employee(name, age, salary, job);
        for (int i = 0; i < employees.getList().size(); i++) {
            if (name.equals(employees.getList().get(i).getName())) {
                employees.getList().set(i, new_Employee);
                storeEmployees();
                return true;
            }
        }
        employees.getList().add(new Employee(name, age, salary, job));
        storeEmployees();
        return true;
    }
}
