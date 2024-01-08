import main.java.DatabaseHandler;
import main.java.Task;

import java.sql.SQLException;


public class main{
    public static void main(String[] args) {
        try {
            var db = DatabaseHandler.getInstance();
            //Заполнение таблиц базы данных
            //db.fillDataBase(Parser.parseCSV());
            //Задания
            var task = new Task(db);
            System.out.println("№1.Сформируйте график по показателю экономики объеденив их по странам: ");
            task.countriesEconomyBar(); //№1
            System.out.println("№2.Выведите в консоль страну с самым высоким показателем экономики среди \"Latin America and Caribbean\" и \"Eastern Asia\":");
            task.highEconomyCountry(); //№2
            System.out.println("№3.Найдите страну с \"самыми средними показателями\" среди \"Western Europe\" и \"North America\"");
            task.averageEconomyCountry(); //№3
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
