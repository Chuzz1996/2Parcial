/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.persistence;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public interface WordsRepository {

    public List<String> loadAllWords();
    
    
    
}
