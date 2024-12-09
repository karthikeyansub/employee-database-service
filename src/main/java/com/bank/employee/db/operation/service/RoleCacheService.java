package com.bank.employee.db.operation.service;

import com.bank.employee.db.operation.domain.Role;
import com.bank.employee.db.operation.domain.dto.RoleReponse;
import com.bank.employee.db.operation.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;


@Service
@Slf4j
public class RoleCacheService implements SmartInitializingSingleton {

    private static final String ROLE_CACHE_MANAGER = "roleCacheManager";

    private static final String ROLE_CACHED_LIST = "roleCachedList";

    private final RoleRepository roleRepository;

    private final Cache cache;

    public RoleCacheService(final CacheManager cacheManager, final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.cache = cacheManager.getCache(ROLE_CACHE_MANAGER);
        if(this.cache == null) {
            log.error("Could not retrieve cache (cacheName={}) from the cache manager.", ROLE_CACHE_MANAGER);
        }
    }

    /**
     * This method is called on startup of Spring Boot and is blocking the startup until the cache has been loaded.
     */
    @Override
    public void afterSingletonsInstantiated() {
        updateRolesInCache();
    }

    public List<RoleReponse> getRoles() {
        List<RoleReponse> roleReponseList = this.cache.get(ROLE_CACHED_LIST, List.class);
        if(isNull(roleReponseList) || roleReponseList.isEmpty()) {
            log.debug("Cache is empty, updating the roles cache list");
            return updateRolesInCache();
        }
        return roleReponseList;
    }

    private List<RoleReponse> updateRolesInCache() {
        List<RoleReponse> roleReponseList = Stream.ofNullable(roleRepository.findAll())
                .filter(roles -> !roles.isEmpty())
                .flatMap(Collection::stream)
                .map(roleMapperFunction)
                .toList();

        this.cache.put(ROLE_CACHED_LIST, roleReponseList);

        return roleReponseList;
    }

    private final Function<Role, RoleReponse> roleMapperFunction = role ->
            new RoleReponse(role.getId(), role.getName());
}
