package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.dto.request.role.RoleAssignDto;
import com.example.electronic_numbering.domain.dto.request.role.RoleDto;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.entity.role.RoleEntity;
import com.example.electronic_numbering.exception.RequestValidationException;
import com.example.electronic_numbering.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping("/create")
//    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    public StandardResponse<RoleEntity> createRole(
            @Valid @RequestBody RoleDto roleDto,
            BindingResult bindingResult
            )throws RequestValidationException {
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return roleService.save(roleDto);
    }

    @GetMapping("/get-role")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN' or 'ADMIN')")
    public StandardResponse<RoleEntity> getRole(
            @RequestParam String name
    ){
        return roleService.getRole(name);
    }

    @PutMapping("/add-permissions-to-role")
//    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    public StandardResponse<RoleEntity> update(
            @RequestBody RoleDto roleDto
    ){
        return roleService.update(roleDto);
    }

    @PostMapping("/assign-role-permissions-to-user")
//    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    public StandardResponse<String> assignRoleToUser(
            @RequestBody RoleAssignDto roleAssignDto,
            Principal principal
    ) {
        return roleService.assignRoleToUser(roleAssignDto, principal);
    }

    @PostMapping("/add-permissions-to-user")
//    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    public StandardResponse<String> assignPermissionsToUser(
            @RequestBody RoleAssignDto roleAssignDto
    ) {
        return roleService.addPermissionsToUser(roleAssignDto);
    }
}
