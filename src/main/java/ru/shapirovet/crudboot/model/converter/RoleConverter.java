package ru.shapirovet.crudboot.model.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.shapirovet.crudboot.model.Role;
import ru.shapirovet.crudboot.service.RoleService;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleService roleService;

    @Override
    public Role convert(String id) {
        System.out.println("Trying to convert id = " + id + " into a role");

        Long parseId = Long.parseLong(id);

        return roleService.getRoleById(parseId);
    }
}