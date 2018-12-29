package rabbitproduce.jacy;

import com.sun.xml.internal.ws.developer.Serialization;

@Serialization
public class Bar {
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;
}
