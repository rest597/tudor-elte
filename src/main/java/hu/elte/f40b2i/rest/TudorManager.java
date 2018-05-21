package hu.elte.f40b2i.rest;

import hu.elte.f40b2i.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tudor")
@Transactional
public class TudorManager {

    private static Logger log = LoggerFactory.getLogger(AdminManager.class);

    @Autowired
    private AnswerRepository answerDao;

    @Autowired
    private QuestionRepository questionDao;

    @Autowired
    private TudorRepository tudorDao;

    @GetMapping("/getMySpec")
    public ResponseEntity<String> getMySpecHandler(Authentication a) {
        Tudor me = this.tudorDao.getOne(((AuthUserPrincipal)a.getPrincipal()).getId());

        return new ResponseEntity<>(me.getSpecialization(), HttpStatus.OK);
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Question.ReturnObject>> getAllQuestionsHandler(Authentication a) {
        Tudor me = this.tudorDao.getOne(((AuthUserPrincipal)a.getPrincipal()).getId());

        List<Question> resultQL = this.questionDao.findQuestionBySpecialization(me.getSpecialization());
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

    @GetMapping("/getAllAnswersForQuestion/{questionId}")
    public ResponseEntity<List<Answer.ReturnObject>> getAllAnswersForQuestionHandler(
            @PathVariable("questionId") Integer questionId) {

        Question question = this.questionDao.getOne(questionId);

        List<Answer> resultAL = this.answerDao.findAnswerByQuestion(question);
        List<Answer.ReturnObject> result = resultAL
                .stream().map(a -> a.createReturnObject())
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/newAnswer/{questionId}")
    public ResponseEntity<IdMessageObject> newAnswerHandler(@PathVariable("questionId") Integer questionId,
                                                     @RequestBody(required=true) Answer answer,
                                                            Authentication a) {

        // Set question
        Question question = this.questionDao.getOne(questionId);
        answer.setQuestion(question);

        // Set tudor
        Tudor me = this.tudorDao.getOne(((AuthUserPrincipal)a.getPrincipal()).getId());
        answer.setTudor(me);

        // Set date
        answer.setDate(new Date());

        // Set rating
        answer.setRating(0);

        // Set archived
        answer.setArchived(false);

        String resMsg = "Answer saved";
        Integer resId = this.answerDao.save(answer).getAnswerId();

        return new ResponseEntity<>(new IdMessageObject(resId, resMsg),HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAnswer/{answerId}")
    public ResponseEntity<String> deleteAnswerHandler(@PathVariable("answerId") Integer answerId) {
        this.answerDao.deleteById(answerId);
        return new ResponseEntity<>("Answer deleted",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/archiveAnswer/{answerId}")
    public ResponseEntity<String> archiveAnswerHandler(@PathVariable("answerId") Integer answerId) {
        Answer result = this.answerDao.getOne(answerId);
        result.setArchived(true);
        this.answerDao.save(result);
        return new ResponseEntity<>("Answer archived",HttpStatus.OK);
    }

    @PostMapping("/unarchiveAnswer/{answerId}")
    public ResponseEntity<String> unarchiveAnswerHandler(@PathVariable("answerId") Integer answerId) {
        Answer result = this.answerDao.getOne(answerId);
        result.setArchived(false);
        this.answerDao.save(result);
        return new ResponseEntity<>("Answer unarchived",HttpStatus.OK);
    }

}