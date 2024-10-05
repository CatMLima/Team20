package is.hi.hbv501g.team20.Persistence.Entities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class Subject {
    private String subjectID;
    private String subjectNafn;
    private String subjectName;

    public Subject() {}

    public Subject(String subjectID, String subjectNafn, String subjectName) {
        this.subjectID = subjectID;
        this.subjectNafn = subjectNafn;
        this.subjectName = subjectName;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public String getSubjectNafn() {
        return subjectNafn;
    }

    public String getSubjectName() {
        return subjectName;
    }

    // Static method to read CSV file and create subjects
    public static List<Subject> createSubjectsFromCSV(String filePath) throws IOException {
        filePath = "src/main/resources/subjects.csv";
        List<Subject> subjects = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("subjectID", "subjectNafn", "subjectName").parse(reader);
            for (CSVRecord record : records) {
                String subjectID = record.get("subjectID");
                String subjectNafn = record.get("subjectNafn");
                String subjectName = record.get("subjectName");

                subjects.add(new Subject(subjectID, subjectNafn, subjectName));
            }
        }

        return subjects;
    }
}