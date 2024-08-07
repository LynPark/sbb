package com.mysite.sbb.answer;

import com.mysite.sbb.question.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository aRepo;

    public void create(Question question, String content, SiteUser author) {
       //답변의 부모가 질문이기 때문에 질문 필요
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question); //질문 입력
        answer.setAuthor(author); //글쓴이 추가
        aRepo.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = aRepo.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        aRepo.save(answer);
    }

    public void delete(Answer answer) {
        aRepo.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        aRepo.save(answer);
    }
}
