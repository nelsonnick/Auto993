package com.wts;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception{
        System.out.println("-------------------------------------------------------");
        System.out.println(" ");
        String result;
        do {
            // 输出提示文字
            System.out.print("请输入待查的Excel文件名：");
            InputStreamReader is_reader = new InputStreamReader(System.in);
            result = new BufferedReader(is_reader).readLine();
        } while (result.equals("")); // 当用户输入无效的时候，反复提示要求用户输入
        File file = new File("C:\\" + result + ".xlsx");
        if (!file.exists()) {
            System.out.print("C:\\" + result + ".xlsx文件不存在！");
            System.out.print("按回车关闭程序...");
            while (true) {
                if (System.in.read() == '\n')
                    System.exit(0);
            }
        } else {





            System.out.println("按回车键退出程序...");
            while (true) {
                if (System.in.read() == '\n')
                    System.exit(0);
            }
        }
    }
}
