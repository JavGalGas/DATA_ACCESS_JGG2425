public class Main {
    public static void main(String[] args) {
        String file1 = UI.getFile1();
        String file2 = UI.getFile2();
        FileMerger fileMerger = new FileMerger();
        fileMerger.mergeSortedFiles(file1,file2);
    }
}