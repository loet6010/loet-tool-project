package com.loet.mine.test;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：RaffleTest
 *
 * @author 罗锅
 * @date 2021/8/4 10:47
 */
public class RaffleTest {

    public static void main(String[] args) {
        List<PrizeInfo> prizeInfoList = getPrizeInfoList();

        Map<String, Integer> raffleMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            PrizeInfo prizeInfo = doRaffle(prizeInfoList);
            if (raffleMap.get(prizeInfo.prizeName) == null) {
                raffleMap.put(prizeInfo.getPrizeName(), 0);
            } else {
                Integer index = raffleMap.get(prizeInfo.prizeName);
                index += 1;
                raffleMap.put(prizeInfo.getPrizeName(), index);
            }
        }

        for (Map.Entry<String, Integer> raffleEntry : raffleMap.entrySet()) {
            System.out.println(raffleEntry.getKey() + "：" + raffleEntry.getValue());
        }
    }

    private static PrizeInfo doRaffle(List<PrizeInfo> prizeInfoList) {
        int raffleNum = 1000000;
        BigDecimal totalProbability = BigDecimal.ZERO;
        int randomRaffle = RandomUtils.nextInt(0, raffleNum);
        for (PrizeInfo prizeInfo : prizeInfoList) {
            totalProbability = totalProbability.add(prizeInfo.getProbability());
            int maxProbability = totalProbability.multiply(BigDecimal.valueOf(10000)).intValue();
            System.out.println("随机数：" + randomRaffle);
            System.out.println(prizeInfo.prizeName + "_区间：" + maxProbability);
            if (maxProbability >= randomRaffle) {
                return prizeInfo;
            }
        }

        return new PrizeInfo("未中奖");
    }

    private static List<PrizeInfo> getPrizeInfoList() {
        List<PrizeInfo> list = new ArrayList<>();
        list.add(new PrizeInfo("特等奖", new BigDecimal(10)));
        list.add(new PrizeInfo("一等奖", new BigDecimal(20)));
        list.add(new PrizeInfo("二等奖", new BigDecimal(30)));
        list.add(new PrizeInfo("未中奖", new BigDecimal(40)));

        return list;
    }

    private static class PrizeInfo {
        private String prizeName;
        private BigDecimal probability;
        ;

        public PrizeInfo(String prizeName) {
            this.prizeName = prizeName;
        }

        public PrizeInfo(String prizeName, BigDecimal probability) {
            this.prizeName = prizeName;
            this.probability = probability;
        }

        public String getPrizeName() {
            return prizeName;
        }

        public void setPrizeName(String prizeName) {
            this.prizeName = prizeName;
        }

        public BigDecimal getProbability() {
            return probability;
        }

        public void setProbability(BigDecimal probability) {
            this.probability = probability;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
