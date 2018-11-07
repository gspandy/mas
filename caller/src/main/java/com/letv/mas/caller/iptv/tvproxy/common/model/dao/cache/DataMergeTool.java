package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.apache.commons.io.FileUtils;

public class DataMergeTool {

    /**
     * 支持的地区
     */
    // private static final String[] REGIONS = { LocaleConstant.Wcode.CN,
    // LocaleConstant.Wcode.HK,
    // LocaleConstant.Wcode.US };

    private static final String[] REGIONS = { LocaleConstant.Wcode.US };
    /**
     * 目前所支持的语言
     */
    private static final String[] LANG_CODES = { LocaleConstant.Langcode.ZH_CN, LocaleConstant.Langcode.ZH_HK,
            LocaleConstant.Langcode.EN_US };

    // private static final String[] LANG_CODES = {
    // LocaleConstant.Langcode.ZH_CN, LocaleConstant.Langcode.ZH_HK };
    /**
     * 所有的查询类型，0--非正片视频，1--正片视频，2--全部视频
     */
    private static final String[] QUERY_TYPES = { VideoTpConstant.QUERY_TYPE_NON_POSITIVE,
            VideoTpConstant.QUERY_TYPE_POSITIVE, VideoTpConstant.QUERY_TYPE_ALL };

    public static void main(String[] args) {
        DataMergeTool tool = new DataMergeTool();
        // tool.deleteVideos();
        // tool.deleteAlbums();
        tool.checkVideos();
    }

    public void checkVideos() {
        VideoCacheDao cacheDao = new VideoCacheDao();
        try {
            List<String> fileContent = FileUtils.readLines(new File("C:/Users/KevinYi/Desktop/usGlobalVideo.txt"),
                    "UTF-8");
            int count = 0;
            Set<String> pids = new HashSet<String>();
            for (String content : fileContent) {
                System.out.println(content);
                int hit = 0;
                Long pid = null;
                Long videoId = StringUtil.toLong(content, null);
                if (videoId != null) {
                    // cacheDao.deleteVideo(videoId, "us", "en_us");

                    for (String region : REGIONS) {
                        for (String lang : LANG_CODES) {
                            AlbumVideoDataWrapper videoWrapter = cacheDao.getVideo(videoId, region, lang);
                            if (videoWrapter != null && videoWrapter.getVideo() != null
                                    && videoWrapter.getVideo().getPid() != null) {
                                pid = videoWrapter.getVideo().getPid();
                                hit++;
                            }
                        }
                    }

                    if (hit == 3) {
                        pids.add(String.valueOf(pid));
                    }
                }
                if (pids.size() == 3) {
                    break;
                }
            }
            FileUtils.writeLines(new File("C:/Users/KevinYi/Desktop/out.txt"), pids);
            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        }
    }

    public void deleteVideos() {
        VideoCacheDao cacheDao = new VideoCacheDao();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            fis = new FileInputStream("C:/Users/" + " /Desktop/usGlobalVideo.txt");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
                                         // InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                Long videoId = StringUtil.toLong(str, null);
                if (videoId != null) {
                    // cacheDao.deleteVideo(videoId, "us", "en_us");
                    for (String region : REGIONS) {
                        for (String lang : LANG_CODES) {
                            cacheDao.deleteVideo(videoId, region, lang);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAlbums() {
       VideoCacheDao cacheDao = new VideoCacheDao();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            fis = new FileInputStream("C:/Users/KevinYi/Desktop/usGlobalAlbum.txt");// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
                                         // InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                Long albumId = StringUtil.toLong(str, null);
                if (albumId != null) {
                    // cacheDao.deleteAlbum(albumId, "us", "en_us");
                    for (String region : REGIONS) {
                        for (String lang : LANG_CODES) {
                            cacheDao.deleteAlbum(albumId, region, lang);

                            for (String queryType : QUERY_TYPES) {
                                Integer videoTotal = cacheDao.getAlbumPageInfo(albumId, region, lang, queryType);
                                if (videoTotal == null) {
                                    // no pages to delete
                                    continue;
                                }
                                // logger.info("AlbumAndVideoTask::Del Album-" +
                                // id + " video list,region:" + region
                                // + ",lang:" + lang + ",query:" + queryType);
                                int totalPage = VideoCommonUtil.getPageTotal(videoTotal);
                                for (int i = 1; i <= totalPage; i++) {
                                    cacheDao.deleteVideoList(albumId, region, lang, queryType, i);
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
