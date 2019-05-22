package tablemaker;

import java.util.Arrays;
import java.util.Random;

public class InsertDataMaker {
    private static String fam = "Иванов, Смирнов, Кузнецов, Попов, Васильев, Петров, Соколов, Михайлов, Новиков, Фёдоров, Морозов, Волков, Алексеев, Лебедев, Семёнов, Егоров, Павлов, Козлов, Степанов, Николаев, Орлов, Андреев, Макаров, Никитин, Захаров";
    private static String nam = "Дмитрий, Максим, Даниил, Кирилл, Ярослав, Денис, Никита, Иван, Артём";
    private static String otcch = "Александрович, Алексеевич, Андреевич, Петрович, Иванович, Дмитриевич, Евгеньевич, Сергеевич";

    public static String createStudens() {
        StringBuilder result = new StringBuilder();
        int[] groups = {1, 2, 3, 4};
        String[] families = fam.split(",");
        String[] names = nam.split(",");
        String[] otchestva = otcch.split(",");
        for (int i = 0; i < groups.length * 15; i++) {
            result.append("ADD_USER('login").append(i).append("', 'password").append(i).append("', '").
                    append(getRandomString(families)).append(" ").append(getRandomString(names)).append(" ").
                    append(getRandomString(otchestva)).append("', 4, 2);\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(createStudents());
    }

    public static String getRandomString(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String createStudents() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <=58 ; i++) {
            int group = 1;
            if (i >= 14) {
                group =2;
            }
            if (i >=28) {
                group = 3;
            }
            if (i >=42) {
                group =4;
            }
            sb.append("ADD_STUDENTS(").append(i).append(", ").append(group).
                    append(", '").append((i + 1000)).append("');\n");
        }
        return sb.toString();
    }
}
