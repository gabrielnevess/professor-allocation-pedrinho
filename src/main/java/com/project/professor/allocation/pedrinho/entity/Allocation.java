package com.project.professor.allocation.pedrinho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "startHour", nullable = false)
    private Time startHour;

    @Column(name = "endHour", nullable = false)
    private Time endHour;

    @ManyToOne(optional = false)
    private Professor professor;

    @ManyToOne(optional = false)
    private Course course;
}