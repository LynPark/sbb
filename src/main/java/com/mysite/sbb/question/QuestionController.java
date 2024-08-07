package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private UserService uService;

    @RequestMapping("/list")
    public String questionList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        /*List<Question> qList = qService.getAllQuestions();
        model.addAttribute("qList", qList);*/
        Page<Question> paging = this.qService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    //url 주소에 /{변수} => PathVariable 주소변수
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id,
                         AnswerForm answerForm){
        Question q = qService.getQuestionById(id);
        model.addAttribute("question", q);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm,
                         BindingResult result, Principal principal){
        if(result.hasErrors()){
            return "question_form"; //되돌아감
        }
        SiteUser siteUser = uService.getUser(principal.getName());
        //질문 저장하기
        qService.createQuestion(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(QuestionForm questionForm, @PathVariable("id") int id, Principal principal){
        Question question = qService.getQuestionById(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm, BindingResult result,
                         @PathVariable("id") int id, Principal principal){
        if (result.hasErrors()) {
            return "question_form";
        }
        Question question = qService.getQuestionById(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        qService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%d",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") int id) {
        Question question = qService.getQuestionById(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        qService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") int id) {
        Question question = qService.getQuestionById(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        qService.vote(question,siteUser);
        return String.format("redirect:/question/detail/%d",id);
    }
}
