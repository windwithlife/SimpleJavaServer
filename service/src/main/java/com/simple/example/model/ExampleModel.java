package com.simple.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="example")
public class ExampleModel {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(columnDefinition = "varchar(250)")
    private String id;
    private String name;
    private String email;
    private String message;
    private boolean confirmedAndActive;
    private boolean support;
    private String phoneNumber;
    private String photoUrl;
    private Date   updatedDate;
}
