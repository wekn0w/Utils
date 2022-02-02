package cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * store values in Cache for duration
 */
public class CacheService {
    private static final Cache<UUID, Object> TEMPORARY_UL = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();

    public static UUID saveInCache(Object json) {
        UUID key = UUID.randomUUID();
        TEMPORARY_UL.put(key, json);
        return key;
    }

    private Optional<Object> getFromCache(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return Optional.ofNullable(TEMPORARY_UL.getIfPresent(uuid));
    }
}
