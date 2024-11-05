package com.hhplus.ecommerce.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private Integer points;
    private LocalDateTime updatedAt;

    public void addPoints(Integer points) {
        this.points += points;
        this.updatedAt = LocalDateTime.now();
    }

    public void usePoints(Integer points) {
        this.points -= points;
        this.updatedAt = LocalDateTime.now();
    }

    public User(Long userId, String name, Integer points, LocalDateTime updatedAt) {
        this.userId = userId;
        this.name = name;
        this.points = points;
        this.updatedAt = updatedAt;
    }
}
