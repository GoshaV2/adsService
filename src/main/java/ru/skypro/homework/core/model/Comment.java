package ru.skypro.homework.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "crated_date", nullable = false)
    private LocalDateTime createdDate;
    @JoinColumn(name = "ad_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ad ad;
    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private User author;
}
