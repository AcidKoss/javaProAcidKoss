package homework5;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    public static final String PU = "demoPU"; // как в persistence.xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
    private JpaUtil() {}
    public static EntityManagerFactory emf() { return emf; }
    public static void close() { emf.close(); }
}