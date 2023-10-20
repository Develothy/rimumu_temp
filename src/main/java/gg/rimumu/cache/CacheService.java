package gg.rimumu.cache;

import com.google.gson.JsonObject;
import gg.rimumu.exception.RimumuException;
import gg.rimumu.common.util.FileUtil;
import org.springframework.stereotype.Component;

@Component
public class CacheService {

    private static CacheManager cacheManager;

    private static final String CACHE_KEY_ITEM_ALL = "itemData";
    private static final String PREFIX_ITEM = "item_";

    public static JsonObject getItem(int itemNum) throws RimumuException {
        String key = PREFIX_ITEM + itemNum;
        JsonObject itemCached = (JsonObject) cacheManager.get(key);

        if (itemCached != null) {
            return itemCached;
        }

        JsonObject itemAllCached = getItemAllData();
        JsonObject itemData = itemAllCached.getAsJsonObject("data");
        JsonObject itemResult = itemData.getAsJsonObject(String.valueOf(itemNum));
        itemCached = itemResult;
        cacheManager.put(key, itemCached);

        return itemCached;
    }

    public static JsonObject getItemAllData() throws RimumuException {

        JsonObject itemAllCached = (JsonObject) cacheManager.get(CACHE_KEY_ITEM_ALL);

        if (itemAllCached != null) {
            return itemAllCached;
        }

        itemAllCached = FileUtil.readJsonFile("/datadragon/item.json");
        cacheManager.put(CACHE_KEY_ITEM_ALL, itemAllCached);

        return itemAllCached;
    }

}
