import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        EmployeeManager manager = new EmployeeManager("employee.dat");

        Scanner scanner = new Scanner(System.in);
        String line;
        boolean play = true;
        while (play) {
            line = scanner.nextLine();
            String[] command = line.split(" ");
            switch (command[0]) {
                case "save":
                    if (command.length == 5) {
                        try {
                            String name = command[1];
                            int age = Integer.parseInt(command[2]);
                            int salary = Integer.parseInt(command[3]);
                            String job = command[4];
                            if (manager.saveEmployee(name, age, salary, job)) {
                                System.out.println("Ok");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Illegal command");
                    }
                    break;
                case "name":
                    System.out.println(manager.getByName(command[1]));
                    break;
                case "job":
                    for (Employee emp : manager.getByJob(command[1])) {
                        System.out.println(emp);
                    }
                    break;
                case "salary" :
                    System.out.println(manager.getSalary());
                    break;
                case "del":
                    if (manager.deleteEmployee(command[1])) {
                        System.out.println("Ok");
                    }
                    break;
                case "all":
                    for (Employee emp : manager.readEmployees().getList()) {
                        System.out.println(emp);
                    }
                    break;
                case "exit":
                    play = false;
                    break;
                default:
                    System.out.println("Illegal command");
            }
        }

    }
}
