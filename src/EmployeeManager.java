import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
            result = new EmployeeArray();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int count = bis.read();
            bis.read();
            ArrayList<Integer> bytes = new ArrayList<>();
            while (count > 0) {
                int read = 0;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean saveEmployee(String name, int age, int salary, String job) {
        boolean result = true;
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
        return result;
    }
}
