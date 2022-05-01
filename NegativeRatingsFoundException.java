public class NegativeRatingsFoundException extends RuntimeException
{
 
    public NegativeRatingsFoundException()
    {
        super("Negative ratings have been found. Do not change the ratings, please.");
    }
 
}
