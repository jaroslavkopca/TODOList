package app.backend.rest;

import app.backend.model.TODOEntry;
import app.backend.service.TODOEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TODOEntryController {

    private final TODOEntryService todoEntryService;

    public TODOEntryController(TODOEntryService todoEntryService) {
        this.todoEntryService = todoEntryService;
    }

    // Endpoint to get all TODOEntries
    @GetMapping("/getAllTODOEntries")
    public List<TODOEntry> getAllTODOEntries() {
        return todoEntryService.getAllEntries();
    }

    // Endpoint to get a TODOEntry by ID
    @GetMapping("/getTODOEntryById/{id}")
    public ResponseEntity<TODOEntry> getTODOEntryById(@PathVariable Integer id) {
        TODOEntry entry = todoEntryService.getEntryById(id);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    // Endpoint to create a new TODOEntry
    @PostMapping("/createTODOEntry")
    public ResponseEntity<TODOEntry> createTODOEntry(@RequestBody TODOEntry todoEntry) {
        todoEntryService.addEntry(todoEntry);
        return new ResponseEntity<>(todoEntry, HttpStatus.CREATED);
    }

    // Endpoint to update an existing TODOEntry
//    @PutMapping("/updateTODOEntry")
//    public ResponseEntity<TODOEntry> updateTODOEntry(@RequestBody TODOEntry todoEntry) {
//        TODOEntry existingEntry = todoEntryService.getEntryById(todoEntry.getId());
//        if (existingEntry == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        todoEntryService.updateEntry(todoEntry);
//        return new ResponseEntity<>(todoEntry, HttpStatus.OK);
//    }

    @PutMapping("/updateTODOEntry")
    public ResponseEntity<TODOEntry> updateTODOEntry(@RequestBody TODOEntry todoEntry) {
        TODOEntry existingEntry = todoEntryService.getEntryById(todoEntry.getId());

        if (existingEntry == null) {
            return ResponseEntity.notFound().build();
        }

        // Update fields only if they are provided in the request body
        Optional.ofNullable(todoEntry.getTitle()).ifPresent(existingEntry::setTitle);
        existingEntry.setCompleted(todoEntry.isCompleted());


        // Persist the updated entry
        todoEntryService.updateEntry(existingEntry);

        return ResponseEntity.ok(existingEntry);
    }




    // Endpoint to delete a TODOEntry by ID
    @DeleteMapping("/deleteTODOEntry/{id}")
    public ResponseEntity<Void> deleteTODOEntry(@PathVariable Integer id) {
        TODOEntry entry = todoEntryService.getEntryById(id);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        todoEntryService.removeEntry(entry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 No Content
    }



    // Endpoint to filter TODOEntries by completion status
    @GetMapping("/status/{completed}")
    public List<TODOEntry> getTODOEntriesByCompletionStatus(@PathVariable boolean completed) {
        return todoEntryService.getEntriesByCompletionStatus(completed);
    }
}

