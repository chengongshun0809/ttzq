package zz.itcast.jiujinhui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DomeBean {



    public String message;
    public String income;
    public String mobile;
    public String realprice;
    public String buybackprice;


    public DealdataBean dealdata;


    public List<TodaydealBean> todaydeal;


    public List<DealpriceBean> dealprice;


    public List<BuyoutBean> buyout;


    public List<BuyinBean> buyin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealprice() {
        return realprice;
    }

    public void setRealprice(String realprice) {
        this.realprice = realprice;
    }

    public String getBuybackprice() {
        return buybackprice;
    }

    public void setBuybackprice(String buybackprice) {
        this.buybackprice = buybackprice;
    }

    public DealdataBean getDealdata() {
        return dealdata;
    }

    public void setDealdata(DealdataBean dealdata) {
        this.dealdata = dealdata;
    }

    public List<TodaydealBean> getTodaydeal() {
        return todaydeal;
    }

    public void setTodaydeal(List<TodaydealBean> todaydeal) {
        this.todaydeal = todaydeal;
    }

    public List<DealpriceBean> getDealprice() {
        return dealprice;
    }

    public void setDealprice(List<DealpriceBean> dealprice) {
        this.dealprice = dealprice;
    }

    public List<BuyoutBean> getBuyout() {
        return buyout;
    }

    public void setBuyout(List<BuyoutBean> buyout) {
        this.buyout = buyout;
    }

    public List<BuyinBean> getBuyin() {
        return buyin;
    }

    public void setBuyin(List<BuyinBean> buyin) {
        this.buyin = buyin;
    }

    public static class DealdataBean {
        public int buybacknum;
        public int buyintotal;
        public int buynum;
        public int buyouttotal;
        public int consumenum;
        public String createtime;
        public String ddid;
        public int dealnum;
        public String dgid;
        public int downaward;
        public int downnum;
        public int getnum;
        public int id;
        public String jid;
        public String mobile;
        public String owner;
        public int putnum;
        public int state;
        public int stock;
        public int subnum;
        public String unionid;
        public String upjid;
        public String wxopenid;

        public int getBuybacknum() {
            return buybacknum;
        }

        public void setBuybacknum(int buybacknum) {
            this.buybacknum = buybacknum;
        }

        public int getBuyintotal() {
            return buyintotal;
        }

        public void setBuyintotal(int buyintotal) {
            this.buyintotal = buyintotal;
        }

        public int getBuynum() {
            return buynum;
        }

        public void setBuynum(int buynum) {
            this.buynum = buynum;
        }

        public int getBuyouttotal() {
            return buyouttotal;
        }

        public void setBuyouttotal(int buyouttotal) {
            this.buyouttotal = buyouttotal;
        }

        public int getConsumenum() {
            return consumenum;
        }

        public void setConsumenum(int consumenum) {
            this.consumenum = consumenum;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDdid() {
            return ddid;
        }

        public void setDdid(String ddid) {
            this.ddid = ddid;
        }

        public int getDealnum() {
            return dealnum;
        }

        public void setDealnum(int dealnum) {
            this.dealnum = dealnum;
        }

        public String getDgid() {
            return dgid;
        }

        public void setDgid(String dgid) {
            this.dgid = dgid;
        }

        public int getDownaward() {
            return downaward;
        }

        public void setDownaward(int downaward) {
            this.downaward = downaward;
        }

        public int getDownnum() {
            return downnum;
        }

        public void setDownnum(int downnum) {
            this.downnum = downnum;
        }

        public int getGetnum() {
            return getnum;
        }

        public void setGetnum(int getnum) {
            this.getnum = getnum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJid() {
            return jid;
        }

        public void setJid(String jid) {
            this.jid = jid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getPutnum() {
            return putnum;
        }

        public void setPutnum(int putnum) {
            this.putnum = putnum;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getSubnum() {
            return subnum;
        }

        public void setSubnum(int subnum) {
            this.subnum = subnum;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getUpjid() {
            return upjid;
        }

        public void setUpjid(String upjid) {
            this.upjid = upjid;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }
    }

    public static class TodaydealBean {
        public String createtime;
        public String dgid;
        public int id;
        public float price;
        public int state;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDgid() {
            return dgid;
        }

        public void setDgid(String dgid) {
            this.dgid = dgid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public static class DealpriceBean {
        public float beginprice;
        public float buybackprice;
        public int cellarnum;
        public int dealback;
        public int dealbacknum;
        public int dealbuy;
        public String dealcode;
        public String dealday;
        public int dealordernum;
        public int dealpick;
        public int dealput;
        public int dealwinenum;
        public String dgid;
        public int id;
        public int maxprice;
        public int minprice;
        public String name;
        public String owner;
        public float realprice;
        public int state;
        public int usernum;

        public float getBeginprice() {
            return beginprice;
        }

        public void setBeginprice(int beginprice) {
            this.beginprice = beginprice;
        }

        public float getBuybackprice() {
            return buybackprice;
        }

        public void setBuybackprice(int buybackprice) {
            this.buybackprice = buybackprice;
        }

        public int getCellarnum() {
            return cellarnum;
        }

        public void setCellarnum(int cellarnum) {
            this.cellarnum = cellarnum;
        }

        public int getDealback() {
            return dealback;
        }

        public void setDealback(int dealback) {
            this.dealback = dealback;
        }

        public int getDealbacknum() {
            return dealbacknum;
        }

        public void setDealbacknum(int dealbacknum) {
            this.dealbacknum = dealbacknum;
        }

        public int getDealbuy() {
            return dealbuy;
        }

        public void setDealbuy(int dealbuy) {
            this.dealbuy = dealbuy;
        }

        public String getDealcode() {
            return dealcode;
        }

        public void setDealcode(String dealcode) {
            this.dealcode = dealcode;
        }

        public String getDealday() {
            return dealday;
        }

        public void setDealday(String dealday) {
            this.dealday = dealday;
        }

        public int getDealordernum() {
            return dealordernum;
        }

        public void setDealordernum(int dealordernum) {
            this.dealordernum = dealordernum;
        }

        public int getDealpick() {
            return dealpick;
        }

        public void setDealpick(int dealpick) {
            this.dealpick = dealpick;
        }

        public int getDealput() {
            return dealput;
        }

        public void setDealput(int dealput) {
            this.dealput = dealput;
        }

        public int getDealwinenum() {
            return dealwinenum;
        }

        public void setDealwinenum(int dealwinenum) {
            this.dealwinenum = dealwinenum;
        }

        public String getDgid() {
            return dgid;
        }

        public void setDgid(String dgid) {
            this.dgid = dgid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(int maxprice) {
            this.maxprice = maxprice;
        }

        public int getMinprice() {
            return minprice;
        }

        public void setMinprice(int minprice) {
            this.minprice = minprice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public float getRealprice() {
            return realprice;
        }

        public void setRealprice(int realprice) {
            this.realprice = realprice;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getUsernum() {
            return usernum;
        }

        public void setUsernum(int usernum) {
            this.usernum = usernum;
        }
    }

    public static class BuyoutBean {
        public String createtime;
        public int id;
        public String mobile;
        public String name;
        public int num;
        public String oid;
        public int price;
        public int state;
        public int total;
        public String wxopenid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }
    }

    public static class BuyinBean {
        public String createtime;
        public int id;
        public String mobile;
        public String name;
        public int num;
        public String oid;
        public int price;
        public int state;
        public int total;
        public String wxopenid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }
    }
}
