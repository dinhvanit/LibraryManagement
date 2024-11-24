package uet.librarymanagementsystem.util;

import javafx.scene.image.Image;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.documents.MaterialType;

import java.util.Objects;

import static uet.librarymanagementsystem.entity.documents.MaterialType.*;

public class ImageUtils {
    public static Image loadDefaultImage(MaterialType materialType, Class<?> loaderClass) {
        String path;
        switch (materialType) {
            case JOURNAL -> path = ImagesOfLibrary.JOURNAL.getPath();
            case NEWSPAPER -> path = ImagesOfLibrary.NEWSPAPER.getPath();
            case THESIS -> path = ImagesOfLibrary.THESIS.getPath();
            default -> path = ImagesOfLibrary.BOOK.getPath(); // BOOK làm mặc định
        }
        return new Image(Objects.requireNonNull(loaderClass.getResourceAsStream(path)));
    }
}
