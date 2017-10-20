package net.inzoe.web;

import java.util.ArrayList;
import java.util.List;

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
	private List <User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("/create")
	public String create(User user) {
		//users.add(user);
		System.out.println("user:"+user.toString());
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		User user = userRepository.findOne(id);
		System.out.println(user.toString());
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}/update")
	public String updateUser(@PathVariable Long id, User updateUser) {
		User user = userRepository.findOne(id);
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
}
