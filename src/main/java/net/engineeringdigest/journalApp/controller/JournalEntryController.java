package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        Optional<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).findFirst();
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean removed = journalEntryService.deleteById(id, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id,
                                               @RequestBody JournalEntry newEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        Optional<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).findFirst();
        if (entry.isPresent()) {
            JournalEntry old = journalEntryService.findById(id).orElse(null);
            if (old != null) {
            old.setTitle((newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) ? newEntry.getTitle() : old.getTitle());
            old.setContent((newEntry.getContent() != null && !newEntry.getContent().isBlank()) ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
