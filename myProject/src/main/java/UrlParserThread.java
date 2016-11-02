import org.htmlparser.util.ParserException;

/**
 * 处理url的线程
 *
 * Created by xiejiahao on 2016/11/2.
 */
public class UrlParserThread implements Runnable {
    public void run() {
        while (!Client.parserUrl.isEmpty()) {
            String url = null;
            synchronized (Client.class) {
                url = Client.parserUrl.first();
                Client.parserUrl.remove(url);//删除已被处理的链接
            }
            URLExplain urlExplain = new URLExplain();
            try {
                urlExplain.explainInfo(url);
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
    }
}
