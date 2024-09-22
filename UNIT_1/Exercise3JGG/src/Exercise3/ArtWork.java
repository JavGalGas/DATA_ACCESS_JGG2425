package Exercise3;

public abstract class ArtWork {
    private String title;
    private Author author;
    private Gallery location;

    public ArtWork(String title, Author author, Gallery location) {
        setTitle(title);
        setAuthor(author);
        setLocation(location);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException{
        if (title == null)
            throw new IllegalArgumentException("Title cannot be null");
        this.title = title;
    }

    public Author getAuthor() throws NullPointerException {
        if (author == null){
            throw new NullPointerException();
        }
        return author;
    }

    public void setAuthor(Author author) throws NullPointerException {
        if (author == null){
            throw new NullPointerException();
        }
        if (!this.author.equals(author)){
            Author temp = this.author;
            this.author = author;
            temp.removeArtWork(this);
            author.addArtWork(this);
        }
    }

    public Gallery getLocation() throws NullPointerException {
        if (location == null){
            throw new NullPointerException();
        }
        return location;
    }

    public void setLocation(Gallery location) throws IllegalArgumentException {
        if (location == null){
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (!this.location.equals(location)){
            Gallery temp = this.location;
            this.location = location;
            temp.removeArtWork(this);
            location.addArtWork(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        ArtWork artWork = (ArtWork) obj;
        return this.title.equals(artWork.title) && this.author.equals(artWork.author) && this.location.equals(artWork.location);
    }
}
