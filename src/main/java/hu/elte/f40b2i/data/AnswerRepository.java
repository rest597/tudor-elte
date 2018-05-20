package hu.elte.f40b2i.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findAnswerByQuestion(Question question);
    List<Answer> findAnswerByTudor(Tudor tudor);
}