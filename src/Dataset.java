import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dataset implements Searchable, Sortable {
    private static final int IMAGE_WIDTH = 125;
    private static final int IMAGE_HEIGHT = 200;
    private static final String RESOURCE_ROOT = "data/";
    private static final Path[] DATA_DIRECTORIES = {
            Paths.get("src", "data"),
            Paths.get("data")
    };

    private final Record[] records;
    private int foundAt = -1;

    public Dataset() {
        records = loadRecords();
    }

    private Record[] loadRecords() {
        List<Record> loadedRecords = new ArrayList<>();

        try (InputStream inputStream = openAsset("CerealsDataset.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] columns = line.split(",", -1);
                if (columns.length != 5) {
                    throw new IllegalStateException("Unexpected cereal row: " + line);
                }

                String name = columns[0];
                String manuf = columns[1];
                String type = columns[2];
                int sugars = Integer.parseInt(columns[3]);
                double rating = Double.parseDouble(columns[4]);
                BufferedImage image = loadImage(name + ".jpeg", IMAGE_WIDTH, IMAGE_HEIGHT);
                loadedRecords.add(new Record(image, name, manuf, type, sugars, rating));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load cereal dataset.", e);
        }

        return loadedRecords.toArray(new Record[0]);
    }

    public Record[] getRecords() {
        return records;
    }

    @Override
    public void sort() {
        SortingMethods.sort(records);
    }

    @Override
    public int find(double rating) {
        return SearchingMethods.search(records, rating);
    }

    public void findBestRated() {
        foundAt = find(99.99);
    }

    public void findClosestTo(double rating) {
        foundAt = find(rating);
    }

    public int getFoundAt() {
        return foundAt;
    }

    private static InputStream openAsset(String fileName) throws IOException {
        InputStream resourceStream = Dataset.class.getClassLoader()
                .getResourceAsStream(RESOURCE_ROOT + fileName);
        if (resourceStream != null) {
            return resourceStream;
        }

        for (Path dataDirectory : DATA_DIRECTORIES) {
            Path path = dataDirectory.resolve(fileName);
            if (Files.isRegularFile(path)) {
                return Files.newInputStream(path);
            }
        }

        throw new FileNotFoundException("Could not locate data asset: " + fileName);
    }

    private static BufferedImage loadImage(String fileName, int width, int height) throws IOException {
        try (InputStream inputStream = openAsset(fileName)) {
            BufferedImage source = ImageIO.read(inputStream);
            if (source == null) {
                throw new IOException("Unsupported image format for asset: " + fileName);
            }

            if (source.getWidth() == width && source.getHeight() == height) {
                return source;
            }

            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            try {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.drawImage(source, 0, 0, width, height, null);
            } finally {
                g2.dispose();
            }
            return resized;
        }
    }
}
