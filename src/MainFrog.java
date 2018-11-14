
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class MainFrog extends Application {

    private AnimationTimer timer;
    
    private MenuBox newGameMenu,pauseMenu;
    
    private Pane root;
    private boolean running=true;
    private boolean gameStart=false;
    
    /*store the fish on the screen*/
    private List<Node> fish = new LinkedList<Node>();
    private List<Node> collision = new LinkedList<Node>();
    private List<Node> lifes= new LinkedList<Node>();
    
    private Node frog;
    private long startTime; 
    private long finishTime;
    private long period;
    private Text time;
    private int life=3;
    
    private Scene createContent(Stage stage) {
        
        fish.clear();
        collision.clear();
        startTime=0;
        finishTime=0;
        period=0;
        
        gameStart=false;
        
        root = new Pane();
        Scene gameScene=new Scene(root);
        root.setPrefSize(800, 600);
        life=3;
        
        gameScene.setOnKeyPressed(event -> {
            if(running)
            {
                switch (event.getCode()) {
                case W:
                    if(!gameStart)
                    {
                        gameStart=true;
                        startTime=System.currentTimeMillis();
                    }
                    if(frog.getTranslateY()>1)
                    frog.setTranslateY(frog.getTranslateY() - 40);
                    break;
                case S:
                    if(!gameStart)
                    {
                        gameStart=true;
                        startTime=System.currentTimeMillis();
                    }
                    if(frog.getTranslateY()<600-40*2)
                    frog.setTranslateY(frog.getTranslateY() + 40);
                    break;
                case A:
                    if(!gameStart)
                    {
                        gameStart=true;
                        startTime=System.currentTimeMillis();
                    }
                    if(frog.getTranslateX()>40)
                    frog.setTranslateX(frog.getTranslateX() - 40);
                    break;
                case D:
                    if(!gameStart)
                    {
                        gameStart=true;
                        startTime=System.currentTimeMillis();
                    }
                    if(frog.getTranslateX()<760)
                    frog.setTranslateX(frog.getTranslateX() + 40);
                    break;
                default:
                    break;
            }
            }
        });
        
        try (InputStream is = Files.newInputStream(Paths.get("res/River3.png"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(800);
            img.setFitHeight(600);
            root.getChildren().add(img);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
       
        frog = initFrog();
        
        Text text = new Text("LIFE:");
        text.setFill(Color.CYAN);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));
        text.setTranslateX(540);
        text.setTranslateY(590);

        try (InputStream is = Files.newInputStream(Paths.get("res/life.png"))) {
            ImageView img1 = new ImageView(new Image(is));
            img1.setFitWidth(30);
            img1.setFitHeight(30);
            img1.setTranslateX(600);
            img1.setTranslateY(560);
            lifes.add(img1);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        
        ImageView img = null;
        try (InputStream is = Files.newInputStream(Paths.get("res/fish.png"))) {
            img = new ImageView(new Image(is));
            img.setFitWidth(80);
            img.setFitHeight(40);
            img.setTranslateY((int)((Math.random() * 10)/1+3) * 40);
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        
        try (InputStream is = Files.newInputStream(Paths.get("res/life.png"))) {
            ImageView img2 = new ImageView(new Image(is));
            img2.setFitWidth(30);
            img2.setFitHeight(30);
            img2.setTranslateY(560);
            img2.setTranslateX(640);
            lifes.add(img2);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        
        try (InputStream is = Files.newInputStream(Paths.get("res/life.png"))) {
            ImageView img3 = new ImageView(new Image(is));
            img3.setFitWidth(30);
            img3.setFitHeight(30);
            img3.setTranslateY(560);
            img3.setTranslateX(680);
            lifes.add(img3);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        
        root.getChildren().addAll(lifes.get(0),lifes.get(1),lifes.get(2));
        
        AslanButton startNewGame=new AslanButton(" RESTART ");
        startNewGame.setOnMouseClicked(event->{
            root.getChildren().remove(pauseMenu);
            stage.setScene(createContent(stage));
        });
        
        AslanButton startNewGame1=new AslanButton(" RESTART ");
        startNewGame1.setOnMouseClicked(event->{
            root.getChildren().remove(pauseMenu);
            stage.setScene(createContent(stage));
        });
        
        AslanButton resumeGame=new AslanButton(" RESUME ");
        resumeGame.setOnMouseClicked(event->{
            if(!running)
            {
                timer.start();
                startTime=System.currentTimeMillis();
                root.getChildren().remove(pauseMenu);
                running=true;
            }
        });
        
        AslanButton quit=new AslanButton(" QUIT ");
        quit.setOnMouseClicked(event->{
            stage.close();
        });
        
        AslanButton quit1=new AslanButton(" QUIT ");
        quit1.setOnMouseClicked(event->{
            stage.close();
        });
        
        newGameMenu= new MenuBox(startNewGame,quit);
        newGameMenu.setTranslateX(300);
        newGameMenu.setTranslateY(350);
        
        pauseMenu= new MenuBox(resumeGame,startNewGame1,quit1);
        pauseMenu.setTranslateX(300);
        pauseMenu.setTranslateY(250);
        
        AslanButton pause= new AslanButton("PAUSE");
        pause.setTranslateX(10);
        pause.setTranslateY(565);
        pause.setOnMouseClicked(event->{
            if(running)
            {
                timer.stop();
                period+=finishTime-startTime;
                root.getChildren().add(pauseMenu);
                running=false;
             }
        });
        
        root.getChildren().addAll(frog,pause,text);
        
        time=new Text();
        time.setFill(Color.CYAN);
        time.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));
        time.setTranslateX(240);
        time.setTranslateY(590);
        root.getChildren().add(time);
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        running=true;
        return gameScene;
    }
    
    private Node initFrog() {
        ImageView img = null;
        try (InputStream is = Files.newInputStream(Paths.get("res/frog.png"))) {
            img = new ImageView(new Image(is));
            img.setFitWidth(38);
            img.setFitHeight(38);
            img.setTranslateX(370);
            img.setTranslateY(600 - 39-40);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        return img;
    }

    private Node spawnfish() {
        
        ImageView img = null;
        try (InputStream is = Files.newInputStream(Paths.get("res/fish.png"))) {
            img = new ImageView(new Image(is));
            img.setFitWidth(80);
            img.setFitHeight(40);
            img.setTranslateY((int)((Math.random() * 10)/1+3) * 40);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        root.getChildren().add(img);
        return img;
    }

    private void onUpdate() {
        if(gameStart)
        {
            finishTime=System.currentTimeMillis();
            time.setText("TIME : "+(period+finishTime-startTime)/1000+" SEC ");
        }
        for (int i=0;i<fish.size();i++)
        {
            if(fish.get(i).getTranslateX()>=800)
            {
                if(collision.contains(fish.get(i)))
                {
                    collision.remove(fish.get(i));
                }
                fish.remove(i);
                i--;
            }
          else
                fish.get(i).setTranslateX(fish.get(i).getTranslateX() + Math.random() * 15);
        }
           
        if (Math.random() < 0.018) {
            fish.add(spawnfish());
        }
        checkState();
    }

    private void checkState() {
        for (Node fi : fish) 
        {
            if (fi.getBoundsInParent().intersects(frog.getBoundsInParent())&&!collision.contains(fi)) 
            {
                if(life>0)
                {
                    collision.add(fi);
                    root.getChildren().remove(lifes.get(life-1));
                    life--;
                }
                else
                {
                    gameFinished(" YOU LOSE !");
                }
                return;
            }
        }

        if (frog.getTranslateY() <40) 
        {
            gameFinished(" YOU WIN !");
        }
    }
    
    private void gameFinished(String condition)
    {
        timer.stop();
        running=false;
        HBox hBox = new HBox();
        hBox.setTranslateX(255);
        hBox.setTranslateY(250);
        
        ImageView img = null;
        String path=new String();
        if(condition.equals(" YOU WIN !"))
            path="res/win.png";
        else
            path="res/lose.png";
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            img = new ImageView(new Image(is));
            img.setFitWidth(90);
            img.setFitHeight(90);
            is.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        hBox.getChildren().add(img);
        root.getChildren().addAll(newGameMenu,hBox);
        for (int i = 0; i < condition.toCharArray().length; i++) 
        {
            char letter = condition.charAt(i);
            Text text = new Text(String.valueOf(letter));
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 60));
            text.setOpacity(0);
            text.setFill(Color.CYAN);
            hBox.getChildren().add(text);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.15));
            ft.play();
        }
    }
    public void start(Stage stage ) throws Exception {
        stage.setScene(createContent(stage));
        stage.setTitle("JUMP! FROG!");
        stage.show();
    }
}
