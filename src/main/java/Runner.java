import Annotation.AfterSuite;
import Annotation.BeforeSuite;
import Annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Runner {
    public static void runTests(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method before = null, after = null;
        List<Method> tests = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(BeforeSuite.class)) before = m;
            else if (m.isAnnotationPresent(AfterSuite.class)) after = m;
            else if (m.getAnnotation(Test.class).priority() < 1 || m.getAnnotation(Test.class).priority() > 10)
                throw new IllegalArgumentException("Некорректный приоритет в аннотации Test, должено быть от 1 до 10, :а указано " + m.getAnnotation(Test.class).priority() + " у класса  " + m.getName());
            else if (m.isAnnotationPresent(Test.class)) tests.add(m);
        }
        tests.sort(Comparator
                .comparingInt((Method m) -> m.getAnnotation(Test.class).priority()));

        if (before != null) before.invoke(null);
        for (Method m : tests) m.invoke(instance);
        if (after != null) after.invoke(null);
    }
}
