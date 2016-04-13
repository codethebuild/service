package stickynote.data;


import lombok.*;

/**
 * Domain class for a stickynote.
 *
 * @author Niek Palm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class StickyNote {

    private String id;
    @NonNull
    private String title;
    @NonNull
    private String note;

}
