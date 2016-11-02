package pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiejiahao on 2016/11/1.
 */
public class PatternUtil {

    /**
     * 获取指定属性值
     *
     * @param str
     * @param attr
     * @return
     */
    public static String getAttrText(String str, String attr) {
        Pattern p1 = Pattern.compile(attr + "=\"(.*)\"");
        Pattern p2 = Pattern.compile("\"(.*)\"");
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            Matcher m2 = p2.matcher(m1.group());
            if(m2.find()) {
                return m2.group().replaceAll("[\"]", "");
            }
        }
        return null;
    }

    /**
     * 获取评分
     *
     * @param str
     * @return
     */
    public static String getStars(String str) {
        Pattern p1 = Pattern.compile(">(.*)<");
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            return m1.group().replaceAll("[<>]", "");
        }
        return null;
    }

    /**
     * 获取评论数
     *
     * @param str
     * @return
     */
    public static int getComments(String str) {
        Pattern p1 = Pattern.compile("\\((.*)\\)");
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            return Integer.valueOf(m1.group().replaceAll("[^\\d]",""));
        }
        return 0;
    }
}
