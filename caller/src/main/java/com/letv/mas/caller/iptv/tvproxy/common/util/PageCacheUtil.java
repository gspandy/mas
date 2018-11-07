package com.letv.mas.caller.iptv.tvproxy.common.util;

//import ch.lambdaj.Lambda;
//import ch.lambdaj.function.compare.ArgumentComparator;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.dto.VideoCacheIndex;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.PageCacheDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.PageCacheItem;

import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.commons.collections4.ComparatorUtils;

/**
 * 基于Redis等缓存中间件列表数据检索、排序、分页工具类
 */
public class PageCacheUtil {

    /**
     * 创建分页缓存操作对象
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> PageCacheDto buildPageCacheDto(List<T> list, Class<T> clazz) {
        PageCacheDto pageCacheDto = new PageCacheDto();
        PageCacheItem pageCacheItem = null;
        Map<String, Object> sortFields = null;
        List pageCacheItems = new ArrayList(list.size());

        for (T item : list) {
            if (clazz == VideoMysqlTable.class) {
                pageCacheItem = new PageCacheItem();
                sortFields = new HashMap<String, Object>();
                pageCacheItem.setBizId(String.valueOf(((VideoMysqlTable) item).getId()));
                sortFields.put("porder", ((VideoMysqlTable) item).getPorder());
                sortFields.put("time", ((VideoMysqlTable) item).getCreate_time());
                pageCacheItem.setSortFields(sortFields);
                pageCacheItems.add(pageCacheItem);
            } else if (clazz == VideoCacheIndex.class) {
                pageCacheItem = new PageCacheItem();
                sortFields = new HashMap<String, Object>();
                pageCacheItem.setBizId(String.valueOf(((VideoCacheIndex) item).getId()));
                sortFields.put("porder", ((VideoCacheIndex) item).getPorder());
                sortFields.put("time", ((VideoCacheIndex) item).getTime());
                pageCacheItem.setSortFields(sortFields);
                pageCacheItems.add(pageCacheItem);
            }
        }

        pageCacheDto.setSize(pageCacheItems.size());
        pageCacheDto.setItems(pageCacheItems);
        return pageCacheDto;
    }

    public static <T> List<T> trans4CacheIndexes(List list, Class<T> clazz) {
        List<T> cacheIndexes = null;

        if (clazz == VideoCacheIndex.class) {
            VideoCacheIndex videoCacheIndex = null;
            VideoMysqlTable videoMysqlTable = null;
            cacheIndexes = new ArrayList<T>(list.size());
            for (Object item : list) {
                if (item instanceof VideoMysqlTable) {
                    videoMysqlTable = (VideoMysqlTable) item;
                    videoCacheIndex = new VideoCacheIndex();
                    videoCacheIndex.setId(String.valueOf(videoMysqlTable.getId()));
                    videoCacheIndex.setPorder(videoMysqlTable.getPorder());
                    videoCacheIndex.setTime(videoMysqlTable.getCreate_time().getTime());
                    videoCacheIndex.setVideoType(videoMysqlTable.getVideo_type());
                    cacheIndexes.add((T) videoCacheIndex);
                }
            }
        }

        return cacheIndexes;
    }

    /**
     * 页缓逻辑排序
     * @param pageCacheDto
     * @return
     */
    public static PageCacheDto sort(PageCacheDto pageCacheDto) {
        final Map<String, String> sortParams = pageCacheDto.getSortParams();
        if (null != sortParams && sortParams.size() > 0) {
            List list = pageCacheDto.getItems();
            Collections.sort(list, new Comparator<PageCacheItem>() {
                @Override
                public int compare(PageCacheItem o1, PageCacheItem o2) {
                    int ret = 0;
                    for (Map.Entry<String, String> param : sortParams.entrySet()) {
                        Object field1 = null, field2 = null;

                        if (null != o1.getSortFields() && o1.getSortFields().size() > 0) {
                            field1 = o1.getSortFields().get(param.getKey());
                        }
                        if (null != o2.getSortFields() && o2.getSortFields().size() > 0) {
                            field2 = o2.getSortFields().get(param.getKey());
                        }

                        if (null != field1 && null != field2) {
                            if (field1 instanceof Integer && field2 instanceof Integer) {
                                ret = ret == 0 ? ((Integer) field1).compareTo((Integer) field2) : ret;
                            } else if (field1 instanceof Long && field2 instanceof Long) {
                                ret = ret == 0 ? ((Long) field1).compareTo((Long) field2) : ret;
                            } else { // 类型不一致或者不支持时，维持原排序
                                ret = ret == 0 ? 0 : ret;
                            }
                        } else {
                            if (null == field1 && null == field2) {
                                ret = ret == 0 ? 0 : ret;
                            }
                            if (null == field1) {
                                ret = ret == 0 ? -1 : ret;
                            } else {
                                ret = ret == 0 ? 1 : ret;
                            }
                        }

                        // 倒序处理
                        if (StringUtil.isNotBlank(param.getValue()) && param.getValue().equalsIgnoreCase("DESC")) {
                            if (ret != 0) {
                                ret = ret > 0 ? -1 : 1;
                            }
                        }
                        if (ret != 0) {
                            break;
                        }
                    }
                    return ret;
                }
            });
        }
        return pageCacheDto;
    }

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间或结束时间, 则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 单元测试 & 使用示例
     * @param args
     */
    public static void main(String[] args) {
        PageCacheDto pageCacheDto = new PageCacheDto();
        int total = 1000;
        List<PageCacheItem> pageCacheItems = new ArrayList<PageCacheItem>(total);
        PageCacheItem pageCacheItem = null;
        LinkedHashMap<String, String> sortParams = new LinkedHashMap<String, String>();
        HashMap<String, Object> sortFields = null;
        Random random = new Random();
        long begin = System.currentTimeMillis();

        System.out.println("//==================Initialize unsort data(B)==================//");
        // PageCacheDto(效率高)
        System.out.println("//------------------PageCacheDto-------------------//");
        for (int i = 0; i < total; i++) {
            pageCacheItem = new PageCacheItem();
            pageCacheItem.setBizId(String.valueOf(i));
            sortFields = new HashMap<String, Object>();
            sortFields.put("porder", random.nextInt(total));
            sortFields.put("time", randomDate("1980-01-01", "2018-09-07").getTime());
            // if (i == total / 2) {
            // sortFields.put("porder", pageCacheItems.get(i -
            // 1).getSortFields().get("porder"));
            // }
            pageCacheItem.setSortFields(sortFields);
            pageCacheItems.add(pageCacheItem);
        }
        System.out.println(pageCacheItems);
        pageCacheDto.setItems(pageCacheItems);
        pageCacheDto.setSize(10);
        sortParams.put("porder", "asc");
        sortParams.put("time", "desc");
        pageCacheDto.setSortParams(sortParams);

        // Lambda
        // System.out.println("//------------------Lambda-------------------//");
        // List<VideoCacheIndex> videoCacheIndexes = new
        // ArrayList<VideoCacheIndex>();
        // VideoCacheIndex videoCacheIndex = null;
        // for (int i = 0; i < total; i++) {
        // videoCacheIndex = new VideoCacheIndex();
        // videoCacheIndex.setId(String.valueOf(i));
        // videoCacheIndex.setPorder(random.nextInt(total));
        // videoCacheIndex.setTime(randomDate("1980-01-01",
        // "2018-09-07").getTime());
        // videoCacheIndexes.add(videoCacheIndex);
        // }
        // System.out.println(videoCacheIndexes);
        System.out.println("//==================Initialize unsort data(E)==================//");

        System.out.println("//==================Sort the data(B)==================//");
        // PageCacheDto
        begin = System.currentTimeMillis();
        System.out.println(PageCacheUtil.sort(pageCacheDto));
        System.out.println("PageCacheUtil.sort() by Collections: timeCost=" + (System.currentTimeMillis() - begin));

        // Lambda
        // begin = System.currentTimeMillis();
        // final Comparator byPorder = new ArgumentComparator<VideoCacheIndex,
        // Integer>(Lambda.on(VideoCacheIndex.class).getPorder(),
        // new Comparator<Integer>() {
        // @Override
        // public int compare(Integer o1, Integer o2) {
        // return o1.compareTo(o2); // ASC
        // //return o2.compareTo(o1); // DESC
        // }
        // });
        // final Comparator byTime = new ArgumentComparator<VideoCacheIndex,
        // Long>(Lambda.on(VideoCacheIndex.class).getTime(),
        // new Comparator<Long>() {
        // @Override
        // public int compare(Long o1, Long o2) {
        // //return o1.compareTo(o2); // ASC
        // return o2.compareTo(o1); // DESC
        // }
        // });
        // final Comparator orderBy =
        // ComparatorUtils.chainedComparator(byPorder, byTime);
        // System.out.println(Lambda.sort(videoCacheIndexes,
        // Lambda.on(VideoCacheIndex.class), orderBy));
        // System.out.println("PageCacheUtil.sort() by Lambda: timeCost=" +
        // (System.currentTimeMillis() - begin));
        System.out.println("//==================Sort the data(E)==================//");

        System.out.println("//==================build cache data(B)==================//");
        // VideoMysqlTable
        System.out.println("//------------------VideoMysqlTable-------------------//");

        System.out.println("//==================build cache data(E)==================//");
    }
}
