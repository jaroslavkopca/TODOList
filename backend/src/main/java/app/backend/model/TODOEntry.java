package app.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TODO_ENTRY")
@Data
@NoArgsConstructor
public class TODOEntry extends AbstractEntity {
    @Basic(optional = false)
    private String title;

    @Basic(optional = false)
    private boolean isCompleted = false;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public TODOEntry(int id, String title, boolean isCompleted) {
        this.setId(id);
        this.title = title;
        this.isCompleted = isCompleted;
    }
}
