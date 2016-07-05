package comp3350.dailysprint.objects;

public class Message
{

    private String to;
    private String from;
    private String type;
    private String message;

    public Message(String to, String from, String type, String message)
    {
        this.to = to;
        this.from = from;
        this.type = type;
        this.message = message;
    }


    public String getTo() {return to;}
    public String getFrom() {return from;}
    public String getType() {return type;}
    public String getMessage() {return message;}

    public boolean equals(Object object)
    {
        boolean result;
        Message check;

        result = false;

        if (object instanceof Message)
        {
            check = (Message) object;
            if (check.getTo().equals(to) && check.getFrom().equals(from) && check.getType().equals(type) && check.getMessage().equals(message))
            {
                result = true;
            }
        }
        return result;
    }

}
