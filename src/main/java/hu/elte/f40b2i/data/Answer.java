package hu.elte.f40b2i.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="answer")
public class Answer {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;

    @NotNull
    private String title;

    private Date date;

    private String body;

    @Min(0)
    @Max(5)
    @JsonIgnore
    private int rating;

    @JsonIgnore
    private Boolean archived;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Tudor tudor;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;


    public int getAnswerId() { return this.answerId; }
    public void setAnswerId(int id) { this.answerId = id; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public Date getDate() { return this.date; }
    public void setDate(Date date) { this.date = date; }

    public String getBody() { return this.body; }
    public void setBody(String body) { this.body = body; }

    public int getRating() { return this.rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Boolean getArchived() { return this.archived; }
    public void setArchived(Boolean archived) { this.archived = archived; }

    public Question getQuestion() { return this.question; }
    public void setQuestion(Question question) { this.question = question; }

    public Tudor getTudor() { return this.tudor; }
    public void setTudor(Tudor tudor) { this.tudor = tudor; }

    // Return object
    public ReturnObject createReturnObject(){
        return new ReturnObject(this.getAnswerId(),
                this.getTitle(),
                this.getDate(),
                this.getBody(),
                this.getRating(),
                this.getArchived());
    }

    public class ReturnObject{
        public Integer answerId;
        public String title;
        public Date date;
        public String body;
        public Integer rating;
        public Boolean archived;

        ReturnObject(Integer answerId,
                     String title,
                     Date date,
                     String body,
                     Integer rating,
                     Boolean archived){
            this.answerId = answerId;
            this.title = title;
            this.date = date;
            this.body = body;
            this.rating = rating;
            this.archived = archived;
        }
    }

}
