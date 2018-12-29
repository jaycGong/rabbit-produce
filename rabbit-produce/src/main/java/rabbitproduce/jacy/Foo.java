package rabbitproduce.jacy;

import com.sun.xml.internal.ws.developer.Serialization;


public class Foo implements java.io.Serializable
{
    public Foo(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
