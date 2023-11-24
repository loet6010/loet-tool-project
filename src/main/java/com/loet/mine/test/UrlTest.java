package com.loet.mine.test;

import cn.hutool.core.date.StopWatch;

import java.io.UnsupportedEncodingException;

/**
 * 描述：UrlTest
 *
 * @author 罗锅
 * @date 2023/10/25
 */
public class UrlTest {

    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //目标跳转链接
        Thread.sleep(300L);
        //String  queryParam = "http://222.217.101.158:33200/EPG/jsp/gdtestHD2/en/epgToThirdUrl_HW.jsp?optFlag=EDUCATION&contentID=content0000000000000000000014289&thirdurl=http://222.217.76.250:19001/epg/skipHtml/guangxi_skip.html&returnurl=http%3A%2F%2F222.217.101.158%3A33200%2FEPG%2Fjsp%2FgdtestHD2%2Fen%2Ftest%2Fepg-test%2Flinks%2Findex.jsp%3Freturnurl%3Dhttp%253A%252F%252F222.217.101.158%253A33200%252FEPG%252Fjsp%252FgdtestHD2%252Fen%252Ftest%252Fepg-preview%252Fepg-preview-index.html%253Freturnurl%253Dhttp%25253A%25252F%25252F222.217.101.158%25253A33200%25252FEPG%25252Fjsp%25252FHDV3Formal%25252Fen%25252Fv4b%25252Fwidget%25252Findex%25252Findex.jsp%25253Fv%25253D2.0%252526home%25253D1%252526biz%25253Dbiz_25155607%252526df%25253Dbiz_96705722%2525257CLayout%252525401%2525252FSection%252525400%2525252FNav%252525400%2525252Ffocus.Element%252525403%2525252C0%2525252C0%252526source%25253Dv5_MoreContent_Esports%252526timestamp%25253D125572%2526df%253Dfocus.Element%25254013%26recommCollectId%3Depg-preview-index%2425b4feb4273d607488e651f6ca3d33e0%26column%3D00000021000000092021112300276889%26page%3D0%26df%3Dfocus.Element%25408";
        String  queryParam = " /EPG/jsp/gdtestHD2/en/epgToThirdUrl_HW.jsp?optFlag=EDUCATION&contentID=content0000000000000000000014289&thirdurl=http://222.217.76.250:19001/epg/skipHtml/guangxi_skip.html&returnurl=http%3A%2F%2F222.217.101.158%3A33200%2FEPG%2Fjsp%2FgdtestHD2%2Fen%2Ftest%2Fepg-test%2Flinks%2Findex.jsp%3Freturnurl%3Dhttp%253A%252F%252F222.217.101.158%253A33200%252FEPG%252Fjsp%252FgdtestHD2%252Fen%252Ftest%252Fepg-preview%252Fepg-preview-index.html%253Freturnurl%253Dhttp%25253A%25252F%25252F222.217.101.158%25253A33200%25252FEPG%25252Fjsp%25252FHDV3Formal%25252Fen%25252Fv4b%25252Fwidget%25252Findex%25252Findex.jsp%25253Fv%25253D2.0%252526home%25253D1%252526biz%25253Dbiz_25155607%252526df%25253Dbiz_96705722%2525257CLayout%252525402%2525252FSection%252525400%2525252FImageGanged%252525400%2525252Fview%252525400%2525252Ffocus.Element%252525400%2525252C0%2525252C0%2526df%253Dfocus.Element%25254013%26recommCollectId%3Depg-preview-index%249ad4a36800cce918b9e401db866132e6%26column%3D00000021000000092021112300276889%26page%3D0%26df%3Dfocus.Element%25408";
        String targetURL ="";
        if(!"".equals(queryParam)&&  null!=queryParam && queryParam.contains("thirdurl=")){
            targetURL = queryParam.split("thirdurl=")[1];
            if(targetURL.contains("%3F") || targetURL.contains("%3D") || targetURL.contains("%26") || targetURL.contains("%3A") || targetURL.contains("%2F")){
                targetURL = java.net.URLDecoder.decode(targetURL);
                // targetURL = targetURL.split("&")[0];
            }
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }


    private String addparamter(String url, String userpuff, String userpar) {
        try {
            if (url.indexOf("http://") == 0) {
                int len = url.length();
                String temp = url.substring(0, len);
                if (url.indexOf("?") > 0) {
                    temp = temp + "&INFO=" + userpuff;
                } else {
                    temp = temp + userpar;
                }

                url = temp;
            }
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String EncodeInfo(String useId, int key, int key2) {
        if (useId == null)
            return null;
        String u1 = useId.substring(0, key);
        String u2 = useId.substring(key, useId.length());
        int start = 0, end = u2.length() - 1;
        char[] nu2 = u2.toCharArray();
        while (start < end) {
            char temp = nu2[end];
            nu2[end] = nu2[start];
            nu2[start] = temp;
            start++;
            end--;
        }
        u2 = "";
        for (int i = 0; i < nu2.length; i++)
            u2 += nu2[i];
        useId = u2 + u1;
        char[] use = useId.toCharArray();
        for (int i = 0; i < useId.length(); i++) {
            if ((i + 1) % 2 == 1)
                use[i] = (char) (use[i] - key2);
            else
                use[i] = (char) (use[i] + key2);
        }
        useId = "";
        for (int i = 0; i < use.length; i++)
            useId += use[i];
        return useId;
    }
}
