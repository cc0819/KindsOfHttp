package com.kindofhttp.cc.entity;

/**
 * Created by cc on 17/5/23.
 */

public class UserInfo {
    /**
     * name : 灌灌
     * year : 1989-08-19
     * age : 28
     * other : {"hobby":"嘿嘿嘿","interest":"啪啪啪"}
     */

    private String name;
    private String year;
    private String age;
    private OtherBean other;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public static class OtherBean {
        /**
         * hobby : 嘿嘿嘿
         * interest : 啪啪啪
         */

        private String hobby;
        private String interest;

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", age='" + age + '\'' +
                ", other=" + other +
                '}';
    }
}
