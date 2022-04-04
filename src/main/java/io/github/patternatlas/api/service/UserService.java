package io.github.patternatlas.api.service;

import io.github.patternatlas.api.entities.candidate.Candidate;
import io.github.patternatlas.api.entities.user.role.Privilege;
import io.github.patternatlas.api.entities.user.role.Role;
import io.github.patternatlas.api.entities.user.UserEntity;
import io.github.patternatlas.api.rest.model.user.RoleModel;
import io.github.patternatlas.api.rest.model.user.RoleModelRequest;
import io.github.patternatlas.api.rest.model.user.UserModel;
import io.github.patternatlas.api.rest.model.user.UserModelRequest;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity saveUser(UserEntity user);


    @PreAuthorize(value = "hasGlobalPermission(@PC.USER_CREATE)")
    UserEntity createUser(UserModelRequest userModelRequest);

    @PostFilter(value = "hasGlobalPermission(@PC.USER_READ_ALL) " +
            "or (hasGlobalPermission(@PC.USER_READ) and filterObject.id.equals(loggedInUUID()))")
    List<UserEntity> getAllUsers();

    @PreAuthorize(value = "hasGlobalPermission(@PC.USER_READ_ALL)" +
            "or (hasGlobalPermission(@PC.USER_READ) and #userId.equals(loggedInUUID()))")
    UserEntity getUserById(UUID UserId);

    @PreAuthorize(value = "hasGlobalPermission(@PC.USER_EDIT_ALL)")
    UserEntity updateUser(UUID userId, UserModelRequest userModelRequest);

    @PreAuthorize(value = "hasGlobalPermission(@PC.USER_DELETE_ALL)" +
            "or (hasGlobalPermission(@PC.USER_DELETE) and #userId.equals(loggedInUUID()))")
    void deleteUser(UUID UserId);

    /**
     * Checks if a user has one of the supplied privileges, without querying all
     * available roles and privileges
     * @param userId
     * @param privileges
     * @return true if one of the privileges is present for the user
     */
    boolean hasAnyPrivilege(UUID userId, String ... privileges);

    /** Role */
    List<Role> getAllRoles();
    List<Role> getAllPlatformRoles();
    List<Role> getAllAuthorRoles();
    List<Role> getAllRolesFromEntity(UUID entityId);
    List<Privilege> getAllPlatformPrivileges();
    List<Privilege> getAllPrivilegesFromEntity(UUID entityId);

    @PreAuthorize(value = "hasGlobalPermission(@PC.USER_EDIT_ALL)")
    Role updateRole(UUID roleId, UUID privilegeId, RoleModelRequest roleModelRequest);
}