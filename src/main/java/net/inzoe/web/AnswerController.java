package net.inzoe.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.inzoe.domain.Answer;
import net.inzoe.domain.AnswerRepository;
import net.inzoe.domain.Question;
import net.inzoe.domain.QuestionRepository;
import net.inzoe.domain.User;

@Controller
@RequestMapping("/qna/{qid}/ans")
public class AnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	
	@PostMapping("/reply")
	public String replay(@PathVariable Long qid, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.getOne(qid);
		Answer newAnswer = new Answer(loginUser, question, contents);
		answerRepository.save(newAnswer);
		
		return "redirect:/qna/{qid}/show";
	}
	
	@PostMapping("/delete")
	public String delete(@PathVariable Long qid, String contents, HttpSession session) {
		return "redirect:/qna/{qid}/show";
	}
}
