package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    public List<Question> getAllQuestions() {
        return qRepo.findAll();
    }

    public Question getQuestionById(int id) {
        Optional<Question> q = qRepo.findById(id);
        if (q.isPresent()) {
            return q.get();
        } else {
            //id에 해당하는 질문을 못찾을 경우 에러를 발생하며 메세지 표시 , 404 상태코드
            throw new DataNotFoundException("question not found");
        }
    }

    public void createQuestion(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        qRepo.save(q);
    }

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
        return this.qRepo.findAll(pageable);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
    }

    public void delete(Question question) {
        qRepo.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        qRepo.save(question);
    }
}
