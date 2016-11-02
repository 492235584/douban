/**
 * Created by xiejiahao on 2016/11/1.
 */
public class Book implements Comparable<Book> {
    private String url;
    private float stars;
    private int comments;
    private String bookName;

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getStars() {
        return this.stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getComments() {
        return this.comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "" +
                "书名：《" + bookName + "》" +
                "，评分：" + stars +
                "，评论数：" + comments +
                "\r\n";
    }

    public int compareTo(Book o) {
        int flag = (int) (o.stars * 10) - (int) (stars * 10);
        if (0 == flag) {
            if (bookName.equals(o.bookName)) {
                return 0;
            }
            return -1;
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return bookName.hashCode() + (int) stars * 27 + comments;
    }


}
