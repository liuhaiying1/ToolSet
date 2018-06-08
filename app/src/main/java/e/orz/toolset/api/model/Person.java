package e.orz.toolset.api.model;


public class Person {
    private String birthday;
    private String sex;
    private String area;

    public Person(String birthday, String sex, String area){
        this.birthday = birthday;
        this.sex = sex;
        this.area = area;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getArea() {
        return area;
    }
}
