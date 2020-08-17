package ru.otus.project.masterPass.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Data
@ToString(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;
    @Column(name = "resource")
    private String resource;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "start_dt")
    private LocalDateTime startDt;
    @Column(name = "remark")
    private String remark;
    @Column(name = "initialization_vector")
    private String initializationVector;
}
