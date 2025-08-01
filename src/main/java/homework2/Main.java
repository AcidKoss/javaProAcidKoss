package homework2;

import homework1.Runner;
import homework1.StepTest;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println("Задание № 1");

        List<Integer> numbers = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);

        List<Integer> res = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println("3-е наибольшее число: " + res.get(2));

        System.out.println("Задание № 2");

        res = numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println("3-е наибольшее число без повторов: " + res.get(2));

        System.out.println("Задание № 3");

        List<Employee> people = List.of(
                new Employee("Иван", 23, "Инженер"),
                new Employee("Олег", 40, "Рабочий"),
                new Employee("Ирина", 31, "Руководитель"),
                new Employee("Маша", 18, "Инженер"),
                new Employee("Евгений", 43, "глав бух"),
                new Employee("Роман", 26, "Инженер"),
                new Employee("Юра", 20, "Девопс"),
                new Employee("Вика", 23, "Инженер")
        );


        List<String> resEmp = people.stream()
                .filter(emp -> emp.title().equals("Инженер"))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .toList();

        System.out.println("список имён трёх самых старших сотрудников с должностью Инженер: " + resEmp);

        System.out.println("Задание № 4");

        double resEmpAge = people.stream()
                .mapToInt(Employee::age)
                .average()
                .orElse(0);


        System.out.println("средний возраст сотрудников с должностью Инженер: " + resEmpAge);

        System.out.println("Задание № 5");

        List<String> words = List.of("сервер", "клиент", "база", "интерфейс", "протокол", "алгоритм", "облако", "API", "сеть", "файрвол", "виртуализация", "контейнер", "скрипт", "отладка", "фреймворк", "компилятор", "репозиторий", "сертификация", "интеграция", "драйвер");

        String longestWord = words.stream()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .findFirst()
                .orElse("");

        System.out.println("слово с максимально длинной: " + longestWord);

        System.out.println("Задание № 6");

        String lineSpace = "сервер клиент база сервер интерфейс протокол клиент алгоритм облако API сеть протокол файрвол виртуализация контейнер скрипт отладка фреймворк компилятор репозиторий сервер интеграция драйвер API";

        Map <String,Long> result = Arrays.stream(lineSpace.split(" "))
                .collect(Collectors.groupingBy(String::toString,Collectors.counting()));


        System.out.println("сколько раз каждое слово встречается: " + result);
    }
}
