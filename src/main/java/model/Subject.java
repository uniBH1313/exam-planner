package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Subject {
    private int id;
    private String name;
    private String type;
    private String difficulty;
    private int numOfExams;
    private boolean isInPlan;




    public Subject(int id, String name) {
        this.id=id;
        this.name = name;
        this.numOfExams = 0;
        this.isInPlan=false;
    }

    @Override
    public String toString() {
        return id+" "+name+" ("+numOfExams+"db)";
    }
}
