package Exercise3;

public class ArtWork {
    private String title;
    private Author author;
    private Room location;//????

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        if (author == null){
            throw new NullPointerException();
        }
        return author;
    }

    public void setAuthor(Author author) {
        if (author == null){
            throw new NullPointerException();
        }
        this.author = author;
    }
}
