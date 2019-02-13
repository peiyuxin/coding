package org.pyx.natives.lambda;

/**
 * @author pyx
 * @date 2019/1/3
 */
public class Apple {
    private int id;
    private String name;
    private String month;

    public Apple(){}

    public Apple(int id, String name, String month) {
        this.id = id;
        this.name = name;
        this.month = month;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Apple{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", month='" + month + '\'' +
            '}';
    }
}
