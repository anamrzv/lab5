package commands;

import other.CommandHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Команда получает текстовый файл со скриптом и выполняет команды оттуда
 */
public class ExecuteScript extends Command {

    /**
     * Конструктор - создание нового объекта
     *
     * @param ch - обработчик команд
     */
    public ExecuteScript(CommandHandler ch) {
        super(ch);
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    @Override
    public boolean execute(String... args) {
        if (args.length == 1) {
            try {
                Path path = Paths.get(args[0]);
                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    System.out.println("Нельзя передать специальный файл в качестве скрипта. Введите команду снова с новым аргументом.");
                } else {
                    String dir = path.toString();
                    try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                        Map<String, Command> commands = ch.getMap();
                        String line;
                        while ((line = br.readLine()) != null) {
                            line = line.trim();
                            if (line.equals("execute_script " + args[0])) {
                                System.out.println("В скрипте обнаружена рекурсия, удалите строку execute_script " + args[0]);
                                return false;
                            } else {
                                String cmd = ch.parseCommand(line);
                                String[] arguments = ch.getArguments(line);
                                Command command = commands.get(cmd);
                                command.execute(arguments);
                                System.out.println();
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден. Убедитесь, что вы правильно указали путь к файлу и введите команду снова.");
                        return false;
                    } catch (Exception e) {
                        System.out.println("Произошла ошибка при чтении команды скрипта.");
                        return false;
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при обработке файла. Проверьте, что команде не был передан специальный файл.");
            }
        } else {
            System.out.println("У команды execute_script должен быть один аргумент - путь к файлу. Введите команду снова.");
            return false;
        }
        return true;
    }


    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла (вместо file_name укажите путь к файлу). В скрипте содержатся команды в таком же виде, в котором они вводятся в интерактивном режиме.";
    }
}
