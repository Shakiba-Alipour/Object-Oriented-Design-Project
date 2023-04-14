import java.io.File;
import java.util.HashMap;

public class DirectoryExplorer {
    private File directory;
    private HashMap<String, File> srcFiles;

    public DirectoryExplorer(String name, String path) {
        this.directory = new File(path + "/src");
        HashMap<String, File> files = new HashMap<>();
        addList(files, name, this.directory.listFiles());
        for (File file :
                this.directory.listFiles()) {
            if (file.isDirectory()) {
                addList(files, name, file.listFiles());
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                srcFiles.put(name, file);
            }
        }
    }

    private HashMap addList(HashMap map, String name, File[] files) {
        HashMap<String, File> newMap = new HashMap<>();
        for (File file :
                files) {
            newMap.put(name, file);
        }
        return newMap;
    }

    public HashMap getFiles() {
        return this.srcFiles;
    }
}
