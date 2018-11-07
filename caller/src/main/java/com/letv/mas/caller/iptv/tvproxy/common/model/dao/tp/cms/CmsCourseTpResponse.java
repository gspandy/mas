package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import java.io.Serializable;
import java.util.List;

/**
 * cms排课系统
 * @author Jalon
 */
public class CmsCourseTpResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6776448800602559274L;
    private Integer statusCode;
    private String msg;
    private List<CmsCourseDataTpResponse> data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CmsCourseDataTpResponse> getData() {
        return data;
    }

    public void setData(List<CmsCourseDataTpResponse> data) {
        this.data = data;
    }

    public static class CmsCourseDataTpResponse implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -6159008835574090520L;
        private String date;
        private String packageName;
        private List<CourseDataTpResponse> courses;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public List<CourseDataTpResponse> getCourses() {
            return courses;
        }

        public void setCourses(List<CourseDataTpResponse> courses) {
            this.courses = courses;
        }

        public static class CourseDataTpResponse implements Serializable {

            /**
             * 
             */
            private static final long serialVersionUID = 2179975665924008472L;
            private Integer id;
            private String name;
            private String desc;
            private List<ThemesTpResponse> themes;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public List<ThemesTpResponse> getThemes() {
                return themes;
            }

            public void setThemes(List<ThemesTpResponse> themes) {
                this.themes = themes;
            }

            public static class ThemesTpResponse implements Serializable {

                /**
                 * 
                 */
                private static final long serialVersionUID = 9027468612023515708L;

                private Integer id;
                private String name;
                private String desc;
                private String pic;
                private String vids;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getVids() {
                    return vids;
                }

                public void setVids(String vids) {
                    this.vids = vids;
                }

            }
        }
    }
}
