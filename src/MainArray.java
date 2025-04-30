import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for ArrayStorage implementation.
 * (just run, no need to understand)
 */
public class MainArray {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - " +
                    "(list | size | save uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume();
                    r.uuid = uuid;
                    boolean isSaved = ARRAY_STORAGE.save(r);
                    System.out.println(isSaved ? r.uuid + " saved" : "Resume is existing");
                    printAll();
                    break;
                case "delete":
                    boolean isDeleted = ARRAY_STORAGE.delete(uuid);
                    System.out.println(isDeleted ?
                            uuid + " deleted" :
                            "The resume does not exist");
                    printAll();
                    break;
                case "get":
                    Resume resume = ARRAY_STORAGE.get(uuid);
                    System.out.println(resume == null ?
                            "The resume does not exist" :
                            resume.toString());
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    System.out.println("Cleared");
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда");
                    break;
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
