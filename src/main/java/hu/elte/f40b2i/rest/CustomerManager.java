package hu.elte.f40b2i.rest;

import hu.elte.f40b2i.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customer")
@Transactional
public class CustomerManager {

    private static Logger log = LoggerFactory.getLogger(AdminManager.class);

    @Autowired
    private QuestionRepository questionDao;

    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private AnswerRepository answerDao;


    @PostMapping("/newQuestion")
    public ResponseEntity<IdMessageObject> newQuestionHandler(@RequestBody(required=false) Question question) {

        // Set customer
        Customer me = this.customerDao.getOne(10);
        question.setCustomer(me);

        // Set date
        question.setDate(new Date());

        // Set archived
        question.setArchived(false);

        String resMsg = "Question saved";
        Integer resId = this.questionDao.save(question).getQuestionId();

        return new ResponseEntity<>(new IdMessageObject(resId, resMsg),HttpStatus.CREATED);
    }

    @GetMapping("/getMyQuestions")
    public ResponseEntity<List<Question.ReturnObject>> getMyQuestionsHandler() {
        Customer me = this.customerDao.getOne(10);

        List<Question> resultQL = this.questionDao.findQuestionByCustomer(me);
        List<Question.ReturnObject> result = resultQL
                .stream().map(q -> q.createReturnObject())
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getQuestion/{questionId}")
    public ResponseEntity<Question.ReturnObject> getQuestionHandler(@PathVariable("questionId") Integer questionId) {
        Question res = this.questionDao.getOne(questionId);

        return new ResponseEntity<>(res.createReturnObject(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteQuestion/{questionId}")
    public ResponseEntity<String> deleteQuestionHandler(@PathVariable("questionId") Integer questionId) {
        this.questionDao.deleteById(questionId);
        return new ResponseEntity<>("Question deleted",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/archiveQuestion/{questionId}")
    public ResponseEntity<String> archiveQuestionHandler(@PathVariable("questionId") Integer questionId) {
        Question result = this.questionDao.getOne(questionId);
        result.setArchived(true);
        this.questionDao.save(result);
        return new ResponseEntity<>("Question archived",HttpStatus.OK);
    }

    @PostMapping("/unarchiveQuestion/{questionId}")
    public ResponseEntity<String> unarchiveQuestionHandler(@PathVariable("questionId") Integer questionId) {
        Question result = this.questionDao.getOne(questionId);
        result.setArchived(false);
        this.questionDao.save(result);
        return new ResponseEntity<>("Question unarchived",HttpStatus.OK);
    }

    @PostMapping("/rateAnswer/{answerId}/{rating}")
    public ResponseEntity<String> rateAnswerHandler(@PathVariable("answerId") Integer answerId,
                                                    @PathVariable("rating") Integer rating) {
        Answer result = this.answerDao.getOne(answerId);
        result.setRating(rating);
        this.answerDao.save(result);
        return new ResponseEntity<>("Answer rated",HttpStatus.CREATED);    }

}
