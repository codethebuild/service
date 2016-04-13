package stickynote.web;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import stickynote.data.StickyNote;
import stickynote.data.StickyNoteRepository;

import java.util.Collection;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * Rest controller for the sticky notes
 *
 * @author Niek Palm
 */
@Api(value = "notes", description = "Operations about stickynotes")
@RestController
@RequestMapping("/notes")
public class StickyNoteController {

    private static final String PAGE_SIZE_DEFAULT = "100";

    private static final Integer PAGE_SIZE_MAX = 1000;

    private static final String ZERO = "0";

	// code with blocker
    @Autowired
    StickyNoteRepository stickyNoteRepository;
	
	// code without blocker
	//@Autowired
    //private StickyNoteRepository stickyNoteRepository;

    /**
     * List all the notes withe a maximum (pegenation)
     *
     * @param startPage the page where to start, this is note size of page times start page.
     * @param pageSize  the number of notes on one page, can not be greater then the maximum.
     * @return a list of notes.
     */
    @RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Returns stickynotes details", notes = "Returns a complete list of stickynote details")
	@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of user detail"),
        @ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."), 
		@ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
		@ApiResponse(code = 500, message = "Internal server error") })
    public Collection<StickyNote> list(@ApiParam(name = "startPage", value = "The page where to start, this is note size of page times start page.") @RequestParam(required = false, defaultValue = ZERO) final Integer startPage,
                                       @ApiParam(name = "pageSize", value = "The number of notes on one page, can not be greater then the maximum.") @RequestParam(required = false, defaultValue = PAGE_SIZE_DEFAULT) final Integer pageSize) {
        Preconditions.checkArgument(Range.closed(Integer.valueOf(ZERO), PAGE_SIZE_MAX).contains(pageSize),
                String.format("The size of the page %s should be smaller then the maximum allowed size of %s and greater then the %s", pageSize, PAGE_SIZE_MAX, ZERO));

        return stickyNoteRepository.findAll(new PageRequest(startPage, pageSize)).getContent();
    }

    /**
     * Get a note for a specific id.
     * @param id the ide of the note te be retrieved.
     * @return the note.
     */
	@ApiOperation(value = "Returns stickynote details for given id", notes = "Returns stickynote details for given id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of stickynote detail"),
		@ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."), 
		@ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
        @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public StickyNote getNote(@ApiParam(name = "id", value = "The id of the note te be retrieved.") @PathVariable String id) {
        return stickyNoteRepository.findOne(id);
    }
    /**
     * Update a note
     * @param id the id of the note to be updated.
     * @param note the updated note, id will be ignored.
     * @return the updated note.
     */
	@ApiOperation(value = "Update a stickynote", notes = "Update a stickynote")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful updated stickynote"),
	    @ApiResponse(code = 201, message = "The request has been fulfilled and resulted in a new resource being created."),
		@ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."), 
		@ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
        @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public StickyNote updateNote(@ApiParam(name = "id", value = "The id of the note to be updated.") @PathVariable String id, @ApiParam(name = "note", value = "The updated note, id will be ignored.") @RequestBody final StickyNote note) {
        note.setId(id);
        return stickyNoteRepository.save(note);
    }

    /**
     * Add a new note.
     * @param note the new note te be added, id should be not set.
     * @return the new note.
     */
	@ApiOperation(value = "Add a stickynote", notes = "Add a stickynote")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful added a stickynote"),
		@ApiResponse(code = 201, message = "The request has been fulfilled and resulted in a new resource being created."),
		@ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."), 
		@ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
        @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(method = RequestMethod.POST)
    public StickyNote addNote(@ApiParam(name = "note", value = "The new note to be added, id should be not set.") @RequestBody final StickyNote note) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(note.getId()), "Id not allowed when creating a new note.");
        return stickyNoteRepository.save(note);
    }

    /**
     * Delete a single note.
     * @param id the id of the note te be deleted.
     */
	@ApiOperation(value = "Delete a stickynote for given id", notes = "Delete a stickynote for given id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful deleted a stickynote"),
		@ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."), 
		@ApiResponse(code = 204, message = "The server has fulfilled the request but does not need to return an entity-body."), 
        @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteNote(@ApiParam(name = "id", value = "The id of the note te be deleted.") @PathVariable final String id) {
        stickyNoteRepository.delete(id);
    }

	@ApiOperation(value = "Delete all the stickynotes", notes = "Delete all the stickynotes")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted all the stickynotes"),
        @ApiResponse(code = 401, message = "The request requires user authentication."),
		@ApiResponse(code = 403, message = "The server understood the request, but is refusing to fulfill it."),
		@ApiResponse(code = 204, message = "The server has fulfilled the request but does not need to return an entity-body, and might want to return updated metainformation. "),
		@ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllNotes() {
        stickyNoteRepository.deleteAll();
    }


}
