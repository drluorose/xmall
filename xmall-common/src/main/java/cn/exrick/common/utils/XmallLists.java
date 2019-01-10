package cn.exrick.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class XmallLists {

    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        if (Objects.isNull(fromList)) {
            throw new NullPointerException("fromList is null");
        }
        if (CollectionUtils.isEmpty(fromList)) {
            return Lists.newArrayList();
        }
        List<T> result = Lists.newArrayListWithCapacity(fromList.size());
        fromList.forEach(f -> {
            result.add(function.apply(f));
        });
        return result;
    }
}
