package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    //Question findByContent(String content);
    Question findBySubjectAndContent(String subject, String content);
    //질문제목에 해당하는 문자열 있을경우 모두 가져옴
    List<Question> findBySubjectLike(String subject);
    // 페이징 리스트 (페이지, 사이즈 등이 입력된 pageable 객체 입력)
    Page<Question> findAll(Pageable pageable);
}
