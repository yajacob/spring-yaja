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
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if (!question.isSameWriter(loginUser)) {
			System.out.println("not same writer");
			return "redirect:/";
		}

		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PostMapping("/updateFormProc")
	public String updateFormProc(Long id, String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		// no data found
		if (question == null) {
			return "redirect:/";
		}

		if (!question.isSameWriter(loginUser)) {
			return "redirect:/";
		}

		question.update(title, contents);
		questionRepository.save(question);

		return String.format("redirect:/qna/%d/show", id);
	}

	@PostMapping("/delete")
	public String deleteProc(Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		// no data found
		if (question == null) {
			return "redirect:/";
		}

		if (!question.isSameWriter(loginUser)) {
			return "redirect:/";
		}

		questionRepository.delete(id);

		return "redirect:/";
	}

}
