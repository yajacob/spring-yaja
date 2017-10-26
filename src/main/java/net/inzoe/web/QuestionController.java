package net.inzoe.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.inzoe.domain.Answer;
import net.inzoe.domain.AnswerRepository;
import net.inzoe.domain.Question;
import net.inzoe.domain.QuestionRepository;
import net.inzoe.domain.User;

@Controller
@RequestMapping("/qna")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";
		}
		return "/qna/form";
	}

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("You need to login!");
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("You can only deal with articles you write!");
		}
		return true;
	}

	@PostMapping("/formProc")
	public String formProc(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionedUser, title, contents);
		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	@GetMapping("/{id}/show")
	public String show(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);

		return "/qna/show";
	}

	@GetMapping("/{id}/updateForm")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/users/login";
		}
	}

	@PostMapping("/updateFormProc")
	public String updateFormProc(Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/qna/%d/show", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/users/login";
		}
	}

	@PostMapping("/delete")
	public String deleteProc(Long id, HttpSession session, Model model) {
		try {
			User loginUser = HttpSessionUtils.getUserFromSession(session);
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			questionRepository.delete(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/users/login";
		}
	}

}
