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
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/login")
	public String login() {
		return "/user/login";
	}
	
	@PostMapping("/loginProc")
	public String loginProc(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			System.out.println("login fail1");
			return "/user/login";
		}
		
		if(!user.matchPassword(password)) {
			System.out.println("login fail3");
			return "/user/login";
		}
		
		System.out.println("login success");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		
		return "redirect:/";
	}
	
	@PostMapping("/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		//Object tempUser = session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		//if (tempUser == null) {
		if (HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/user/login";
		}
		
		//User sessionedUser = (User)tempUser;
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		//if (!id.equals(sessionedUser.getId())) {
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can update only your information!");
		}
		
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}/update")
	public String updateUser(@PathVariable Long id, User updatedUser, HttpSession session) {
		//Object tempUser = session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		//if (tempUser == null) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/user/login";
		}
		
		//User sessionedUser = (User)tempUser;
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		//if (!id.equals(sessionedUser.getId())) {
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can update only your information!");
		}
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
}
