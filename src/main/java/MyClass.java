import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyClass {
    private final Date m_time;
    private final String m_name;
    private final List<Long> m_numbers;
    private List<String> m_strings;

    public MyClass(Date time, String name, List<Long> numbers, List<String>
            strings) {
        m_time = time;
        m_name = name;
        m_numbers = numbers;
        m_strings = strings;
    }

    // Using instanceof (performance inefficiencies)
//    public boolean equals(Object obj) {
//        if (obj instanceof MyClass) {
//            return m_name.equals(((MyClass) obj).m_name);
//        }
//        return false;
//    }

    public boolean equals(MyClass obj) {
        return m_name.equals(obj.m_name);
    }

    // prevent using string concatenation use StringBuilder (performance inefficiencies)
//    public String toString() {
//        String out = m_name;
//        for (long item : m_numbers) {
//            out += " " + item;
//        }
//        return out;
//    }

    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (long item : m_numbers) {
            toString.append(" ").append(item);
        }
        return toString.toString();
    }

    // bug remove element from the collection while iterating
//    public void removeString(String str) {
//        for (int i = 0; i < m_strings.size(); i++) {
//            if (m_strings.get(i).equals(str)) {
//                m_strings.remove(i);
//            }
//        }
//    }

    // Using JDK < 8
    public void removeString(String str) {
        List<String> elementsToRemove = new ArrayList<>();
        // should copy otherwise toString could throw ConcurrentModificationException
        List<String> copy = new ArrayList<>(m_strings);
        for (String element : copy) {
            if (element.equals(str)) {
                elementsToRemove.add(element);
            }
        }
        copy.removeAll(elementsToRemove);
        m_strings = copy;
    }

    // Using JDK >= 8
    public void removeStringUsingStreams(String str) {
        // map() creates immutable copy of string
        // otherwise toString could throw ConcurrentModificationException
        m_strings = m_strings.stream().map(Object::toString)
                .filter(b -> b.equals(str))
                .collect(Collectors.toList());
    }

    public boolean isHistoric() {
        //return m_time.before(new Date());

        // use System.currentTimeMillis() more effective upon create new Date() (memory inefficiencies)
        return m_time.getTime() < (System.currentTimeMillis());
    }
}