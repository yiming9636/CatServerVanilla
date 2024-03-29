package catserver.server.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageUtils {
    private static Map<String, String> CN = new HashMap<>();
    private static Map<String, String> EN = new HashMap<>();
    private static Map<String, String> current;

    static {
        CN.put("launch.java_wrong", "服务端不支持当前Java版本(%s), 请更换为Java8");
        CN.put("launch.lib_missing", "需要下载缺少的库文件, 请耐心等待..");
        CN.put("launch.lib_failure_check", "文件 %s 校验失败, 你也可以手动下载: %s");
        CN.put("launch.lib_failure_download", "下载文件失败(HTTP状态: %s), 你也可以手动下载: %s");
        CN.put("launch.lib_downloading", "正在下载文件 %s 大小: %s");
        CN.put("async.caught_async", "拦截异步操作List, 请检查插件和MOD!");
        CN.put("world.chunk_update_error", "区块更新时发生错误, 已将区块卸载防止崩溃!");

        EN.put("launch.java_wrong", "Current Java version (%s) is not supported, please replace to Java8");
        EN.put("launch.lib_missing", "Missing libraries file, downloading..");
        EN.put("launch.lib_failure_check", "File %s verification failed, URL: %s");
        EN.put("launch.lib_failure_download", "File download failed(HTTP Status: %s), URL: %s");
        EN.put("launch.lib_downloading", "Downloading %s Size: %s");
        EN.put("async.caught_async", "Caught asynchronously modify List, please check plugins and mods!");
        EN.put("world.chunk_update_error", "An error occurred while chunk updating, unloaded chunk to prevent crash!");

        current = ("zh".equals(Locale.getDefault().getLanguage()) ? CN : EN);
    }

    public static String I18nToString(String text) {
        return current.getOrDefault(text, current != EN ? EN.getOrDefault(text, "null") : "null");
    }
}
