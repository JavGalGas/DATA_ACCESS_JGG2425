package Exercise3;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class Museum {
    private String name;
    private String address;
    private String city;
    private String country;
    private final HashSet<Gallery> galleries = new HashSet<>();

    public Museum() {
        name = "";
        address = "";
        city = "";
        country = "";
    }

    public Museum(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public Museum(String name, String address, String city, String country, HashSet<Gallery> galleries) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.galleries.addAll(galleries);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Museum name cannot be empty or null");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IllegalArgumentException {
        if (address == null || address.isEmpty())
            throw new IllegalArgumentException("Museum address cannot be empty or null");
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws IllegalArgumentException {
        if (city == null || city.isEmpty())
            throw new IllegalArgumentException("Museum city cannot be empty or null");
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) throws IllegalArgumentException {
        if (country == null || country.isEmpty())
            throw new IllegalArgumentException("Museum country cannot be empty or null");
        this.country = country;
    }

    public Gallery getGallery(String name) throws IllegalArgumentException, NoSuchElementException {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Museum room cannot be empty or null");
        for (Gallery gallery : galleries) {
            if (gallery.getName().equals(name))
                return gallery;
        }
        throw new NoSuchElementException("Museum room not found");
    }

    public HashSet<Gallery> getGalleries() {
        return galleries;
    }

    public void addGallery(Gallery gallery) {
        galleries.add(gallery);
        if (!gallery.getMuseum().equals(this))
            gallery.setMuseum(this);
    }

    public void removeGallery(Gallery gallery) throws IllegalArgumentException {
        if (gallery == null)
            throw new IllegalArgumentException("Museum cannot be null");
        galleries.remove(gallery);
    }
}
