package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.impl.NullsAsEmptyProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import model.Subject;
import model.Exam;


@Getter @Setter
public class ExamPlanner {

    public ObjectMapper om = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

    private final File subjectsFile=new File("src/main/resources/json/subjects.json");
    private final File examsFile=new File("src/main/resources/json/exams.json");
    private final File planFile=new File("src/main/resources/json/plan.json");
    private ArrayList<Subject> subjectsList = new ArrayList<>();
    private ArrayList<Exam> examsList = new ArrayList<>();
    private ArrayList<Exam> planList = new ArrayList<>();
    private LocalDate today=LocalDate.now();

    @SneakyThrows
    public ExamPlanner() {
            if(subjectsFile.length() != 0)
                subjectsList = om.readValue(subjectsFile, new TypeReference<ArrayList<Subject>>(){});
            if(examsFile.length() != 0)
                examsList = om.readValue(examsFile, new TypeReference<ArrayList<Exam>>(){});
            if(planFile.length() != 0)
                planList = om.readValue(subjectsFile, new TypeReference<ArrayList<Exam>>(){});

    }

    //DateTimeFormatter

    public Subject matchingSubject (int subjId) {
        Subject match = new Subject();
        for (Subject subject1 : subjectsList) {
            if (subject1.getId() == subjId) {
                match = subject1;
                break;
            }
        }
        return match;
    }
    @SneakyThrows
    public void addSubject (String name) {
        int nextId=(subjectsList.isEmpty() ? 1 : subjectsList.getLast().getId()+1);
        Subject newSubj=new Subject(nextId, name);
        subjectsList.add(newSubj);
        om.writeValue(subjectsFile, subjectsList);
    }

    @SneakyThrows
    public void removeSubject (int id) {
        for (Subject subject1 : subjectsList) {
            if (subject1.getId() == id) {
                subjectsList.remove(subject1);
                break;
            }
        }
        om.writeValue(subjectsFile, subjectsList);
    }

    @SneakyThrows
    public void addExam (int subjId, String dateString) {
        //dateString=yearStr+"-"+monthStr+"-"+dayStr;
        Exam e = new Exam(subjId, dateString);
        examsList.add(e);
        om.writeValue(examsFile, examsList);
        matchingSubject(subjId).setNumOfExams(matchingSubject(subjId).getNumOfExams()+1);
        om.writeValue(subjectsFile, subjectsList);
    }

    @SneakyThrows
    public void removeExam (Exam e) {
        matchingSubject(e.getSubjId()).setNumOfExams(matchingSubject(e.getSubjId()).getNumOfExams()-1);
        if(matchingSubject(e.getSubjId()).getNumOfExams() == 0) {
            removeSubject(e.getSubjId());
        }
        examsList.remove(e);
        om.writeValue(examsFile, examsList);

    }

    public void makePlan () {
        //TODO

    }

}
