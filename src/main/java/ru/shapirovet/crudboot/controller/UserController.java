package ru.shapirovet.crudboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shapirovet.crudboot.model.User;
import ru.shapirovet.crudboot.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
    public String loginPage() {
        return "login3";
    }

	@GetMapping("/user")
	public String showUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.getUserByLogin(username);
		model.addAttribute("user", user);

		boolean isUser = user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_USER"));
		boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"));
		model.addAttribute("isUser", isUser);
		model.addAttribute("isAdmin", isAdmin);

		return "user3";
	}

}