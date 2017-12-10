import java.io.*;
import java.util.ArrayList;

public class EmployeeManager {

    private File file;
    private EmployeeArray employees;

    EmployeeManager(String filename) {
        file = new File(filename);
        employees = readEmployees();
    }

    EmployeeArray readEmployees() {
        EmployeeArray result = null;
        try {
            result = new EmployeeArray();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int count = bis.read();
            bis.read();
            ArrayList<Integer> bytes = new ArrayList<>();
            while (count > 0) {
                int read;
                StringBuilder sb = new StringBuilder();
                while ((read = bis.read()) != 10) {
                    sb.append((char)read);
                }
                String[] emp = sb.toString().split(" :: ");
                String name = emp[0];
                int age = Integer.parseInt(emp[1]);
                int salary = Integer.parseInt(emp[2]);
                String job = emp[3];
                result.getList().add(new Employee(name, age, salary, job));
                count--;
            }
            result.setSalarySum(bis.read());
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found.");
        } catch (IOException e) {
            System.out.println("Can't read file " + file.getName());
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
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(employees.getList().size());
            bos.write("\n".getBytes());
            int salary = 0;
            for (int i = 0; i < employees.getList().size(); i++) {
                bos.write(employees.getList().get(i).toFile().getBytes());
                salary += employees.getList().get(i).getSalary();
            }
            bos.write(salary);
            bos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found");
        } catch (IOException e) {
            System.out.println("Can't write file " + file.getName());
        }
    }

    boolean saveEmployee(String name, int age, int salary, String job) {
        boolean result = false;
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