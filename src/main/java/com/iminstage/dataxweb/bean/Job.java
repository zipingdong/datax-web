package com.iminstage.dataxweb.bean;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Job {

    /**
     * job : {"content":[{"reader":{"name":"oraclereader","parameter":{}},"writer":{"name":"hdfswriter","parameter":{}}}],"setting":{"speed":{"channel":"1","byte":1048576},"errorLimit":{"record":0,"percentage":0.02}}}
     */

    private JobBean job;

    public JobBean getJob() {
        return job;
    }

    public void setJob(JobBean job) {
        this.job = job;
    }

    public static class JobBean {
        /**
         * content : [{"reader":{"name":"oraclereader","parameter":{}},"writer":{"name":"hdfswriter","parameter":{}}}]
         * setting : {"speed":{"channel":"1","byte":1048576},"errorLimit":{"record":0,"percentage":0.02}}
         */

        private SettingBean setting;
        private List<ContentBean> content;

        public SettingBean getSetting() {
            return setting;
        }

        public void setSetting(SettingBean setting) {
            this.setting = setting;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class SettingBean {
            /**
             * speed : {"channel":"1","byte":1048576}
             * errorLimit : {"record":0,"percentage":0.02}
             */

            private SpeedBean speed;
            private ErrorLimitBean errorLimit;

            public SpeedBean getSpeed() {
                return speed;
            }

            public void setSpeed(SpeedBean speed) {
                this.speed = speed;
            }

            public ErrorLimitBean getErrorLimit() {
                return errorLimit;
            }

            public void setErrorLimit(ErrorLimitBean errorLimit) {
                this.errorLimit = errorLimit;
            }

            public static class SpeedBean {
                /**
                 * channel : 1
                 * byte : 1048576
                 */

                private String channel;
                @SerializedName("byte")
                private int byteX;

                public String getChannel() {
                    return channel;
                }

                public void setChannel(String channel) {
                    this.channel = channel;
                }

                public int getByteX() {
                    return byteX;
                }

                public void setByteX(int byteX) {
                    this.byteX = byteX;
                }
            }

            public static class ErrorLimitBean {
                /**
                 * record : 0
                 * percentage : 0.02
                 */

                private int record;
                private double percentage;

                public int getRecord() {
                    return record;
                }

                public void setRecord(int record) {
                    this.record = record;
                }

                public double getPercentage() {
                    return percentage;
                }

                public void setPercentage(double percentage) {
                    this.percentage = percentage;
                }
            }
        }

        public static class ContentBean {
            /**
             * reader : {"name":"oraclereader","parameter":{}}
             * writer : {"name":"hdfswriter","parameter":{}}
             */

            private ReaderBean reader;
            private WriterBean writer;

            public ReaderBean getReader() {
                return reader;
            }

            public void setReader(ReaderBean reader) {
                this.reader = reader;
            }

            public WriterBean getWriter() {
                return writer;
            }

            public void setWriter(WriterBean writer) {
                this.writer = writer;
            }

            public static class ReaderBean {
                /**
                 * name : oraclereader
                 * parameter : {}
                 */

                private String name;
                private JsonObject parameter;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public JsonObject getParameter() {
                    return parameter;
                }

                public void setParameter(JsonObject parameter) {
                    this.parameter = parameter;
                }
            }

            public static class WriterBean {
                /**
                 * name : hdfswriter
                 * parameter : {}
                 */

                private String name;
                private JsonObject parameter;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public JsonObject getParameter() {
                    return parameter;
                }

                public void setParameter(JsonObject parameter) {
                    this.parameter = parameter;
                }
            }
        }
    }
}
