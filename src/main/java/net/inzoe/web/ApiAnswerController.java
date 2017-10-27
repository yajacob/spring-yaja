package net.inzoe.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.inzoe.domain.Answer;
import net.inzoe.domain.AnswerRepository;
import net.inzoe.domain.Question;
import net.inzoe.domain.QuestionRepository;
import net.inzoe.domain.User;

@RestController
@RequestMapping("/api/qna/{qid}/ans")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("/reply")
	public Answer create(@PathVariable Long qid, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(qid);
		Answer answer = new Answer(loginUser, question, contents);
		
		return answerRepository.save(answer);
	}
	
/*	@DeleteMapping("/{id}/delete")
	public Result delete(@PathVariable Long qid, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("You need to login!");
		}
	
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("You can delete only your articles!");
		}
		
		answerRepository.delete(id);
		return Result.ok();

	}*/
}
