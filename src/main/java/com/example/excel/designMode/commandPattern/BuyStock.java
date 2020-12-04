package com.example.excel.designMode.commandPattern;

//步骤 3
//创建实现了 Order 接口的实体类。
public class BuyStock implements Order {
    private Stock abcStock;

    public BuyStock(Stock abcStock){
        this.abcStock = abcStock;
    }

    public void execute() {
        abcStock.buy();
    }
}
