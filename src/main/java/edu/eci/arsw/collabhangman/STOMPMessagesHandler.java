/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman;

import edu.eci.arsw.collabhangman.model.game.entities.HangmanLetterAttempt;
import edu.eci.arsw.collabhangman.model.game.entities.HangmanWordAttempt;
import edu.eci.arsw.collabhangman.services.GameServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 2105409
 */
@Controller
public class STOMPMessagesHandler {
	
	@Autowired
	SimpMessagingTemplate msgt;
        
        @Autowired
        GameServices gameServices;
        
    
	@MessageMapping("/wupdate.{gameid}")    
	public void handlePointEvent(HangmanWordAttempt hwa,@DestinationVariable Integer gameid) throws Exception {
            boolean win=gameServices.guessWord(hwa.getUsername(),gameid, hwa.getWord());
            System.out.println("NO");
            if(win){
                System.out.println("PASO");
                msgt.convertAndSend("/winner."+gameid,hwa);
            }
	}
}