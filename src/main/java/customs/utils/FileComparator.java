package customs.utils;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileComparator {

    private final File original;

    private final File revised;

    public FileComparator(File original, File revised) {
        this.original = original;
        this.revised = revised;
    }

    public List<Chunk> getChangesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }

    private List<Delta> getDeltas() throws IOException {

        final List<String> originalFileLines = fileToLines(original);
        final List<String> revisedFileLines = fileToLines(revised);

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    private List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

	public static List<String> fileToLines(String diffcontent) {
		    List<String> lines = new LinkedList<String>();
		    String line = "";
		    String[] filas = diffcontent.split("\n");
		    int lenght = filas.length, i=0;
		    while (i < lenght) {
		    			line = filas[i];
		            lines.add(line);
		            i++;
		        }
		    return lines;
		}

}