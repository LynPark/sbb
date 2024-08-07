package com.mysite.sbb;

import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository qRepo;

    @Autowired
    private AnswerRepository aRepo;

    @Autowired
    private QuestionService questionService;

    @Test
    void testJpa() {
        /*Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now()); //지금날짜 입력
        qRepo.save(q1); //첫번째 질문을 DB에 저장
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now()); //지금날짜 입력
        qRepo.save(q2);

        DB에 질문이 2개면 맞음
        List<Question> questions = qRepo.findAll();
        assertEquals(2, questions.size());

        질문 id로 가져와서 질문 제목을 비교 맞으면 합격
        Question question = questions.get(0);
        assertEquals("sbb가 무엇인가요?", question.getSubject());

        JPA 객체 하나를 가져올때 옵셔널 타입 (null 일때 에러 발생가능)
        Optional<Question> oq = qRepo.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get(); //항상 참일때 가져오므로 에러발생 없음.
            System.out.println(q.getContent());
        }

        Question q = qRepo.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());

        Question q = qRepo.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());

        List<Question> qList = qRepo.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals(q.getSubject(), "sbb가 무엇인가요?");

        Optional<Question> q = qRepo.findById(1);
        assertTrue(q.isPresent()); //참이면 합격
        Question q1 = q.get();
        q1.setSubject("수정된 제목");
        qRepo.save(q1); //제목이 수정됨

        Optional<Question> q = qRepo.findById(2);
        assertTrue(q.isPresent()); //참이면 합격
        Question q2 = q.get();
        qRepo.delete(q2);
        assertEquals(1, qRepo.count());

        for (int i = 1; i < 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용없음";
            questionService.createQuestion(subject, content, null);
        }*/
    }

    /*@Test
    void testAnswer(){
        //답변은 질문에 해당답변이므로 질문 참조를 위해 질문먼저 가져옴
        Optional<Question> q = qRepo.findById(1);
        assertTrue(q.isPresent());
        Question q1 = q.get();

        Answer a = new Answer();
        a.setContent("sbb는 질문 답변 게시판 입니다.");
        a.setQuestion(q1); //이 답변은 q1 질문의 답변이 됨
        a.setCreateDate(LocalDateTime.now());
        aRepo.save(a);
    }*/
}