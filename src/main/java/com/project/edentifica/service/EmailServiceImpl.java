package com.project.edentifica.service;

import com.project.edentifica.model.Email;
import com.project.edentifica.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService{
    @Autowired
    EmailRepository emailDAO;

    /**
     * @param email email object to be inserted
     * @return Optional of email.
     */
    @Override
    public Optional<Email> insert(Email email) {

        //I assign the id automatically.
        if(email.getId() == null){
            email.setId(UUID.randomUUID().toString());
        }

        return Optional.of(emailDAO.insert(email));
    }

    /**
     * @param email String of email to find.
     * @return Optional of email.
     */
    @Override
    public Optional<Email> findByEmail(String email) {
        return emailDAO.findByEmail(email);
    }
}
