package stickynote.web;

import stickynote.data.StickyNote;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Niek Palm
 */
public class Cache {

    private static final Collection<StickyNote> cache = new LinkedHashSet<>();

    static Collection<StickyNote> getCache() {
        return cache;
    }

    static void add(StickyNote stickyNote) {
        cache.add(stickyNote);
    }

    static void clear() {
        cache.clear();
    }
}
