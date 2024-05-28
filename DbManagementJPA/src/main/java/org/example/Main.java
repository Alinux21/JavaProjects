package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.*;
import org.example.JDBC.BookDAO;
import org.example.JDBC.JDBCEntities.JDBCBook;
import org.example.JPA.AbstractRepository;
import org.example.JPA.JPAentities.BaseEntity;


public class Main {
    public static void main(String[] args) {


        //Demonstration of the DAO
        daoDemo();



        //Solving the model with JDBC
        BookSelectionSolver bookSelectionSolver = new BookSelectionSolver();
        BookDAO JDBCbookDAO = new BookDAO();
        List<JDBCBook> books  = JDBCbookDAO.findAll(1000);
        bookSelectionSolver.solve(books, 10, 1);




    }

    public static void loggerSetup(){
        Logger logger = Logger.getLogger(AbstractRepository.class.getName());
        Handler consoleHandler = new ConsoleHandler();
        Handler fileHandler = null;
        try {
            fileHandler = new FileHandler("src/main/java/org/example/app.log", true);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occur in FileHandler.", e);
        }

        // Assigning handlers to LOGGER object
        logger.addHandler(consoleHandler);
        if (fileHandler != null) {
            logger.addHandler(fileHandler);
        }

        // Setting levels to handlers and LOGGER
        consoleHandler.setLevel(Level.ALL);
        if (fileHandler != null) {
            fileHandler.setLevel(Level.ALL);
        }

        SimpleFormatter simpleFormatter = new SimpleFormatter();
        Objects.requireNonNull(fileHandler).setFormatter(simpleFormatter);

        logger.setLevel(Level.ALL);
    }


    public static void daoDemo(){
        Properties properties = new Properties();
        InputStream input = null;
        try{

            input = new FileInputStream("src/main/resources/application.properties");
            properties.load(input);

            DAOFactory daoFactory = DAOFactory.getDAOFactory(Integer.parseInt(properties.getProperty("daoType")));

            //Using the Logger only when running JPA
            if(daoFactory instanceof JPADAOFactory) {
                loggerSetup();
            }

            //Demonstration on how the DAO (Repositories) work
            //Change the daoType in application.properties to 1 for JDBC and 2 for JPA
            //The code below remains the same for both JPA and JDBC

            BookDAOInterface<BaseEntity> bookDAO = daoFactory.getBookDAO();
            GenreDAOInterface<BaseEntity> genreDAO = daoFactory.getGenreDAO();
            AuthorDAOInterface<BaseEntity> authorDAO = daoFactory.getAuthorDAO();


            if(bookDAO.findByName("Creepers").isPresent()){
                System.out.println(bookDAO.findByName("Creepers").get());
            }
            BaseEntity book = bookDAO.findById(28915).get();

            if(book != null){
                bookDAO.save(book);
            }

            if(authorDAO.findByName("Robert Greene").isPresent()){
                System.out.println(authorDAO.findByName("Robert Greene").get());
            }
            BaseEntity author = authorDAO.findById(30922).get();
            if(author != null){
                authorDAO.save(author);
            }

            if(genreDAO.findByName("Scientific").isPresent()){
                System.out.println(genreDAO.findByName("Scientific").get());
            }
            BaseEntity genre = genreDAO.findById(31).get();
            if(genre != null){
                genreDAO.save(genre);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (input != null) {
                try {
                    properties.load(input);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}