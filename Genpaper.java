package sample;

import insidefx.undecorator.Undecorator;
import insidefx.undecorator.UndecoratorScene;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.util.Collection;
import java.util.Observable;
import java.util.ResourceBundle;
import java.io.*;
import javafx.concurrent.*;
/**
 * Created by Champ on 9/6/2015.
 */

public class Genpaper implements Initializable{
   @FXML
    public ComboBox<String>ComboBox1;
    public Button delete,createPaper,addPattern;
    public Slider distinction,difficulty;
    @FXML
    public Button about;

  public  void backToMain()throws Exception
    {
        Pane root= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(Main.window,root);
        undecoratorScene.addStylesheet("demoapp/demoapp.css");


        Main.window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                we.consume();   // Do not hide yet
                undecoratorScene.setFadeOutTransition();
            }
        });
        Main.window.setScene(undecoratorScene);
    }

    public void comboAction()throws Exception
    {
        distinction.setDisable(false);
        difficulty.setDisable(false);
        createPaper.setDisable(false);
        delete.setDisable(false);

    }
    //to load pattern screen
    public void addNewPattern()throws Exception
    {
        Pane root=FXMLLoader.load(getClass().getResource("GenPattern.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(Main.window,root);


        Main.window.setScene(undecoratorScene);

    }
    public void deletePattern()
    {
       int c=ComboBox1.getSelectionModel().getSelectedIndex(),i=0;
       File f=new File("testPattern");
        TestPattern t=null;
        System.out.println(c);
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {   try
            {if(i++==c){t=(TestPattern)oin.readObject();oin.close();break;}
                oin.readObject();}
            catch(Exception e)
            {oin.close();
                fin.close();
                break;
            }
            }
                if(t.delete(new File("testPattern")))
                {ComboBox1.getItems().remove(ComboBox1.getValue()); new Message().create("Pattern has been delete",Main.window);
                    ComboBox1.getSelectionModel().clearSelection();createPaper.setDisable(true);delete.setDisable(true);
                distinction.setDisable(true);difficulty.setDisable(true);}
        }catch(Exception e)
        {
            System.out.println("agyi re agvyi");
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      File f=new File("testPattern");
        String name=null;

        try
        {

            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {
                try
                {
                    name=((TestPattern)oin.readObject()).name;
                    ComboBox1.getItems().add(name);
                }catch(Exception e)
                {
                    oin.close();
                    break;
                }
            }
        }catch(Exception e)
        {
            System.out.println("error agya bhai jaan");

        }

    }
    public void generateThePattern()throws Exception
    {

        new Wait().createTask().run();

       // try{Thread.sleep(1000);System.out.println("bye");}catch(Exception e){}
        ReadyPaper.temp1=ComboBox1.getSelectionModel().getSelectedItem();
        ReadyPaper.temp3=(int)difficulty.getValue();
        new Thread(new ReadyPaper()).start();
    }
    static Stage p;
    public void openAbout()throws Exception{

    }
}
