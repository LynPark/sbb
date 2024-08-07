package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private AnswerService aService;
    @Autowired
    private UserService uService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") int id, Model model,
                               @Valid AnswerForm answerForm, BindingResult result,
                               Principal principal) { //principal : security의 인증된 정보가 담겨 있는 객체
        Question question = qService.getQuestionById(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        if(result.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail"; //질문상세페이지로 돌아감
        }
        //답변저장하기
        aService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") int id, Principal principal) {
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") int id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        aService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%d", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") int id) {
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        aService.delete(answer);
        return String.format("redirect:/question/detail/%d", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") int id) {
        Answer answer = aService.getAnswer(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        aService.vote(answer,siteUser);
        return String.format("redirect:/question/detail/%d", answer.getQuestion().getId());
    }
}
