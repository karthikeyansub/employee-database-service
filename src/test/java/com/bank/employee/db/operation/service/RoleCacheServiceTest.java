package com.bank.employee.db.operation.service;

import com.bank.employee.db.operation.domain.Role;
import com.bank.employee.db.operation.domain.dto.RoleReponse;
import com.bank.employee.db.operation.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RoleCacheServiceTest {

    private static final String ROLE_CACHED_LIST = "roleCachedList";

    private RoleCacheService classUnderTest;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private CacheManager mockCacheManager;

    @Mock
    private Cache mockCache;

    @BeforeEach
    void setUp() {
        when(mockCacheManager.getCache("roleCacheManager")).thenReturn(mockCache);
        classUnderTest = new RoleCacheService(mockCacheManager, mockRoleRepository);
    }

    @Test
    void testGetRoles_ShouldGetResultFromDB_WhenNoResultFoundInCache() {
        when(mockCache.get(ROLE_CACHED_LIST, List.class)).thenReturn(new ArrayList<>());
        when(mockRoleRepository.findAll()).thenReturn(getRoles());

        List<RoleReponse> result = classUnderTest.getRoles();

        assertFalse(result.isEmpty());
        verify(mockRoleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoles_ShouldGetResultFromCache_WhenResultFoundInCache() {
        when(mockCache.get(ROLE_CACHED_LIST, List.class)).thenReturn(getRoleResponse());

        List<RoleReponse> result = classUnderTest.getRoles();

        assertFalse(result.isEmpty());
        verify(mockRoleRepository, times(0)).findAll();
    }

    @Test
    void testGetRoles_ShouldReturnEmptyList_WhenNoDataInCacheAndFindAllReturnNull() {
        when(mockCache.get(ROLE_CACHED_LIST, List.class)).thenReturn(new ArrayList<>());
        when(mockRoleRepository.findAll()).thenReturn(null);

        List<RoleReponse> result = classUnderTest.getRoles();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRoles_ShouldReturnEmptyList_WhenNoDataInCacheAndFindAllReturnEmpty() {
        when(mockCache.get(ROLE_CACHED_LIST, List.class)).thenReturn(new ArrayList<>());
        when(mockRoleRepository.findAll()).thenReturn(Collections.emptyList());

        List<RoleReponse> result = classUnderTest.getRoles();

        assertTrue(result.isEmpty());
    }

    private List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(1).name("ADMIN").build());
        roles.add(Role.builder().id(2).name("USER").build());

        return roles;
    }

    private List<RoleReponse> getRoleResponse() {
        List<RoleReponse> roles = new ArrayList<>();
        roles.add(new RoleReponse(1, "ADMIN"));
        roles.add(new RoleReponse(2, "USER"));

        return roles;
    }
}