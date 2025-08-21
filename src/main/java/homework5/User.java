package homework5;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "ux_users_login", columnList = "login", unique = true)
        }
)
public class User {

    public User (String login, String ferstName, String lastName){
        this.login = login;
        this.ferstName = ferstName;
        this.lastName = lastName;
    }
    public User(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 50)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String ferstName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 30)
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9_]+$")
    private String login;

    // Владелец связи — Note (поле author). Здесь обратная сторона.
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,       // при создании/удалении пользователя каскадно трогаем его заметки
            orphanRemoval = true,            // удаление из коллекции => DELETE у «сироты»
            fetch = FetchType.LAZY
    )


    private List<Note> notes = new ArrayList<>();

    // Утилитарные методы поддерживают «двустороннюю» связь в памяти.
    public void addNote(Note note) {
        notes.add(note);
        note.setAuthor(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setAuthor(null);
    }

    // --- getters/setters ---

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public List<Note> getNotes() { return notes; }

    public String getFerstName() {
        return ferstName;
    }

    public void setFerstName(String ferstName) {
        this.ferstName = ferstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // equals/hashCode только по id (когда он уже присвоен)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User other = (User) o;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() { return 31; }

//    @Override
//    public String toString() {
//        // Не логируем коллекции/LAZY-поля — чтобы не провоцировать LazyInitializationException
//        return "User{id = " + id + ", login = '" + login + "'}";
//    }


    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", ferstName = '" + ferstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", login = '" + login + '\'' +
                '}';
    }
}