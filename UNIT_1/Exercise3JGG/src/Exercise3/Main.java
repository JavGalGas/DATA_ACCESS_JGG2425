package Exercise3;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

    }

    public void iterateMuseum(Museum museum) {
        HashSet<Gallery> galleries = museum.getGalleries();
        for (Gallery gallery : galleries) {
            System.out.println("Room: " + gallery.getName());
            HashSet<ArtWork> artWorks = gallery.getArtWorks();
            System.out.println("Art works:");
            for (ArtWork artWork : artWorks) {
                System.out.println("    Title: " + artWork.getTitle());
                if (artWork instanceof Painting painting) {
                    System.out.println("    Painting type: " + painting.getPaintingType());
                    System.out.println("    Format: " + painting.getFormat());
                }
                else if (artWork instanceof Sculpture sculpture) {
                    System.out.println("    Style: " + sculpture.getStyle());
                    System.out.println("    Material: " + sculpture.getMaterial());
                }
                Author author = artWork.getAuthor();
                System.out.println("    Author:");
                System.out.println("        Name: " + author.getName());
                System.out.println("        Nationality: " + author.getNationality());
                System.out.println(" ");
            }
            System.out.println("_____________________________________________________________");
            System.out.println(" ");
        }
    }
    //Hacer bien el ejercicio: atributos privados, funciones necesarias, relaciones correctas...
}