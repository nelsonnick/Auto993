package com.wts.util;


import com.wts.entity.PersonGG;
import com.wts.entity.PersonKN;
import com.wts.entity.PersonLH;
import com.wts.entity.PersonQY;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Import {
    public static List<PersonQY> ImportQY(String result) throws Exception {
        List<PersonQY> Persons = new ArrayList<PersonQY>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int total = sheet.getLastRowNum();
        for (int i = 1; i < total + 1; i++) {
            PersonQY person = new PersonQY();
            person.setGmsfhm(sheet.getRow(i).getCell(0).getStringCellValue());
            person.setGrxm(sheet.getRow(i).getCell(1).getStringCellValue());
            Persons.add(person);
        }
        workbook.close();
        return Persons;
    }

    public static List<PersonGG> ImportGG(String result) throws Exception {
        List<PersonGG> Persons = new ArrayList<PersonGG>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int total = sheet.getLastRowNum();
        for (int i = 2; i < total + 1; i++) {
            PersonGG person = new PersonGG();
            person.setGmsfhm(sheet.getRow(i).getCell(0).getStringCellValue());
            person.setGrxm(sheet.getRow(i).getCell(1).getStringCellValue());
            Persons.add(person);
        }
        workbook.close();
        return Persons;
    }

    public static List<PersonLH> ImportLH(String result) throws Exception {
        List<PersonLH> Persons = new ArrayList<PersonLH>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int total = sheet.getLastRowNum();
        for (int i = 1; i < total + 1; i++) {
            PersonLH person = new PersonLH();
            person.setGmsfhm(sheet.getRow(i).getCell(0).getStringCellValue());
            person.setGrxm(sheet.getRow(i).getCell(1).getStringCellValue());
            Persons.add(person);
        }
        workbook.close();
        return Persons;
    }

    public static List<PersonKN> ImportKN(String result) throws Exception {
        List<PersonKN> Persons = new ArrayList<PersonKN>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("c:\\" + result + ".xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int total = sheet.getLastRowNum();
        for (int i = 2; i < total + 1; i++) {
            PersonKN person = new PersonKN();
            person.setGmsfhm(sheet.getRow(i).getCell(0).getStringCellValue());
            person.setGrxm(sheet.getRow(i).getCell(1).getStringCellValue());
            Persons.add(person);
        }
        workbook.close();
        return Persons;
    }
}
