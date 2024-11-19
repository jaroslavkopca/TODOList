package app.backend;
import app.backend.model.TODOEntry;
import app.backend.service.TODOEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TODOEntryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TODOEntryService todoEntryService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test for creating a new TODOEntry
    @Test
    public void givenTODOEntry_whenCreateTODOEntry_thenReturnSavedTODOEntry() throws Exception {
        // given - precondition or setup
        TODOEntry todoEntry = new TODOEntry();
        todoEntry.setId(1);
        todoEntry.setTitle("Test TODO");
        todoEntry.setCompleted(false);
        doAnswer(invocation -> {
            TODOEntry addedEntry = invocation.getArgument(0);
            return null;
        }).when(todoEntryService).addEntry(todoEntry);


        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(post("/api/createTODOEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoEntry)));

        // then - verify the result
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(todoEntry.getId())))
                .andExpect(jsonPath("$.completed", is(todoEntry.isCompleted())));
    }

    // Test for getting all TODOEntries
    @Test
    public void givenListOfTODOEntries_whenGetAllTODOEntries_thenReturnTODOEntriesList() throws Exception {
        // given - precondition or setup
        List<TODOEntry> listOfTODOEntries = new ArrayList<>();
        listOfTODOEntries.add(new TODOEntry(1, "First TODO", false));
        listOfTODOEntries.add(new TODOEntry(2, "Second TODO", true));
        given(todoEntryService.getAllEntries()).willReturn(listOfTODOEntries);

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/getAllTODOEntries"));

        // then - verify the result
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfTODOEntries.size())));
    }

    // Test for getting a TODOEntry by ID (valid case)
    @Test
    public void givenTODOEntryId_whenGetTODOEntryById_thenReturnTODOEntry() throws Exception {
        // given - precondition or setup
        int todoEntryId = 1;
        TODOEntry todoEntry = new TODOEntry(todoEntryId, "Test TODO", false);
        given(todoEntryService.getEntryById(todoEntryId)).willReturn(todoEntry);

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/getTODOEntryById/{id}", todoEntryId));

        // then - verify the result
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(todoEntry.getId())))
                .andExpect(jsonPath("$.completed", is(todoEntry.isCompleted())));
    }

    // Test for getting a TODOEntry by ID (not found case)
    @Test
    public void givenInvalidTODOEntryId_whenGetTODOEntryById_thenReturnNotFound() throws Exception {
        // given - precondition or setup
        int todoEntryId = 1;
        given(todoEntryService.getEntryById(todoEntryId)).willReturn(null);

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/getTODOEntryById/{id}", todoEntryId));

        // then - verify the result
        response.andExpect(status().isNotFound());
    }

    // Test for updating a TODOEntry
    @Test
    public void givenUpdatedTODOEntry_whenUpdateTODOEntry_thenReturnUpdatedTODOEntry() throws Exception {
        // given - precondition or setup
        int todoEntryId = 1;
        TODOEntry existingEntry = new TODOEntry(todoEntryId, "Old TODO", false);
        TODOEntry updatedEntry = new TODOEntry(todoEntryId, "Updated TODO", true);
        given(todoEntryService.getEntryById(todoEntryId)).willReturn(existingEntry);
        doAnswer(invocation -> {
            TODOEntry entryToUpdate = invocation.getArgument(0); // This gets the passed TODOEntry
            existingEntry.setTitle(entryToUpdate.getTitle());
            existingEntry.setCompleted(entryToUpdate.isCompleted());
            return null; // No return value since updateEntry is void
        }).when(todoEntryService).updateEntry(any(TODOEntry.class));


        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(put("/api/updateTODOEntry", todoEntryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEntry)));

        // then - verify the result
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(updatedEntry.isCompleted())));
    }

    // Test for updating a TODOEntry (not found case)
    @Test
    public void givenInvalidTODOEntry_whenUpdateTODOEntry_thenReturnNotFound() throws Exception {
        // given - precondition or setup
        int todoEntryId = 1;
        TODOEntry updatedEntry = new TODOEntry(todoEntryId, "Updated TODO", true);
        given(todoEntryService.getEntryById(todoEntryId)).willReturn(null);

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(put("/api/updateTODOEntry/{id}", todoEntryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEntry)));

        // then - verify the result
        response.andExpect(status().isNotFound());
    }

    // Test for deleting a TODOEntry
    @Test
    public void givenTODOEntryId_whenDeleteTODOEntry_thenReturnNoContent() throws Exception {
        // given - precondition or setup
        int todoEntryId = 1;
        TODOEntry todoEntry = new TODOEntry();
        todoEntry.setId(todoEntryId);
        todoEntry.setTitle("Test TODO");
        todoEntry.setCompleted(false);
        given(todoEntryService.getEntryById(todoEntryId)).willReturn(todoEntry);
        willDoNothing().given(todoEntryService).removeEntry(any(TODOEntry.class));

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(delete("/api/deleteTODOEntry/{id}", todoEntryId));

        // then - verify the result
        response.andExpect(status().isNoContent());  // Verify 204 No Content
    }


    // Test for filtering TODOEntries by completion status
    @Test
    public void givenCompletionStatus_whenGetTODOEntriesByCompletionStatus_thenReturnFilteredTODOEntries() throws Exception {
        // given - precondition or setup
        boolean isCompleted = true;
        List<TODOEntry> filteredEntries = new ArrayList<>();
        filteredEntries.add(new TODOEntry(2, "Completed TODO", true));
        given(todoEntryService.getEntriesByCompletionStatus(isCompleted)).willReturn(filteredEntries);

        // when - action or behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/status/{isCompleted}", isCompleted));

        // then - verify the result
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(filteredEntries.size())));
    }
}
