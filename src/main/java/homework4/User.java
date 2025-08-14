package homework4;

import jakarta.validation.ValidationException;

public class User {
    private Long id;
    private String username;

    public User() {
    }

    public User(Long id, String username) {
        this.id = id;
        setUsername(username);
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username не может быть пустым");
        }
        if (username.length() < 3 || username.length() > 30) {
            throw new ValidationException("Username должен быть от 3 до 30 символов: " + username);
        }
        if (!username.matches("^[a-zA-Z0-9_ ]+$")) {
            throw new ValidationException("Username может содержать только буквы, цифры и подчёркивания: " + username);
        }
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}