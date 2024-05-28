package homework;

import java.io.*;

import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Main app = new Main();

                                  
        try {
            app.excel();                                //the bonus part 
        } catch (FileNotFoundException e) {
            System.out.println("The excel file was not found:" + e);
        } catch (IOException e) {
            System.out.println("Error at intializing the excel file :" + e);
        }

    }

    private void excel() throws FileNotFoundException, IOException {

        File excel = new File("C:\\Users\\alinm\\Desktop\\java_course_fii_2024\\lab5\\homework+bonus\\repo\\abilities.xlsx");

        // Create a FileInputStream object
        // for getting the information of the file

        FileInputStream fip;
        fip = new FileInputStream(excel);
        XSSFWorkbook workbook = new XSSFWorkbook(fip);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        Map<String,Integer> abilityAppearances = new HashMap<>();
        Map<String,List<String>> persons = new HashMap<>();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();

            // For each row, iterate through all the columns (two in total)
            Iterator<Cell> cellIterator = row.cellIterator();

            Cell employerName= cellIterator.next();
            Cell employerAbilities = cellIterator.next();

            String name = employerName.getStringCellValue();
            List<String> abilities = new ArrayList<String>(Arrays.asList(employerAbilities.getStringCellValue().split(",")));
            abilities.stream().forEach(ability -> abilityAppearances.merge(ability, 1, Integer::sum));

            System.out.println("name:"+name+" abilities:"+abilities);
            persons.put(name, abilities);
        }

            Integer max = abilityAppearances.values().stream().mapToInt(Integer::intValue).max().getAsInt();

            List<String> mostCommonAbilities = new ArrayList<>();
            for (Map.Entry<String,List<String>> person : persons.entrySet()) {
                person.getValue().stream().forEach(ability -> {
                    if(abilityAppearances.get(ability)==max && !mostCommonAbilities.contains(ability)){
                        mostCommonAbilities.add(ability);
                    }
                });
            }

            System.out.println("\nMost common abilities are :"+mostCommonAbilities);

            mostCommonAbilities.stream().forEach(ability->{
                System.out.println("\nMax card set for the ability:"+ability);
                persons.entrySet().stream()
                .filter(entry -> entry.getValue().contains(ability))
                .forEach(person->System.out.println(person));
            });

            workbook.close();                    

        

    }

}
