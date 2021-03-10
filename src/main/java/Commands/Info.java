package Commands;

import java.util.*;
import Other.*;

/** Команда выводит информацию о коллекции в консоль */
public class Info extends Command{

    /** Конструктор - создание нового объекта
     * @param ch - обработчик команд
     */
    public Info(CommandHandler ch){
        super(ch);
    }

    /** Главный метод класса, запускает команду
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    @Override
    public boolean execute(String...args) {
        if (args == null) {
            LinkedList<Person> people = ch.getPeople();
            Collections.sort(people);
            System.out.println("Тип коллекции: " + people.getClass());
            System.out.println("Тип элементов: Person");
            System.out.println("Количество элементов: " + people.size());
            System.out.println("Дата инициализации: " + people.get(0).getTime());
            return true;
        }
        else {
            System.out.println("У команды info нет аргументов. Введите команду снова.");
            return false;
        }
    }

    /** Возвращает имя команды
     * @return имя
     */
    @Override
    public String getName() {
        return "info";
    }

    /** Возвращает описание команды
     * @return описание
     */
    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
