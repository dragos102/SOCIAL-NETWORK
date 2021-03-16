package socialnetwork.domain.validators;

public class ValidatorUi {

    public void validate_name(String name)
    {
        if(Character.isUpperCase(name.charAt(0))==false)
            throw new ValidationException("Name must start with uppercase");

        if(name.equals(""))
            throw new ValidationException("Name should contain something");

        if(name.trim().equals(""))
            throw new ValidationException("name should contain something");

    }
    public void validate_day_year(String a,String c)
    {
        if(is_int(a)==false)
        {
            throw new ValidationException("day must be an int");
        }
        int d=Integer.parseInt(a);
        if(d>31 && d<0)
        {
            throw new ValidationException("day must be between 0 and 31");
        }
        if(is_int(c)==false)
        {
            throw new ValidationException("year must be an int");
        }

    }
    public void validate_id(String id)
    {
       if(is_int(id)== false)
           throw new ValidationException("id must be an int");
    }



    public void validate_month(String month1)
    {
        String month=month1.toUpperCase();
        if(!month.toUpperCase().equals("JANUARY") && !month.toUpperCase().equals("FEBRUARY") && !month.toUpperCase().equals("MARCH")
         && !month.toUpperCase().equals("APRIL") && !month.toUpperCase().equals("MAY") && !month.toUpperCase().equals("JUNE") &&
                !month.toUpperCase().equals("JULY") && !month.toUpperCase().equals("AUGUST") && !month.toUpperCase().equals("OCTOBER")
                && !month.toUpperCase().equals("NOVEMBER") && !month.toUpperCase().equals("DECEMBER") && !month.toUpperCase().equals("SEPTEMBER"))
        {
            throw new ValidationException("THIS ISN'T A MONTH");
        }
    }
    public static boolean is_int(String id)
    {
        try{
            int i = Integer.parseInt(id);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }


}
