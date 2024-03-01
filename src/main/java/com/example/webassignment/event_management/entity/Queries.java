package com.example.webassignment.event_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "queries")
public class Queries {
    @Id
    @SequenceGenerator(name = "queries_seq_gen", sequenceName = "queries_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "queries_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "E-mail", nullable = false)
    private String email;
    @Column(name = "Subject", nullable = false)
    private String subject;
    @Column(name = "Message", nullable = false)
    private String message;

}
