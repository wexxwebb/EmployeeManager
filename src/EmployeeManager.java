import java.io.*;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees;

    private BufferedInputStream bis;
    private ObjectInputStream ois;
    private CustomObjectOutputStream oos;
    private File file;

    private boolean storeEmployees(ArrayList<Employee> employees) {
        boolean result = false;
        try {
            oos = new CustomObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(employees);
            oos.flush();
            result = true;
        } catch (IOException e) {
            System.out.println("Can't rewrite file " + file.getName());
        }
        return result;
    }

    private ArrayList<Employee> readEmployees() {
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            ois = new ObjectInputStream(bis);
            emps = (ArrayList<Employee>) ois.readObject();
            ois.close();
        } catch (EOFException e) {
            System.out.println(1);
        } catch (IOException e) {
            System.out.println("Cannot read file " + file.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return emps;
    }

    public EmployeeManager(String filename) {
        file = new File(filename);
        employees = readEmployees();
    }

    public ArrayList<Employee> getByJob(String job) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee emp : getEmployees()) {
            if (emp.getJob().equals(job)) {
                result.add(emp);
            }
        }
        return result;
    }

    public Employee getByName(String name) {
        for (Employee emp : getEmployees()) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }

    public boolean saveEmployee(String name, int age, int salary, String job) {
        Employee emp = new Employee(name, age, salary, job);
        employees.add(emp);
        return storeEmployees(employees);
    }

    public boolean deleteEmployee(String name) {
        boolean result = false;
        for (Employee emp : employees) {
            if (emp.getName().equals(name)) {
                employees.remove(emp);
                if (storeEmployees(employees)) {
                    result = true;
                }
                break;
            }
        }
        return result;
    }

    public ArrayList<Employee> getEmployees() {
        return readEmployees();
    }

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
}
