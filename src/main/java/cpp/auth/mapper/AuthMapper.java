package cpp.auth.mapper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AuthMapper {
    private static Set<String> passwords = new HashSet<>();

    public AuthMapper() {
        try {
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() throws Exception {
        SAXReader reader = new SAXReader();
        passwords.clear();
        InputStream in = AuthMapper.class.getResourceAsStream("passwords.xml");
        Document document = reader.read(in);
        Element root = document.getRootElement();
        List<Element> list = root.elements("password");
        for (Element e : list) {
            passwords.add(e.getText());
        }
    }

    public boolean auth(String password) {
        if (!needPassword()) {
            return true;
        }
        return passwords.contains(password);
    }

    public boolean needPassword() {
        return !passwords.contains("");
    }
}
