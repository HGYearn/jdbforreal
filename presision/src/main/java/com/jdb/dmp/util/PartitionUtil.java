package com.jdb.dmp.util;



public final class PartitionUtil {
    // 分区长度:数据段分布定义，其中取模的数一定要是2^n， 因为这里使用x % 2^n == x & (2^n - 1)等式，来优化性能。
    private final int partitionLength;

    // %转换为&操作的换算数值
    private final long andValue;

    // 分区线段
    private final int[] segment;

    /**
     * <pre>
     * &#64;param count 表示定义的分区数
     * &#64;param length 表示对应每个分区的取值长度
     * 算法：映射算法，segment[sum(count[i]*length[i])]~segment[sum(count[i+1]*length[i+1])]=count
     * 注意：其中count,length两个数组的长度必须是一致的。
     * 约束：sum((count[i]*length[i]))是2到n次方。
     * </pre>
     */
    public PartitionUtil(int[] count, int[] length) {
        if (count == null || length == null || (count.length != length.length))
            throw new RuntimeException("error,check your scope & scopeLength definition.");

        int segmentLength = 0;
        for (int i = 0; i < count.length; i++) {
            segmentLength += count[i];
        }
        // 计算区间边界值，区间边界值数量为区间数＋1
        int[] ai = new int[segmentLength + 1];
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                ai[++index] = ai[index - 1] + length[i];
            }
        }
        partitionLength = ai[ai.length - 1];
        andValue = partitionLength - 1;
        if ((partitionLength & andValue) != 0) {
            throw new RuntimeException("error,check your partitionScope definition.");
        }
        segment = new int[partitionLength];
        // 数据映射操作
        for (int i = 1; i < ai.length; i++) {
            for (int j = ai[i - 1]; j < ai[i]; j++) {
                segment[j] = (i - 1);
            }
        }
    }

    public int partition(long hash) {
        return segment[(int) (hash & andValue)];
    }

//    public int partition(String key, int start, int end) {
//        return partition(StringUtil.hash(key, start, end));
//    }

}
