import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuBox extends VBox {
        public MenuBox(AslanButton... items) {
            getChildren().add(createSeparator());

            for (AslanButton item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }