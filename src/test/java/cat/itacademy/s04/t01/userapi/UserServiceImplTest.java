package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.exception.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.exception.UserIdDoesNotExists;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import cat.itacademy.s04.t01.userapi.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        String email = "juan@mail.com";
        String name = "Juan Garcia";
        User user = new User(name, email);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(user));

        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void createUser_shouldGenerateAUUIDAndAddUserWhenEmailDoesNotExists() throws EmailAlreadyExistsException {
        String email = "pedro@gmail.com";
        String name = "Pedro Reyes";
        User user = new User(name, email);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(o -> o.getArgument(0));
        User result = userService.createUser(user);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(name, result.getName());
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(user);
    }

    @Test
    public void readAllUsers_shouldReturnAllUsers() {
        User firstUser = new User("Juan", "juan@gmail.com");
        User secondUser = new User("Pedro", "pedro@gmail.com");
        List<User> users = List.of(firstUser, secondUser);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.readAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    public void findByName_shouldReturnUsers() {
        String name = "juan";
        User firstUser = new User("Juan Garcia", "juan@gmail.com");
        User secondUser = new User("Juan Pedro", "juanpedro@gmail.com");
        List<User> users = List.of(firstUser, secondUser);
        when(userRepository.searchByName(name)).thenReturn(users);
        List<User> result = userService.findByName(name);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(users, result);
        verify(userRepository).searchByName(name);
    }

    @Test
    public void getUserId_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = new User("Pedro", "pedro@gmail.com");
        user.setId(id);
        when(userRepository.findById(id)).thenReturn((Optional.of(user)));
        User result = userService.getUserId(id);
        assertNotNull(result);
        assertEquals(user, result);
        assertEquals(id, result.getId());
        verify(userRepository).findById(id);
    }

    @Test
    void getUserId_shouldReturnException() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserIdDoesNotExists.class, () -> userService.getUserId(id));
        verify(userRepository).findById(id);
    }

    @Test
    void removeAllUsers_shouldCallRepositoryRemoveAll() {
        doNothing().when(userRepository).removeAll();
        userService.removeAllUsers();
        verify(userRepository).removeAll();
    }
}
