import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import pattern.PatternUtil;

import java.util.ArrayList;

/**
 * Created by xiejiahao on 2016/10/31.
 */
public class URLExplain {
    public void explainInfo(String url) throws ParserException {
        Parser parser = new Parser(url);
        parser.setEncoding("UTF-8");
        NodeList nodeList = parser.parse(new HasAttributeFilter("class", "info"));//获取含有书本信息的标签
        for (Node node : nodeList.toNodeArray()) {
            try {//解析出标签里的数据，保存在bookSet中
                Node nodeHaveBookName = node.getChildren().elementAt(1).getChildren().elementAt(1);
                String strHaveBookName = nodeHaveBookName.getText();
                NodeList nodeHaveStarAndComment = node.getChildren().elementAt(5).getChildren();
                String strHaveStars = nodeHaveStarAndComment.elementAt(3).toHtml();
                String strHaveComment = nodeHaveStarAndComment.elementAt(5).toHtml();
                Book book = new Book();
                book.setBookName(PatternUtil.getAttrText(strHaveBookName, "title"));
                book.setStars(Float.valueOf(PatternUtil.getStars(strHaveStars)));
                book.setComments(PatternUtil.getComments(strHaveComment));
                if (PatternUtil.getComments(strHaveComment) > 2000) {
                    Client.bookSet.add(book);
                }
            } catch (Exception e) {
                continue;//如果解析失败则跳过
            }
        }
    }

    public void explainURL(String Url, String keyWord) {
        ArrayList<String> pageNum = new ArrayList<String>();
        ArrayList<String> pageUrl = new ArrayList<String>();
        try {
            Parser parser = new Parser(Url);
            Client.parserUrl.add(Url);//添加可能能被解析的路径，Set数据结构保证了连接的唯一性
            parser.setEncoding("UTF-8");
            NodeList nodeList = parser.parse(new LinkStringFilter("/tag/" + keyWord + "?"));
            if (0 == nodeList.size()) {//如果没有链接，则退出
                return;
            }
            for (Node node : nodeList.toNodeArray()) {//递归调用获取分页连接
                String hrefTag = node.toHtml();
                String realHerf = Client.BASE_URL + PatternUtil.getAttrText(hrefTag, "href");
                String num = PatternUtil.getStars(hrefTag);
                Client.parserUrl.add(realHerf);
                pageNum.add(PatternUtil.getStars(hrefTag));
                pageUrl.add(realHerf);
            }
            int total = pageNum.size();
            if (total > 4 && Integer.valueOf(pageNum.get(total - 3)) > Integer.valueOf(pageNum.get(total - 4)) + 1) {
                explainURL(pageUrl.get(total - 4), keyWord);
            }

        } catch (ParserException e) {
            return;
        }
    }
}
