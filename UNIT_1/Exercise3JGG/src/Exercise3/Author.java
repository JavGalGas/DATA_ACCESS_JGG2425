package Exercise3;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class Author {
    private String name;
    private String nationality;
    private HashSet<ArtWork> artWorks = new HashSet<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ArtWork getPiece(String title) throws IllegalArgumentException, NoSuchElementException {
        if (title == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        for (ArtWork artWork1 : artWorks){
            if (artWork1.getTitle().equals(title))
                return artWork1;
        }
        throw new NoSuchElementException();
    }
}
