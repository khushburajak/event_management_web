package com.example.webassignment.event_management.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Events {
    @Id
    @SequenceGenerator(name = "events_seq_gen", sequenceName = "events_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "events_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String photo;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "quantity", nullable = false)
    private String quantity;
    @Column(name = "price", nullable = false)
    private String price;
    @Transient
    private String imageBase64;
}
