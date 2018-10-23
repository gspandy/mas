package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 需要存入缓存的对象类型信息封装类；
 * 当直接使用T.getclass时，可能丢失类型信息，这里封装一下，保存完整信息；
 * @author KevinYi
 * @param <T>
 */
public class LetvTypeReference<T> extends TypeReference<T> {

}
