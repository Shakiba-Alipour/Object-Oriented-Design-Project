import java.io.File;
import java.util.ArrayList;

public class DirectoryExplorer {
    private File directory;
    private ArrayList<File> srcFiles;

    public DirectoryExplorer(String path) {
        this.directory = new File(path + "/src");
        ArrayList<File> files = new ArrayList<>();
        addList(files, this.directory.listFiles());
        for (File file :
                files) {
            if (file.isDirectory()) {
                addList(files, file.listFiles());
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                srcFiles.add(file);
            }
        }
    }

    private ArrayList addList(ArrayList list, File[] files) {
        ArrayList<File> newList = new ArrayList<>();
        for (File file :
                files) {
            list.add(file);
        }
        return newList;
    }

    public ArrayList getFiles() {
        return this.srcFiles;
    }
}
