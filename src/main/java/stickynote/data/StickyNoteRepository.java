package stickynote.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

/**
 * @author Niek Palm
 *
 * Stickynote repository.
 */
@Transactional
public interface StickyNoteRepository extends MongoRepository<StickyNote, String> {

    StickyNote findByTitle(String title, Pageable pageable);
}
