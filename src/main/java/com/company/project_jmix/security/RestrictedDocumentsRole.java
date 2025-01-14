package com.company.project_jmix.security;

import com.company.project_jmix.entity.Document;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "RestrictedDocumentsRole", code = RestrictedDocumentsRole.CODE)
public interface RestrictedDocumentsRole {
    String CODE = "restricted-documents-role";

    @JpqlRowLevelPolicy(entityClass = Document.class,
            join = "join {E}.project proj",
            where = "proj.manager.id = :current_user_id or {E}.createdBy = :current_user_username"
    )

    void restrictDocumentsByProjectManager();
}