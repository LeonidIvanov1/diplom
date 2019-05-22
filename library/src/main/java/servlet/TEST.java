package servlet;

public class TEST {

    public static void main(String[] args) {
        String name = "ВВЕДЕНИЕ В СИСТЕМЫ " +
                "БАЗ ДАННЫХ";
        String author ="П.В. Бураков, В.Ю. Петров";
        String year = "2017";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 40; i++) {
            String result = "ADD_LIBRARY_FUND('"+name+"', '"+author+"', '"+ (10500 + i)+"', " +
                    "'"+year+"', null, '52', 1, 1, null);\n";
            sb.append(result);
        }

        System.out.println(sb.toString());
    }
}
