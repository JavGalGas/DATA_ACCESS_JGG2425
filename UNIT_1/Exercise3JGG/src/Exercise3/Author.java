package Exercise3;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class Author {
    private String name;
    private String nationality;
    private final HashSet<ArtWork> artWorks = new HashSet<>();

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public Author(String name, String nationality, HashSet<ArtWork> artWorks) {
        this.name = name;
        this.nationality = nationality;
        this.artWorks.addAll(artWorks);
    }

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

    public ArtWork getArtWork(String title) throws IllegalArgumentException, NoSuchElementException {
        if (title == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        for (ArtWork artWork1 : artWorks){
            if (artWork1.getTitle().equals(title))
                return artWork1;
        }
        throw new NoSuchElementException("Art work not found");
    }

    public HashSet<ArtWork> getArtWorks() {
        return artWorks;
    }

    public void addArtWork(ArtWork artWork) throws IllegalArgumentException {
        if (artWork == null) {
            throw new IllegalArgumentException("Art work cannot be null");
        }
        artWorks.add(artWork);
        if (!artWork.getAuthor().equals(this)) {
            artWork.setAuthor(this);
        }
    }

    public void addArtWorks(HashSet<ArtWork> artWorks) throws IllegalArgumentException {
        if (artWorks == null) {
            throw new IllegalArgumentException("Art works cannot be null");
        }
        this.artWorks.addAll(artWorks);
    }

    public void removeArtWork(ArtWork artWork) throws IllegalArgumentException {
        if (artWork == null) {
            throw new IllegalArgumentException("Art work cannot be null");
        }
        if (!artWorks.contains(artWork)) {
            throw new IllegalArgumentException("Art work does not exist");
        }
        if (!artWork.getAuthor().equals(this)) {
            artWorks.remove(artWork);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Author author = (Author) obj;
        return name.equals(author.name) && nationality.equals(author.nationality) && artWorks.equals(author.artWorks);
    }

}
