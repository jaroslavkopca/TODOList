package app.backend.repository;

import app.backend.model.TODOEntry;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TODOEntryDao extends BaseDao<TODOEntry> {


    public TODOEntryDao() {
        super(TODOEntry.class);
    }

    public List<TODOEntry> findByCompletionStatus(boolean completed) {
        try {
            return em.createQuery("SELECT t FROM TODOEntry t WHERE t.isCompleted = :status", TODOEntry.class)
                    .setParameter("status", completed)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

}

