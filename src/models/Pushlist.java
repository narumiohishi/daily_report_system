package models;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Table(name = "pushlist")
@NamedQueries({
    @NamedQuery(
        name = "getAllPushlist",
        query = "SELECT p FROM Pushlist AS p ORDER BY p.id DESC"
    ),
    @NamedQuery(
        name = "getPushlistCount",
        query = "SELECT COUNT(p) FROM Report AS p"
    ),
    @NamedQuery(
        name = "getMyAllPushlist",
        query = "SELECT p FROM Pushlist AS p WHERE p.report = :report ORDER BY p.id DESC"
    ),
    @NamedQuery(
        name = "getMyPushlistCount",
        query = "SELECT COUNT(p) FROM Pushlist AS p WHERE p.report = :report"
    ),
    @NamedQuery(
            name = "getMyPushlist_pushCount",
            query = "SELECT COUNT(p) FROM Pushlist AS p WHERE p.employee = :employee and p.report = :report"
    ),

})
@Entity
public class Pushlist {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

  @ManyToOne
   @JoinColumn(name = "employee_id", nullable = false)
   private Employee employee;

 @ManyToOne
   @JoinColumn(name = "reports_id", nullable = false)
   private Report report;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}