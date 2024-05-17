package de.codecentric.springbootapispecvalidationrestassured.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UsersController(UserService userService) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        var user = this.userService.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.userMapper.toDto(user));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDTO user) {
        if (this.userService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        var updatedUser = this.userService.update(this.userMapper.toEntity(user));
        return ResponseEntity.ok(updatedUser);
    }
}
