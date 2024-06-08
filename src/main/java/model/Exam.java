package model;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Exam implements Comparable<Exam> {
    private int subjId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;


    public Exam(int subjId, String dateString) {
        this.subjId = subjId;
        setDate(dateString);
    }

    public Exam(int subjId, LocalDate date) {
        this.subjId = subjId;
        this.date = date;
    }

    public Exam() {
    }

    public int getSubjId() {
        return subjId;
    }

    public void setSubjId(int subjId) {
        this.subjId = subjId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String dateString) {
        this.date = LocalDate.parse(dateString);
    }


    @Override
    public int compareTo(Exam o) {
        return this.date.compareTo(o.getDate());
    }
}
