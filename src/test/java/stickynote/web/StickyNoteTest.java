package stickynote.web;

import com.google.common.collect.FluentIterable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import stickynote.data.StickyNote;
import stickynote.data.StickyNoteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Basic junit tests.
 */
@RunWith(MockitoJUnitRunner.class)
public class StickyNoteTest {

    @InjectMocks
    private StickyNoteController stickyNoteController;

    @Mock
    private StickyNoteRepository stickyNoteRepository;

    @Test
    public void testAdd() {
        MockitoAnnotations.initMocks(this);

        StickyNote newNote = new StickyNote("title", "note");

        when(stickyNoteRepository.save(newNote)).thenReturn(newNote);

        StickyNote savedNote = stickyNoteController.addNote(newNote);
        Assert.assertEquals(newNote.getTitle(), savedNote.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalAdd() {
        MockitoAnnotations.initMocks(this);

        StickyNote newNote = new StickyNote("123", "title", "note");

        when(stickyNoteRepository.save(newNote)).thenReturn(newNote);

        stickyNoteController.addNote(newNote);
    }

    @Test
    public void testBoundaries() {
        setupDefaultMocking();
        addNotes(1001);

        final Collection<StickyNote> result = stickyNoteController.list(0, 1000);
        Assert.assertEquals(1000, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoundariesUpperBound() {
        setupDefaultMocking();
        addNotes(1001);

        stickyNoteController.list(0, 1001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoundariesLowerBound() {
        setupDefaultMocking();
        addNotes(1001);

        stickyNoteController.list(0, -1);
    }


    private List<StickyNote> addNotes(final int count) {
        List<StickyNote> notes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            notes.add(stickyNoteController.addNote(generate(i)));
        }

        return notes;
    }

    private StickyNote generate(final int i) {
        return new StickyNote("title " + i, "some text for note " + i);
    }

    private void setupDefaultMocking() {
        Cache.clear();
        MockitoAnnotations.initMocks(this);

        when(stickyNoteRepository.save(any(StickyNote.class))).thenAnswer(new Answer<StickyNote>() {
            public StickyNote answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                if (args[0] instanceof StickyNote) {
                    Cache.add((StickyNote) args[0]);
                    return (StickyNote) args[0];
                } else {
                    return null;
                }
            }
        });


        when(stickyNoteRepository.findAll(any(PageRequest.class))).thenAnswer(new Answer<Page<StickyNote>>() {
            public Page<StickyNote> answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                if (args[0] instanceof PageRequest) {
                    PageRequest p = (PageRequest) args[0];
                    int size = p.getPageSize();
                    int number = p.getPageNumber();

                    List<StickyNote> notes = FluentIterable.from(Cache.getCache()).skip(size * number).limit(size).toList();
                    Page<StickyNote> page = new PageImpl<>(notes, p, notes.size());

                    return page;
                } else {
                    return null;
                }
            }
        });
    }
}
