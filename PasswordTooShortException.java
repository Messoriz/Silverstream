public class PasswordTooShortException extends Exception
{
        
    public PasswordTooShortException()
    {
        super("Your password is too short. Please try again");          
        
    }    

}