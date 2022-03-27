package com.example.logs.models;
import com.example.visitorslogs.dtos.VisitorLogsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VisitorLogs extends BaseModel{

    @Column(name = "date_of_visit", nullable = false)
    private Date dateOfVisit;

    @Column(name = "reason_for_visit", nullable = false)
    private String reasonForVisit;

    @ManyToOne
    @JoinColumn
    private Staff staff;

    @ManyToOne
    @JoinColumn
    private Visitor visitor;

    public static VisitorLogs from(VisitorLogsDto visitorLogsDto) {
        VisitorLogs visitorLogs = new VisitorLogs();
        visitorLogs.setReasonForVisit(visitorLogsDto.getReasonForVisit());
        Date date = Date.valueOf(visitorLogsDto.getDateOfVisit());
        visitorLogs.setDateOfVisit(date);

        return visitorLogs;
    }
}
