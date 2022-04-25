package io.github.patternatlas.api.repositories;

import io.github.patternatlas.api.entities.user.role.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

    public Optional<Privilege> findByName(String name);

    @Query(value = "SELECT * FROM privilege p WHERE p.name like '%ALL' OR p.name like '%CREATE'", nativeQuery = true)
    public List<Privilege> findAllPlatformPrivileges();

    @Query(value = "SELECT * FROM privilege p WHERE (p.name LIKE 'ISSUE%' OR p.name LIKE 'PATTERN_CANDIDATE%' OR p.name LIKE 'APPROVED_PATTERN%') AND p.name NOT LIKE '%ALL' AND p.name NOT LIKE '%CREATE' AND p.name NOT LIKE '%\\_%-%'", nativeQuery = true)
    public List<Privilege> findAllDefaultPrivileges();

    @Query(value = "SELECT * FROM privilege p WHERE p.name like %:entityId", nativeQuery = true)
    public List<Privilege> findAllFromEntity(@Param("entityId") UUID entityId);

    @Modifying
    @Query(value = "DELETE FROM privilege p WHERE p.name like %:entityId", nativeQuery = true)
    public void deleteAllFromEntity(@Param("entityId") UUID entityId);

    @Query(value = "SELECT case when (count(priv) > 0) then true else false end from Privilege priv " +
            "LEFT JOIN priv.roles r " +
            "LEFT JOIN r.users u " +
            "WHERE u.id = :userId " +
            "AND priv.name = :privilegeName")
    boolean existsPrivilegeForUser(@Param("privilegeName") String privilegeName, @Param("userId") UUID userId);
}
