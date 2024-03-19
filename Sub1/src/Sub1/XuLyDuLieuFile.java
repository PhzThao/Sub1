package Sub1;

import java.io.*;
import java.util.*;

class Student {
    String studentID;
    String name;
    String gender;
    double pythonScore;
    double javaScore;
    double averageScore;
    String result;

    public Student(String studentID, String name, String gender, double pythonScore, double javaScore) {
        this.studentID = studentID;
        this.name = name;
        this.gender = gender;
        this.pythonScore = pythonScore;
        this.javaScore = javaScore;
        this.averageScore = (javaScore * 2 + pythonScore) / 3;
        this.result = (averageScore >= 5) ? "Dau" : (gender.equals("Nam") ? "Truot" : "Dau");
    }

    public String toString() {
        return studentID + ", " + name + ", " + gender + ", " + pythonScore + ", " + javaScore + ", " + averageScore + ", " + result;
    }
}

public class XuLyDuLieuFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập số lượng học viên
        System.out.print("Nhap so luong hoc vien: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Nhập thông tin học viên và ghi vào file input.txt
        try {
            PrintWriter writer = new PrintWriter("input.txt");
            for (int i = 0; i < n; i++) {
                System.out.println("Nhap thong tin hoc vien thu " + (i + 1) + ":");
                System.out.print("Ma sinh vien: ");
                String studentID = scanner.nextLine();
                System.out.print("Ho ten: ");
                String name = scanner.nextLine();
                System.out.print("Gioi tinh: ");
                String gender = scanner.nextLine();
                System.out.print("Diem Python: ");
                double pythonScore = scanner.nextDouble();
                System.out.print("Diem Java: ");
                double javaScore = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                writer.println(studentID + ", " + name + ", " + gender + ", " + pythonScore + ", " + javaScore);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Loi: Khong the tao hoac mo file input.txt");
            return;
        }

        // Đọc dữ liệu từ file và thực hiện yêu cầu
        List<Student> students = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                String studentID = parts[0];
                String name = parts[1];
                String gender = parts[2];
                double pythonScore = Double.parseDouble(parts[3]);
                double javaScore = Double.parseDouble(parts[4]);

                Student student = new Student(studentID, name, gender, pythonScore, javaScore);
                students.add(student);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Loi: Khong the tim thay file input.txt");
            return;
        }

        // Sắp xếp học viên theo điểm trung bình giảm dần
        Collections.sort(students, new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.averageScore, s1.averageScore);
            }
        });

        // In ra và ghi vào file output.txt
        try {
            PrintWriter writer = new PrintWriter("output.txt");
            System.out.println("Danh sach hoc vien:");
            for (Student student : students) {
                System.out.println(student);
                writer.println(student);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Loi: Khong the tao hoac mo file output.txt");
            return;
        }

        // Nhập tên học viên cần tìm
        System.out.print("Nhap ten hoc vien can tim: ");
        String searchName = scanner.nextLine();

        // Tìm kiếm học viên theo tên
        boolean found = false;
        System.out.println("Thong tin hoc vien co ten \"" + searchName + "\":");
        for (Student student : students) {
            if (student.name.contains(searchName)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay hoc vien.");
        }

        // Hiển thị thông tin các học viên đã đậu và trượt
        System.out.println("Thong tin cac hoc vien da dau:");
        for (Student student : students) {
            if (student.result.equals("Dau")) {
                System.out.println(student);
            }
        }
        System.out.println("Thong tin cac hoc vien da truot:");
        for (Student student : students) {
            if (!student.result.equals("Dau")) {
                System.out.println(student);
            }
        }
    }
}
