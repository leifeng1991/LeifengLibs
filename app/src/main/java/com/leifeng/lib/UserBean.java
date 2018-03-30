package com.leifeng.lib;

import com.leifeng.lib.net.BaseBean;

import java.util.List;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/20 16:13
 */


public class UserBean extends BaseBean {
    /**
     * data : [{"id":30,"uid":111337,"phone":"15911056751","name":"刘路通","password":"e10adc3949ba59abbe56e057f20f883e"},{"id":36,"uid":1113045,"phone":"14444444444","name":"144444444","password":"e10adc3949ba59abbe56e057f20f883e"},{"id":37,"uid":111339,"phone":"18623792320","name":"毕珂","password":"e10adc3949ba59abbe56e057f20f883e"},{"id":44,"uid":111346,"phone":"15110181021","name":"张荣旗","password":"e10adc3949ba59abbe56e057f20f883e"},{"id":49,"uid":111357,"phone":"18614052773","name":"廖","password":"e10adc3949ba59abbe56e057f20f883e"},{"id":58,"uid":111355,"phone":"13521845202","name":"黄冈","password":"e10adc3949ba59abbe56e057f20f883e"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 30
         * uid : 111337
         * phone : 15911056751
         * name : 刘路通
         * password : e10adc3949ba59abbe56e057f20f883e
         */

        private int id;
        private int uid;
        private String phone;
        private String name;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
