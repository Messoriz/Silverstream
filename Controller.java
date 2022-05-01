import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.JButton;

public class Controller implements ActionListener{
    protected View view;
    protected Model model;
    private String lastAction;

    public Controller (Model model, View view){

        this.model = model;
        this.view = view;
        model.addObserver(view);
        view.addController(this);
        view.makeFrame();
        view.loginView();
    }

    public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand();
        switch(action){
            case "Login": loginButton(); break;
            case "RegisterView" : view.registerView();; break;
            case "Register" : registerButton(); break;
            case "Profile" : model.profile(); break;
            case "Favorites" : favoritesButton(); break;
            case "Films" : model.initialize(model.getFilms()); break;
            case "Series" : model.initialize(model.getSeries()); break;
            case "Search" : search(); break;
            case "Log Out": view.loginView(); break;
            case "registerBack" : view.loginView(); break;
            case "back" : model.initialize(model.getFilms()); break;
            case "favorites" : addFavorite(); break;
            case "notFavorite" : deleteFavorite(); break;
            case "play" : playMedia(lastAction); break;
            case "backMedia" : displayMedia(lastAction); break;
            default:
            if(action.matches("\\d*")){
                displayMedia(action);
            }
            break;
        }
    }

    public void registerButton() {

        int q = model.addUser(view.getUserText().getText(), view.getPasswordText().getText(), true, false);
        if(q == 2)
        {
            String title = "Register Complete";
            String info = "You are now ready to login";
            view.loginError(title, info);
            view.loginView();
        }
        else if(q == 1) {
            String title = "Register Error";
            String info = "Illegal username";
            view.loginError(title, info);
            view.registerView();
        }
        else if(q == 0){
            String title = "Register Error";
            String info = "This username already exists";
            view.loginError(title, info);
            view.registerView();
        }else if(q == 3)
        {
            String title = "Register Error";
            String info = "Your password is fewer than 8 characters. Please, try again";
            view.loginError(title, info);
            view.registerView();            
        }

    }

    public void loginButton(){
        if(model.login(view.getUserText().getText(), view.getPasswordText().getText()) == true){
            model.initialize(model.getFilms());
        }else{
            String title = "Login Error";
            String info = "Wrong username or password";
            view.loginError(title, info);
            view.loginView();
        }
    }

    public void favoritesButton(){
        ArrayList<Media> medias = new ArrayList<Media>();
        try{
            medias.addAll(model.addPictures(model.read(model.getCurrentUser().getName() + ".txt"), "Films"));
            medias.addAll(model.addPictures(model.read(model.getCurrentUser().getName() + ".txt"), "Series"));
            model.initialize(medias);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void search(){
        ArrayList<Media> medias = new ArrayList<Media>();
        medias.addAll(model.searchTitle(model.getFilms(),view.getSearchField().getText()));
        medias.addAll(model.searchTitle(model.getSeries(),view.getSearchField().getText()));
        medias.addAll(model.searchGenre(model.getFilms(),view.getSearchField().getText()));
        medias.addAll(model.searchGenre(model.getSeries(),view.getSearchField().getText()));
        model.initialize(medias);
        if(medias.size() == 0){

            String title = "Search Error";
            String info = "No match found, please try again";
            view.loginError(title, info);
            model.initialize(model.getFilms());
        }
    }

    public void playMedia(String lastAction){
        view.playMedia(model.getCurrentMedias().get(Integer.parseInt(lastAction)));

    }

    public void displayMedia(String action){
        try{
            boolean isFavorite = model.isFavorite(model.getCurrentUser(), model.getCurrentMedias().get(Integer.parseInt(action)));
            view.mediaView(model.getCurrentMedias().get(Integer.parseInt(action)), isFavorite);
            lastAction = action;
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void addFavorite(){
        try{
            model.addFavourite(model.getCurrentUser(), model.getCurrentMedias().get(Integer.parseInt(lastAction)));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        displayMedia(lastAction);
    }

    public void deleteFavorite(){
        try{
            model.deleteFavorite(model.getCurrentUser(), model.getCurrentMedias().get(Integer.parseInt(lastAction)));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        displayMedia(lastAction);
    }
}