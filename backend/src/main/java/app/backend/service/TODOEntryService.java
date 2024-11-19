package app.backend.service;

import app.backend.model.TODOEntry;
import app.backend.repository.TODOEntryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//@RequiredArgsConstructor
public class TODOEntryService {
    private final TODOEntryDao todoEntryDao;

    @Autowired
    public TODOEntryService(TODOEntryDao todoEntryDao) {
        this.todoEntryDao = todoEntryDao;
    }

    public List<TODOEntry> getAllEntries() {
        return todoEntryDao.findAll();
    }

    public TODOEntry getEntryById(Integer id) {
        return todoEntryDao.find(id);
    }

    public void addEntry(TODOEntry todoEntry) {
        todoEntryDao.persist(todoEntry);
    }

    public void updateEntry(TODOEntry todoEntry) {
        todoEntryDao.update(todoEntry);
    }

    public void removeEntry(TODOEntry entry) {
        todoEntryDao.remove(entry);
    }

    public List<TODOEntry> getEntriesByCompletionStatus(boolean isCompleted) {
        return todoEntryDao.findAll()
                .stream()
                .filter(entry -> entry.isCompleted() == isCompleted)
                .toList();
//        return todoEntryDao.findByCompletionStatus(isCompleted);
    }
}
