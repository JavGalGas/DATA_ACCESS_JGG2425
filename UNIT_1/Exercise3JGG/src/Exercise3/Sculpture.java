package Exercise3;

public class Sculpture extends ArtWork{
    private Materials material;
    private Styles style;

    public Sculpture(String title, Author author, Gallery location, Materials material, Styles style) {
        super(title, author, location);
        this.material = material;
        this.style = style;
    }

    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }
}
