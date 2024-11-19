package app.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


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

    public TODOEntry(int id, String title, boolean isCompleted) {
        this.setId(id);
        this.title = title;
        this.isCompleted = isCompleted;
    }
}
