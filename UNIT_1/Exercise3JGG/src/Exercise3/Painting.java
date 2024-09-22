package Exercise3;

public class Painting extends ArtWork {
    private String format;
    private PaintingType paintingType;

    public Painting(String title, Author author, Gallery location, String format, PaintingType paintingType) {
        super(title, author, location);
        this.format = format;
        this.paintingType = paintingType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public PaintingType getPaintingType() {
        return paintingType;
    }

    public void setPaintingType(PaintingType paintingType) {
        this.paintingType = paintingType;
    }
}
