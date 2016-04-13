package stickynote.web;

import com.jayway.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import stickynote.data.StickyNote;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class StickyNoteRestTest {

    private static final String SERVICE_URL = "http://localhost:8888/notes";

    @Test
    public void addGetDeleteNote() {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("title", "test3");
        map.put("note", "note");

        final StickyNote newNote = new StickyNote("title", "note");

        // Add
        String id =
                given().
                        contentType(ContentType.JSON).
                        body(newNote).
                        when().
                        post(SERVICE_URL).
                        then().
                        statusCode(200).
                        body("title", Matchers.is(newNote.getTitle())).
                        body("note", Matchers.is(newNote.getNote())).
                        body("id", Matchers.notNullValue()).
                        extract().
                        path("id");

        // Get
        given().
                contentType(ContentType.JSON).
                when().
                get(SERVICE_URL + "/" + id).
                then().
                statusCode(200).
                body("title", Matchers.is(newNote.getTitle())).
                body("note", Matchers.is(newNote.getNote())).
                body("id", Matchers.is(id));

        StickyNote note = given().
                contentType(ContentType.JSON).
                when().
                get(SERVICE_URL + "/" + id).as(StickyNote.class);

        // update
        note.setNote(newNote.getNote() + newNote.getNote());
        given().
                contentType(ContentType.JSON).
                body(note).
                when().
                post(SERVICE_URL + "/" + id).
                then().statusCode(200);

        // Get
        given().
                contentType(ContentType.JSON).
                when().
                get(SERVICE_URL + "/" + id).
                then().
                statusCode(200).
                body("title", Matchers.is(newNote.getTitle())).
                body("note", Matchers.is(newNote.getNote() + newNote.getNote())).
                body("id", Matchers.is(id));


        // Delete
        given().
                contentType(ContentType.JSON).
                when().
                delete(SERVICE_URL + "/" + id).
                then().
                statusCode(200);
    }

}
