package tasksJDBC.task2.dbManagers.creareAdnFill;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConvertDate {

    public ConvertDate() {
    }

    public Date convert(String date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;
        java.sql.Date sqlDate = null;
        try {
            utilDate = formatter.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }
}
