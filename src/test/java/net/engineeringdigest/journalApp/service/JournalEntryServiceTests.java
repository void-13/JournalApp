package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JournalEntryServiceTests {

    @Autowired
    private JournalEntryService journalEntryService;

    @ParameterizedTest
    @CsvSource({"testTitle1, testContent1", "testTitle2, testContent2"})
    public void testSaveEntry(String title, String content) {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(title);
        journalEntry.setContent(content);
        journalEntryService.saveEntry(journalEntry, "Anamika");
        assertNotNull(journalEntry);
        assertEquals(title, journalEntry.getTitle());
        assertEquals(content, journalEntry.getContent());
    }
}
