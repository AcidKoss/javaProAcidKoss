package homework5;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;

public class DemoMain {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.emf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            User user = new User("user1","Иван", "Свистоплясов");
            Note note1 = new Note("Первая заметка");
            user.addNote(note1);
            user.addNote(new Note("Вторая заметка"));
            em.persist(user);

            User user1 = new User("user2","Тест", "Тестович");
            Note note2 = new Note("Первая заметка второго");
            user1.addNote(note2);
            user1.addNote(new Note("Вторая заметка второго"));
            em.persist(user1);

            em.flush();
            tx.commit();
            em.clear();

            tx.begin();
            User loaded = em.createQuery(
                            "select u from User u where u.login = :un", User.class)
                    .setParameter("un", "user1")
                    .getSingleResult();

            System.out.println("Loaded user = " + loaded);
            int notesCount = loaded.getNotes().size();
            System.out.println("Notes count = " + notesCount);
            List<Note> allNote= loaded.getNotes();
            System.out.println("Автор: " + loaded + " Статьи: " + allNote);

            //Удаление одной статьи
            loaded.removeNote(allNote.get(1));
            em.flush();
            tx.commit();
            em.clear();

            //Проверка сколько и какие статьи у пользователя
            tx.begin();
            loaded = em.createQuery(
                            "select u from User u where u.login = :un", User.class)
                    .setParameter("un", "user1")
                    .getSingleResult();

            System.out.println("Loaded user = " + loaded);
            notesCount = loaded.getNotes().size();
            System.out.println("Notes count = " + notesCount);
            allNote= loaded.getNotes();
            System.out.println("Автор: " + loaded + " Статьи: " + allNote);

            //Удаление автора
            System.out.println("Удаляем автора");
            em.remove(loaded);
            em.flush();
            tx.commit();
            em.clear();

            tx.begin();
            System.out.println("Ищем автора которого нет");
            String autor = "user1";
            try {

                loaded = em.createQuery(
                                "select u from User u where u.login = :un", User.class)
                        .setParameter("un", autor)
                        .getSingleResult();
            } catch (NoResultException e){
                System.out.println("Автора " + autor + " нет в БД " + e);
            }


        } finally {
            em.close();
            JpaUtil.close();
        }
    }
}