package ismart.is.com.ismart.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {



    private int status;
    private String bg;

    private List<PostEntity> post;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }

    public int getStatus() {
        return status;
    }

    public String getBg() {
        return bg;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public static class PostEntity {
        private String code;
        private String title;
        private String details;
        private String file_img;
        private int status_img;
        private String OWNER;
        private String count;

        public void setCode(String code) {
            this.code = code;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public void setFile_img(String file_img) {
            this.file_img = file_img;
        }

        public void setStatus_img(int status_img) {
            this.status_img = status_img;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }

        public String getDetails() {
            return details;
        }

        public String getFile_img() {
            return file_img;
        }

        public int getStatus_img() {
            return status_img;
        }

        public String getOWNER() {
            return OWNER;
        }

        public String getCount() {
            return count;
        }
    }
}
