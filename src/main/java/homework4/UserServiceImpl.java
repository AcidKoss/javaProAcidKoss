package homework4;

import jakarta.validation.ValidationException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username) {
        User user = new User();
        try {
            user.setUsername(username);
            userDao.create(user);
        } catch (ValidationException e){
            System.out.println("Ошибка валидации: " + e);
        }
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void renameUser(Long id, String newUsername) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(newUsername);
            userDao.update(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }



}