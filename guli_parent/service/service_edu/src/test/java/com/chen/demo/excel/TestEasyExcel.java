package com.chen.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写操作
        //1、设置写入文件夹地址和excel文件名称
        String filename="D:\\DemoData.xlsx";

        //调用easyExcel里面的方法实现写操作
        //参数1：文件名称
        //参数2：对应实体类
        EasyExcel
                .write(filename,DemoData.class)
                .sheet("学生列表")
                .doWrite(getLists());
    }

    @Test
    public void testReadExcel(){
        String filename="D:\\DemoData.xlsx";

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(filename,DemoData.class,new ExcelListener())
                .sheet().doRead();
    }

    //创建方法返回List集合
    private static List<DemoData> getLists(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("chen ："+ i);
            list.add(demoData);
        }
        return list;
    }
}
