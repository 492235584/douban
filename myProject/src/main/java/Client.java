import org.htmlparser.util.ParserException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiejiahao on 2016/10/31.
 */
public class Client {
    public static final String BASE_URL = "https://book.douban.com";
    public static final int ThreadNum = 5;
    public static ConcurrentSkipListSet<Book> bookSet = new ConcurrentSkipListSet<Book>();
    public static ConcurrentSkipListSet<String> parserUrl = new ConcurrentSkipListSet<String>();

    public static void main(String[] args) throws ParserException {
        URLExplain urlExplain = new URLExplain();
        String[] keyWords = {"算法", "互联网", "编程"};
        for (String keyWord : keyWords) {
            urlExplain.explainURL(BASE_URL + "/tag/" + keyWord, keyWord);
        }

        //开启线程分析url
        ExecutorService service = Executors.newFixedThreadPool(ThreadNum);
        for (int i = 0; i < ThreadNum; i++) {
            service.execute(new UrlParserThread());
        }

        while (!parserUrl.isEmpty()) {
        }//如果没有全部处理完阻塞

        //输出结果到txt文件
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("d:\\douban.txt"));
            int i = 0;
            for (Book book : bookSet) {
                i++;
                bufferedWriter.write(i + "." + book.toString());
                if (i > 100) break;//只显示前200条
            }
        } catch (IOException e) {
            System.out.println("路径异常，请更换文件路径");
            e.printStackTrace();
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        service.shutdown();
    }
}
