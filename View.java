import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.JScrollPane;
import java.nio.file.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.*;
import javax.swing.border.*;

public class View extends JFrame implements Observer{
    private ActionListener actionListener;
    private JPanel loginPanel, registerPanel, profilePanel, mediaPanel, playPanel;
    private JScrollPane mainScrollPane;
    private JTextField userText;
    private JPasswordField passwordText;
    private JTextField searchField;
    private int numberOfButtons;

    public View (){
        loginPanel = new JPanel();
        mainScrollPane = new JScrollPane();
    }

    @Override
    public void update(Observable o, Object arg){
        if(arg != null){
            mainView((ArrayList<Media>) arg);
        }
        else{
            profileView(((Model)o).getCurrentUser());
        }
    }

    public void makeFrame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,650);
        this.setVisible(true);
        this.validate();

    }

    public void updateFrame(String display){

        this.setTitle(display);
        this.getContentPane().removeAll();
        switch(display){
            case "Login": this.add(loginPanel); break;
            case "Silverstream" : this.add(mainScrollPane); break;
            case "Register" : this.add(registerPanel); break;
            case "Profile" : this.add(profilePanel); break;
            case "Media" : this.add(mediaPanel); break;
            case "Play" : this.add(playPanel); break;
        }
        //this.pack();
        this.revalidate();
        this.repaint();
    }

    public void loginView(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(150,150,150,150));
        panel.setBackground(Color.DARK_GRAY);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome to Silverstream");
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        panel2.add(welcomeLabel);

        Dimension minSize = new Dimension(20, 20);
        Dimension prefSize = new Dimension(20, 20);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 20);
        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.white);
        panel2.add(userLabel);

        userText = new JTextField(20);
        panel2.add(userText);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.white);
        panel2.add(passwordLabel);

        passwordText = new JPasswordField(20);
        panel2.add(passwordText);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton loginButton = new JButton("Login");
        loginButton.setActionCommand("Login");
        loginButton.addActionListener(actionListener);
        panel2.add(loginButton);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton registerButton = new JButton("Register");
        registerButton.setActionCommand("RegisterView");
        registerButton.addActionListener(actionListener);
        panel2.add(registerButton);

        panel.add(panel2);
        loginPanel =panel;
        updateFrame("Login");
    }

    public void loginError(String title, String info){
        JOptionPane.showMessageDialog(null, info , "Info: " + title, JOptionPane.INFORMATION_MESSAGE);

    }

    public void mainView(ArrayList<Media> medias){
        numberOfButtons = medias.size();
        JPanel panel = new JPanel(new GridLayout(0,5,5,5));
        panel.setBackground(Color.DARK_GRAY);
        JButton[] buttons = new JButton[(medias.size())];
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for (int i = 0; i < medias.size(); i++){
            buttons[i] = new JButton(new ImageIcon(medias.get(i).getPicture()));
            buttons[i].setBorder(null);
            buttons[i].setBorderPainted(false);
            buttons[i].setPreferredSize( new Dimension(140, 209));
            buttons[i].addActionListener(actionListener);
            buttons[i].setActionCommand(String.valueOf(i));
            panel.add(buttons[i]);
        }

        JPanel panel2 = new JPanel(new GridLayout(2,3,5,5));
        panel2.setBackground(Color.DARK_GRAY);

        JButton filmsButton = new JButton("Films");
        filmsButton.setPreferredSize( new Dimension(40, 30));
        filmsButton.setActionCommand("Films");
        filmsButton.addActionListener(actionListener);
        panel2.add(filmsButton);

        JButton seriesButton = new JButton("Series");
        seriesButton.setPreferredSize( new Dimension(40, 30));
        seriesButton.setActionCommand("Series");
        seriesButton.addActionListener(actionListener);
        panel2.add(seriesButton);

        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.setPreferredSize( new Dimension(40, 30));
        favoritesButton.setActionCommand("Favorites");
        favoritesButton.addActionListener(actionListener);
        panel2.add(favoritesButton);

        searchField = new JTextField(15);
        searchField.setPreferredSize( new Dimension(25, 25));
        
        JButton search = new JButton("Search");
        search.setPreferredSize( new Dimension(75, 25));
        search.setActionCommand("Search");
        search.addActionListener(actionListener);
        JPanel searchBox = new JPanel();
        searchBox.add(searchField);
        searchBox.add(search);
        searchBox.setBackground(Color.DARK_GRAY);
        panel2.add(searchBox);

        JButton profileButton = new JButton("Profile");
        profileButton.setPreferredSize( new Dimension(40, 30));
        profileButton.setActionCommand("Profile");
        profileButton.addActionListener(actionListener);
        panel2.add(profileButton);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize( new Dimension(40, 30));
        logOutButton.setActionCommand("Log Out");
        logOutButton.addActionListener(actionListener);
        panel2.add(logOutButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setColumnHeaderView(panel2);
        scrollPane.setViewportView(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mainScrollPane = scrollPane;
        updateFrame("Silverstream");

    }

    public void registerView(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setOpaque(false);

        JLabel usernameLabel = new JLabel("Enter Username");
        usernameLabel.setBounds(10, 10, 140, 25);
        usernameLabel.setForeground(Color.white);
        panel2.add(usernameLabel);

        Dimension minSize = new Dimension(10, 10);
        Dimension prefSize = new Dimension(10, 10);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 10);
        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        userText = new JTextField(20);
        userText.setBounds(120, 10, 160, 25);
        panel2.add(userText);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel passwordLabel = new JLabel("Enter Password");
        passwordLabel.setBounds(10, 40, 140, 25);
        passwordLabel.setForeground(Color.white);
        panel2.add(passwordLabel);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 40, 160, 25);
        panel2.add(passwordText);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel ageLabel = new JLabel("Adult or Child?");
        ageLabel.setBounds(10, 70, 140, 25);
        ageLabel.setForeground(Color.white);
        panel2.add(ageLabel);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        String genre[]={"Adult", "Child"};  
        JComboBox g = new JComboBox(genre);
        g.setBounds(120, 70, 160, 25);
        panel2.add(g);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(180, 110, 100, 25);
        registerButton.setActionCommand("Register");
        registerButton.addActionListener(actionListener);
        panel2.add(registerButton);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton backRegister = new JButton("Back");
        backRegister.setBounds(180, 140, 100, 25);
        backRegister.setActionCommand("registerBack");
        backRegister.addActionListener(actionListener);
        panel2.add(backRegister);

        panel.add(panel2);

        registerPanel = panel;
        updateFrame("Register");

    }

    public void profileView(User user){
        Random randomAge = new Random();
        int age;

        if(user.isAdult()){
            age = randomAge.nextInt(83) +18;;
        }
        else{
            age = randomAge.nextInt(17) +1;
        }
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(150,150,150,150));
        panel.setBackground(Color.DARK_GRAY);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username: " + user.getName());
        usernameLabel.setBounds(10, 10, 140, 25);
        usernameLabel.setForeground(Color.white);
        panel2.add(usernameLabel);

        Dimension minSize = new Dimension(20, 20);
        Dimension prefSize = new Dimension(20, 20);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 20);
        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel passwordLabel = new JLabel("Password: " + user.getPassword());
        passwordLabel.setBounds(10, 40, 140, 25);
        passwordLabel.setForeground(Color.white);
        panel2.add(passwordLabel);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel ageLabel = new JLabel("Age: " + age);
        ageLabel.setBounds(10, 70, 140, 25);
        ageLabel.setForeground(Color.white);
        panel2.add(ageLabel);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JLabel adminLabel = new JLabel("Administrator: " + user.isAdministrator());
        adminLabel.setBounds(10, 100, 140, 25);
        adminLabel.setForeground(Color.white);
        panel2.add(adminLabel);

        panel2.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton backRegister = new JButton("Back");
        backRegister.setBounds(180, 140, 100, 25);
        backRegister.setActionCommand("back");
        backRegister.addActionListener(actionListener);
        panel2.add(backRegister);

        panel.add(panel2);

        profilePanel = panel;
        updateFrame("Profile");

    }

    public void mediaView(Media media, boolean isFavorite) {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setBorder(new EmptyBorder(150,150,150,150));
        panel1.setBackground(Color.DARK_GRAY);

        JPanel panel11 = new JPanel();
        panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));
        panel11.setOpaque(false);

        JLabel pictureLabel = new JLabel(new ImageIcon(media.getPicture()));
        pictureLabel.setPreferredSize( new Dimension(140, 209));
        panel11.add(pictureLabel);

        Dimension minSize = new Dimension(20, 20);
        Dimension prefSize = new Dimension(20, 20);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 20);
        panel11.add(new Box.Filler(minSize, prefSize, maxSize));

        JPanel panel12 = new JPanel();
        panel12.setLayout(new BoxLayout(panel12, BoxLayout.PAGE_AXIS));
        panel12.setBackground(Color.DARK_GRAY);

        JLabel year = new JLabel("Year: " + Integer.toString(media.getYear()));
        year.setFont(new Font("Serif", Font.BOLD, 25));
        year.setForeground(Color.WHITE);
        panel12.add(year);
        
        String genre1 = media.getGenre().get(0);
        String genre2 = "";
        String genre3 = "";
        if(media.getGenre().size() > 1){
            genre2 = ", " + media.getGenre().get(1);
        }
        if(media.getGenre().size() > 2){
            genre3 = ", " + media.getGenre().get(2);
        }

        JLabel genres = new JLabel("Genres: " + genre1 + genre2 + genre3);
        genres.setFont(new Font("Serif", Font.BOLD, 25));
        genres.setForeground(Color.WHITE);
        panel12.add(genres);

        JLabel rating = new JLabel("Rating: " + String.valueOf(media.getRating()));
        rating.setFont(new Font("Serif", Font.BOLD, 25));
        rating.setForeground(Color.WHITE);
        panel12.add(rating);

        panel11.add(panel12);
        panel1.add(panel11);
        panelMain.add(panel1, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();  
        if(!isFavorite){
            JButton addToFavorites = new JButton("Add to Favorites <3");
            addToFavorites.setPreferredSize( new Dimension(170, 50));
            addToFavorites.setActionCommand("favorites");
            addToFavorites.addActionListener(actionListener);
            panel2.add(addToFavorites);
        }
        else{
            JButton removeFromFavorites = new JButton("Remove from Favorites :(");
            removeFromFavorites.setPreferredSize( new Dimension(170, 50));
            removeFromFavorites.setActionCommand("notFavorite");
            removeFromFavorites.addActionListener(actionListener);
            panel2.add(removeFromFavorites);
        }
        JButton play = new JButton("Play");
        play.setPreferredSize( new Dimension(170, 50));
        play.setActionCommand("play");
        play.addActionListener(actionListener);
        panel2.add(play);

        JButton backMain = new JButton("Back");
        backMain.setPreferredSize( new Dimension(150, 50));
        backMain.setActionCommand("back");
        backMain.addActionListener(actionListener);
        panel2.add(backMain);

        panel2.setBackground(Color.GRAY);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);

        JLabel title = new JLabel(media.getTitle());
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        panel3.add(title);
        panelMain.add(panel3, BorderLayout.NORTH);

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.GRAY);

        panelMain.add(panel4, BorderLayout.WEST);
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.GRAY);
        panelMain.add(panel5, BorderLayout.EAST);

        JPanel panel7 = new JPanel();
        panel7.setLayout(new BoxLayout(panel7, BoxLayout.PAGE_AXIS));
        if(media instanceof Series){
            JPanel panel6 = new JPanel();
            JLabel seasons = new JLabel("Seasons: ");
            String[] sesonsS = ((Series)media).getSeasons().toArray(new String[0]);  
            JComboBox seasonsCB = new JComboBox(sesonsS);
            panel6.add(seasons);
            panel6.add(seasonsCB);

            JLabel episodes = new JLabel("Episodes: ");
            Integer[] episodesS = {1,2,3,4,5,6};  
            JComboBox episodesCB = new JComboBox(episodesS);
            panel6.add(episodes);
            panel6.add(episodesCB);

            panel6.setBackground(Color.GRAY);
            panel7.add(panel6);
        }
        panel7.setBackground(Color.GRAY);
        panel7.add(panel2);
        panelMain.add(panel7, BorderLayout.SOUTH);

        mediaPanel = panelMain;
        updateFrame("Media");

    }
    
    public void playMedia(Media media) {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setBorder(new EmptyBorder(150,150,150,150));
        panel1.setBackground(Color.DARK_GRAY);

        JPanel panel11 = new JPanel();
        panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));
        panel11.setOpaque(false);

        JLabel pictureLabel = new JLabel(new ImageIcon(media.getPicture()));
        pictureLabel.setPreferredSize( new Dimension(140, 209));
        panel11.add(pictureLabel);

        panel1.add(panel11);
        panelMain.add(panel1, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();  
        panel2.setBackground(Color.GRAY);
        
        JButton backMain = new JButton("Back");
        backMain.setPreferredSize( new Dimension(150, 50));
        backMain.setActionCommand("backMedia");
        backMain.addActionListener(actionListener);

        panel2.add(backMain);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);

        JLabel title = new JLabel(media.getTitle());
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        panel3.add(title);
        panelMain.add(panel3, BorderLayout.NORTH);

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.GRAY);

        panelMain.add(panel4, BorderLayout.WEST);
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.GRAY);
        panelMain.add(panel5, BorderLayout.EAST);

        JPanel panel7 = new JPanel();
        panel7.setLayout(new BoxLayout(panel7, BoxLayout.PAGE_AXIS));
        panel7.setBackground(Color.GRAY);
        panel7.add(panel2);
        panelMain.add(panel7, BorderLayout.SOUTH);

        playPanel = panelMain;
        updateFrame("Play");

    }
    
    public void addController(ActionListener actionListener){
        this.actionListener = actionListener;
    }

    public JTextField getUserText(){
        return userText;
    }

    public JPasswordField getPasswordText(){
        return passwordText;
    }

    public JTextField getSearchField(){
        return searchField;
    }

    public int getNumberOfButtons(){
        return numberOfButtons;
    }
}
