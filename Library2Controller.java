/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Author;
import model.Book;
import model.Library2Model;

public class Library2Controller {
    
    private final Library2Model model;
    private final Library2View view;

    public Library2Controller(Library2Model model, Library2View view) {
        this.model = model;
        this.view = view;
    }
    
    public void handleCheckLogin(String username,String password){
        System.out.println(username + " " + password);
        System.out.println("ree");
        try{
            model.tryToConnect(username,password);
            //view.mainMenuScene();
            model.setPassword(password);
            model.setUsername(username);
            view.bigTableBookScene();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        } catch(Exception er){
            System.out.println(er.getMessage());
        }

        
    }
    
    
    

   
   public void handleCustomQuery(String chosenGenre,String chosenISBN,
           String chosenTitle,String chosenPublisher,String chosenAuthor){
                 //"SELECT * FROM Book"  
        String customQuery = "";
        
        customQuery += "Select book.*,authorname from book";

        if("Any".matches(chosenGenre.toString())){  
            customQuery += " join authorof on authorof.isbn = book.isbn";
            customQuery += " join author on author.authorid = authorof.authorid";
            customQuery += " where book.isbn like '%" + chosenISBN + "%'";
            customQuery += " and book.title like '%" + chosenTitle + "%'";
            customQuery += " and book.publisher like '%" + chosenPublisher + "%'";
            customQuery += " and author.authorname like '%" + chosenAuthor + "%'";
           // customQuery += " order by author.authorID ASC";
            customQuery += " order by isbn DESC";
            
        }else{ 
            customQuery += " join authorof on authorof.isbn = book.isbn";
            customQuery += " join author on author.authorid = authorof.authorid";
            customQuery += " where book.genre = '" + chosenGenre+"'"; 
            customQuery += " and book.isbn like '%" + chosenISBN + "%'";
            customQuery += " and book.title like '%" + chosenTitle + "%'";
            customQuery += " and book.publisher like '%" + chosenPublisher + "%'";
            customQuery += " and author.authorname like '%" + chosenAuthor + "%'";
            //customQuery += " order by author.authorID ASC";
            customQuery += " order by isbn DESC";
        }

        //System.out.println(customQuery);
        
        try{
            model.executeQuery2(customQuery);
        }catch(SQLException sqla){
            System.out.println(sqla.getMessage());
        }
        view.bigTableBookScene();
   }
   
   public void handleDeleteBookButton(Button button){
       
      /* if(event.getSource() == deleteButton){
           
       }*/
   }
   
   public void handleSeeReviewsButton(ActionEvent event){
       
   }
   
   public void handleDestroyBook(String anISBN){
        // just make a call and kill any isbn with that number ree
        
        if(anISBN.length() == 13){
            String deleteBookQuery = "";
            deleteBookQuery += "Delete from book where isbn = '"
            + anISBN + "'";
            
            
            try{
                model.executeDeleteBook(deleteBookQuery);
            }catch(SQLException sqla){
                System.out.println(sqla.getMessage());
            } catch(Error erra){
                System.out.println(erra.getMessage());
            }
   
        }

       // view.bigTableBookScene();
       
       
   }
   
    public void handleAddBook(String chosenGenre,String chosenISBN,
           String chosenTitle,String chosenPublisher,String chosenAuthor){
      
        
         //Start bg thread here
       
        /*final javafx.application.Platform.runlater(
        new Runnable(){
            public void run(){
                // do stuff here?
            }
            
        });*/
        
        
        
        boolean goodGenre = false;
        

        if(chosenGenre.matches("Crime")){
           
            goodGenre = true;
        } else if(chosenGenre.matches("Mystery")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Romance")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Drama")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Memoir")) {
           
            goodGenre = true;
        } else if(chosenGenre.matches("Fantasy")) {
           
            goodGenre = true;
        } else if(chosenGenre.matches("Learning")) {
           
            goodGenre = true;           
        } else {
            throw new Error("You didn't enter a viable genre");
            
        }
        if(Book.isValidIsbn(chosenISBN)){
            
        } else{
            throw new Error("bad isbn");
        }
        
        String[] elements = chosenAuthor.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> authorsList = new ArrayList<String>(fixedLenghtList);
        /*ArrayList<Author> authorsList = new ArrayList<Author>();
        for(String atts : attList){
            authorsList.add(new Author(atts));
        }*/
        //System.out.println(authorsList);
        
 
        String addBookQuery = "";

        addBookQuery += "Insert into Book values('" + chosenISBN +"','" +
        chosenGenre+"'" + ",'"+chosenTitle+"',"+"'"+chosenPublisher+"')";
        System.out.println(addBookQuery);
       
       if(!authorsList.isEmpty()){
            try{
                model.executeAddBook(addBookQuery,authorsList,chosenISBN);
            }catch(SQLException sqla){
                System.out.println(sqla.getMessage());
            } catch(Error erra){
                System.out.println(erra.getMessage());
            }
        
       } 

        
        //Kill bg thread here
        //Thread dies
        //updateUI
        
        
        
        //Doesn't actually work cus lambda <3
       // view.bigTableBookScene();
    }
    

    
    
    
}