package com.example.excel.controller;


import com.example.excel.entity.Member;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {
    public static void main(String[] args) throws Exception {
        File file = new File("F://abc.xlsx");
        List<Member> list = new ArrayList<Member>();
        String filetype = getFileExp("abc.xlsx");
        Workbook workbook=null;
        if(filetype.equals(".xls")||filetype.equals(".xlsx")){
            if (file!=null&&file.length()>0) {
                workbook=getWorkbook(file);
                Sheet sheet=deleteNullRow(workbook.getSheetAt(0));
                for (int numRows = 1; numRows <= sheet.getLastRowNum(); numRows++) {
                    Row row = sheet.getRow(numRows); //poi中的
                    Member member = new Member();
                    for (int CellNum = 0; CellNum < row.getLastCellNum(); CellNum++) {

                        Cell cell = row.getCell(CellNum);//poi中的
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                        }
                        switch (CellNum) {
                            case 0://姓名    //你Excel中的第一列，一定和Excel中的顺序保持一致
                                if (cell == null) {
                                    member.setName("");
                                } else {
                                    member.setName(cell.toString());
                                }
                                break;
                            case 1://名族
                                if (cell == null) {
                                    member.setNation("");
                                } else {
                                    member.setNation(cell.toString());
                                }
                                break;
                            case 2://身份证号码
                                if (cell == null) {
                                    member.setNumber("");
                                } else {
                                    member.setNumber(cell.toString());
                                    //member.setName("a" + cell.toString());
                                }
                                break;
                            case 3://名族
                                if (cell == null) {
                                    member.setBeiZ("");
                                } else {
                                    member.setBeiZ(cell.toString());
                                }
                                break;
                            case 4://身份证号码
                                if (cell == null) {
                                    member.setClassR("");
                                } else {
                                    member.setClassR(cell.toString());
                                }
                                break;
                        }
                    }
                    list.add(member);
                    System.out.println(list);
                }
            }
        }
    }

    public static String getFileExp(String fileName) throws Exception{
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

    public static Workbook getWorkbook(File file) throws Exception{
        FileInputStream inputStream=new FileInputStream(file);
        Workbook workbook= WorkbookFactory.create(inputStream);
        return workbook;
    }
    public static Sheet deleteNullRow(Sheet sheet){
        if(sheet!=null){
            Row row = null;
            Cell cell = null;
            for (int numRows = 1; numRows <= sheet.getLastRowNum(); numRows++){
                row = sheet.getRow(numRows);
                boolean nullRowMark = true;
                //每一行的前三列如果都为空则删掉这行
                for(int numCells = 0; numCells<=2; numCells++){
                    cell = row.getCell(numCells);
                    nullRowMark = nullRowMark&&(cell==null||cell.getCellType()== CellType.BLANK);
                }
                //删除空行
                if(nullRowMark){
                    int ls=sheet.getLastRowNum();
                    sheet.shiftRows(numRows, numRows, ls-numRows);
                    numRows = numRows-1;
                    Row nullrow=sheet.getRow(ls);
                    sheet.removeRow(nullrow);
                }
            }
        }
        return sheet;
    }



}
