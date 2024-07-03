package com.dhkim.blog.post.Domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String writer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        modifiedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }
}
