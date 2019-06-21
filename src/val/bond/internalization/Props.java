package val.bond.internalization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class Props {

    private static Properties props;

    public static Properties getProps() {
        return props;
    }

    public static void loadProps(String locale){
        try {
        InputStream utf8in = Props.class.getClassLoader().getResourceAsStream("val/bond/internalization/locale_"+ locale +".properties");
        Reader reader = new InputStreamReader(utf8in, "UTF-8");
        props = new Properties();
        props.load(reader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



}
