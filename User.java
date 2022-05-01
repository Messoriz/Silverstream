import java.io.*;
import java.util.*;
import java.nio.file.*;
public final class User {
    private String name;
    private String password;
    private boolean administrator;
    private boolean adult;

    public User(String name, String password, boolean adult, boolean administrator){
        this.name = name;
        this.password = password;
        this.adult = adult;
        this.administrator = administrator;
    }

    public void setAdministrator(){
        administrator = true;
    }

    public boolean isAdministrator(){
        return administrator;
    }

    public String getName(){
        return name;
    }

    public boolean isAdult(){
        return adult;
    }

    public String getPassword(){
        return password;
    }
}