package net.inzoe.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.inzoe.domain.User;
import net.inzoe.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form() {
		return "/users/form";
	}

	@GetMapping("/login")
	public String login() {
		return "/users/login";
	}
	
	@PostMapping("/loginProc")
	public String loginProc(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			return "redirect:/users/login";
		}
		
		if (!password.equals(user.getPassword())) {
			return "redirect:/users/login";
		}
		
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		
		return "redirect:/";
	}
	
	@PostMapping("/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/users/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/login";
		}
		
		User sessionedUser = (User)tempUser;
		if (!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can update only your information!");
		}
		
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/users/updateForm";
	}
	
	@PutMapping("/{id}/update")
	public String updateUser(@PathVariable Long id, User updatedUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/login";
		}
		
		User sessionedUser = (User)tempUser;
		if (!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can update only your information!");
		}
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users/list";
	}
	
}
