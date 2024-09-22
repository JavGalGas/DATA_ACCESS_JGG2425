package Exercise3;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class Gallery {
    private String name;
    private final HashSet<ArtWork> artWorks = new HashSet<>();
    private Museum museum;

    public Gallery(){
        name = "";
        museum = new Museum();
    }

    public Gallery(String name, Museum museum) {
        setName(name);
        setMuseum(museum);
    }

    public Gallery(String name, Museum museum, HashSet<ArtWork> artWorks) {
        setName(name);
        setMuseum(museum);
        this.artWorks.addAll(artWorks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) throws IllegalArgumentException {
        if (museum == null) {
            throw new IllegalArgumentException("Museum cannot be null");
        }
        if (!this.museum.equals(museum)) {
            Museum oldMuseum = this.museum;
            this.museum = museum;
            oldMuseum.removeGallery(this);
            museum.addGallery(this);
        }
    }

    public ArtWork getArtWork(String title) throws IllegalArgumentException, NoSuchElementException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        for (ArtWork artWork : artWorks) {
            if (artWork.getTitle().equals(title)) {
                return artWork;
            }
        }
        throw new NoSuchElementException("Art work not found");
    }

    public HashSet<ArtWork> getArtWorks() {
        return artWorks;
    }

    public void addArtWork(ArtWork artWork) {
        if (artWork == null) {
            throw new IllegalArgumentException("Art work cannot be null");
        }
        artWorks.add(artWork);
        if (!artWork.getLocation().equals(this)) {
            artWork.setLocation(this);
        }
    }

    public void addArtWorks(HashSet<ArtWork> artWorks) throws IllegalArgumentException {
        if (artWorks == null) {
            throw new IllegalArgumentException("Art works cannot be null");
        }
        this.artWorks.addAll(artWorks);
    }

    public void removeArtWork(ArtWork artWork) {
        if (artWork == null) {
            throw new IllegalArgumentException("ArtWork cannot be null");
        }
        if (!artWorks.contains(artWork)) {
            throw new IllegalArgumentException("ArtWork does not exist");
        }
        artWorks.remove(artWork);
        artWork.setLocation(new Gallery());
    }

    public void removeArtWork(String title) throws IllegalArgumentException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        for (ArtWork artWork : artWorks) {
            if (artWork.getTitle().equals(title)) {
                removeArtWork(artWork);
            }
        }

        /*Iterator<ArtWork> iterator = artWorks.iterator();

        while (iterator.hasNext()) {
            ArtWork artWork = iterator.next();

            if (artWork.getTitle().equals(title)) {
                removeArtWork(artWork);
            }
        }*/
    }

}
