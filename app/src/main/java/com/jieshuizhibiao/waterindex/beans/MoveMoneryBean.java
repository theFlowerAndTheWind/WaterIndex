package com.jieshuizhibiao.waterindex.beans;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:移交资产
 */

public class MoveMoneryBean {

    String total; 	//划转总额 	字符串(string)
    String arrive; 	//到达数量 	字符串(string)
    String gyj; 	//公益金数量 	字符串(string)

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getGyj() {
        return gyj;
    }

    public void setGyj(String gyj) {
        this.gyj = gyj;
    }
}
