import Annotation.AfterSuite;
import Annotation.BeforeSuite;
import Annotation.Test;

public class StepTest {

    @BeforeSuite
    static void start() {
        System.out.println("Выполнил перед всеми тестами");
    }


    @AfterSuite
    static void end() {
        System.out.println("Выполнил после всех тестов");
    }

    @Test(priority = 8)
    public void high() {
        System.out.println("Выполнил тест high");
    }

    @Test
    public void medium() {
        System.out.println("Выполнил тест medium");
    }

    @Test(priority = 2)
    public void low() {
        System.out.println("Выполнил тест low");
    }
}
