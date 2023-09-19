import java.io.IOException;

public class ImageEditorEC {
    public static void main(String[] args) throws IOException {
        ImageEditor.load("input-orange.png");
        ImageEditor.rotate(180);
        int[][] patchImage = ImageEditor.open("input-gwenn.png");
        ImageEditor.patch(50, 100, patchImage, 255, 255, 255);
        ImageEditor.downSample(3, 2);
        ImageEditor.save("output.png");
    }
}
