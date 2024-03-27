package com.project.edentifica.controlador;

import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.model.Phone;
import com.project.edentifica.service.IMathematicalChallengeService;
import com.project.edentifica.service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("edentifica/phones")
public class PhoneController {
    @Autowired
    private IPhoneService phoneService;


    /**
     * @param phone Phone object to be inserted.
     * @return Phone object.
     */
    @PostMapping("/insert")
    public ResponseEntity<Phone> insertPhone(@RequestBody Phone phone)
    {
        Optional<Phone> phoneInserted;
        ResponseEntity<Phone> response;

        phoneInserted= phoneService.insert(phone);

        if(phoneInserted.isPresent()){
            response = new ResponseEntity<>(phoneInserted.get(), HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
