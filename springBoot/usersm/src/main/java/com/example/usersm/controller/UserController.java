package com.example.usersm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usersm.Dto.UserRequestDto;
import com.example.usersm.Dto.UserResponceDto;
import com.example.usersm.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserResponceDto> createUser(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("/get-user")
    public ResponseEntity<List<UserResponceDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get-byid/{id}")
    public ResponseEntity<UserResponceDto> getUserByid(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByid(id));
    }

    @DeleteMapping("/delete-byid/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Sucessfully!!");
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserResponceDto> updateUser(@RequestBody UserRequestDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(userService.UpadteUser(dto, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponceDto> UpdateUserByPatch(@PathVariable Long id,
            @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserByPatch(id, fields));
    }
}
