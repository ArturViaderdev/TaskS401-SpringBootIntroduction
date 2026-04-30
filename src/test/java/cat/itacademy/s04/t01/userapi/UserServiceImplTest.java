package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.exception.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import cat.itacademy.s04.t01.userapi.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    // Creem una instància real de la classe a provar (UserServiceImpl).
    // Els mocks definits a dalt s’injectaran aquí automàticament.

    @InjectMocks
    private UserServiceImpl userService;


    // ERROR PATH
    // Volem comprovar què passa quan l’email ja existeix.
    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
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
    void createUser_shouldGenerateAUUIDAndAddUserWhenEmailDoesNotExists() throws EmailAlreadyExistsException {
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
}
