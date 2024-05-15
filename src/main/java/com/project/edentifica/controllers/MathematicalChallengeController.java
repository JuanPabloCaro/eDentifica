package com.project.edentifica.controllers;

import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.service.IMathematicalChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("edentifica/mathematical_challenges")
public class MathematicalChallengeController {
    @Autowired
    private IMathematicalChallengeService mathematicalChallengeService;


    /**
     * @param mathematicalChallenge MathematicalChallenge object to be inserted.
     * @return ResponseEntity of MathematicalChallenge object.
     */
    @PostMapping("/insert")
    public ResponseEntity<MathematicalChallenge> insertMathematicalChallenge(@RequestBody MathematicalChallenge mathematicalChallenge)
    {
        Optional<MathematicalChallenge> mathematicalChallengeInserted;
        ResponseEntity<MathematicalChallenge> response;

        mathematicalChallengeInserted= mathematicalChallengeService.insert(mathematicalChallenge);

        if(mathematicalChallengeInserted.isPresent()){
            response = new ResponseEntity<>(mathematicalChallengeInserted.get(), HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param id String of Object to be deleted
     * @return ResponseEntity of boolean
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMathematicalChallenge(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<MathematicalChallenge> mathematicalChallengeFounded = mathematicalChallengeService.findById(id);

        if(mathematicalChallengeFounded.isPresent()){
            mathematicalChallengeService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @return ResponseEntity of boolean
     */
    @DeleteMapping("/deleteexpired")
    public ResponseEntity<Boolean> deleteExpiredMathematicalChallenge(){
        ResponseEntity<Boolean> response;
            if(mathematicalChallengeService.deleteExpiredMathematicalChallenges()>0){
                response = new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(false, HttpStatus.OK);
            }

        return response;
    }


    /**
     * @param id String representing the mathematicalChallenge's id to be found.
     * @return ResponseEntity of MathematicalChallenge object
     */
    @GetMapping("/get")
    public ResponseEntity<MathematicalChallenge> getMathematicalChallengeById(@RequestParam("id") String id)
    {
        ResponseEntity<MathematicalChallenge> response;
        Optional<MathematicalChallenge> mathematicalChallengeFounded = mathematicalChallengeService.findById(id);

        if(mathematicalChallengeFounded.isPresent()){
            response= new ResponseEntity<>(mathematicalChallengeFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }
}
