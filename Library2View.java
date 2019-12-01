/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author frith
 */
import javafx.application.Platform;
import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import model.Library2Model;
import model.Book;
import model.Author;

public class Library2View extends StackPane {
    
    private Library2Model model;
    private Stage stage;
    private Library2Controller controller;
    private Canvas canvas;
    
    
    
    

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    public Library2View(Library2Model model,Stage stage) {
        this.model = model;
        this.stage = stage;
        
        controller = new Library2Controller(model, this);
        
        loginScene();
        
      

       
    }
    
    public void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
       
        alert.show();
        
        //use these for changing review?
        //alert.setDialogPane(value);
      
        //alert.setOnCloseRequest(value);
    }
  
    public void loginScene(){
        
        this.getChildren().clear();   
         
        Label loginlabel = new Label("Enter username and password");
        loginlabel.setTranslateX(40);
        loginlabel.setTranslateY(0);
        
        Label unlabel = new Label("Username:");
        unlabel.setTranslateX(-100);
        unlabel.setTranslateY(40);

        
        Label pwlabel = new Label("password:");
        pwlabel.setTranslateX(-100);
        pwlabel.setTranslateY(120);
        
        Button loginButton = new Button("Log in");
        loginButton.setTranslateY(200);
        
        Button lurker = new Button("Browse");
        lurker.setTranslateY(200);
        lurker.setTranslateX(-100);

        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(unlabel,pwlabel,loginlabel,loginButton,lurker);    
        
        
        TextField untxt1 = new TextField();
        untxt1.setText("Username");
        untxt1.setTranslateX(50);
        untxt1.setTranslateY(50);
        untxt1.setMaxSize(200, 50);
        TextField pwtxt2 = new TextField();
        pwtxt2.setText("Password");
        pwtxt2.setTranslateX(50);
        pwtxt2.setTranslateY(120);
        pwtxt2.setMaxSize(200, 50);
        
        
        loginButton.setOnMousePressed(e->{
            controller.handleCheckLogin(untxt1.getText(),pwtxt2.getText());
        });

        
        
        this.getChildren().addAll(untxt1,pwtxt2,stack);
        
        untxt1.clear();
        pwtxt2.clear();
        
        untxt1.setOnMouseClicked(ke-> {
            untxt1.requestFocus();
            untxt1.setMouseTransparent(true);
            untxt1.setEditable(true);
            
        });
        
        pwtxt2.setOnMouseClicked(ke-> {
            pwtxt2.requestFocus();
            pwtxt2.setMouseTransparent(true);
            pwtxt2.setEditable(true);
        });
        
        
        
        lurker.setOnMousePressed(e->{
            //controller.handlePrintQuery("Query1");
            model.setPassword("321");
            model.setUsername("lurker");
            bigTableBookScene();
        });
        
        
        
        //use this when changing view
        //this.getChildren().clear();            
            
    }
    
    public void mainMenuScene(){
        
        this.getChildren().clear();
        
        ComboBox<String> choice1 = new ComboBox<String>();
        
        String Query1 = "Query1";
        String Query2 = "Query2";
        
        choice1.getItems().addAll(Query1,Query2);
        
        choice1.setTranslateX(-200);
        choice1.setTranslateY(-200);
        
        //choice1.setPromptText(Query1);
        
        
        
        Button queryButton = new Button("Query Selection");
        queryButton.setTranslateX(100);
        queryButton.setTranslateY(150);
        
        
        queryButton.setOnAction(e->{
           // controller.handlePrintQuery(choice1.getValue());
        });
        
        Button booksButton = new Button("books");
        booksButton.setTranslateX(0);
        booksButton.setTranslateY(150);

        
        booksButton.setOnAction(e->{
          
            ArrayList<Book> booklist = model.getBooks();
            System.out.println("|   ISBN      " + " |   Genre   " + "|     Title     |" +
                    "     Publisher     |" + "    |   Authors    |");
            for(Book book : booklist){
            System.out.println(book);
            
            }
            System.out.println();
   
        });
        
       
        Button viewSelection = new Button("show tables");
        
  
        viewSelection.setTranslateX(-100);
        viewSelection.setTranslateY(150);
       
        viewSelection.setOnAction(e->{
            bigTableBookScene();
        });
        
        
        this.getChildren().addAll(queryButton,booksButton,choice1,viewSelection);
        
        
    }
    
    public void bigTableBookScene(){
        
        this.getChildren().clear();
        
        stage.setTitle("Library");
        
        ArrayList<Author>specAuthor = new ArrayList<Author>();
        specAuthor.add(new Author("Josef"));
        
        TableView<Book> table = new TableView<Book>();
        ObservableList<Book> data =
            FXCollections.observableArrayList(
            //new Book("1234567891234", Book.Genre.Fantasy, "hoodtitle","shitcompany",
            //specAuthor)
           // new Book("1234563791234", Book.Genre.Crime, "sometitle","crashco")            
        );
        //data = model.getBooks();
        data = FXCollections.observableArrayList(model.getBooks());
        //System.out.println("books model ree: " + model.getBooks());
        
        
        
        final Label label = new Label("Book List");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("ISBN");
        firstNameCol.setMinWidth(120);
        firstNameCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("isbn"));
        firstNameCol.setEditable(true);
        firstNameCol.setOnEditStart(e->{
            
            
        });
        
        
        TableColumn lastNameCol = new TableColumn("Genre");
        lastNameCol.setMinWidth(60);
        lastNameCol.setCellValueFactory(
        new PropertyValueFactory<Book, Book.Genre>("genre"));
 
        TableColumn emailCol = new TableColumn("Title");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("title"));
        
        TableColumn pubCol = new TableColumn("Publisher");
        pubCol.setMinWidth(100);
        pubCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("publisher"));
        
        TableColumn authorCol = new TableColumn("Authors");
        authorCol.setMinWidth(400);
        authorCol.setCellValueFactory(
        new PropertyValueFactory<Book, ArrayList<Author>>("theAuthors"));
        
        TableColumn reviewCol = new TableColumn("Reviews");
        reviewCol.setMinWidth(100);
        reviewCol.setCellValueFactory(
        new PropertyValueFactory<Book, Button>("reviewButton"));        
        
        //need to be high rank
        TableColumn deleteBookCol = new TableColumn("Delete book");
        deleteBookCol.setMinWidth(100);
        deleteBookCol.setCellValueFactory(
        new PropertyValueFactory<Book, Button>("deleteButton"));
        deleteBookCol.setEditable(true);
        deleteBookCol.setOnEditStart(e->{
            System.out.println("Pure Anger");
            
        });
        
      
 
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol,pubCol,
                authorCol,reviewCol,deleteBookCol);
        
        
        ObservableList<String> hobbies = FXCollections.observableArrayList(
                "Crime", "Mystery", "Romance", "Drama", "Memoir", "Fantasy",
                "Learning","Any");
        ComboBox<String> pickGenre = new ComboBox<>(hobbies);
        pickGenre.setVisibleRowCount(3);
        pickGenre.setValue("Any");
        //pickGenre.setTranslateX(0);
        //pickGenre.setTranslateY(30);
        //pickGenre.getvalue,pickPublisher.getValue or just whatever in a txt
        Button searchButton = new Button("search");
        Button killMeButton = new Button("Kill book");
        Button AddABookButton = new Button("Add book");
        
        //searchButton.setTranslateX(200);
        //searchButton.setTranslateY(0);
        
        /*for(Book aBook : model.getBooks()){
            aBook.getDeleteButton().setOnAction(e->{
                System.out.println("ello");
            });
        }*/
        //causes more fun lambda cancer
       /* if(!model.getBooks().isEmpty()){
            for(int i = 0; i<=model.getBooks().size();i++){
                model.getBooks().get(i).getDeleteButton().setOnAction(e->{
                    System.out.println("Delete button " + i + " pressed");
                });
            }
        }*/
       
        

        
        final TextField checkISBN = new TextField();
        checkISBN.setPromptText("ISBN");
        checkISBN.setMaxWidth(120);
        
        
        final TextField checkTitle = new TextField();
        checkTitle.setPromptText("Title");
        checkTitle.setMaxWidth(120);
        
        final TextField checkAuthor = new TextField();
        checkAuthor.setMaxWidth(120);
        checkAuthor.setPromptText("Author");
        
        final TextField checkPublisher = new TextField();
        checkPublisher.setMaxWidth(120);
        checkPublisher.setPromptText("Publisher");
        
        searchButton.setOnAction(e->{
            
            controller.handleCustomQuery(pickGenre.getValue(),
            checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
            checkAuthor.getText());
          
            
           
        });
        
        AddABookButton.setOnAction(e->{
        
            //How do I use this? :(
            //buildAddBookDialog();
            try{
               controller.handleAddBook(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText()); 
               //Refresh here
                checkISBN.clear();
                checkTitle.clear();
                checkPublisher.clear();
                checkAuthor.clear();
                pickGenre.setValue("Any");
                controller.handleCustomQuery(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText());
            }catch(Error err){
                showAlert(err.getMessage());
            }
            
            
            
        });
        
        killMeButton.setOnAction(e->{
            
            controller.handleDestroyBook(checkISBN.getText());
            //checkISBN.clear();
            
                checkISBN.clear();
                checkTitle.clear();
                checkPublisher.clear();
                checkAuthor.clear();
                pickGenre.setValue("Any");
                controller.handleCustomQuery(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText());
            
        });
        
        
        

        
        
        // just make a button to manually update/delete/add everything using
        // methods that I know how to implement and save some time
        
        
        /*deleteButton.setOnAction(e-> {
        
        
            
        });*/
        int row = 0;
        if(!model.getBooks().isEmpty()){
           // TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
            //row = pos.getRow();
        }
        
        /*TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
int row = pos.getRow();

// Item here is the table view type:
Item item = table.getItems().get(row);

TableColumn col = pos.getTableColumn();

// this gives the value in the selected cell:
String data = (String) col.getCellObservableValue(item).getValue(); */
        

        // still more lambda:)
       // killMeButton.setOnAction(e->{
            //controller.handleDestroyBook(checkISBN.getText());
            //System.out.println(row);
       // });
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(checkISBN,checkTitle,checkAuthor,
                checkPublisher,searchButton,killMeButton,AddABookButton);
        
        
        vbox.getChildren().addAll(label,table,hbox,pickGenre);
 
        this.getChildren().addAll(vbox);
        
        
        
    }
    
    // try making an editable here
    
    

    
    
    
}