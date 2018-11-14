
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Start extends Application {

    /*to ensure the status for continue button. it will work after the game starts.*/
    private boolean gameStart=false;
    /*make the pane for the main scene, set up all elements.*/
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        try (InputStream is = Files.newInputStream(Paths.get("res/image.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(800);
            img.setFitHeight(600);
            root.getChildren().add(img);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title("F R O G G E R");
        title.setTranslateX(75);
        title.setTranslateY(200);

        AslanButton itemExit = new AslanButton("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit(0));
        
        AslanButton itemStart= new AslanButton("NEW GAME");
        MainFrog frog = new MainFrog();
        Stage newstage=new Stage();
        
        itemStart.setOnMouseClicked(event -> {
            try {
                gameStart=true;
                frog.start(newstage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        AslanButton itemContinue= new AslanButton("CONTINUE");
        itemContinue.setOnMouseClicked(event -> {
            if(gameStart)
            {
                try {
                    newstage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        
        HBox intro=makeIntroduction();
        AslanButton itemIntroduction= new AslanButton("INTRODUCTION");
        itemIntroduction.setOnMouseClicked(event->{
            if(root.getChildren().contains(intro))
                root.getChildren().remove(intro);
            else
                root.getChildren().add(intro);
            
        });
        
        MenuBox menu = new MenuBox(itemStart, itemContinue, itemIntroduction,itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);

        root.getChildren().addAll(title, menu);
        return root;
    }
    
    private HBox makeIntroduction()
    {
        HBox mainhBox = new HBox();
        String[] text={"LITTLE FROG JUMP!"," TO THE OTHER SIDE OF THE RIVER!","KEEP AWAY FROM PIRANHAS","USE 'W' 'A' 'S' 'D' TO MOVE!"};
        mainhBox.setTranslateX(550);
        mainhBox.setTranslateY(250);
        
        for(int j=0;j<text.length;j++)
        {
            HBox hBox = new HBox();

        for (int i = 0; i < text[j].toCharArray().length; i++) 
        {
            char letter = text[j].charAt(i);
            Text text1 = new Text(String.valueOf(letter));
            text1.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
            text1.setOpacity(0);
            text1.setFill(Color.WHITE);
            hBox.getChildren().add(text1);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text1);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.2));
            ft.play();
        }
        
        if(j==0)
            hBox.setTranslateX(0);
        if(j==1)
            hBox.setTranslateX(-200);
        if(j==2)
            hBox.setTranslateX(-450);
        if(j==3)
            hBox.setTranslateX(-670);
       
        hBox.setTranslateY(j*40);
        mainhBox.getChildren().add(hBox);
        }
        return mainhBox;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("JUMP! FROG!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
}
