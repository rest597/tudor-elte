package hu.elte.f40b2i.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findQuestionByCustomer(Customer customer);
    List<Question> findQuestionBySpecialization(String specialization);
}
