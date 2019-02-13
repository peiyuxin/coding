package org.pyx.natives.concurrent.forkjoin;

import java.util.Date;
import java.util.Objects;

/**
 * @author pyx
 * @date 2018/9/14
 */
public class Update implements Comparable<Update>{
    private int id;

    /**
     * 作者
     */
    private Author author;

    /**
     * 文档
     */
    private String text;

    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    Update(final int id,final Author author,final String text,final Date createTime){
        this.id = id;
        this.author = author;
        this.text = text;
        this.createTime = createTime;
    }
    /**create Builder method**/
    public static Update.Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(Update o) {
        return (int)((int)this.getCreateTime().getTime() - o.getCreateTime().getTime());
    }

    public static class Builder {
        private int id;
        private Author author;
        private String text;
        private Date createTime;

        Builder(){
            this.id = -1;
            this.text = "";
            this.createTime = new Date();
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }


        public Builder setAuthor(Author author) {
            this.author = author;
            return this;
        }


        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setCreateTime(long createTime) {
            this.createTime = new Date(createTime);
            return this;
        }

        public Update build(){
            return new Update(id, author, text, createTime);
        }
    }

    @Override
    public String toString() {
        return "Update{" +
            "id=" + id +
            ", author=" + author +
            ", text='" + text + '\'' +
            ", createTime=" + createTime +
            '}';
    }
}
