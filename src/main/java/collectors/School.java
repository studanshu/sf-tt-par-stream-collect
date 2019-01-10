package collectors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class School {
    public static String letterGrade(Student s) {
        double gpa = s.getGpa();
        if (gpa > 3.6) return "A";
        if (gpa > 3.2) return "B";
        if (gpa > 3.0) return "C";
        if (gpa > 2.5) return "D";
        return "E";
    }


    public static void main(String[] args) {
        List<Student> roster = Arrays.asList(
                Student.ofNameGpaCourses("Fred", 2.2, "Math", "Physics"),
                Student.ofNameGpaCourses("Jim", 3.2, "Art"),
                Student.ofNameGpaCourses("Jim2", 3.3, "Art"),
                Student.ofNameGpaCourses("Jim3", 3.8, "Art"),
                Student.ofNameGpaCourses("Sheila", 3.7, "Math", "Physics", "Quantum Mechanics")
        );

        Map<String, List<Student>> results =
                roster.stream()
//                .collect(Collectors.groupingBy(s -> School.letterGrade(s)))
                        .collect(Collectors.groupingBy(School::letterGrade));

        results.forEach((k, v) -> System.out.println("Students with grade " + k + " are: " + v));

        roster.stream()
                .collect(Collectors.groupingBy(School::letterGrade, Collectors.counting()))
                .forEach((k, v) -> System.out.println(v + " students have grade " + k));

        roster.stream()
                .collect(Collectors.groupingBy(School::letterGrade,
                        Collectors.mapping(s -> s.getName() + ": " + s.getGpa(), Collectors.toList())))
                .forEach((k, v) -> System.out.println("Students with grade " + k + ": " + v));

        roster.stream()
                .collect(Collectors.groupingBy(School::letterGrade,
                        Collectors.mapping(s -> s.getName(), Collectors.joining(", "))))
                .forEach((k, v) -> System.out.println("Students with grade " + k + ": " + v));


    }
}
