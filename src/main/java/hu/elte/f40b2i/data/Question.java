package hu.elte.f40b2i.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="question")
//@NamedQuery(name="Question.getQuestionBySpec", query="SELECT * from question q WHERE q.specialization=\":spec\"")
public class Question {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @NotNull
    private String title;

    private Date date;

    private String body;

    private String specialization;

    @JsonIgnore
    private Boolean archived;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Customer customer;



    public int getQuestionId() { return this.questionId; }
    public void setQuestionId(int id) { this.questionId = id; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public Date getDate() { return this.date; }
    public void setDate(Date date) { this.date = date; }

    public String getBody() { return this.body; }
    public void setBody(String body) { this.body = body; }

    public String getSpecialization() { return this.specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Boolean getArchived() { return this.archived; }
    public void setArchived(Boolean archived) { this.archived = archived; }

    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    // Return object
    public ReturnObject createReturnObject(){
        return new ReturnObject(this.getQuestionId(),
                this.getTitle(),
                this.getDate(),
                this.getBody(),
                this.getSpecialization(),
                this.getArchived());
    }

    public class ReturnObject{
        public Integer questionId;
        public String title;
        public Date date;
        public String body;
        public String specialization;
        public Boolean archived;

        ReturnObject(Integer questionId,
                     String title,
                     Date date,
                     String body,
                     String specialization,
                     Boolean archived){
            this.questionId = questionId;
            this.title = title;
            this.date = date;
            this.body = body;
            this.specialization = specialization;
            this.archived = archived;
        }
    }
}
