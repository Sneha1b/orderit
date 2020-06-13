package com.learn.orderit.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public void setCreatedAt(Long timeInMillis) {
        this.createdAt = new Timestamp(timeInMillis);
    }

    public void setUpdatedAt(Long timeInMillis) {
        this.updatedAt = new Timestamp(timeInMillis);
    }

    public void setBirthDate(long timeMillis){
        birthDate = new Date(timeMillis);
    }
}
