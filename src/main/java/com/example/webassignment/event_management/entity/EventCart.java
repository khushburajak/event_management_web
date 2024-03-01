package com.example.webassignment.event_management.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventsCarts")
public class EventCart {
    @Id
    @SequenceGenerator(name = "eventsCarts_seq_gen", sequenceName = "eventsCarts_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "eventsCarts_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_productId"))
    private Events events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_userId"))
    private User user;
}
